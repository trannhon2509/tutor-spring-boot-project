package com.project.tutor.controller;

import com.project.tutor.request.UserRequest;
import com.project.tutor.respone.ResponeData;
import com.project.tutor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
  public  static  ResponeData data = new ResponeData();
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> singup (@RequestBody UserRequest request){
        data = userService.signup(request);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signup (@RequestBody UserRequest request){
        data = userService.signin(request);
        return new ResponseEntity<>(data , HttpStatus.OK);
    }
    @GetMapping("/active")
    public ResponseEntity<?> activeAccount (@RequestParam String email , @RequestParam String activeCode){
        boolean checkActive = userService.activeAccount(email,activeCode);
        if(checkActive){
            data.setData(true);
            data.setMsg("Actice success");
        }else{
            data.setData(false);
            data.setMsg("Actice fail!");
        }
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
}
