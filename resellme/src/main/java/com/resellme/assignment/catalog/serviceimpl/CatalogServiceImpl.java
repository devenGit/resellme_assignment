package com.resellme.assignment.catalog.serviceimpl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.resellme.assignment.brand.entity.Brand;
import com.resellme.assignment.brand.repository.BrandRepository;
import com.resellme.assignment.catalog.dto.CatalogResponseDto;
import com.resellme.assignment.catalog.dto.CatalogSaveDto;
import com.resellme.assignment.catalog.dto.exception.BrandNotFoundException;
import com.resellme.assignment.catalog.entity.Catalog;
import com.resellme.assignment.catalog.repository.CatalogRepository;
import com.resellme.assignment.catalog.service.CatalogService;
import com.resellme.assignment.imageservice.FileStorageService;
import com.resellme.assignment.product.ImageType;
import com.resellme.assignment.product.Product;
import com.resellme.assignment.product.ProductDto;
import com.resellme.assignment.product.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CatalogServiceImpl implements CatalogService {
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private CatalogRepository catalogRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Override
	public CatalogResponseDto saveCatalog(CatalogSaveDto catalogSaveDto) throws BrandNotFoundException {
		Optional<Brand> brand = brandRepository.findById(catalogSaveDto.getBrandId());
		if(brand.isPresent()) {
			Catalog catalog = new Catalog();
			catalog.setBrand(brand.get());
			catalog.setCatalogDescription(catalogSaveDto.getCatalogDescription());
			catalog.setCatalogTitle(catalogSaveDto.getCatalogTitle());
			catalog.setCreateDate(new Date());
			catalog = catalogRepository.save(catalog);
			Map<String, String> productImages = new HashMap<>();
			for(ProductDto dto : catalogSaveDto.getProducts()) {
				Product product = new Product();
				product.setPrice(dto.getPrice());
				product.setProductCode(dto.getProductCode());
				product.setCatalog(catalog);
				productRepository.save(product);
				productImages.put(dto.getProductCode(), dto.getImageUrl());
			}
			CompletableFuture.runAsync(() -> {
				for (Map.Entry<String, String> entrySet: productImages.entrySet()) {
					try {
						uploadImage(entrySet);
					} catch (IOException e) {
						log.warn("Image is not uploaded for "+entrySet.getKey());
						e.printStackTrace();
					}
				}
			});
			return CatalogResponseDto.builder().catalogId(catalog.getCatalogId()).build();
		}
		throw new BrandNotFoundException("Brand not found");
	}

	@Override
	public Page<Catalog> getCatalogs(int page, int size, Brand brand) {
		return catalogRepository.findByBrand(brand, PageRequest.of(page, size));	
	}

	public void uploadImage(Map.Entry<String, String> entrySet) throws IOException {
		URL imageUrl = new URL(entrySet.getValue());
		BufferedImage image = ImageIO.read(imageUrl);
		BufferedImage thumbnail = (BufferedImage) ImageIO.read(new File("test.jpg")).getScaledInstance(100, 100,
				BufferedImage.SCALE_SMOOTH);
		BufferedImage highResImg = resizeImage(image, 1920, 1080);
		List<MultipartFile> multipartFiles = new ArrayList<>();
		addMultiPartImage(image, multipartFiles);
		addMultiPartImage(thumbnail, multipartFiles);
		addMultiPartImage(highResImg, multipartFiles);
		List<String> s3ImageUrls = fileStorageService.uploadFile(multipartFiles);
		Product product = productRepository.findByProductCode(entrySet.getKey());
		ImageType imageType = new ImageType();
		imageType.setNormal(s3ImageUrls.get(0));
		imageType.setHighRes(s3ImageUrls.get(2));
		imageType.setThumbnail(s3ImageUrls.get(1));
		product.setImageType(imageType);
		productRepository.save(product);

	}
	private void addMultiPartImage(BufferedImage originalImage, List<MultipartFile> multipartFiles) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(originalImage,"jpg",byteArrayOutputStream);
		String fileName = "Deven"+new Date().getTime() + ".jpg";
		multipartFiles.add(new MockMultipartFile(fileName,fileName,"image/jpg",byteArrayOutputStream.toByteArray()));
		byteArrayOutputStream.flush();
		byteArrayOutputStream.close();
	}
	private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
	    Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
	    BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
	    outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
	    return outputImage;
	}
}
