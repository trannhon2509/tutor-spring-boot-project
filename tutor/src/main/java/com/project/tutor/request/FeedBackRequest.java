package com.project.tutor.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.config.annotation.web.oauth2.login.OAuth2LoginSecurityMarker;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackRequest {
    private String content;
    private double rating;
    private LocalDateTime createAt;
    private int userId;
    private int tutorId;
}
