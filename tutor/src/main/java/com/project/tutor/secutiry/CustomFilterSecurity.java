package com.project.tutor.secutiry;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomFilterSecurity {

    private JwtValidator jwtValidator;

    @Autowired
    public void setJwtValidator(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Autowired
    CustomUserDetails customUserDetails;

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setUserDetailsService(customUserDetails);
        dap.setPasswordEncoder(passwordEncoder());
        return dap;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())

                .addFilterBefore(jwtValidator, UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/auth/**", "/api/tutor/add",
                                        "/api/subject/list", "/api/tutor/list",
                                        "/api/teaching/list").permitAll()

                                .requestMatchers("/api/booking/add", "/api/feedback/**").hasAuthority("ROLE_USER")

                                .requestMatchers(
                                        // USER AND ROLE
                                        "/api/user/**", "/api/role/**",
                                        // SUBJECT
                                        "/api/subject/add", "/api/subject/delete/*", "/api/subject/update/*",
                                        // TUTOR
                                        "/api/tutor/delete/*", "/api/tutor/update/*", "/api/tutor/*","/api/tutor/list/approved",
                                        // TEACHING
                                        "/api/teaching/add", "/api/teaching/delete/*", "/api/teaching/update/*", "/api/teaching/*",
                                        // PAYMENT
                                        "/api/payment/list", "/api/payment/add", "/api/payment/delete/*", "/api/payment/update/*",
                                        //BOOKING
                                        "/api/booking/list", "/api/booking/delete/*","/api/booking/update/*"
                                ).hasAuthority("ROLE_ADMIN")

                                .requestMatchers("/api/subject/list", "/api/tutor/list","/api/booking/*"
                                ).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")


                                .anyRequest().authenticated()).
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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
