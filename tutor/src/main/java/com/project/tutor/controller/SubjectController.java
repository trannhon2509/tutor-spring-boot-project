package com.project.tutor.controller;

import com.project.tutor.dto.SubjectDTO;
import com.project.tutor.many.dto.SubjectManyDTO;
import com.project.tutor.request.SubjectRequest;
import com.project.tutor.respone.ResponeData;
import com.project.tutor.service.SubjectService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    public  static ResponeData data = new ResponeData();
    @Autowired
    SubjectService subjectService;

    @GetMapping
    public ResponseEntity<?> getAllSubject (){
        return new ResponseEntity<>(subjectService.listSubject(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById (@PathVariable int id){
        SubjectManyDTO SubjectManyDTO = subjectService.getSubjectById(id);
        return  new ResponseEntity<>(SubjectManyDTO,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSubjectById (@PathVariable int id){
        boolean checkDelete = subjectService.deleteSubject(id);
        if(checkDelete) {
            data.setData(true);
            data.setMsg("Delete subject successfully");
        }else{
            data.setData(false);
            data.setMsg("Delete subject not successfully");
        }
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addSubject (@RequestBody  SubjectRequest request){
        SubjectRequest subjectRequest = subjectService.addSubject(request);
        if(subjectRequest == null){
            data.setData(true);
            data.setMsg("Add successfully");
        }else{
            data.setData(false);
            data.setMsg("Add fail");
        }
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSubject (@PathVariable int id , @RequestBody SubjectRequest request){
        boolean checkUpdateSuccess = subjectService.updateSubject(id , request);
        if(checkUpdateSuccess){
            data.setData(true);
            data.setMsg("Update successfully");
        }else{
            data.setData(false);
            data.setMsg("Update fail");
        }
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
}
