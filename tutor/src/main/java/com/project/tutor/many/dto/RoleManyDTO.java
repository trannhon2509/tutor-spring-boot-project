package com.project.tutor.many.dto;

import com.project.tutor.dto.UserDTO;
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
public class RoleManyDTO {
    private int id;
    private String roleName;
    private LocalDateTime createAt;
    private List<UserDTO> listUserDTO;
}
