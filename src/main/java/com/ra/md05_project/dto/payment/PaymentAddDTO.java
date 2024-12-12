package com.ra.md05_project.dto.payment;

import com.ra.md05_project.model.constant.PaymentMethod;
import com.ra.md05_project.model.constant.PaymentStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentAddDTO {
    private Long bookingId;
    private PaymentMethod paymentMethod; // Enum VIETQR, VNPAY, VIETTEL_PAY, PAYPAL
    private PaymentStatus paymentStatus; // Enum PENDING, COMPLETED, FAILED, CANCELLED
    private Double amount;
    private String transactionId; //ma GD ben thu 3
}
