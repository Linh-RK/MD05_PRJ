package com.ra.md05_project.service.payment;

import com.ra.md05_project.dto.payment.PaymentAddDTO;
import com.ra.md05_project.dto.payment.PaymentUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Payment;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PaymentService {
    Page<Payment> findAll(String search, Pageable pageable);

    void delete(Long id);

    Payment create(@Valid PaymentAddDTO payment);

    Payment findById(Long id);

    Payment update(Long id, @Valid PaymentUpdateDTO payment);
}
