package com.project.tutor.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeachingRequest {
    private String teachingName;
    private int schedule;
    private int tutorId;
}
