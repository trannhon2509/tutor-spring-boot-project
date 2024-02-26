//package com.project.tutor.implementservice;
//
//import com.project.tutor.mapper.UserRole;
//import com.project.tutor.model.Role;
//import com.project.tutor.model.User;
//import com.project.tutor.repository.UserRepository;
//import com.project.tutor.repository.UserRoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class CustomUserDetailService implements UserDetailsService {
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    UserRoleRepository userRoleRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        User user = userRepository.findByUsername(username);
////        if(user == null){
////            throw  new BadCredentialsException("Cannot found username : " + username);
////        }
////
////        // CHECK ACCOUNT ACTIVATED OR NOT
////        if(!user.isActive()){
////            throw  new BadCredentialsException("Account don't acive : ");
////        }
////        List<GrantedAuthority> auth = new ArrayList<>();
////
////        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),auth);
//
//        UserRole user = userRoleRepository.findByUserUsername(username);
//        if(user == null){
//            throw  new BadCredentialsException("Cannot found username : " + username);
//        }
//
//        // CHECK ACCOUNT ACTIVATED OR NOT
//        if(!user.getUser().isActive()){
//            throw  new BadCredentialsException("Account don't acive : ");
//        }
//
//
//        return new org.springframework.security.core.userdetails.User(user.getUser().getUsername(),user.getUser().getPassword(),roleAuthorization(user.getUser().getListUserRoles()));
//    }
//
//    public Collection<? extends GrantedAuthority> roleAuthorization (Collection <UserRole> roles){
//        return roles.stream().map(
//                role -> new SimpleGrantedAuthority(role.getRole().getRoleName())
//        ).collect(Collectors.toList());
//    }
//}
