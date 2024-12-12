package com.ra.md05_project.controller.admin;

import com.ra.md05_project.dto.snack.SnackAddDTO;
import com.ra.md05_project.dto.snack.SnackUpdateDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.model.entity.ver1.Snack;
import com.ra.md05_project.service.snack.SnackService;
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
@RequestMapping("/admin/snack")
public class ADSnackController {
    @Autowired
    private SnackService snackService;

    @GetMapping
    public ResponseEntity<Page<Snack>> findAllSnack(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Sort sortOrder = Sort.by(sort);
        sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Snack> Snacks = snackService.findAll(search ,pageable );
        return new ResponseEntity<>(Snacks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Snack> getSnackById (@PathVariable Long id) {
        return new ResponseEntity<>(snackService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Snack> createSnack(@Valid @RequestBody SnackAddDTO snackAddDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(snackService.create(snackAddDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Snack> updateSnack(@PathVariable Long id,@Valid @RequestBody SnackUpdateDTO snackUpdateDTO) throws IOException {
            return new ResponseEntity<>(snackService.update(id, snackUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSnack(@PathVariable Long id) {
            snackService.delete(id);
            return new ResponseEntity<>(new ResponseDTOSuccess<>("Delete successfully",200),HttpStatus.OK);
    }
}
