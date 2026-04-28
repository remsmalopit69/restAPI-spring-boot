package com.rem.restapi.service;

import com.rem.restapi.entity.Reservation;
import com.rem.restapi.entity.Student;
import com.rem.restapi.entity.VirtualMachine;
import com.rem.restapi.repository.ReservationRepository;
import com.rem.restapi.repository.StudentRepository;
import com.rem.restapi.repository.VirtualMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private VirtualMachineRepository vmRepository;

    public Reservation bookVirtualMachine(int studentId, int vmId, LocalDateTime startTime, LocalDateTime endTime) {


            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() ->new RuntimeException("Student not found with ID: " + studentId));

            VirtualMachine vm = vmRepository.findById(vmId)
                    .orElseThrow(()-> new RuntimeException("VM not found with ID: " + vmId));

            boolean isBooked = reservationRepository.existsOverlappingReservation(vm, startTime, endTime);

            if(isBooked){
                throw new RuntimeException("Conflict of time schedule");
            }

            Reservation newReservation = new Reservation(student, vm, startTime, endTime);
            return reservationRepository.save(newReservation);

    }

    public Reservation updateReservation(int reservationId, int newVmId, LocalDateTime newStartTime, LocalDateTime newEndTime) {

        Reservation isExistingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(()-> new RuntimeException("Reservation " + reservationId + " already exists"));

        VirtualMachine vm = vmRepository.findById(newVmId)
                .orElseThrow(()->new RuntimeException("VM not found"));

        boolean isBooked = reservationRepository.existsOverlappingReservation(vm, newStartTime, newEndTime);

        if (isBooked && isExistingReservation.getVirtualMachine().getVmId() != newVmId) {
            throw new RuntimeException("Conflict of time schedule");
        }

        isExistingReservation.setVirtualMachine(vm);
        isExistingReservation.setStartTime(newStartTime);
        isExistingReservation.setEndTime(newEndTime);

        return reservationRepository.save(isExistingReservation);

    }
}
