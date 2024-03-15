package com.project.tutor.exception;

import com.project.tutor.respone.ResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handlerException (RuntimeException exception){
        ResponseData response = new ResponseData();

        response.setStatus(1001);
        response.setMsg(exception.getMessage());

        return ResponseEntity.badRequest().body(response);
    }
}
