package com.project.tutor.implementservice;

import com.project.tutor.dto.BookingDTO;
import com.project.tutor.dto.TutorDTO;
import com.project.tutor.dto.UserDTO;
import com.project.tutor.mapper.Booking;
import com.project.tutor.model.Tutor;
import com.project.tutor.model.User;
import com.project.tutor.repository.BookingRepository;
import com.project.tutor.repository.TutorRepository;
import com.project.tutor.repository.UserRepository;
import com.project.tutor.request.BookingRequest;
import com.project.tutor.secutiry.CustomUserDetails;
import com.project.tutor.service.BookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class BookingImlementService implements BookingService {


    TutorRepository tutorRepository;


    BookingRepository bookingRepository;


    UserRepository userRepository;


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
            UserDTO userDTO = UserDTO.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .address(user.getAddress())
                    .phoneNumber(user.getPhoneNumber())
                    .createAt(user.getCreatAt())
                    .isActive(user.isActive())
                    .build();

            bookingDTO.setUserDTO(userDTO);

            // Set TutorDTO information
            Tutor tutor = booking.getTutor();
            TutorDTO tutorDTO = TutorDTO.builder()
                    .id(tutor.getId())
                    .cityTech(tutor.getCityTeach())
                    .fullName(tutor.getFullName())
                    .gender(tutor.getGender())
                    .dateOfBirth(tutor.getDateOfBirth())
                    .address(tutor.getAddress())
                    .phoneNumber(tutor.getPhoneNumber())
                    .email(tutor.getEmail())
                    .voice(tutor.getVoice())
                    .major(tutor.getMajor())
                    .ecademicLevel(tutor.getEcademicLevel())
                    .description(tutor.getDescription())
                    .citizenIdentificationCard(tutor.getCitizenIdentificationCard())
                    .issued(tutor.getIssued())
                    .citizenIdentificationCardFront(tutor.getCitizenIdentificationCardFront())
                    .citizenIdentificationCardFrontBackside(tutor.getCitizenIdentificationCardFrontBackside())
                    .cardPhoto(tutor.getCardPhoto())
                    .schoolTeachOrStudent(tutor.getSchoolTeachOrStudent())
                    .numberTeachOfWeak(tutor.getNumberTeachOfWeek())
                    .salaryRequest(tutor.getSalaryRequest())
                    .createAt(tutor.getCreateAt())
                    .approved(tutor.isApproved())
                    .build();

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
                UserDTO userDTO = UserDTO.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .address(user.getAddress())
                        .phoneNumber(user.getPhoneNumber())
                        .createAt(user.getCreatAt())
                        .isActive(user.isActive())
                        .build();

                bookingDTO.setUserDTO(userDTO);

                // Set TutorDTO information
                Tutor tutor = booking.getTutor();
                TutorDTO tutorDTO = TutorDTO.builder()
                        .id(tutor.getId())
                        .cityTech(tutor.getCityTeach())
                        .fullName(tutor.getFullName())
                        .gender(tutor.getGender())
                        .dateOfBirth(tutor.getDateOfBirth())
                        .address(tutor.getAddress())
                        .phoneNumber(tutor.getPhoneNumber())
                        .email(tutor.getEmail())
                        .voice(tutor.getVoice())
                        .major(tutor.getMajor())
                        .ecademicLevel(tutor.getEcademicLevel())
                        .description(tutor.getDescription())
                        .citizenIdentificationCard(tutor.getCitizenIdentificationCard())
                        .issued(tutor.getIssued())
                        .citizenIdentificationCardFront(tutor.getCitizenIdentificationCardFront())
                        .citizenIdentificationCardFrontBackside(tutor.getCitizenIdentificationCardFrontBackside())
                        .cardPhoto(tutor.getCardPhoto())
                        .schoolTeachOrStudent(tutor.getSchoolTeachOrStudent())
                        .numberTeachOfWeak(tutor.getNumberTeachOfWeek())
                        .salaryRequest(tutor.getSalaryRequest())
                        .createAt(tutor.getCreateAt())
                        .approved(tutor.isApproved())
                        .build();

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
                throw new RuntimeException("User not found : " + username);
            }

            bookingRepository.delete(checkBookingExistOrNot);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Delete booking fail!");
        }
    }
}
