package com.example.saveImage.entity.service;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.saveImage.ImageUtils.ImageUtils;
import com.example.saveImage.entity.ImageData;
import com.example.saveImage.repository.StorageRepository;

@Service
public class StorageService {
	
	@Autowired
	private StorageRepository repo;
	
	public String uploadImage(MultipartFile file) throws IOException {
	//	System.out.println("BYTES"+ImageUtils.compress(file.getBytes()));
		ImageData imageDate = repo.save(ImageData.builder()
				.name(file.getOriginalFilename())
				.type(file.getContentType())
				.imageData(ImageUtils.compress(file.getBytes())).build());
		if(imageDate != null ) {
			return "file uploaded successfully : "+file.getOriginalFilename();
		}
		
		return null;
	}
	
	public byte[] downloadImage(String fileName) throws DataFormatException {
		Optional<ImageData> dbImage = repo.findByName(fileName);
		 byte[] images = ImageUtils.decompress(dbImage.get().getImageData());
		return images;
	}

}
