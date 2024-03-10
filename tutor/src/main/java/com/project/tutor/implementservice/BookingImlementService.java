package com.project.tutor.implementservice;

import com.project.tutor.dto.BookingDTO;
import com.project.tutor.dto.TutorDTO;
import com.project.tutor.dto.UserDTO;
import com.project.tutor.mapper.Booking;
import com.project.tutor.mapper.UserRole;
import com.project.tutor.model.Tutor;
import com.project.tutor.model.User;
import com.project.tutor.repository.BookingRepository;
import com.project.tutor.repository.TutorRepository;
import com.project.tutor.repository.UserRepository;
import com.project.tutor.request.BookingRequest;
import com.project.tutor.request.TutorRequest;
import com.project.tutor.request.UserRequest;
import com.project.tutor.secutiry.CustomUserDetails;
import com.project.tutor.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public boolean updateBooking(int bookingId, BookingRequest request) {
        try {
            Optional<Booking> checkBookingExistOrNot = bookingRepository.findById(bookingId);
            if (checkBookingExistOrNot.isPresent()) {

                Booking booking = checkBookingExistOrNot.get();

                int userId = request.getUserId();
                int tutorId = request.getTutorId();


                Optional<User> optionalUser = userRepository.findById(userId);
                Optional<Tutor> optionalTutor = tutorRepository.findById(tutorId);

                if (optionalUser.isPresent() && optionalTutor.isPresent()) {
                    User user = optionalUser.get();
                    Tutor tutor = optionalTutor.get();


                    booking.setUser(user);
                    booking.setTutor(tutor);


                    bookingRepository.save(booking);

                    return true;
                }
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Update booking fail!");
        }
        return false;
    }

    @Override
    public List<BookingDTO> getAllListBooking() {
        List<Booking> listBookings = bookingRepository.findAll();

        List<BookingDTO> listBookingDTO = new ArrayList<>();
        Collections.sort(listBookings, Comparator.comparing(b -> b.getTutor().getId()));

        for (Booking booking : listBookings) {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setBookingId(booking.getId());

            // Set UserDTO information
            User user = booking.getUser();
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setEmail(user.getEmail());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setAddress(user.getAddress());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setActive(user.isActive());
            userDTO.setCreateAt(user.getCreatAt());

            bookingDTO.setUserDTO(userDTO);

            // Set TutorDTO information
            Tutor tutor = booking.getTutor();
            TutorDTO tutorDTO = new TutorDTO();
            tutorDTO.setId(tutor.getId());
            tutorDTO.setCityTech(tutor.getCityTeach());
            tutorDTO.setFullName(tutor.getFullName());
            tutorDTO.setGender(tutor.getGender());
            tutorDTO.setDateOfBirth(tutor.getDateOfBirth());
            tutorDTO.setAddress(tutor.getAddress());
            tutorDTO.setPhoneNumber(tutor.getPhoneNumber());
            tutorDTO.setEmail(tutor.getEmail());
            tutorDTO.setVoice(tutor.getVoice());
            tutorDTO.setMajor(tutor.getMajor());
            tutorDTO.setEcademicLevel(tutor.getEcademicLevel());
            tutorDTO.setDescription(tutor.getDescription());
            tutorDTO.setCitizenIdentificationCard(tutor.getCitizenIdentificationCard());
            tutorDTO.setIssued(tutor.getIssued());
            tutorDTO.setCitizenIdentificationCardFront(tutor.getCitizenIdentificationCardFront());
            tutorDTO.setCitizenIdentificationCardFrontBackside(tutor.getCitizenIdentificationCardFrontBackside());
            tutorDTO.setCardPhoto(tutor.getCardPhoto());
            tutorDTO.setSchoolTeachOrStudent(tutor.getSchoolTeachOrStudent());
            tutorDTO.setNumberTeachOfWeak(tutor.getNumberTeachOfWeek());
            tutorDTO.setSalaryRequest(tutor.getSalaryRequest());
            tutorDTO.setCreateAt(tutor.getCreateAt());

            bookingDTO.setTutorDTO(tutorDTO);

            listBookingDTO.add(bookingDTO);
        }

        return listBookingDTO;
    }

    @Override
    public BookingDTO getBookingById(int bookingId) {
        try {
            Optional<Booking> checkBookingExistOrNot = bookingRepository.findById(bookingId);

            if (checkBookingExistOrNot.isPresent()) {
                Booking booking = checkBookingExistOrNot.get();

                BookingDTO bookingDTO = new BookingDTO();
                bookingDTO.setBookingId(booking.getId());

                User user = booking.getUser();
                UserDTO userDTO = new UserDTO();
                userDTO.setUserId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setPassword(user.getPassword());
                userDTO.setEmail(user.getEmail());
                userDTO.setFirstName(user.getFirstName());
                userDTO.setLastName(user.getLastName());
                userDTO.setAddress(user.getAddress());
                userDTO.setPhoneNumber(user.getPhoneNumber());
                userDTO.setActive(user.isActive());
                userDTO.setCreateAt(user.getCreatAt());

                bookingDTO.setUserDTO(userDTO);

                // Set TutorDTO information
                Tutor tutor = booking.getTutor();
                TutorDTO tutorDTO = new TutorDTO();
                tutorDTO.setId(tutor.getId());
                tutorDTO.setCityTech(tutor.getCityTeach());
                tutorDTO.setFullName(tutor.getFullName());
                tutorDTO.setGender(tutor.getGender());
                tutorDTO.setDateOfBirth(tutor.getDateOfBirth());
                tutorDTO.setAddress(tutor.getAddress());
                tutorDTO.setPhoneNumber(tutor.getPhoneNumber());
                tutorDTO.setEmail(tutor.getEmail());
                tutorDTO.setVoice(tutor.getVoice());
                tutorDTO.setMajor(tutor.getMajor());
                tutorDTO.setEcademicLevel(tutor.getEcademicLevel());
                tutorDTO.setDescription(tutor.getDescription());
                tutorDTO.setCitizenIdentificationCard(tutor.getCitizenIdentificationCard());
                tutorDTO.setIssued(tutor.getIssued());
                tutorDTO.setCitizenIdentificationCardFront(tutor.getCitizenIdentificationCardFront());
                tutorDTO.setCitizenIdentificationCardFrontBackside(tutor.getCitizenIdentificationCardFrontBackside());
                tutorDTO.setCardPhoto(tutor.getCardPhoto());
                tutorDTO.setSchoolTeachOrStudent(tutor.getSchoolTeachOrStudent());
                tutorDTO.setNumberTeachOfWeak(tutor.getNumberTeachOfWeek());
                tutorDTO.setSalaryRequest(tutor.getSalaryRequest());
                tutorDTO.setCreateAt(tutor.getCreateAt());

                bookingDTO.setTutorDTO(tutorDTO);

                return bookingDTO;
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Update booking fail!");
        }
        return null;
    }


    @Override
    public boolean addBooking(List<Map<String, Integer>> listTutorMaps) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            UserDetails userDetails = customUserDetails.loadUserByUsername(username);

            if (userDetails == null) {
                throw new BadCredentialsException("User not found");
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
