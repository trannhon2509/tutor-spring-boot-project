package com.project.tutor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "teaching")
@Getter
@Setter
@NoArgsConstructor
public class Teaching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teaching_id", unique = true , nullable = false)
    private int id;

    @Column(name = "teaching_name")
    private String teachingName;

    @Column(name = "schedule")
    private int schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

}
