package com.project.tutor.model;

import com.project.tutor.mapper.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Entity(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique = true , nullable = false)
    private int id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name ="create_at")
    private LocalDateTime createAt;

    @OneToMany( mappedBy = "role",fetch = FetchType.EAGER)
    private List<UserRole> ListUserRoles = new ArrayList<>();
}
