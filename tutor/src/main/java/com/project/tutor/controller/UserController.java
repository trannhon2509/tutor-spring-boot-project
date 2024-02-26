package com.project.tutor.controller;

import com.project.tutor.implementservice.UserImplementService;
import com.project.tutor.model.User;
import com.project.tutor.request.UserRequest;
import com.project.tutor.respone.ResponeData;
import com.project.tutor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    public static ResponeData data = new ResponeData();
    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        boolean checkDelete = userService.deleteUserById(id);
        if (checkDelete) {
            data.setMsg("Delete Success");
            data.setData(true);
        } else {
            data.setMsg("Delete Fail");
            data.setData(false);
        }

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping("update/user/{id}")
    public ResponseEntity <?> updateUser (@PathVariable int id , @RequestBody UserRequest request){
           boolean checkUpdateUser = userService.updateUser(id , request);
        if (checkUpdateUser) {
            data.setMsg("Update Success");
            data.setData(true);
        } else {
            data.setMsg("Update Fail");
            data.setData(false);
        }

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfileUser(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        if (user != null) {
            data.setData(true);
            data.setMsg("Success");
        } else {
            data.setMsg("Fail");
        }

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
