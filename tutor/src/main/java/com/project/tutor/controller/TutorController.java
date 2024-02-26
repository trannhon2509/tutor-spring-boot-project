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
    public ResponseEntity<?> getAllTutor (){
       return new ResponseEntity<>(tutorService.getAllTutor(), HttpStatus.OK);
   }
   @GetMapping("/{id}")
    public ResponseEntity<?> getTutorById (@PathVariable int id){
       TutorManyDTO tutorManyDTO = tutorService.getTutorById(id);
       return new ResponseEntity<>(tutorManyDTO,HttpStatus.OK);
   }
   @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTutorById (@PathVariable int id){
       boolean checkDeleteTutor = tutorService.deleteTutorById(id);
       if(checkDeleteTutor){
           data.setData(true);
           data.setMsg("Delete tutor success");
       }else{
           data.setData(false);
           data.setMsg("Delete tutor fail");
       }
       return new ResponseEntity<>(data,HttpStatus.OK);
   }

   @PostMapping("/add")
    public ResponseEntity <?> addTutor (@RequestParam MultipartFile file, @RequestParam TutorRequest request){
       TutorRequest tutorRequest = tutorService.addTutor(file,request);
        return new ResponseEntity<>(tutorRequest , HttpStatus.OK);
   }

}
