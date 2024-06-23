package com.bornbhukkad.merchant.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//Service
@Service
public class ImageService {
	private final String tempDir = System.getProperty("java.io.tmpdir");

    public String saveImage(MultipartFile file) {
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        String fullPath = Paths.get(tempDir, fileName).toString();

        try {
            file.transferTo(new File(fullPath));
        } catch (IOException e) {
            throw new RuntimeException("Error saving image", e);
        }

//        storeImagePathInMongoDB(fullPath);

        return fullPath;
    }
	


    public byte[] getImageData(String path) {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Error reading image data", e);
        }
    }

 private String generateUniqueFileName(String originalFileName) {

     return UUID.randomUUID().toString() + "." + getFileExtension(originalFileName);
 }

 private String getFileExtension(String fileName) {
     return fileName.substring(fileName.lastIndexOf(".") + 1);
 }

 private void storeImagePathInMongoDB(String path) {

 }
}