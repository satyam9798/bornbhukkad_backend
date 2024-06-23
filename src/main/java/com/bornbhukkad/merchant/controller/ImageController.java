package com.bornbhukkad.merchant.controller;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bornbhukkad.merchant.Service.ImageService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = {"Authorization", "Content-Type"})
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    @CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = {"Authorization", "Content-Type"})
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile file) {
        String imagePath = imageService.saveImage(file);
        Map<String, String> response = new HashMap<>();
        response.put("path", imagePath);
        return ResponseEntity.ok(response);
    }
    @CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = {"Authorization", "Content-Type"})
    @GetMapping(value = "/images/**", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@RequestParam("path") String path) {
        byte[] imageData = imageService.getImageData(path);
        return ResponseEntity.ok().body(imageData);
    }
}
