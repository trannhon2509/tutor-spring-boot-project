package com.project.tutor.many.dto;

import com.project.tutor.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearningManyDTO {
    private int id;
    private UserDTO userDTO;
    private String startTime;
    private String endTime;
    private String startDate;

}
