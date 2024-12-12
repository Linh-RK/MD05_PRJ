package com.ra.md05_project.controller.admin;

import com.ra.md05_project.dto.festival.FestivalAddDTO;
import com.ra.md05_project.dto.festival.FestivalUpdateDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.entity.ver1.Comment;
import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.service.festival.FestivalService;
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
@RequestMapping("/admin/festival")
public class ADFestivalController {
    @Autowired
    private FestivalService festivalService;
   
    @GetMapping
    public ResponseEntity<Page<Festival>> findAllFestival(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Sort sortOrder = Sort.by(sort);
        sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Festival> Festivals = festivalService.findAll(search ,pageable );
        return new ResponseEntity<>(Festivals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Festival> getFestivalById (@PathVariable Long id) {
        return new ResponseEntity<>(festivalService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Festival> createFestival(@Valid @ModelAttribute FestivalAddDTO festivalAddDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(festivalService.create(festivalAddDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Festival> updateFestival(@PathVariable Long id,@Valid @ModelAttribute FestivalUpdateDTO festivalUpdateDTO) throws IOException {
            return new ResponseEntity<>(festivalService.update(id, festivalUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFestival(@PathVariable Long id) {
            festivalService.delete(id);
            return new ResponseEntity<>(new ResponseDTOSuccess<>("Delete successfully",200),HttpStatus.OK);
    }
}
