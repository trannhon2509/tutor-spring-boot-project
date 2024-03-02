package com.project.tutor.controller;

import com.project.tutor.respone.ResponeData;
import com.project.tutor.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RequestMapping("/booking")
@RestController
public class BookingController {
    public static ResponeData data = new ResponeData();

    @Autowired
    BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<?> addBooking(@RequestBody List<Map<String, Integer>> listTutors) {
        try {
            boolean checkBooking = bookingService.addBooking(listTutors);
            return new ResponseEntity<>(checkBooking, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Booking failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
