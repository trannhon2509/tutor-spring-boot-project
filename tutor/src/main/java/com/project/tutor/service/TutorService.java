package com.project.tutor.service;

import com.project.tutor.dto.TutorDTO;
import com.project.tutor.many.dto.TutorManyDTO;
import com.project.tutor.model.Tutor;
import com.project.tutor.request.TutorRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface TutorService {
    public List<TutorManyDTO> getAllTutor();


    public TutorRequest addTutor(MultipartFile[] file , TutorRequest request);

    public boolean deleteTutorById(int tutorId);

    public boolean updateTutor(MultipartFile[] file ,int tutorId, TutorRequest request);

    public TutorManyDTO getTutorById(int tutorId);
}

