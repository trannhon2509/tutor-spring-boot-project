package com.project.tutor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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
