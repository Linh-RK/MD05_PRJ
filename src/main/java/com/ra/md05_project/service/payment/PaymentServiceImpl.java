package com.ra.md05_project.service.payment;

import com.ra.md05_project.dto.payment.PaymentAddDTO;
import com.ra.md05_project.dto.payment.PaymentUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Booking;
import com.ra.md05_project.model.entity.ver1.Payment;
import com.ra.md05_project.repository.BookingRepository;
import com.ra.md05_project.repository.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Page<Payment> findAll(String search, Pageable pageable) {
        if (search.isEmpty()) {
            return paymentRepository.findAll(pageable);
        } else {
            // Tìm kiếm theo transactionId hoặc bookingId
            return paymentRepository.findByTransactionIdContainingIgnoreCaseOrBooking_IdContaining(search, search, pageable);
        }
    }

    @Override
    @Transactional
    public Payment create(PaymentAddDTO paymentAddDTO) {
        // Kiểm tra xem booking có tồn tại hay không
        Booking booking = bookingRepository.findById(paymentAddDTO.getBookingId())
                .orElseThrow(() -> new NoSuchElementException("Booking not found"));

        // Sử dụng Builder để tạo đối tượng Payment
        Payment payment = Payment.builder()
                .booking(booking)
                .paymentMethod(paymentAddDTO.getPaymentMethod())
                .paymentStatus(paymentAddDTO.getPaymentStatus())
                .paymentTime(LocalDateTime.now())
                .amount(paymentAddDTO.getAmount())
                .transactionId(paymentAddDTO.getTransactionId())
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public Payment update(Long id, PaymentUpdateDTO paymentUpdateDTO) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Payment not found"));

        payment = Payment.builder()
                .id(id)
                .booking(payment.getBooking())
                .paymentMethod(paymentUpdateDTO.getPaymentMethod())
                .paymentStatus(paymentUpdateDTO.getPaymentStatus())
                .paymentTime(LocalDateTime.now())
                .amount(paymentUpdateDTO.getAmount())
                .transactionId(paymentUpdateDTO.getTransactionId())
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Payment not found"));

        paymentRepository.delete(payment);
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Payment not found"));
    }
}