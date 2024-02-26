package com.project.tutor.model;

import com.project.tutor.mapper.TutorSubject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "subject")

public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id", unique = true , nullable = false)
    private int id;

    @Column(name ="subject_name")
    private String subjectName;

    @Column(name ="description")
    private String description;

    @Column(name = "total_money_month_teaching")
    private double totalMoneyMonthTeaching;

    @Column(name ="number_teach_of_week")
    private int numberTeachOfWeek;

    @Column(name ="one_hour_teaching")
    private double oneHourTeaching;

    @OneToMany(mappedBy = "subject",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<TutorSubject> listTutorSubject = new ArrayList<>();
}
