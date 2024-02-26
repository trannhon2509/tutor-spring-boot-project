package com.project.tutor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO {
    private int id;
    private String roleName;
    private LocalDateTime createAt;
}
