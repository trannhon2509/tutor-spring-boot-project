package com.project.tutor.dto;


import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO {
    private int id;
    private String roleName;
    private LocalDateTime createAt;
}
