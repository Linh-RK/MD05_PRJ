package com.ra.md05_project.controller.admin;

import com.ra.md05_project.dto.payment.PaymentAddDTO;
import com.ra.md05_project.dto.payment.PaymentUpdateDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.model.entity.ver1.Payment;
import com.ra.md05_project.service.payment.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/admin/payment")
public class ADPaymentController {
    @Autowired
    private PaymentService paymentService;
    @GetMapping
    public ResponseEntity<Page<Payment>> findAllPayment(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Sort sortOrder = Sort.by(sort);
        sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Payment> payments = paymentService.findAll(search ,pageable );
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById (@PathVariable Long id) {
        return new ResponseEntity<>(paymentService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody PaymentAddDTO paymentAddDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.create(paymentAddDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id,@Valid @RequestBody PaymentUpdateDTO paymentUpdateDTO) throws IOException {
            return new ResponseEntity<>(paymentService.update(id, paymentUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
            paymentService.delete(id);
            return new ResponseEntity<>(new ResponseDTOSuccess<>("Delete successfully",200),HttpStatus.OK);
    }
}
