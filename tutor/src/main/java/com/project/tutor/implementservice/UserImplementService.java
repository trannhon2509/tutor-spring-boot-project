package com.project.tutor.implementservice;

import com.project.tutor.dto.FeedBackDTO;
import com.project.tutor.dto.RoleDTO;
import com.project.tutor.many.dto.LearningManyDTO;
import com.project.tutor.many.dto.UserManyDTO;
import com.project.tutor.mapper.UserRole;
import com.project.tutor.model.FeedBack;
import com.project.tutor.model.Role;
import com.project.tutor.model.User;
import com.project.tutor.repository.FeedBackRepository;
import com.project.tutor.repository.RoleRepository;
import com.project.tutor.repository.UserRepository;
import com.project.tutor.repository.UserRoleRepository;
import com.project.tutor.request.UserRequest;
import com.project.tutor.respone.ResponeDataAuth;
import com.project.tutor.secutiry.CustomUserDetails;
import com.project.tutor.secutiry.JwtProvider;
import com.project.tutor.service.EmailService;
import com.project.tutor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
@Transactional(rollbackFor = Exception.class)
public class UserImplementService implements UserService {
    public final static ResponeDataAuth data = new ResponeDataAuth();
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;
    private UserRoleRepository userRoleRepository;
    private EmailService emailService;

    private CustomUserDetails customUserDetails;

    private FeedBackRepository feedBackRepository;

    @Autowired
    public UserImplementService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                                @Lazy JwtProvider jwtProvider, UserRoleRepository userRoleRepository, EmailService emailService,
                                CustomUserDetails customUserDetails, FeedBackRepository feedBackRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.userRoleRepository = userRoleRepository;
        this.emailService = emailService;
        this.customUserDetails = customUserDetails;
        this.feedBackRepository = feedBackRepository;
    }

    @Override
    @Transactional
    public ResponeDataAuth signup(UserRequest request) {
        ResponeDataAuth data = new ResponeDataAuth();
        try {
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
                throw new BadCredentialsException("Username  already exists for another account");
            }
            if (existEmail != null) {
                throw new BadCredentialsException("Gmail already exists for another account");
            }
            if (!password.equals(passwordRepeat)) {
                throw new BadCredentialsException("Passowrd repeat not macth");
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
//            emailService.sendEmailActive(newUser.getEmail(), newUser.getActiveCode());

            // Tạo và gán authentication token
//            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getUsername(), savedUser.getPassword());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String token = jwtProvider.generateToken(username);
//            data.setJwt(token);
            data.setMsg("Signup success");
            data.setData(true);
            return data;
        } catch (Exception e) {
            data.setToken(null);
            data.setMsg("Signup failed");
            data.setData(false);
            return data;
        }
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

    @Override
    public List<LearningManyDTO> getAllListLearning() {
        List<User> listUser = userRepository.findAll();
        List<LearningManyDTO> listLearningMany = new ArrayList<>();

        for (User user : listUser) {
            LearningManyDTO learningMany = new LearningManyDTO();

        }
        return null;
    }

    public Authentication authentication(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public ResponeDataAuth signin(UserRequest request) {
        ResponeDataAuth data = new ResponeDataAuth();
        try {
            String username = request.getUsername();
            String password = request.getPassword();

            Authentication authentication = authentication(username, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtProvider.generateToken(username);

            data.setToken(token);
            data.setMsg("Signin success");
            data.setData(true);
            return data;
        } catch (Exception e) {
            data.setToken(null);
            data.setMsg("Signin fail");
            data.setData(false);
            return data;
        }
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

            UserManyDTO userMany = new UserManyDTO();
            userMany.setUserId(user.getId());
            userMany.setUsername(user.getUsername());
            userMany.setPassword(user.getPassword());
            userMany.setEmail(user.getEmail());
            userMany.setFirstName(user.getFirstName());
            userMany.setLastName(user.getLastName());
            userMany.setAddress(user.getAddress());
            userMany.setPhoneNumber(user.getPhoneNumber());
            userMany.setActive(user.isActive());
            userMany.setCreateAt(user.getCreatAt());

            List<FeedBack> listFeedbacks = user.getListFeedbacks();

            List<RoleDTO> roleDTOList = userRoles.stream()
                    .map(userRole -> {
                        RoleDTO roleDTO = new RoleDTO();
                        roleDTO.setId(userRole.getRole().getId());
                        roleDTO.setRoleName(userRole.getRole().getRoleName());
                        roleDTO.setCreateAt(userRole.getRole().getCreateAt());
                        return roleDTO;
                    })
                    .collect(Collectors.toList());

            List<FeedBackDTO> listFeedBackDTO = listFeedbacks.stream().map(
                    feedback -> {
                        FeedBackDTO feedBackDTO = new FeedBackDTO();
                        feedBackDTO.setFeedbackId(feedback.getId());
                        feedBackDTO.setContent(feedback.getContent());
                        feedBackDTO.setRating(feedback.getRating());
                        feedBackDTO.setContent(feedback.getContent());
                        feedBackDTO.setContent(feedback.getContent());

                        return feedBackDTO;
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
            throw new RuntimeException("Active account fail!" + e.getMessage());
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
    public List<UserManyDTO> getAllUser() {
        List<User> listUser = userRepository.findAll();
        List<UserManyDTO> userDTOList = new ArrayList<>();

        for (User user : listUser) {
            UserManyDTO userMany = new UserManyDTO();

            userMany.setUserId(user.getId());
            userMany.setUsername(user.getUsername());
            userMany.setPassword(user.getPassword());
            userMany.setEmail(user.getEmail());
            userMany.setFirstName(user.getFirstName());
            userMany.setLastName(user.getLastName());
            userMany.setAddress(user.getAddress());
            userMany.setPhoneNumber(user.getPhoneNumber());
            userMany.setActive(user.isActive());
            userMany.setCreateAt(user.getCreatAt());

            List<UserRole> userRoleList = userRoleRepository.findByUserId(user.getId());
            List<FeedBack> listFeedbacks = user.getListFeedbacks();

            List<RoleDTO> listRoleDto = new ArrayList<>();
            List<FeedBackDTO> listFeedbackDTO = new ArrayList<>();

            for (UserRole userRole : userRoleList) {
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setId(userRole.getRole().getId());
                roleDTO.setRoleName(userRole.getRole().getRoleName());
                roleDTO.setCreateAt(userRole.getRole().getCreateAt());

                listRoleDto.add(roleDTO);
            }
            for (FeedBack feedBack : listFeedbacks) {
                FeedBackDTO feedBackDTO = new FeedBackDTO();
                feedBackDTO.setFeedbackId(feedBack.getId());
                feedBackDTO.setRating(feedBack.getRating());
                feedBackDTO.setContent(feedBack.getContent());
                feedBackDTO.setCreateAt(feedBack.getCreateAt());

                listFeedbackDTO.add(feedBackDTO);
            }
            userMany.setListRoleDTOs(listRoleDto);
            userMany.setListFeedbackDTO(listFeedbackDTO);
            userDTOList.add(userMany);
        }

        return userDTOList;
    }

}



