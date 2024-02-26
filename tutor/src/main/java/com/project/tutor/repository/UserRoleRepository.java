package com.project.tutor.repository;

import com.project.tutor.model.User;
import com.project.tutor.mapper.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole , Integer> {
    public List<UserRole> findByUserId(int userId);

    public UserRole deleteByUser (User user);
    public UserRole findByUserUsername(String username);

    public boolean existsByUser_IdAndRole_Id(int userId, int roleId);
    public void deleteAllByUser_Id(int id);
    public List<UserRole> findByRoleId(int roleId);

    public void deleteAllByRole_Id (int id);
    public UserRole findByUserEmail(String email);


}
