package com.surplus.fwm.repository.custom;

import com.surplus.fwm.dto.PaginationDto;
import com.surplus.fwm.dto.UserFilterWithPaginationDto;

public interface UserRepositoryCustom {
	PaginationDto getUserListByFilterWithPagination(UserFilterWithPaginationDto filterWithPagination);
}
