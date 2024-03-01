package com.project.tutor.controller;

import com.project.tutor.service.TeachingService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/teaching")
@RestController
public class TeachingController {

    @Autowired
    TeachingService teachingService;

    @GetMapping
    public ResponseEntity<?> getAllTeaching (){
        return new ResponseEntity<>(teachingService.getAllListTeaching(), HttpStatus.OK);
    }
}
