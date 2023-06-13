package com.fwm.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.fwm.dto.UserListResponseDto;
import com.fwm.dto.UserRequestDto;
import com.fwm.model.User;

@Mapper(componentModel = "spring")
public interface CustomMapper {

	User userRequestDtoToUser(UserRequestDto userRequestDto);

	List<UserListResponseDto> userListToUserListResponseDtoList(List<User> userList);


}