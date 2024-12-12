package com.ra.md05_project.repository;

import com.ra.md05_project.model.constant.DayType;
import com.ra.md05_project.model.constant.MovieType;
import com.ra.md05_project.model.constant.SeatType;
import com.ra.md05_project.model.constant.TimeSlot;
import com.ra.md05_project.model.entity.ver1.TicketPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPriceRepository extends JpaRepository<TicketPrice, Long> {
    @Query("SELECT tp.price FROM TicketPrice tp " +
            "WHERE tp.movieType = :movieType AND tp.seatType = :seatType " +
            "AND tp.dayType = :dayType AND tp.timeSlot = :timeSlot")
    Double findPrice(@Param("movieType") MovieType movieType,
                     @Param("seatType") SeatType seatType,
                     @Param("dayType") DayType dayType,
                     @Param("timeSlot") TimeSlot timeSlot);



    Page<TicketPrice> findAllByIsDeletedIsFalse(Pageable pageable);

    Page<TicketPrice> findByMovieTypeContainingIgnoreCaseOrSeatTypeContainingIgnoreCaseAndIsDeletedIsFalse(String search, String search1, Pageable pageable);
}
