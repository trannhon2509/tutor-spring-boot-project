package com.project.tutor.many.dto;

import com.project.tutor.dto.SubjectDTO;
import com.project.tutor.dto.TutorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectManyDTO {
    private int id;
    private String subjectName;
    private String description;
    private double totalMoneyMonthTeaching;
    private int numberTeachOfWeek;
    private double oneHourTeaching;
    private List<TutorDTO> listTutorDTO;
}
