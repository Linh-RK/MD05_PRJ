package com.ra.md05_project.controller.admin;

import com.ra.md05_project.dto.cinema.CinemaAddDTO;
import com.ra.md05_project.dto.cinema.CinemaUpdateDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.entity.ver1.Category;
import com.ra.md05_project.model.entity.ver1.Cinema;
import com.ra.md05_project.model.entity.ver1.Cinema;
import com.ra.md05_project.service.cinema.CinemaService;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/admin/cinema")
public class ADCinemaController {
    @Autowired
    private CinemaService cinemaService;

    @GetMapping
    public ResponseEntity<Page<Cinema>> findAllCinema(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Sort sortOrder = Sort.by(sort);
        sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Cinema> cinemas = cinemaService.findAll(search ,pageable );
        return new ResponseEntity<>(cinemas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cinema> getCinemaById (@PathVariable Long id) {
        return new ResponseEntity<>(cinemaService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cinema> createCinema(@Valid @RequestBody CinemaAddDTO cinemaAddDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(cinemaService.create(cinemaAddDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cinema> updateCinema(@PathVariable Long id,@Valid @RequestBody CinemaUpdateDTO cinemaUpdateDTO) throws IOException {
            return new ResponseEntity<>(cinemaService.update(id, cinemaUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCinema(@PathVariable Long id) {
            cinemaService.delete(id);
            return new ResponseEntity<>(new ResponseDTOSuccess<>("Delete successfully",200),HttpStatus.OK);
    }

}
