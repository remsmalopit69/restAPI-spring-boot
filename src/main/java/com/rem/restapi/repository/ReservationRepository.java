package com.rem.restapi.repository;

import com.rem.restapi.entity.Reservation;
import com.rem.restapi.entity.VirtualMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Reservation r " +
    "WHERE r.virtualMachine = :vm " +
    "AND r.startTime < :endTime " +
    "AND r.endTime > :startTime")
    boolean existsOverlappingReservation(
            @Param("vm") VirtualMachine vm,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
    boolean existsByVirtualMachine(VirtualMachine virtualMachine);
}
