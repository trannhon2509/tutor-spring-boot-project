package com.project.tutor.controller;

import com.project.tutor.many.dto.SubjectManyDTO;
import com.project.tutor.request.SubjectRequest;
import com.project.tutor.respone.ResponeDataAuth;
import com.project.tutor.respone.ResponseData;
import com.project.tutor.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    public static ResponseData data = new ResponseData();
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

        data.setData(checkDelete ? true : false);
        data.setMsg(checkDelete ? "Delete subject successfully" : "Delete subject not successfully");
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

        data.setData(checkUpdateSuccess ? true : false);
        data.setMsg(checkUpdateSuccess ? "Update subject successfully" : "Update subject fail!");
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
}
