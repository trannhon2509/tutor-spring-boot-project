package com.project.tutor.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
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
}
