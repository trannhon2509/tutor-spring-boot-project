package com.project.tutor.implementservice;

import com.project.tutor.access.PagingSearchAndSorting;
import com.project.tutor.dto.FeedBackDTO;
import com.project.tutor.dto.RoleDTO;
import com.project.tutor.many.dto.UserManyDTO;
import com.project.tutor.mapper.UserRole;
import com.project.tutor.mapstruct.MapStructGlobal;
import com.project.tutor.model.FeedBack;
import com.project.tutor.model.Role;
import com.project.tutor.model.User;
import com.project.tutor.repository.FeedBackRepository;
import com.project.tutor.repository.RoleRepository;
import com.project.tutor.repository.UserRepository;
import com.project.tutor.repository.UserRoleRepository;
import com.project.tutor.request.UserRequest;
import com.project.tutor.respone.ResponeDataAuth;
import com.project.tutor.secutiry.CustomFilterSecurity;
import com.project.tutor.secutiry.CustomUserDetails;
import com.project.tutor.secutiry.JwtProvider;
import com.project.tutor.service.EmailService;
import com.project.tutor.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class UserImplementService implements UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    JwtProvider jwtProvider;
    UserRoleRepository userRoleRepository;
    EmailService emailService;
    CustomUserDetails customUserDetails;
    FeedBackRepository feedBackRepository;
    PagingSearchAndSorting pagingSearchAndSorting;
    AuthenticationManager authenticationManager;


    @Override
    @Transactional
    public boolean signup(UserRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();
        String passwordRepeat = request.getPasswordRepeat();
        String email = request.getEmail();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String address = request.getAddress();
        String phoneNumber = request.getPhoneNumber();
        // boolean isActive = request.isActive();
        LocalDateTime createAt = request.getCreateAt();
        List<Role> listRoles = request.getListUserRoles();

        // Kiểm tra xem người dùng đã tồn tại hay chưa
        User existUsername = userRepository.findByUsername(username);
        User existEmail = userRepository.findByEmail(email);

        if (existUsername != null) {
            throw new RuntimeException("Username  already exists for another account");
        }
        if (existEmail != null) {
            throw new RuntimeException("Gmail already exists for another account");
        }
        if (!password.equals(passwordRepeat)) {
            throw new RuntimeException("Passowrd repeat not macth");
        }

        // create new user   Assign and send infor actice
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setAddress(address);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setActive(false);
        newUser.setActiveCode(emailService.createActiveCode());

        // Save user
        User savedUser = userRepository.save(newUser);

        // Assign role exist to user
        if (listRoles != null && !listRoles.isEmpty()) {
            List<UserRole> userRoles = new ArrayList<>();
            for (Role role : listRoles) {
                Role existingRole = roleRepository.findById(role.getId()).get();
                UserRole userRole = new UserRole();
                userRole.setUser(savedUser);
                userRole.setRole(existingRole);
                userRoles.add(userRole);
            }
            // Save list userRoles
            userRoleRepository.saveAll(userRoles);
        } else {
            Role roleDefault = roleRepository.findById(3).get();
            UserRole userRole = new UserRole();
            userRole.setUser(savedUser);
            userRole.setRole(roleDefault);
            userRoleRepository.save(userRole);
        }

        //Send email actice account
        //emailService.sendEmailActive(newUser.getEmail(), newUser.getActiveCode());

        return true;
    }

    @Override
    public boolean forgotPassword(String email) {
        try {
            User checkEmailUser = userRepository.findByEmail(email);
            if (checkEmailUser == null) {
                throw new RuntimeException("Cannot found email : " + email);
            }
            emailService.sendEmailResetPassword(email);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Send mail forgot password fail!" + e.getMessage());
        }
    }

    @Override
    public boolean resetPassword(String email, UserRequest request) {
        try {
            User checkEmailUser = userRepository.findByEmail(email);
            if (checkEmailUser == null) {
                throw new RuntimeException("Cannot found email : " + email);
            }

            if (!request.getPasswordRepeat().equals(request.getPasswordRepeatNew())) {
                throw new RuntimeException("New password and password repeat not macth!");
            }

            checkEmailUser.setPassword(passwordEncoder.encode(request.getPasswordRepeatNew()));

            userRepository.save(checkEmailUser);
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Send mail reset password fail!" + e.getMessage());
        }
    }

    public Authentication authentication(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if (userDetails == null) {
            throw new RuntimeException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean signin(UserRequest request) {

            String username = request.getUsername();
            String password = request.getPassword();

            Authentication authentication = authentication(username , password);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return true;

    }


    @Override
    public boolean updateUser(int userId, UserRequest request) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (request.getPassword().equals(request.getPasswordRepeat())) {
                throw new BadCredentialsException("New password must not be the same old password ");
            }

            if (!request.getPasswordRepeat().equals(request.getPasswordRepeatNew())) {
                throw new BadCredentialsException("New password not macth with again new password");
            }

            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPasswordRepeatNew()));
            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setAddress(request.getAddress());
            user.setPhoneNumber(request.getPhoneNumber());

            if (request.isActive()) {
                user.setActive(request.isActive());
            }


            // Xóa toàn bộ user_role bằng user.id
            userRoleRepository.deleteAllByUser_Id(userId);

            // save lại user
            user = userRepository.save(user);

            //Tạo ra list mới
            List<UserRole> list = new ArrayList<>();

            // Gán các quyền mới cho người dùng
            if (request.getListUserRoles() != null) {
                for (Role role : request.getListUserRoles()) {
                    Role existingRole = roleRepository.findById(role.getId()).get();
                    UserRole userRole = new UserRole();
                    userRole.setUser(user);
                    userRole.setRole(existingRole);
                    list.add(userRole);
                }
                userRoleRepository.saveAll(list);
            }
            return true;
        }
        return false;
    }


    @Override
    public UserManyDTO findUserById(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<UserRole> userRoles = userRoleRepository.findByUserId(userId);

            UserManyDTO userMany = UserManyDTO.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .address(user.getAddress())
                    .phoneNumber(user.getPhoneNumber())
                    .isActive(user.isActive())
                    .createAt(user.getCreatAt())
                    .build();
            List<FeedBack> listFeedbacks = user.getListFeedbacks();

            List<RoleDTO> roleDTOList = userRoles.stream()
                    .map(userRole -> {
                        return  RoleDTO.builder()
                                .id(userRole.getRole().getId())
                                .roleName(userRole.getRole().getRoleName())
                                .createAt(userRole.getRole().getCreateAt())
                                .build();
                    }).collect(Collectors.toList());

            List<FeedBackDTO> listFeedBackDTO = listFeedbacks.stream().map(
                    feedback -> {
                        return FeedBackDTO.builder()
                                .feedbackId(feedback.getId())
                                .content(feedback.getContent())
                                .rating(feedback.getRating())
                                .createAt(feedback.getCreateAt())
                                .build();
                    }).collect(Collectors.toList());

            userMany.setListRoleDTOs(roleDTOList);
            userMany.setListFeedbackDTO(listFeedBackDTO);
            return userMany;
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }


    @Override
    public User findUserProfileByJwt(String jwt) {
        String username = jwtProvider.extractUsername(jwt);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Cannot found profile by jwt!");
        }
        return null;
    }

    @Override
    public List<UserManyDTO> getAllUser(int page, int record) {
        List<User> listUser = userRepository.findAll(pagingSearchAndSorting.pageablePageSizeAndRecordOrSearchOrSort(page, record)).get().toList();
        List<UserManyDTO> userDTOList = new ArrayList<>();

        for (User user : listUser) {
            UserManyDTO userMany = UserManyDTO.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .address(user.getAddress())
                    .phoneNumber(user.getPhoneNumber())
                    .isActive(user.isActive())
                    .createAt(user.getCreatAt())
                    .build();

            List<UserRole> userRoleList = userRoleRepository.findByUserId(user.getId());

            List<FeedBack> listFeedbacks = user.getListFeedbacks();

            List<RoleDTO> listRoleDto = new ArrayList<>();
            List<FeedBackDTO> listFeedbackDTO = new ArrayList<>();

            for (UserRole userRole : userRoleList) {
                RoleDTO roleDTO = RoleDTO.builder()
                        .id(userRole.getRole().getId())
                        .roleName(userRole.getRole().getRoleName())
                        .createAt(userRole.getRole().getCreateAt())
                        .build();

                listRoleDto.add(roleDTO);
            }
            for (FeedBack feedback : listFeedbacks) {
                FeedBackDTO feedBackDTO = FeedBackDTO.builder()
                        .feedbackId(feedback.getId())
                        .content(feedback.getContent())
                        .rating(feedback.getRating())
                        .createAt(feedback.getCreateAt())
                        .build();
                listFeedbackDTO.add(feedBackDTO);
            }
            userMany.setListRoleDTOs(listRoleDto);
            userMany.setListFeedbackDTO(listFeedbackDTO);
            userDTOList.add(userMany);
        }

        return userDTOList;
    }

    @Override
    public boolean activeAccount(String email, String activeCode) {
        try {
            User user = userRepository.findByEmail(email);
            // CHECK USER EXIST OR NOT
            if (user == null) {
                throw new RuntimeException("Username not found!");
            }
            // CHECK USER ACTIVE OR NOT
            if (user.isActive()) {
                throw new RuntimeException("Username has been activated!");
            }

            // COMPARE CODE IN DB WITH CODE IN GMAIL IF TRUE THEN SAVE USER AND UPDATE STATUS
            if (activeCode.equals(user.getActiveCode())) {
                user.setActive(true);
                userRepository.save(user);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Active account fail because " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteUserById(int userId) {
        Optional<User> checkUserExistOrNot = userRepository.findById(userId);
        if (checkUserExistOrNot.isPresent()) {
            User user = checkUserExistOrNot.get();
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public List<UserManyDTO> getAlllistUserAndSearching(String title, int page, int record) {
        List<User> listUser = userRepository.findByUsernameContaining(title, pagingSearchAndSorting.pageablePageSizeAndRecordOrSearchOrSort(page, record));
        List<UserManyDTO> userDTOList = new ArrayList<>();

        for (User user : listUser) {
            UserManyDTO userMany = UserManyDTO.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .address(user.getAddress())
                    .phoneNumber(user.getPhoneNumber())
                    .isActive(user.isActive())
                    .createAt(user.getCreatAt())
                    .build();

            List<UserRole> userRoleList = userRoleRepository.findByUserId(user.getId());
            List<FeedBack> listFeedbacks = user.getListFeedbacks();

            List<RoleDTO> listRoleDto = new ArrayList<>();
            List<FeedBackDTO> listFeedbackDTO = new ArrayList<>();

            for (UserRole userRole : userRoleList) {
                RoleDTO roleDTO = RoleDTO.builder()
                        .id(userRole.getRole().getId())
                        .roleName(userRole.getRole().getRoleName())
                        .createAt(userRole.getRole().getCreateAt())
                        .build();

                listRoleDto.add(roleDTO);
            }
            for (FeedBack feedback : listFeedbacks) {
                FeedBackDTO feedBackDTO = FeedBackDTO.builder()
                        .feedbackId(feedback.getId())
                        .content(feedback.getContent())
                        .rating(feedback.getRating())
                        .createAt(feedback.getCreateAt())
                        .build();

                listFeedbackDTO.add(feedBackDTO);
            }
            userMany.setListRoleDTOs(listRoleDto);
            userMany.setListFeedbackDTO(listFeedbackDTO);
            userDTOList.add(userMany);
        }

        return userDTOList;
    }

}



