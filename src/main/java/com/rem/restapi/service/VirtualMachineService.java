package com.rem.restapi.service;

import com.rem.restapi.entity.VirtualMachine;
import com.rem.restapi.exception.ResourceNotFoundException;
import com.rem.restapi.repository.ReservationRepository;
import com.rem.restapi.repository.VirtualMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VirtualMachineService {

    @Autowired
    public VirtualMachineRepository virtualMachineRepository;

    @Autowired
    public ReservationRepository reservationRepository;

    public VirtualMachine updateVirtualMachine(int id,String newOsType, int newRamAllocation, String newStatus) {

        VirtualMachine virtualMachine = virtualMachineRepository.findById(id).orElseThrow(()-> new RuntimeException("Error"));

        virtualMachine.setOsType(newOsType);
        virtualMachine.setRamAllocation(newRamAllocation);
        virtualMachine.setStatus(newStatus);

        return virtualMachineRepository.save(virtualMachine);

    }

    public VirtualMachine deleteVirtualMachine(int id) {
        VirtualMachine virtualMachine = virtualMachineRepository.findById(id).orElseThrow(()-> new RuntimeException("VM not found"));
        if (reservationRepository.existsByVirtualMachine(virtualMachine)){
            throw new RuntimeException("Cannot Delete, VM is still reserved");
        }
        virtualMachineRepository.delete(virtualMachine);
        return virtualMachine;
    }

    public VirtualMachine addVirtualMachine(String osType, int ramAllocation, String status) {
        VirtualMachine virtualMachine = new VirtualMachine();

        virtualMachine.setOsType(osType);
        virtualMachine.setRamAllocation(ramAllocation);
        virtualMachine.setStatus(status);

        return virtualMachineRepository.save(virtualMachine);
    }

    public VirtualMachine getVmError(int id) {
        VirtualMachine virtualMachine = virtualMachineRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Virtual Machine " + id + " not found"));
        return virtualMachine;
    }
}
