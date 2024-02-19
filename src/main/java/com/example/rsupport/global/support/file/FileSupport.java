package com.example.rsupport.global.support.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileSupport {

    String storeFile(MultipartFile file);

    void deleteFile(String filePath);
}
