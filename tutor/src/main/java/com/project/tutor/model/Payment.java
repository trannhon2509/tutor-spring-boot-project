package com.project.tutor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", unique = true , nullable = false)
    private int id;

    @Column(name ="payment_name")
    private String paymentName;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name ="payment_price")
    private double paymentPrice;

    @Column(name ="payment_status")
    private String paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;
}
