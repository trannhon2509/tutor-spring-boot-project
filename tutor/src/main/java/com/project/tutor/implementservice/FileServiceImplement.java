package com.project.tutor.implementservice;

import com.project.tutor.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Service
public class FileServiceImplement implements FileService {

    @Value("${spring.fileUpload.rootPath}")
    private String rootPath;

    private Path root;

    @Override
    public void init() {
        try {
            root = Paths.get(rootPath);
            if (Files.notExists(root)) {
                Files.createDirectories(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't initialize folder" + root);
        }
    }



    @Override
    public boolean uploadFile(MultipartFile file) {
        try {
            init();
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Can't save file!");
        }
    }

    @Override
    public boolean uploadMultiFile(MultipartFile[] files) {
        try {
            init();
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }
                Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            }

            return true;
        } catch (IOException e) {
            throw new RuntimeException("Can't save files!");
        }
    }

    @Override
    public Resource downloadFile(String fileName) {
        try {
            init();
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                return new ByteArrayResource(new byte[0]);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't load file!");
        }
    }

    @Override
    public boolean deleteFile(String fileName) {
        try {
            Path file = root.resolve(fileName);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file!", e);
        }
    }
}
