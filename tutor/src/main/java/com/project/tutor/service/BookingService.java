package com.project.tutor.service;

import com.project.tutor.dto.BookingDTO;
import com.project.tutor.request.BookingRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BookingService {
    public boolean addBooking (List<Map<String, Integer>> listTutorMaps);
    public boolean deleteBooking (int bookingId);

    public boolean updateBooking (int bookingId , BookingRequest request);

    public List<BookingDTO> getAllListBooking ();

    public BookingDTO getBookingById ();

}
