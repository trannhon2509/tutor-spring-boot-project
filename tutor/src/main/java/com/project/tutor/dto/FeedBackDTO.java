package com.project.tutor.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackDTO {
        private int feedbackId;
        private String content;
        private double rating;
        private LocalDateTime createAt;
}
