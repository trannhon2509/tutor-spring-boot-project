package com.project.tutor.many.dto;

import com.project.tutor.dto.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
