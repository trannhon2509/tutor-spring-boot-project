package com.project.tutor.dto;

import com.project.tutor.many.dto.TutorManyDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private int id;
    private String subjectName;
    private String description;
    private double totalMoneyMonthTeaching;
    private int numberTeachOfWeek;
    private double oneHourTeaching;
}
