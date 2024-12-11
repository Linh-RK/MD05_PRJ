package com.ra.md05_project.dto.payment;


import com.ra.md05_project.model.constant.Payment;
import com.ra.md05_project.model.constant.PaymentStatus;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentUpdateDTO {
    private Payment paymentMethod;
    private PaymentStatus paymentStatus;
    private Double amount;
    private String transactionId;
}
