package com.ra.md05_project.advice;

import com.ra.md05_project.exception.CustomException;
import com.ra.md05_project.model.error.DataError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DataError<Map<String, String>> handleNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        // Thêm một thông báo tổng quát vào map lỗi
        errors.put("message", "Data must be unique");
        return new DataError<>(errors, HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST); // 400
    }


    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DataError< String> handleNoSuchElementException(NoSuchElementException e) {
        return new DataError<>(e.getMessage(),404,HttpStatus.NOT_FOUND); //400
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DataError< String> handleResourceNotFoundException(NoResourceFoundException e) {
        return new DataError< String>(e.getMessage(), 404, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public DataError< String> handleResourceNotFoundException(DataIntegrityViolationException e) {
        return new DataError< String>(e.getMessage(), 400, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustom(CustomException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("message",ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

}

