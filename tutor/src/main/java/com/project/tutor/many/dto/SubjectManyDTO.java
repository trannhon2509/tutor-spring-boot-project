package com.project.tutor.many.dto;

import com.project.tutor.dto.SubjectDTO;
import com.project.tutor.dto.TutorDTO;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectManyDTO {
    private int id;
    private String subjectName;
    private String description;
    private double totalMoneyMonthTeaching;
    private int numberTeachOfWeek;
    private double oneHourTeaching;
    private List<TutorDTO> listTutorDTO;
}
