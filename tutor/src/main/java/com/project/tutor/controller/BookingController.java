package com.project.tutor.controller;

import com.project.tutor.respone.ResponeDataAuth;
import com.project.tutor.respone.ResponseData;
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


@RequestMapping("/api/booking")
@RestController
public class BookingController {
    public static ResponseData data = new ResponseData();

    @Autowired
    BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<?> addBooking(@RequestBody List<Map<String, Integer>> listTutors) {
            boolean checkBooking = bookingService.addBooking(listTutors);
            data.setData(checkBooking ? true : false);
            data.setMsg(checkBooking ? "Booking success" : "Booking fail!");
            return new ResponseEntity<>(checkBooking, HttpStatus.OK);
    }

}
