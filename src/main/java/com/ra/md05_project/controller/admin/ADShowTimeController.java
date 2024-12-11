package com.ra.md05_project.controller.admin;

import com.ra.md05_project.dto.showtime.ShowTimeAddDTO;
import com.ra.md05_project.dto.showtime.ShowTimeUpdateDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.model.entity.ver1.ShowTime;
import com.ra.md05_project.service.showtime.ShowTimeService;
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
@RequestMapping("/admin/showtime")
public class ADShowTimeController {
    @Autowired
    private ShowTimeService showTimeService;

    @GetMapping
    public ResponseEntity<Page<ShowTime>> findAllShowTime(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Sort sortOrder = Sort.by(sort);
        sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<ShowTime> ShowTimes = showTimeService.findAll(search ,pageable );
        return new ResponseEntity<>(ShowTimes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowTime> getBookingById (@PathVariable Long id) {
        return new ResponseEntity<>(showTimeService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ShowTime> createShowTime(@Valid @RequestBody ShowTimeAddDTO showTimeAddDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(showTimeService.create(showTimeAddDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowTime> updateShowTime(@PathVariable Long id,@Valid @RequestBody ShowTimeUpdateDTO showTimeUpdateDTO) throws IOException {
            return new ResponseEntity<>(showTimeService.update(id, showTimeUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShowTime(@PathVariable Long id) {
            showTimeService.delete(id);
            return new ResponseEntity<>(new ResponseDTOSuccess<>("Delete successfully",200),HttpStatus.OK);
    }
}
