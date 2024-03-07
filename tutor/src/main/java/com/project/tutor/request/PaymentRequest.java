package com.project.tutor.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String paymentName;
    private LocalDateTime createAt;
    private double paymentPrice;
    private String paymentStatus;
    private int tutorId;
}
