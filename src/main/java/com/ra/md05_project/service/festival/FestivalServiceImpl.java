package com.ra.md05_project.service.festival;

import com.ra.md05_project.dto.festival.FestivalAddDTO;
import com.ra.md05_project.dto.festival.FestivalUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.repository.FestivalRepository;
import com.ra.md05_project.service.uploadFile.UploadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class FestivalServiceImpl implements FestivalService {

    @Autowired
    private FestivalRepository festivalRepository;
    @Autowired
    private UploadService uploadService;

    @Override
    public Page<Festival> findAll(String search, Pageable pageable) {
        return (search == null || search.isBlank())
                ? festivalRepository.findAllByIsDeletedIsFalse(pageable)
                : festivalRepository.findByTitleContainingIgnoreCaseAndIsDeletedIsFalse(search, pageable);
    }

    @Override
    public void delete(Long id) {
        Festival festival = findFestivalById(id);
        Festival updatedFestival = Festival.builder()
                .id(festival.getId())
                .title(festival.getTitle())
                .image(festival.getImage())
                .startTime(festival.getStartTime())
                .endTime(festival.getEndTime())
                .isDeleted(true) // Đánh dấu xóa mềm
                .build();
        festivalRepository.save(updatedFestival);
    }

    @Override
    public Festival create(@Valid FestivalAddDTO dto) throws IOException {
        validateFestivalDates(dto.getStartTime(), dto.getEndTime());
        Festival festival = mapToEntity(dto);
        festival.setIsDeleted(false);
        return festivalRepository.save(festival);
    }

    @Override
    public Festival findById(Long id) {
        return findFestivalById(id);
    }

    @Override
    public Festival update(Long id, @Valid FestivalUpdateDTO dto) throws IOException {
        Festival existingFestival = findFestivalById(id);
        validateFestivalDates(dto.getStartTime(), dto.getEndTime());

        Festival updatedFestival = Festival.builder()
                .id(existingFestival.getId())
                .title(dto.getTitle())
                .image(dto.getImage() == null || dto.getImage().isEmpty() ? existingFestival.getImage(): uploadService.uploadFile(dto.getImage()) )
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .isDeleted(false) // Duy trì trạng thái xóa mềm nếu không được cập nhật
                .build();

        return festivalRepository.save(updatedFestival);
    }

    private Festival findFestivalById(Long id) {
        return festivalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Festival with id " + id + " not found"));
    }

    private void validateFestivalDates(LocalDate startTime, LocalDate endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
    }

    private Festival mapToEntity(FestivalAddDTO dto) throws IOException {
        return Festival.builder()
                .title(dto.getTitle())
                .image(uploadService.uploadFile(dto.getImage()))
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }
}
