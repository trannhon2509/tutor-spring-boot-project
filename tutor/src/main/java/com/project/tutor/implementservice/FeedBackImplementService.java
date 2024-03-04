package com.project.tutor.implementservice;

import com.project.tutor.dto.FeedBackDTO;
import com.project.tutor.mapper.Booking;
import com.project.tutor.model.FeedBack;
import com.project.tutor.model.Tutor;
import com.project.tutor.model.User;
import com.project.tutor.repository.FeedBackRepository;
import com.project.tutor.repository.TutorRepository;
import com.project.tutor.repository.UserRepository;
import com.project.tutor.request.FeedBackRequest;
import com.project.tutor.secutiry.CustomUserDetails;
import com.project.tutor.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FeedBackImplementService implements FeedbackService {

    @Autowired
    FeedBackRepository feedBackRepository;

    @Autowired
    CustomUserDetails customUserDetails;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TutorRepository tutorRepository;

    @Override
    public List<FeedBackDTO> getAllFeedback() {
        List<FeedBack> listFeedbacks = feedBackRepository.findAll();
        List<FeedBackDTO> listFeedbackDTO = new ArrayList<>();

        for (FeedBack feedBack : listFeedbacks) {
            FeedBackDTO feedBackDTO = new FeedBackDTO();
            feedBackDTO.setFeedbackId(feedBack.getId());
            feedBackDTO.setContent(feedBack.getContent());
            feedBackDTO.setRating(feedBack.getRating());
            feedBackDTO.setCreateAt(feedBack.getCreateAt());
            listFeedbackDTO.add(feedBackDTO);
        }
        return listFeedbackDTO;
    }

    @Override
    public boolean addFeedback(FeedBackRequest request) {
        try {
            String content = request.getContent();
            double rating = request.getRating();
            LocalDateTime createAt = request.getCreateAt();
            if(createAt == null){
                createAt = LocalDateTime.now();
            }
            User user = new User();
            user.setId(request.getUserId());

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            UserDetails userDetails = customUserDetails.loadUserByUsername(username);

            if (userDetails == null) {
                throw new BadCredentialsException("User not found");
            }
            User currentUser = userRepository.findByUsername(username);
            if (currentUser == null) {
                throw new BadCredentialsException("Current user not found");
            }

            Tutor checkTutorExist = tutorRepository.findById(request.getTutorId()).orElseThrow(() -> new RuntimeException("Tutor not found"));

            boolean checkUserIfBooked = checkIfUserHasBooking(currentUser,checkTutorExist);

            if(!checkUserIfBooked){
                throw new BadCredentialsException("User has not booked a tutor yet. Cannot add feedback.");
            }

            FeedBack newFeedBack = new FeedBack();
            newFeedBack.setContent(content);
            newFeedBack.setRating(rating);
            newFeedBack.setUser(currentUser);
            newFeedBack.setTutor(checkTutorExist);
            newFeedBack.setCreateAt(createAt);

            feedBackRepository.save(newFeedBack);

            return true;
        }catch (Exception e){
            throw new BadCredentialsException("Can't add feedback");
        }
    }

    // Check user booked tutor or not
    public boolean checkIfUserHasBooking(User user, Tutor tutor) {
        try {
            if (user != null && user.getListBooking() != null) {
                for (Booking booking : user.getListBooking()) {
                    Tutor bookedTutor = booking.getTutor();
                    if (bookedTutor != null && bookedTutor.equals(tutor)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error checking user's bookings");
        }
        return false;
    }




    @Override
    public boolean updateFeedback(int feedbackId, FeedBackRequest request) {
        try {
            return true;
        } catch (Exception e) {
            throw new BadCredentialsException("Add feedback fail!");
        }

    }

    @Override
    public boolean deleteFeedback(int feedbackId) {
        return false;
    }

    @Override
    public FeedBackDTO getFeedbackById(int feedbackId) {
        return null;
    }
}
