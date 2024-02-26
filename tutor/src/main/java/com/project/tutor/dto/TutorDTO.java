package com.project.tutor.dto;

import com.project.tutor.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TutorDTO {
    private int id;
    private String cityTech;
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
    private int numberTeachOfWeak;
    private double  salaryRequest;
    private LocalDateTime createAt;
//    private List<SubjectDTO> listSubjectDTO;
}

