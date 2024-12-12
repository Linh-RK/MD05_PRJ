package com.ra.md05_project.model.entity.ver1;
import com.ra.md05_project.model.constant.PaymentMethod;
import com.ra.md05_project.model.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //lien ket bang booking
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false) // Khóa ngoại đến Booking
    private Booking booking;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod; // Enum VIETQR, VNPAY, VIETTEL_PAY, PAYPAL

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus; // Enum PENDING, COMPLETED, FAILED, CANCELLED

    @Column(name = "payment_time", nullable = false)
    private LocalDateTime paymentTime;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "transaction_id", length = 255)
    private String transactionId;

}
