package com.project.tutor.request;

import com.project.tutor.model.Subject;
import com.project.tutor.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TutorRequest {
    private int tutorId;
    private String cityTeach;
    private String fullName;
    private String gender;
    private String dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private String voice;
    private String major;
    private String ecademicLevel;
    private String description;
    private String citizenIdentificationCard;
    private String issued;
    private String citizenIdentificationCardFront;
    private String citizenIdentificationCardFrontBackside;
    private String cardPhoto;
    private String schoolTeachOrStudent;
    private int numberTeachOfWeek;
    private double  salaryRequest;
    private LocalDateTime createAt;
    private List<Subject> listSubjects;
}
