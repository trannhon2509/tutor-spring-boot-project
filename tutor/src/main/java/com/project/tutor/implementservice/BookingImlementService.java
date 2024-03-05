package com.project.tutor.implementservice;

import com.project.tutor.mapper.Booking;
import com.project.tutor.model.Tutor;
import com.project.tutor.model.User;
import com.project.tutor.repository.BookingRepository;
import com.project.tutor.repository.TutorRepository;
import com.project.tutor.repository.UserRepository;
import com.project.tutor.secutiry.CustomUserDetails;
import com.project.tutor.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookingImlementService implements BookingService {


    @Autowired
    TutorRepository tutorRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;



   @Autowired
    CustomUserDetails customUserDetails;

    @Override
    public boolean addBooking(List<Map<String, Integer>> listTutorMaps) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            UserDetails userDetails = customUserDetails.loadUserByUsername(username);

            if(userDetails == null){
                throw new  BadCredentialsException("User not found");
            }

            User currentUser = userRepository.findByUsername(username);
            List<Booking> bookings = new ArrayList<>();
            for (Map<String, Integer> tutorMap : listTutorMaps) {
                Integer tutorId = tutorMap.get("id");
                if (tutorId != null) {
                    Tutor existingTutor = tutorRepository.findById(tutorId).orElse(null);
                    if (existingTutor != null) {
                        Booking newBooking = new Booking();
                        newBooking.setUser(currentUser);
                        newBooking.setTutor(existingTutor);
                        bookings.add(newBooking);
                    }
                }
            }
            bookingRepository.saveAll(bookings);
            return true;
        } catch (Exception e) {
            throw new BadCredentialsException("Booking failed!");
        }
    }

    @Override
    public boolean deleteBooking(int bookingId) {
        try {

            Booking checkBookingExistOrNot = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found!"));

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            UserDetails userDetails = customUserDetails.loadUserByUsername(username);

            if (userDetails == null) {
                throw new BadCredentialsException("User not found");
            }

            bookingRepository.delete(checkBookingExistOrNot);
            return true;
        } catch (Exception e) {
            throw new BadCredentialsException("Delete booking fail!");
        }
    }
}
