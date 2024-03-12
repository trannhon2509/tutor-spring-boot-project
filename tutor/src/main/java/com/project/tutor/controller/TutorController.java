package com.project.tutor.controller;

import com.project.tutor.many.dto.TutorManyDTO;
import com.project.tutor.request.TutorRequest;
import com.project.tutor.respone.ResponeDataAuth;
import com.project.tutor.respone.ResponseData;
import com.project.tutor.service.FileService;
import com.project.tutor.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/tutor")
public class TutorController {
    public static ResponseData data = new ResponseData();
    @Autowired
    TutorService tutorService;

    @Autowired
    FileService fileService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllTutor(@RequestParam int page , @RequestParam int record) {
        return new ResponseEntity<>(tutorService.getAllTutor(page,record), HttpStatus.OK);
    }

    @GetMapping("/list/approved")
    public ResponseEntity<?> getAllListApprovedTutor (){
        return new ResponseEntity<>(tutorService.getListTutorApprovedFalse() , HttpStatus.OK);
    }

    @GetMapping("/search-and-sort")
    public ResponseEntity<?> getAllTutorAndSearchAndSort (@RequestParam String title , @RequestParam int page , @RequestParam int record){
        return new ResponseEntity<>(tutorService.getAllTutorSearchAndPagingAndSort(title,page,record),HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getTutorById(@PathVariable int id) {
        TutorManyDTO tutorManyDTO = tutorService.getTutorById(id);
        return new ResponseEntity<>(tutorManyDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTutorById(@PathVariable int id) {
        boolean checkDeleteTutor = tutorService.deleteTutorById(id);

        data.setData(checkDeleteTutor ? true : false);
        data.setMsg(checkDeleteTutor ? "Delete tutor success" : "Delete tutor fail");

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTutor(@RequestPart MultipartFile[] files, @RequestPart TutorRequest request) {

        TutorRequest addTutor = tutorService.addTutor(files, request);
        if (addTutor == null) {
            data.setData(true);
            data.setMsg("Add Tutor success");
        } else {
            data.setData(false);
            data.setMsg("Add Tutor fail!");
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateTutor(@RequestPart MultipartFile[] files, @PathVariable int id, @RequestPart TutorRequest request) {
        boolean checkUpdate = tutorService.updateTutor(files, id, request);
        data.setData(checkUpdate ? true : false);
        data.setMsg(checkUpdate ? "Update tutor success" : "Update tutor fail!");

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/approve/{tutorId}")
    public ResponseEntity<?> approveTutor (@PathVariable int tutorId){
        try {
            boolean checkApproveTutor = tutorService.approveTutor(tutorId);
            data.setData(checkApproveTutor);
            data.setMsg(checkApproveTutor ? "Approved tutor success" : "Approved tutor fail!");
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            data.setData(false);
            data.setMsg(e.getMessage());
            return new ResponseEntity<>(data, HttpStatus.UNAUTHORIZED);
        }
    }
}
