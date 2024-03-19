package com.project.tutor.implementservice;

import com.project.tutor.access.PagingSearchAndSorting;
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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleImplementService implements RoleService {

    RoleRepository roleRepository;


    UserRepository userRepository;

    UserRoleRepository userRoleRepository;


    PagingSearchAndSorting pagingSearchAndSorting;

    @Override
    public List<RoleManyDTO> getAllRole(int page, int record) {
        List<Role> listRoles = roleRepository.findAll(pagingSearchAndSorting.pageablePageSizeAndRecordOrSearchOrSort(page, record)).get().toList();
        List<RoleManyDTO> listRoleManyDTO = new ArrayList<>();
        for (Role role : listRoles) {
            RoleManyDTO roleMany = RoleManyDTO.builder()
                    .id(role.getId())
                    .roleName(role.getRoleName())
                    .createAt(role.getCreateAt())
                    .build();
            List<UserRole> listUserRoles = role.getListUserRoles();

            List<UserDTO> listUserDTO = new ArrayList<>();
            for (UserRole userRole : listUserRoles) {
                UserDTO userDTO = UserDTO.builder()
                        .userId(userRole.getUser().getId())
                        .username(userRole.getUser().getUsername())
                        .password(userRole.getUser().getPassword())
                        .email(userRole.getUser().getEmail())
                        .firstName(userRole.getUser().getFirstName())
                        .lastName(userRole.getUser().getLastName())
                        .address(userRole.getUser().getAddress())
                        .phoneNumber(userRole.getUser().getPhoneNumber())
                        .createAt(userRole.getUser().getCreatAt())
                        .isActive(userRole.getUser().isActive())
                        .build();
                listUserDTO.add(userDTO);
            }
            roleMany.setListUserDTO(listUserDTO);
            listRoleManyDTO.add(roleMany);
        }
        return listRoleManyDTO;
    }

    @Override
    public List<RoleManyDTO> getAllRoleSearchAndPagingAndSort(String title, int page, int record) {
        List<Role> listRoles = roleRepository.findByRoleNameContaining(title, pagingSearchAndSorting.pageablePageSizeAndRecordOrSearchOrSort(page, record));
        List<RoleManyDTO> listRoleManyDTO = new ArrayList<>();
        for (Role role : listRoles) {
            RoleManyDTO roleMany = RoleManyDTO.builder()
                    .id(role.getId())
                    .roleName(role.getRoleName())
                    .createAt(role.getCreateAt())
                    .build();
            List<UserRole> listUserRoles = role.getListUserRoles();

            List<UserDTO> listUserDTO = new ArrayList<>();
            for (UserRole userRole : listUserRoles) {
                UserDTO userDTO = UserDTO.builder()
                        .userId(userRole.getUser().getId())
                        .username(userRole.getUser().getUsername())
                        .password(userRole.getUser().getPassword())
                        .email(userRole.getUser().getEmail())
                        .firstName(userRole.getUser().getFirstName())
                        .lastName(userRole.getUser().getLastName())
                        .address(userRole.getUser().getAddress())
                        .phoneNumber(userRole.getUser().getPhoneNumber())
                        .createAt(userRole.getUser().getCreatAt())
                        .isActive(userRole.getUser().isActive())
                        .build();

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

                RoleManyDTO roleMany = RoleManyDTO.builder()
                        .id(role.getId())
                        .roleName(role.getRoleName())
                        .createAt(role.getCreateAt())
                        .build();

                List<UserDTO> listUsrDTO = listUserRole.stream().map(
                        user -> {
                            return UserDTO.builder()
                                    .userId(user.getUser().getId())
                                    .username(user.getUser().getUsername())
                                    .password(user.getUser().getPassword())
                                    .email(user.getUser().getEmail())
                                    .firstName(user.getUser().getFirstName())
                                    .lastName(user.getUser().getLastName())
                                    .address(user.getUser().getAddress())
                                    .phoneNumber(user.getUser().getPhoneNumber())
                                    .createAt(user.getUser().getCreatAt())
                                    .isActive(user.getUser().isActive())
                                    .build();
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
