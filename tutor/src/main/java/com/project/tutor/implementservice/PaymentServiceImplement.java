package com.project.tutor.implementservice;

import com.project.tutor.dto.PaymentDTO;
import com.project.tutor.model.Payment;
import com.project.tutor.model.Tutor;
import com.project.tutor.repository.PaymentRepostiory;
import com.project.tutor.request.PaymentRequest;
import com.project.tutor.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PaymentServiceImplement implements PaymentService {


    PaymentRepostiory paymentRepostiory;
    @Override
    public boolean addPayment(PaymentRequest request) {
        try {
            String paymentName = request.getPaymentName();
            LocalDateTime createAt = request.getCreateAt();
            double paymentPrice = request.getPaymentPrice();
            String paymentStatus = request.getPaymentStatus();

            Tutor tutor = new Tutor();
            tutor.setId(request.getTutorId());

            if (createAt == null) {
                createAt = LocalDateTime.now();
            }

            Payment newPayment = new Payment();
            newPayment.setPaymentName(paymentName);
            newPayment.setCreateAt(createAt);
            newPayment.setPaymentPrice(paymentPrice);
            newPayment.setPaymentStatus(paymentStatus);
            newPayment.setTutor(tutor);

            paymentRepostiory.save(newPayment);
            return true;

        } catch (Exception e) {
            throw new BadCredentialsException("Add payment fail!");
        }
    }

    @Override
    public boolean updatePayment(int paymentId, PaymentRequest request) {
        try {
            Optional<Payment> checkPaymentExistOrNot = paymentRepostiory.findById(paymentId);
            if (checkPaymentExistOrNot.isPresent()) {

                Payment payment = checkPaymentExistOrNot.get();
                payment.setPaymentName(request.getPaymentName());
                payment.setCreateAt(request.getCreateAt());
                payment.setPaymentPrice(request.getPaymentPrice());
                payment.setPaymentStatus(request.getPaymentStatus());

                paymentRepostiory.save(payment);
                return true;
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Cannot update payment with id :" + paymentId);
        }
        return false;
    }

    @Override
    public boolean deletePayment(int paymentId) {
        try {
            Optional<Payment> checkPaymentExistOrNot = paymentRepostiory.findById(paymentId);
            if (checkPaymentExistOrNot.isPresent()) {

                Payment payment = checkPaymentExistOrNot.get();
                paymentRepostiory.delete(payment);

                return true;
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Cannot delete payment with id :" + paymentId);
        }
        return false;
    }

    @Override
    public PaymentDTO getPaymentById(int paymentId) {
        try {
            Optional<Payment> checkPaymentExistOrNot = paymentRepostiory.findById(paymentId);
            if (checkPaymentExistOrNot.isPresent()) {
                Payment payment = checkPaymentExistOrNot.get();

                return PaymentDTO.builder()
                        .paymentId(payment.getId())
                        .createAt(payment.getCreateAt())
                        .paymentPrice(payment.getPaymentPrice())
                        .paymentStatus(payment.getPaymentStatus())
                        .build();
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Cannot found payment with id :" + paymentId);
        }
        return null;
    }



    @Override
    public List<PaymentDTO> getAllPayment() {
        List<Payment> listPayments = paymentRepostiory.findAll();
        List<PaymentDTO> listPaymentDTOs = new ArrayList<>();

        for (Payment payment : listPayments) {
            PaymentDTO paymentDTO = PaymentDTO.builder()
                    .paymentId(payment.getId())
                    .createAt(payment.getCreateAt())
                    .paymentPrice(payment.getPaymentPrice())
                    .paymentStatus(payment.getPaymentStatus())
                    .build();

            listPaymentDTOs.add(paymentDTO);
        }
        return listPaymentDTOs;
    }
}
