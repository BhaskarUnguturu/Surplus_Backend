package com.fwm.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;

@Service
public interface IFileService {

	void uploadFile(MultipartFile file, String fileName, String type, ApiResponseDtoBuilder apiResponseDtoBuilder);

	Resource loadFileAsResource(String fileName, String type, String property);

	void uploadMultipleFile(MultipartFile[] files, String type, String fileName,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

}
