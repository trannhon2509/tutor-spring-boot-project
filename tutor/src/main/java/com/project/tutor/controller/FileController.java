package com.project.tutor.controller;

import com.project.tutor.respone.ResponeData;
import com.project.tutor.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
public class FileController {
    public static ResponeData data  = new ResponeData();

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile (@RequestParam MultipartFile[] files){
        try {
            List<String> fileNames = new ArrayList<>();
            Arrays.asList(files).stream().forEach(file ->{
                fileService.uploadFile(file);
                fileNames.add(file.getOriginalFilename());
            });
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Upload files success","fileNames",fileNames));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(Map.of("message","Upload files fail!"));
        }

    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename) {
        Resource resource = fileService.downloadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}
