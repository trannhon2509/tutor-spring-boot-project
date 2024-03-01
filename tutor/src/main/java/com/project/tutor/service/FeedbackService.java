package com.project.tutor.service;

import com.project.tutor.many.dto.FeedbackManyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedbackService {
    public List<FeedbackManyDTO> getAllFeedBack ();

}
