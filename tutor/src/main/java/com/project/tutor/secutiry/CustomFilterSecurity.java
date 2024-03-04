package com.project.tutor.secutiry;


import com.project.tutor.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class CustomFilterSecurity {

    private JwtValidator jwtValidator;
    @Autowired
    public void setJwtValidator (JwtValidator jwtValidator){
        this.jwtValidator = jwtValidator;
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider (UserDetailsService userService){
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setUserDetailsService(userService);
        dap.setPasswordEncoder(passwordEncoder());
        return dap;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
        authorizeHttpRequests(
                authorize -> authorize.requestMatchers("/auth/**").permitAll()
//                        .requestMatchers("/api/**").hasAuthority("ADMIN")
//                        .requestMatchers("/booking/**").hasAuthority("USER")
//                        .requestMatchers("/feedback/**").hasAuthority("USER")
                        .requestMatchers("/api/booking/add","/api/feedback/add").hasAuthority("USER")
                        .requestMatchers("/api/user/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()).
        addFilterBefore( jwtValidator, UsernamePasswordAuthenticationFilter.class).
        csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration cfg = new CorsConfiguration();
                        cfg.setAllowedOrigins(Arrays.asList(
                                "http://localhost:3000", "http://localhost:8080"
                        ));
                        cfg.setAllowedMethods(Collections.singletonList("*"));
                        cfg.setAllowCredentials(true);
                        cfg.setAllowedHeaders(Collections.singletonList("*"));
                        cfg.setExposedHeaders(Arrays.asList("Authorization"));
                        cfg.setMaxAge(3600L);
                        return cfg;
                    }
                })).httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
