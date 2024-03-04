package com.project.tutor.controller;

import com.project.tutor.model.User;
import com.project.tutor.request.UserRequest;
import com.project.tutor.respone.ResponeDataAuth;
import com.project.tutor.respone.ResponseData;
import com.project.tutor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    public static ResponseData data = new ResponseData();
    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        boolean checkDelete = userService.deleteUserById(id);
        data.setData(checkDelete ? true : false);
        data.setMsg(checkDelete ? "Delete user success" : "Delete user fail!");

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity <?> updateUser (@PathVariable int id , @RequestBody UserRequest request){
           boolean checkUpdateUser = userService.updateUser(id , request);

        data.setData(checkUpdateUser ? true : false);
        data.setMsg(checkUpdateUser ? "Update user success" : "Update user fail!");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfileUser(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        if (user != null) {
            data.setData(true);
            data.setMsg("Success");
        } else {
            data.setMsg("Fail!");
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
