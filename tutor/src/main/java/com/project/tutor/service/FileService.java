package com.project.tutor.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service

public interface FileService {
    public void init();
    public boolean saveFile (MultipartFile file);
    public Resource loadFile (String fileName);
    public boolean deleteFile (String fileName);
}
