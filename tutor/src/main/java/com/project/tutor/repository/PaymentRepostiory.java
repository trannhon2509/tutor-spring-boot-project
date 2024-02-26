package com.project.tutor.repository;

import com.project.tutor.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepostiory extends JpaRepository<Payment,Integer> {
}
