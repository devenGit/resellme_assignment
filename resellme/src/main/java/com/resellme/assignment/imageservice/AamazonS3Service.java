package com.resellme.assignment.imageservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class AamazonS3Service implements FileStorageService {

	@Autowired
	private AmazonS3Client awsS3Client;

	@Override
	public List<String> uploadFile(List<MultipartFile> multipartFiles) {
		List<String> urls = new ArrayList<>();
		for (MultipartFile file : multipartFiles) {
			String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

			String key = UUID.randomUUID().toString() + "." + filenameExtension;

			ObjectMetadata metaData = new ObjectMetadata();
			metaData.setContentLength(file.getSize());
			metaData.setContentType(file.getContentType());

			try {
				awsS3Client.putObject("product-image-bucket", key, file.getInputStream(), metaData);
			} catch (IOException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"An exception occured while uploading the image");
			}

			awsS3Client.setObjectAcl("product-image-bucket", key, CannedAccessControlList.PublicRead);
			urls.add(awsS3Client.getResourceUrl("product-image-bucket", key));
		}
		return urls;
	}

	public void uploadImages(Map<String, String> map) {

	}
}
