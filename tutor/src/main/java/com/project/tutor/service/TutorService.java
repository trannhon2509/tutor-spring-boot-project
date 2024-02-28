package com.project.tutor.service;

import com.project.tutor.dto.TutorDTO;
import com.project.tutor.many.dto.TutorManyDTO;
import com.project.tutor.model.Tutor;
import com.project.tutor.request.TutorRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface TutorService {
    public List<TutorManyDTO> getAllTutor();

    public TutorRequest addTutor(MultipartFile file,
                                 String cityTeach, String fullName, String gender, String dateOfBirth, String address, String phoneNumber,
                                 String email, String voice, String major, String academicLevel, String description,
                                 String issued,
                                 String shoolTeacherOrTeach, int numberTeachOfWeek, double salaryRequest
    );

    public boolean deleteTutorById(int tutorId);

    public boolean updateTutor(int tutorId, TutorRequest request);

    public TutorManyDTO getTutorById(int tutorId);
}

