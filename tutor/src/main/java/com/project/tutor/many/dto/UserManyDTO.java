package com.project.tutor.many.dto;

import com.project.tutor.dto.FeedBackDTO;
import com.project.tutor.dto.RoleDTO;
import com.project.tutor.dto.SubjectDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserManyDTO {
    private int userId;
    private String username;
    private  String password;
    private String email;
    private  String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private boolean isActive;
    private LocalDateTime createAt;
    private List<RoleDTO> listRoleDTOs;
    private List<FeedBackDTO> listFeedbackDTO;
}
