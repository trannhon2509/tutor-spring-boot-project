package com.project.tutor.controller;

import com.project.tutor.model.FeedBack;
import com.project.tutor.request.FeedBackRequest;
import com.project.tutor.respone.ResponseData;
import com.project.tutor.service.FeedbackService;
import com.project.tutor.warpper.FeedbackRequestWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    public static ResponseData data = new ResponseData();
    @Autowired
    FeedbackService feedbackService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllFeedback (){
        return new ResponseEntity<>(feedbackService.getAllFeedback(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFeeback (@RequestBody FeedBackRequest request){
        boolean checkAddFeedback = feedbackService.addFeedback(request);
        data.setData(checkAddFeedback ? true : false);
        data.setMsg(checkAddFeedback  ? "Add feeback success" : "Add feedback fail!");
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{feedbackId}")
    public ResponseEntity<?> deleteFeedbackById (@PathVariable int feedbackId){
        boolean checkDeleteFeedback = feedbackService.deleteFeedback(feedbackId);
        data.setData(checkDeleteFeedback ? true : false);
        data.setMsg(checkDeleteFeedback ? "Delete feedback sucess" : "Delete feedback fail!");
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
}
