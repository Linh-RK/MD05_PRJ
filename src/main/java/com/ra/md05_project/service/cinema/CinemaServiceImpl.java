package com.ra.md05_project.service.cinema;

import com.ra.md05_project.dto.cinema.CinemaAddDTO;
import com.ra.md05_project.dto.cinema.CinemaUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Cinema;
import com.ra.md05_project.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;



    @Override
    public Page<Cinema> findAll(String search, Pageable pageable) {
        if (search == null || search.isEmpty()) {
            return cinemaRepository.findAllByIsDeletedIsFalse(pageable);
        }
        return cinemaRepository.findByNameContainingIgnoreCaseAndIsDeletedFalseAndIsDeletedIsFalse(search, pageable);
    }

    @Override
    public void delete(Long id) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(id);
        if (cinemaOptional.isPresent()) {
            Cinema cinema = cinemaOptional.get();
            cinema.setIsDeleted(true); // Soft delete
            cinema.setUpdatedDate(LocalDate.now());
            cinemaRepository.save(cinema);
        } else {
            throw new NoSuchElementException("Cinema with id " + id + " not found");
        }
    }

    @Override
    public Cinema create(CinemaAddDTO cinemaAddDTO) {
        Cinema cinema = Cinema.builder()
                .name(cinemaAddDTO.getName())
                .address(cinemaAddDTO.getAddress())
                .phoneNumber(cinemaAddDTO.getPhoneNumber())
                .isDeleted(false)
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();
        return cinemaRepository.save(cinema);
    }

    @Override
    public Cinema findById(Long id) {
        return cinemaRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new NoSuchElementException("Cinema with id " + id + " not found"));
    }

    @Override
    public Cinema update(Long id, CinemaUpdateDTO cinemaUpdateDTO) {
        Cinema cinema = cinemaRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NoSuchElementException("Cinema with id " + id + " not found"));

        if (cinemaUpdateDTO.getName() != null) {
            cinema.setName(cinemaUpdateDTO.getName());
        }
        if (cinemaUpdateDTO.getAddress() != null) {
            cinema.setAddress(cinemaUpdateDTO.getAddress());
        }
        if (cinemaUpdateDTO.getPhoneNumber() != null) {
            cinema.setPhoneNumber(cinemaUpdateDTO.getPhoneNumber());
        }

        cinema.setUpdatedDate(LocalDate.now());
        return cinemaRepository.save(cinema);
    }
}
