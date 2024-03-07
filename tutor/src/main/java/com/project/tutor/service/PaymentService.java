package com.project.tutor.service;

import com.project.tutor.dto.PaymentDTO;
import com.project.tutor.request.PaymentRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {
  public List<PaymentDTO> getAllPayment();
  public boolean addPayment (PaymentRequest request);
  public boolean updatePayment (int paymentId , PaymentRequest request);
  public boolean deletePayment (int paymentId);

  public PaymentDTO getPaymentById (int paymentId);
}
