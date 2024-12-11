package com.ra.md05_project.service.festival;

import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FestivalServiceImpl implements FestivalService {

    @Autowired
    private FestivalRepository festivalRepository;

    @Override
    public Page<Festival> findAll(String search, Pageable pageable) {
        if (search == null || search.isBlank()) {
            return festivalRepository.findAll(pageable);
        }
        return festivalRepository.findByTitleContainingIgnoreCase(search, pageable);
    }

    @Override
    public void delete(Long id) {
        Festival festival = festivalRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Festival with id " + id + " not found"));
        festival.setIsDeleted(true);
        festivalRepository.save(festival);
    }

    @Override
    public Festival create(Festival festival) {
        validateFestivalDates(festival);
        festival.setIsDeleted(false);
        return festivalRepository.save(festival);
    }

    @Override
    public Festival findById(Long id) {
        return festivalRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Festival with id " + id + " not found"));
    }

    @Override
    public Festival update(Long id, Festival festival) {
        Festival existingFestival = festivalRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Festival with id " + id + " not found"));

        validateFestivalDates(festival);

        existingFestival.setTitle(festival.getTitle());
        existingFestival.setImage(festival.getImage());
        existingFestival.setStartTime(festival.getStartTime());
        existingFestival.setEndTime(festival.getEndTime());
        existingFestival.setIsDeleted(festival.getIsDeleted());

        return festivalRepository.save(existingFestival);
    }

    private void validateFestivalDates(Festival festival) {
        if (festival.getStartTime().isAfter(festival.getEndTime())) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
    }
}
