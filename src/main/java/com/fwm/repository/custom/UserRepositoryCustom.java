package com.fwm.repository.custom;

import com.fwm.dto.PaginationDto;
import com.fwm.dto.UserFilterWithPaginationDto;

public interface UserRepositoryCustom {
	PaginationDto getUserListByFilterWithPagination(UserFilterWithPaginationDto filterWithPagination);
}
