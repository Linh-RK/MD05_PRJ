package com.ra.md05_project.dto.payment;

import com.ra.md05_project.model.constant.PaymentMethod;
import com.ra.md05_project.model.constant.PaymentStatus;
import com.ra.md05_project.model.entity.ver1.Booking;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDTO {
    private Long id;

    private Booking booking;

    private PaymentMethod paymentMethod; // Enum VIETQR, VNPAY, VIETTEL_PAY, PAYPAL

    private PaymentStatus paymentStatus; // Enum PENDING, COMPLETED, FAILED, CANCELLED

    private LocalDateTime paymentTime;

    private Double amount;

    private String transactionId;
}
