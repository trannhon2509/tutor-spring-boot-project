package com.project.tutor.mapper;

import com.project.tutor.model.Role;
import com.project.tutor.model.User;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id", unique = true , nullable = false)
    private int id;

    @ManyToOne( fetch = FetchType.EAGER  ,  cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER  ,  cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

}
