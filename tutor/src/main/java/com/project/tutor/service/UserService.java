package com.project.tutor.service;

import com.project.tutor.many.dto.LearningManyDTO;
import com.project.tutor.many.dto.UserManyDTO;
import com.project.tutor.model.User;
import com.project.tutor.request.UserRequest;
import com.project.tutor.respone.ResponeDataAuth;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public ResponeDataAuth signup (UserRequest request);
    public ResponeDataAuth signin (UserRequest request);
    public UserManyDTO findUserById (int userId);
    public User findUserProfileByJwt (String jwt);
    public List<UserManyDTO> getAllUser (int page , int record);

    public boolean deleteUserById (int userId);
    public boolean updateUser (int userId, UserRequest request);
    public List<UserManyDTO> getAlllistUserAndSearching(String title, int page, int record);
    public boolean activeAccount (String email , String activeCode);

    public boolean forgotPassword (String email);
    public boolean resetPassword (String email , UserRequest request);


}
