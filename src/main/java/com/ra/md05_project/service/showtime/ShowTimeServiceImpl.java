package com.ra.md05_project.service.showtime;

import com.ra.md05_project.dto.showtime.ShowTimeAddDTO;
import com.ra.md05_project.dto.showtime.ShowTimeUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Movie;
import com.ra.md05_project.model.entity.ver1.Room;
import com.ra.md05_project.model.entity.ver1.ShowTime;
import com.ra.md05_project.repository.MovieRepository;
import com.ra.md05_project.repository.RoomRepository;
import com.ra.md05_project.repository.ShowTimeRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ShowTimeServiceImpl implements ShowTimeService {

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Page<ShowTime> findAll(String search, Pageable pageable) {
        if (search.isEmpty()) {
            return showTimeRepository.findAll(pageable);
        } else {
            return showTimeRepository.findByMovie_TitleContainingIgnoreCaseOrRoom_RoomNameContainingIgnoreCase(search, search, pageable);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ShowTime showTime = showTimeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ShowTime not found with id " + id));

        showTime.setIsDeleted(true);
        showTimeRepository.save(showTime);
    }

    @Override
    @Transactional
    public ShowTime create(@Valid ShowTimeAddDTO showTimeAddDTO) {
        Movie movie = movieRepository.findById(showTimeAddDTO.getMovieId())
                .orElseThrow(() -> new NoSuchElementException("Movie not found"));

        Room room = roomRepository.findById(showTimeAddDTO.getRoomId())
                .orElseThrow(() -> new NoSuchElementException("Room not found"));

        ShowTime showTime = ShowTime.builder()
                .movie(movie)
                .room(room)
                .startTime(showTimeAddDTO.getStartTime())
                .createdAt(LocalDate.now())
                .isDeleted(false)
                .type(showTimeAddDTO.getType())
                .build();

        return showTimeRepository.save(showTime);
    }

    @Override
    @Transactional
    public ShowTime update(Long id, @Valid ShowTimeUpdateDTO showTimeUpdateDTO) {
        ShowTime showTime = showTimeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ShowTime not found with id " + id));

        Movie movie = movieRepository.findById(showTimeUpdateDTO.getMovieId())
                .orElseThrow(() -> new NoSuchElementException("Movie not found"));

        Room room = roomRepository.findById(showTimeUpdateDTO.getRoomId())
                .orElseThrow(() -> new NoSuchElementException("Room not found"));

        ShowTime showTimeUpdate = ShowTime.builder()
                .id(id)
                .movie(movie)
                .room(room)
                .startTime(showTimeUpdateDTO.getStartTime())
                .updatedAt(LocalDate.now())
                .type(showTimeUpdateDTO.getType())
                .build();

        return showTimeRepository.save(showTimeUpdate);
    }

    @Override
    public ShowTime findById(Long id) {
        return showTimeRepository.findById(id).orElseThrow(()->new NoSuchElementException("Show time not found with id " + id));
    }
}