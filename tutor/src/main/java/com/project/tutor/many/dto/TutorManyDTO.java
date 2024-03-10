package com.project.tutor.many.dto;

import com.project.tutor.dto.FeedBackDTO;
import com.project.tutor.dto.SubjectDTO;
import com.project.tutor.dto.TeachingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TutorManyDTO {
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
    private boolean approved;
    private List<SubjectDTO> listSubjectDTO;
    private List<TeachingDTO> listTeachingDTO;
    private List<FeedBackDTO> listFeedbackDTO;
}
