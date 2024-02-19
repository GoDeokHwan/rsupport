package com.example.rsupport.global.support.file.impl;

import com.example.rsupport.global.exception.ApiException;
import com.example.rsupport.global.exception.ResponseMessage;
import com.example.rsupport.global.support.file.FileSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Component
public class FileSupportImpl implements FileSupport {
    private final Path fileLocation;

    public FileSupportImpl() {
        this.fileLocation = Paths.get("./attach/").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileLocation);
        } catch (Exception ex) {
            log.warn("{}", ex);
            throw new ApiException(ResponseMessage.FILE_FOLDER_MAKE_EXCEPTION);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new ApiException(ResponseMessage.FILE_FORMATTER_EXCEPTION, fileName);
            }
            String timestamp = String.valueOf(System.currentTimeMillis());
            Path targetDirectory = this.fileLocation.resolve(timestamp);
            Files.createDirectories(targetDirectory);
            Path targetLocation = targetDirectory.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return targetLocation.toString();
        } catch (IOException ex) {
            log.warn("{}", ex);
            throw new ApiException(ResponseMessage.FILE_NOT_SAVE_EXCEPTION, fileName);
        }
    }

    @Override
    public void deleteFile(String filePath) {
        Path targetPath = Paths.get(filePath).normalize();
        try {
            Files.delete(targetPath);
            log.info("파일이 성공적으로 삭제되었습니다: {}", filePath);
        } catch (IOException ex) {
            log.warn("파일 삭제 중 오류가 발생했습니다: {}", filePath, ex);
            throw new ApiException(ResponseMessage.FILE_DELETE_EXCEPTION, filePath);
        }
    }
}
