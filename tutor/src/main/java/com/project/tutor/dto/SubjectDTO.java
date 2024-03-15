package com.project.tutor.dto;

import com.project.tutor.many.dto.TutorManyDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDTO {
    private int id;
    private String subjectName;
    private String description;
    private double totalMoneyMonthTeaching;
    private int numberTeachOfWeek;
    private double oneHourTeaching;
}
