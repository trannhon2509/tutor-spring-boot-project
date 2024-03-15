package com.project.tutor.many.dto;

import com.project.tutor.dto.UserDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleManyDTO {
    private int id;
    private String roleName;
    private LocalDateTime createAt;
    private List<UserDTO> listUserDTO;
}
