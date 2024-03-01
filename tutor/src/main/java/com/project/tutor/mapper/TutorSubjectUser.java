package com.project.tutor.mapper;

import com.project.tutor.model.Subject;
import com.project.tutor.model.Tutor;
import com.project.tutor.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tutor_subject_user")
public class TutorSubjectUser {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "tutor_subject_user_id", unique = true , nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "start_date")
    private String startDate;
}
