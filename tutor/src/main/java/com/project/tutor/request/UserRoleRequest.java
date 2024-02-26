package com.project.tutor.request;

import com.project.tutor.model.Role;
import com.project.tutor.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRequest {
    private UserRequest userRequest;
    private RoleRequest roleRequest;


}
