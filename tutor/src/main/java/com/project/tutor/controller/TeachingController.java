package com.project.tutor.controller;

import com.project.tutor.request.TeachingRequest;
import com.project.tutor.respone.ResponeData;
import com.project.tutor.service.TeachingService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/teaching")
@RestController
public class TeachingController {
    public static ResponeData data = new ResponeData();

    @Autowired
    TeachingService teachingService;

    @GetMapping
    public ResponseEntity<?> getAllTeaching() {
        return new ResponseEntity<>(teachingService.getAllListTeaching(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTeaching(@RequestBody  TeachingRequest request) {
        boolean checkAddTeachingSuccess = teachingService.addTeaching(request);
        data.setData(checkAddTeachingSuccess ? true : false);
        data.setMsg(checkAddTeachingSuccess ? "Add teaching success" : "Add teaching fail!");

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/delete/{teachingId}")
    public ResponseEntity<?> deleteTeaching (@PathVariable int teachingId){
        boolean checkDeleteTeachingSuccess = teachingService.deleteTeaching(teachingId);
        data.setData(checkDeleteTeachingSuccess ? true : false);
        data.setMsg(checkDeleteTeachingSuccess ? "Delete teaching success" : "Delete teaching fail!");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/update/{teachingId}")
    public ResponseEntity<?> updateTeaching (@PathVariable int teachingId , @RequestBody TeachingRequest request ){
        boolean checkUpdateTeachingSuccess = teachingService.deleteTeaching(teachingId);
        data.setData(checkUpdateTeachingSuccess ? true : false);
        data.setMsg(checkUpdateTeachingSuccess ? "Update teaching success" : "Update teaching fail!");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
