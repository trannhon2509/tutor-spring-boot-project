package com.project.tutor.service;

import com.project.tutor.many.dto.LearningManyDTO;
import com.project.tutor.many.dto.UserManyDTO;
import com.project.tutor.mapper.UserRole;
import com.project.tutor.model.User;
import com.project.tutor.request.UserRequest;
import com.project.tutor.respone.ResponeData;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface UserService {
    public ResponeData signup (UserRequest request);
    public ResponeData signin (UserRequest request);
    public UserManyDTO findUserById (int userId);
    public User findUserProfileByJwt (String jwt);
    public List<UserManyDTO> getAllUser ();
    public boolean deleteUserById (int userId);
    public boolean updateUser (int userId, UserRequest request);

    public boolean activeAccount (String email , String activeCode);

    public boolean forgotPassword (String email);
    public boolean resetPassword (String email , UserRequest request);

    public List<LearningManyDTO> getAllListLearning ();
}
