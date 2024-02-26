package com.project.tutor.request;

import com.project.tutor.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.atn.LexerIndexedCustomAction;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private String roleName;
    private LocalDateTime createAt;
    private List<User> listUsers;
}
