package com.surplus.fwm.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.surplus.fwm.dto.PaginationDataDto;
import com.surplus.fwm.dto.PaginationDto;
import com.surplus.fwm.dto.UserFilterWithPaginationDto;
import com.surplus.fwm.dto.UserListResponseDto;
import com.surplus.fwm.mapper.CustomMapper;
import com.surplus.fwm.model.User;
import com.surplus.fwm.repository.custom.UserRepositoryCustom;
import com.surplus.fwm.utility.Utility;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private CustomMapper customMapper;

	@Override
	public PaginationDto getUserListByFilterWithPagination(UserFilterWithPaginationDto filterWithPagination) {
		String countQuery = "SELECT count(*) from user_details t";
		String query = "SELECT t.* from user_details t";
		String addableQuery = " where t.role != 0";
		boolean flag = true;
		boolean whereFlag = false;
		if (filterWithPagination.getFilter().getKeyword() != null
				&& !filterWithPagination.getFilter().getKeyword().isEmpty()) {
			addableQuery += Utility.addWhere(whereFlag) + Utility.addANDOrOR(flag) + " t.full_name like '%"
					+ filterWithPagination.getFilter().getKeyword() + "%' or t.mobile_number like '%"
					+ filterWithPagination.getFilter().getKeyword() + "%'";
			flag = true;
			whereFlag = false;
		}

		Query queryString = entityManager.createNativeQuery(countQuery + addableQuery);
		int totalCounts = ((Number) queryString.getSingleResult()).intValue();
		PaginationDataDto paginationDataDto = Utility.getPaginationData(totalCounts,
				filterWithPagination.getPagination());
		String limitQuery = " order by t.id desc limit " + paginationDataDto.getFrom() + ","
				+ paginationDataDto.getTo();
		queryString = entityManager.createNativeQuery(query + addableQuery + limitQuery, User.class);
		List<User> userList = queryString.getResultList();
		List<UserListResponseDto> dataList = customMapper.userListToUserListResponseDtoList(userList);
		filterWithPagination.getPagination().setData(dataList);
		filterWithPagination.getPagination().setTotalCount(totalCounts);
		filterWithPagination.getPagination().setTotalPages(paginationDataDto.getTotalPages());
		return filterWithPagination.getPagination();
	}
}
