package com.project.tutor.mapper;

import com.project.tutor.model.Subject;
import com.project.tutor.model.Tutor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tutor_subject")
public class TutorSubject {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "tutor_subject_id", unique = true , nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;
}
