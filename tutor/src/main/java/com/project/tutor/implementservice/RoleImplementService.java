package com.project.tutor.implementservice;

import com.project.tutor.dto.RoleDTO;
import com.project.tutor.dto.UserDTO;
import com.project.tutor.many.dto.RoleManyDTO;
import com.project.tutor.mapper.UserRole;
import com.project.tutor.model.Role;
import com.project.tutor.model.User;
import com.project.tutor.repository.RoleRepository;
import com.project.tutor.repository.UserRepository;
import com.project.tutor.repository.UserRoleRepository;
import com.project.tutor.request.RoleRequest;
import com.project.tutor.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleImplementService implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public List<RoleManyDTO> getAllRole() {
        List<Role> listRoles = roleRepository.findAll();
        List<RoleManyDTO> listRoleManyDTO = new ArrayList<>();
        for (Role role : listRoles) {
            RoleManyDTO roleMany = new RoleManyDTO();
            roleMany.setId(role.getId());
            roleMany.setRoleName(role.getRoleName());
            roleMany.setCreateAt(role.getCreateAt());
            List<UserRole> listUserRoles = role.getListUserRoles();

            List<UserDTO> listUserDTO = new ArrayList<>();
            for (UserRole userRole : listUserRoles) {
                UserDTO userDTO = new UserDTO();
                userDTO.setUserId(userRole.getUser().getId());
                userDTO.setUsername(userRole.getUser().getUsername());
                userDTO.setPassword(userRole.getUser().getPassword());
                userDTO.setEmail(userRole.getUser().getEmail());
                userDTO.setFirstName(userRole.getUser().getFirstName());
                userDTO.setLastName(userRole.getUser().getLastName());
                userDTO.setAddress(userRole.getUser().getAddress());
                userDTO.setPhoneNumber(userRole.getUser().getPhoneNumber());
                userDTO.setCreateAt(userRole.getUser().getCreatAt());

                listUserDTO.add(userDTO);
            }
            roleMany.setListUserDTO(listUserDTO);
            listRoleManyDTO.add(roleMany);
        }
        return listRoleManyDTO;
    }

    @Override
    public RoleRequest addRole(RoleRequest request) {
        try {
            String roleName = request.getRoleName();
            LocalDateTime createAt = request.getCreateAt();
            List<User> listUsers = request.getListUsers();
            if (createAt == null) {
                createAt = LocalDateTime.now();
            }
            Role newRole = new Role();
            newRole.setRoleName(roleName);
            newRole.setCreateAt(createAt);

            Role saveRole = roleRepository.save(newRole);

            if (listUsers != null && !listUsers.isEmpty()) {
                List<UserRole> listUserRole = new ArrayList<>();
                for (User user : listUsers) {
                    User existingUser = userRepository.findById(user.getId()).orElse(null);
                    if (existingUser != null) {
                        UserRole userRole = new UserRole();
                        userRole.setRole(saveRole);
                        userRole.setUser(existingUser);
                        listUserRole.add(userRole);
                    }
                }
                userRoleRepository.saveAll(listUserRole);
            }
        } catch (Exception e) {
            throw new RuntimeException("Add role fail!! " + e.getMessage());
        }
        return request;
    }

    @Override
    public boolean deleteRole(int roleId) {
        Optional<Role> checkRoleExistOrNot = roleRepository.findById(roleId);
        if (checkRoleExistOrNot.isPresent()) {
            Role role = checkRoleExistOrNot.get();
            roleRepository.delete(role);
            return true;
        } else {
            throw new RuntimeException("Cann't delete role with id : " + roleId);
        }
    }

    @Override
    public boolean updateRole(int roleId, RoleRequest request) {
        Optional<Role> checkRoleExistOrNot = roleRepository.findById(roleId);
        try {
            if (checkRoleExistOrNot.isPresent()) {

                Role role = checkRoleExistOrNot.get();

                role.setRoleName(request.getRoleName());

                userRoleRepository.deleteAllByRole_Id(roleId);
                role = roleRepository.save(role);

                List<UserRole> listUserRole = new ArrayList<>();
                if (request.getListUsers() != null) {
                    for (User user : request.getListUsers()) {
                        User existingUser = userRepository.findById(user.getId()).get();
                        UserRole userRole = new UserRole();
                        userRole.setRole(role);
                        userRole.setUser(existingUser);
                        listUserRole.add(userRole);
                    }
                    userRoleRepository.saveAll(listUserRole);
                }
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot update role with id : " + roleId);
        }
        return false;
    }

    @Override
    public RoleManyDTO getRoleById(int roleId) {
        Optional<Role> checkRoleExistOrNot = roleRepository.findById(roleId);
        try {
            if (checkRoleExistOrNot.isPresent()) {
                Role role = checkRoleExistOrNot.get();
                List<UserRole> listUserRole = userRoleRepository.findByRoleId(roleId);

                RoleManyDTO roleMany = new RoleManyDTO();
                roleMany.setId(role.getId());
                roleMany.setRoleName(role.getRoleName());
                roleMany.setCreateAt(role.getCreateAt());

                List<UserDTO> listUsrDTO = listUserRole.stream().map(
                        UserDTO -> {
                            UserDTO userDTO = new UserDTO();
                            userDTO.setUserId(UserDTO.getUser().getId());
                            userDTO.setUsername(UserDTO.getUser().getUsername());
                            userDTO.setPassword(UserDTO.getUser().getPassword());
                            userDTO.setPhoneNumber(UserDTO.getUser().getPassword());
                            userDTO.setEmail(UserDTO.getUser().getEmail());
                            userDTO.setFirstName(UserDTO.getUser().getFirstName());
                            userDTO.setLastName(UserDTO.getUser().getLastName());
                            userDTO.setAddress(UserDTO.getUser().getAddress());
                            userDTO.setPhoneNumber(UserDTO.getUser().getPhoneNumber());
                            userDTO.setActive(UserDTO.getUser().isActive());
                            userDTO.setCreateAt(UserDTO.getUser().getCreatAt());
                            return userDTO;
                        }).collect(Collectors.toList());
                roleMany.setListUserDTO(listUsrDTO);
                return roleMany;
            }

        } catch (Exception e) {
            throw new RuntimeException("Cannot find role with id : " + roleId);
        }
        return null;
    }
}
