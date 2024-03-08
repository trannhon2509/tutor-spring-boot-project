package com.project.tutor.controller;

import com.project.tutor.request.BookingRequest;
import com.project.tutor.respone.ResponeDataAuth;
import com.project.tutor.respone.ResponseData;
import com.project.tutor.service.BookingService;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping("/api/booking")
@RestController
public class BookingController {
    public static ResponseData data = new ResponseData();

    @Autowired
    BookingService bookingService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllListBooking (){
        return new ResponseEntity<>(bookingService.getAllListBooking(),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBooking(@RequestBody List<Map<String, Integer>> listTutors) {
            boolean checkAddBooking = bookingService.addBooking(listTutors);
            data.setData(checkAddBooking ? true : false);
            data.setMsg(checkAddBooking ? "Booking success" : "Booking fail!");
            return new ResponseEntity<>(checkAddBooking, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<?> deleteBookingById (@PathVariable int bookingId){
        boolean checkDeleteBooking = bookingService.deleteBooking(bookingId);
        data.setData(checkDeleteBooking ? true : false);
        data.setMsg(checkDeleteBooking ? "Booking success" : "Booking fail!");
        return new ResponseEntity<>(checkDeleteBooking, HttpStatus.OK);
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<?> updateBooking(@PathVariable int bookingId , @RequestBody BookingRequest request){
        boolean checkUpdateBooking = bookingService.updateBooking(bookingId,request);
        data.setData(checkUpdateBooking ? true : false);
        data.setMsg(checkUpdateBooking ? "Update booking success" : "Update booking fail!");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
