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

@RestController
@RequestMapping("/api/file")
public class FileController {
    public static ResponeData data  = new ResponeData();

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile (@RequestParam MultipartFile file){
        boolean checkUpload = fileService.saveFile(file);
        if(checkUpload){
            data.setData(true);
            data.setMsg("Update file success");
        }else{
            data.setData(false);
            data.setMsg("Update file fail");
        }
        return new ResponseEntity<>(data , HttpStatus.OK);
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename) {
        Resource resource = fileService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}
