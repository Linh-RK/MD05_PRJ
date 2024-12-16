package com.ra.md05_project.service.showtime;

import com.ra.md05_project.dto.showtime.ShowTimeAddDTO;
import com.ra.md05_project.dto.showtime.ShowTimeResponseDTO;
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

    // Phương thức chuyển đổi từ ShowTime sang ShowTimeResponseDTO
    private ShowTimeResponseDTO convertToShowTimeResponseDTO(ShowTime showTime) {
        return ShowTimeResponseDTO.builder()
                .id(showTime.getId())
                .movieId(showTime.getMovie().getId())  // Lấy ID của Movie
                .roomId(showTime.getRoom().getId())    // Lấy ID của Room
                .startTime(showTime.getStartTime())
                .endTime(showTime.getEndTime())
                .createdAt(showTime.getCreatedAt())
                .updatedAt(showTime.getUpdatedAt())
                .isDeleted(showTime.getIsDeleted())
//                .type(showTime.getType())  // Loại Movie
                .build();
    }

    @Override
    public Page<ShowTimeResponseDTO> findAll(String search, Pageable pageable) {
        Page<ShowTime> showTimes;
        if (search.isEmpty()) {
            showTimes = showTimeRepository.findAllByIsDeletedIsFalse(pageable);
        } else {
            showTimes = showTimeRepository.findByMovie_TitleContainingIgnoreCaseOrRoom_RoomNameContainingIgnoreCaseAndIsDeletedIsFalse(search, search, pageable);
        }

        // Chuyển đổi từ Page<ShowTime> sang Page<ShowTimeResponseDTO>
        return showTimes.map(this::convertToShowTimeResponseDTO);
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
    public ShowTimeResponseDTO create(@Valid ShowTimeAddDTO showTimeAddDTO) {
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
//                .type(showTimeAddDTO.getType())
                .build();

        showTime = showTimeRepository.save(showTime);

        return convertToShowTimeResponseDTO(showTime);  // Chuyển đổi sang ShowTimeResponseDTO
    }

    @Override
    @Transactional
    public ShowTimeResponseDTO update(Long id, @Valid ShowTimeUpdateDTO showTimeUpdateDTO) {
        ShowTime showTime = showTimeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ShowTime not found with id " + id));

        Movie movie = movieRepository.findById(showTimeUpdateDTO.getMovieId())
                .orElseThrow(() -> new NoSuchElementException("Movie not found"));

        Room room = roomRepository.findById(showTimeUpdateDTO.getRoomId())
                .orElseThrow(() -> new NoSuchElementException("Room not found"));

        // Cập nhật các thuộc tính của ShowTime, bao gồm startTime, updatedAt, type, v.v.
        showTime.setMovie(movie);
        showTime.setRoom(room);
        showTime.setStartTime(showTimeUpdateDTO.getStartTime());
        showTime.setUpdatedAt(LocalDate.now());  // Cập nhật thời gian sửa đổi
//        showTime.setType(showTimeUpdateDTO.getType());

        // Tính toán lại thời gian kết thúc (endTime)
        showTime.calculateEndTime(); // Phương thức này sẽ tự động tính toán endTime

        // Lưu đối tượng ShowTime đã cập nhật
        showTime = showTimeRepository.save(showTime);
        return convertToShowTimeResponseDTO(showTime);  // Chuyển đổi sang ShowTimeResponseDTO
    }

    @Override
    public ShowTimeResponseDTO findById(Long id) {
        ShowTime showTime = showTimeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ShowTime not found with id " + id));
        return convertToShowTimeResponseDTO(showTime);  // Chuyển đổi sang ShowTimeResponseDTO
    }
}
