package com.project.tutor.request;

import com.project.tutor.model.Tutor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SubjectRequest {
    private String subjectName;
    private String description;
    private double totalMoneyMonthTeaching;
    private int numberTeachOfWeek;
    private double oneHourTeaching;
    private List<Tutor> listTutors ;
}
