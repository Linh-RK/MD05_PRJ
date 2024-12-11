package com.ra.md05_project.service.snack;

import com.ra.md05_project.dto.snack.SnackAddDTO;
import com.ra.md05_project.dto.snack.SnackUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Snack;
import com.ra.md05_project.repository.SnackRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SnackServiceImpl implements SnackService {

    @Autowired
    private SnackRepository snackRepository;

    @Override
    public Page<Snack> findAll(String search, Pageable pageable) {

        if (search == null || search.isEmpty()) {
            return snackRepository.findAll(pageable);
        } else {
            return snackRepository.findByNameContainingIgnoreCaseOrTypeContainingIgnoreCase(search, search, pageable);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Snack snack = snackRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Snack not found with id " + id));

        snack.setIsDeleted(true);
        snackRepository.save(snack);
    }

    @Override
    @Transactional
    public Snack create(@Valid SnackAddDTO snackAddDTO) {
        Snack snack = new Snack();
        snack.setName(snackAddDTO.getName());
        snack.setPrice(snackAddDTO.getPrice());
        snack.setType(snackAddDTO.getType());
        snack.setIsDeleted(false);

        return snackRepository.save(snack);
    }

    @Override
    public Snack findById(Long id) {
        return snackRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Snack not found with id " + id));
    }

    @Override
    @Transactional
    public Snack update(Long id, @Valid SnackUpdateDTO snackUpdateDTO) {
        Snack snack = snackRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Snack not found with id " + id));

        snack.setName(snackUpdateDTO.getName());
        snack.setPrice(snackUpdateDTO.getPrice());
        snack.setType(snackUpdateDTO.getType());

        return snackRepository.save(snack);
    }
}