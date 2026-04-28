package com.rem.restapi.controller;

import com.rem.restapi.entity.Reservation;
import com.rem.restapi.entity.Student;
import com.rem.restapi.repository.ReservationRepository;
import com.rem.restapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationRepository reservationRepository;

    public static class BookingRequest {
        public int studentId;
        public int vmId;
        public LocalDateTime startTime;
        public LocalDateTime endTime;

    }


    @PostMapping("/book")
    public ResponseEntity<?> bookingRequest(@RequestBody BookingRequest bookingRequest) {
        try {
            Reservation reservation = reservationService.bookVirtualMachine(
                    bookingRequest.studentId,
                    bookingRequest.vmId,
                    bookingRequest.startTime,
                    bookingRequest.endTime
            );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(reservation);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")//updating/editing
    public ResponseEntity<?> updateReservation(@PathVariable int id, @RequestBody BookingRequest updateRequest){
        try {
            Reservation updatedReservation = reservationService.updateReservation(
                    id,
                    updateRequest.vmId,
                    updateRequest.startTime,
                    updateRequest.endTime
            );
            return  ResponseEntity.ok(updatedReservation);
        }catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations;
    }

    @GetMapping("/reservations/{id}")
    public Reservation getReservation(@PathVariable int id){
        return reservationRepository.findById(id).get();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReservation(@PathVariable int id) {
        Reservation reservation = reservationRepository.findById(id).get();
        reservationRepository.delete(reservation);
    }


}
