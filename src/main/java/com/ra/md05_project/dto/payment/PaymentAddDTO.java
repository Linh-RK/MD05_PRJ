package com.ra.md05_project.dto.payment;

import com.ra.md05_project.model.constant.Payment;
import com.ra.md05_project.model.constant.PaymentStatus;
import com.ra.md05_project.model.entity.ver1.Booking;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentAddDTO {
    private Long bookingId;
    private Payment paymentMethod; // Enum VIETQR, VNPAY, VIETTEL_PAY, PAYPAL
    private PaymentStatus paymentStatus; // Enum PENDING, COMPLETED, FAILED, CANCELLED
    private Double amount;
    private String transactionId;
}
