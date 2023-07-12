package com.example.brand.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Component
public class ImageSaveService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void saveBrandImage(AmazonS3Client amazonS3Client, MultipartFile img, String fileName) throws IOException {
        log.info("save file name : {}", fileName);
        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(img.getContentType());
        metadata.setContentLength(img.getSize());
        amazonS3Client.putObject(bucket,fileName,img.getInputStream(),metadata);
    }
}
