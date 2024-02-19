package com.example.rsupport.domain.common.service.impl;

import com.example.rsupport.domain.common.service.FileService;
import com.example.rsupport.global.support.file.FileSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileSupport fileSupport;
    @Override
    public String saveFile(MultipartFile file) {
        return fileSupport.storeFile(file);
    }
}
