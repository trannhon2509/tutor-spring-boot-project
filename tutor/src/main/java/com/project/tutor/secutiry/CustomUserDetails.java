package com.project.tutor.secutiry;

import com.project.tutor.mapper.UserRole;
import com.project.tutor.repository.UserRepository;
import com.project.tutor.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRole user = userRoleRepository.findByUserUsername(username);
        if (user == null) {
            throw new BadCredentialsException("Cannot found username : " + username);
        }

        // CHECK ACCOUNT ACTIVATED OR NOT
        if (!user.getUser().isActive()) {
            throw new BadCredentialsException("Account don't acive : ");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName()));
        System.out.println("User roles : " + grantedAuthorities);
        return new org.springframework.security.core.userdetails.User(user.getUser().getUsername(), user.getUser().getPassword(), grantedAuthorities);
    }


    public Collection<? extends GrantedAuthority> roleAuthorization(Collection<UserRole> roles) {
        return roles.stream().map(
                role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getRoleName())
        ).collect(Collectors.toList());
    }
}

