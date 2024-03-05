package com.project.tutor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.tutor.mapper.Booking;
import com.project.tutor.mapper.TutorSubject;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tutor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tutor_id", unique = true , nullable = false)
    private int id;

    @Column(name = "city_teach")
    private String cityTeach;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "voice")
    private String voice;

    @Column(name = "major")
    private String major;

    @Column(name = "academic_level")
    private String ecademicLevel;

    @Column(name = "description")
    private String description;

    @Column(name = "citizen_identification_card")
    private String citizenIdentificationCard;

    @Column(name = "issued")
    private String issued;

    @Column(name = "citizen_identification_card_front")
    private String citizenIdentificationCardFront;

    @Column(name = "citizen_identification_card_font_backside")
    private String citizenIdentificationCardFrontBackside;

    @Column(name = "card_photo")
    private String cardPhoto;

    @Column(name = "shool_teacher_or_student")
    private String schoolTeachOrStudent;

    @Column(name = "number_tech_of_week")
    private int numberTeachOfWeek;

    @Column(name = "salary_request")
    private double salaryRequest;

    @Column(name ="create_at")
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "tutor" ,fetch = FetchType.LAZY , cascade =  CascadeType.ALL)
    private List<TutorSubject> listTutorSubject = new ArrayList<>();

    @OneToMany(mappedBy = "tutor",cascade = CascadeType.ALL)
    private List<Teaching> listTeachings = new ArrayList<>();

    @OneToMany(mappedBy = "tutor" , cascade = CascadeType.ALL)
    private List<Payment> listPayment = new ArrayList<>();

    @OneToMany(mappedBy = "tutor" ,fetch = FetchType.LAZY)
    private List<FeedBack> listFeedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "tutor" , cascade = CascadeType.ALL)
            @JsonIgnore
    private List<Booking> listBooking = new ArrayList<>();

}
