package com.project.tutor.controller;

import com.project.tutor.many.dto.TutorManyDTO;
import com.project.tutor.request.TutorRequest;
import com.project.tutor.respone.ResponeData;
import com.project.tutor.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/tutor")
public class TutorController {
    public static ResponeData data = new ResponeData();
    @Autowired
    TutorService tutorService;

    @GetMapping
    public ResponseEntity<?> getAllTutor() {
        return new ResponseEntity<>(tutorService.getAllTutor(), HttpStatus.OK);
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
    public ResponseEntity<?> addTutor(@RequestParam MultipartFile file,
                                      @RequestParam String cityTeach, @RequestParam String fullName,
                                      @RequestParam String gender, @RequestParam String dateOfBirth,
                                      @RequestParam String address, @RequestParam String phoneNumber,
                                      @RequestParam String email, @RequestParam String voice,
                                      @RequestParam String major, @RequestParam String academicLevel,
                                      @RequestParam String description,
                                      @RequestParam String issued,
                                      @RequestParam String shoolTeacherOrTeacher, @RequestParam int numberTeachOfWeek,
                                      @RequestParam double salaryRequest) {
        TutorRequest tutorRequest = tutorService.addTutor(file, cityTeach, fullName, gender, dateOfBirth, address, phoneNumber, email, voice, major,
                academicLevel, description, issued, shoolTeacherOrTeacher, numberTeachOfWeek, salaryRequest);

        return new ResponseEntity<>(tutorRequest, HttpStatus.OK);
    }

}
