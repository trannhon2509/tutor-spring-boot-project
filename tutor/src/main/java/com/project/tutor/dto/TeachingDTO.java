package com.project.tutor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeachingDTO {
    private int teachingId;
    private String teachingName;
    private int schedule;
}
