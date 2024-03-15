package com.project.tutor.controller;

import com.project.tutor.request.UserRequest;
import com.project.tutor.respone.ResponeDataAuth;
import com.project.tutor.respone.ResponseData;
import com.project.tutor.secutiry.JwtProvider;
import com.project.tutor.service.TutorService;
import com.project.tutor.service.UserService;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    public static ResponseData data = new ResponseData();
    @Autowired
    UserService userService;

    @Autowired
    TutorService tutorService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> singup(@RequestBody UserRequest request) {
        boolean checkSignup = userService.signup(request);
        data.setData(checkSignup ? true : false);
        data.setMsg(checkSignup ? "Signup sucess" : "Signup fail!");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signup(@RequestParam String username , @RequestParam  String password) {
        String token = jwtProvider.generateToken(username);
        boolean checkLogin = userService.signin(username,password);
        data.setData(checkLogin ? token : null);
        data.setMsg(checkLogin ? "Sign in success" : "Sign in fail!");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<?> activeAccount(@RequestParam String email, @RequestParam String activeCode) {
        boolean checkActive = userService.activeAccount(email, activeCode);

        data.setData(checkActive ? true : false);
        data.setMsg(checkActive ? "Active success" : "Active fail!");

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email, @RequestParam String username) {
        boolean checkForgotPassword = userService.forgotPassword(email);

        data.setData(checkForgotPassword ? true : false);
        data.setMsg(checkForgotPassword ? "Send mail forgot password success" : "Send mail forgot password fail!");
        return new ResponseEntity<>(data, HttpStatus.OK);

    }

    @GetMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestBody UserRequest request) {
        boolean checkResetPassword = userService.resetPassword(email, request);

        data.setData(checkResetPassword ? true : false);
        data.setMsg(checkResetPassword ? "Send email reset password success" : "Send email reset password fail!");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


}
