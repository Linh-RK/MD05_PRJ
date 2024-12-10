package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.TicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPriceRepository extends JpaRepository<TicketPrice, Long> {
    @Query("SELECT tp.price FROM TicketPrice tp " +
            "WHERE tp.movieType = :movieType AND tp.seatType = :seatType " +
            "AND tp.dayType = :dayType AND tp.timeSlot = :timeSlot")
    Double findPrice(@Param("movieType") String movieType,
                     @Param("seatType") String seatType,
                     @Param("dayType") String dayType,
                     @Param("timeSlot") String timeSlot);
}
