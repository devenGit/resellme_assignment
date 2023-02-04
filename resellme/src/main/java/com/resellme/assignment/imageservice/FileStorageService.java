package com.resellme.assignment.imageservice;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	List<String> uploadFile(List<MultipartFile> multipartFiles);
	void uploadImages(Map<String, String> map);
}
