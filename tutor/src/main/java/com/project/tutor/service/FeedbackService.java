package com.project.tutor.service;

import com.project.tutor.dto.FeedBackDTO;
import com.project.tutor.model.Tutor;
import com.project.tutor.model.User;
import com.project.tutor.request.FeedBackRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface FeedbackService {
    public List<FeedBackDTO> getAllFeedback();
    public  boolean addFeedback (FeedBackRequest request);

    public boolean deleteFeedback (int feedbackId);

    public boolean checkIfUserHasBooking (User user , Tutor tutor);


}
