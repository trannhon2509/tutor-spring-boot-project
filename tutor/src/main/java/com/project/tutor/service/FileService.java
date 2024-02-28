package com.project.tutor.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service

public interface FileService {
    public void init();
    public boolean uploadFile (MultipartFile file);
    public Resource downloadFile (String fileName);
    public boolean deleteFile (String fileName);

    public boolean uploadMutiparFile(List<MultipartFile> files);
}
