package com.rem.restapi.controller;

import com.rem.restapi.entity.VirtualMachine;
import com.rem.restapi.repository.VirtualMachineRepository;
import com.rem.restapi.service.VirtualMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VirtualMachineController {

    @Autowired
    VirtualMachineRepository virtualMachineRepository;

    @Autowired
    VirtualMachineService virtualMachineService;

    public static class UpdateVirtualMachineRequest {
        public String osType;
        public int ramAllocation;
        public String status;
    }

    public static class AddVirtualMachineRequest {
        public String osType;
        public int ramAllocation;
        public String status;
    }

    public static class DeleteVM {
        public int id;
    }

    @GetMapping("/vms")
    public List<VirtualMachine> getAllVirtualMachine() {
        List<VirtualMachine> virtualMachines = virtualMachineRepository.findAll();
        return virtualMachines;
    }

    @PutMapping("vms/update/{id}")
    public ResponseEntity<?> updateVm(@PathVariable int id, @RequestBody UpdateVirtualMachineRequest updateVirtualMachineRequest) {
        try {
            VirtualMachine updatedVm = virtualMachineService.updateVirtualMachine(
                    id,
                    updateVirtualMachineRequest.osType,
                    updateVirtualMachineRequest.ramAllocation,
                    updateVirtualMachineRequest.status);
            return ResponseEntity.ok(updatedVm);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body((e).getMessage());
        }
    }

//    @DeleteMapping("vms/{id}")
//    public ResponseEntity<?> deleteVm(@PathVariable int id, @RequestBody DeleteVM deleteVm){
//            try {
//                VirtualMachine deletedVm = virtualMachineService.deleteVirtualMachine(id);
//                return ResponseEntity.ok(deletedVm);
//            } catch (RuntimeException e) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body((e).getMessage());
//            }
//    }
    @DeleteMapping("/vms/{id}")
    public ResponseEntity<?> deleteVm(@PathVariable int id) {
        try {
            VirtualMachine deletedVm = virtualMachineService.deleteVirtualMachine(id);
            return ResponseEntity.ok(deletedVm);

        } catch (RuntimeException e) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
        }
    }

    @PostMapping("vms/add")
//    @ResponseStatus(code = HttpStatus.CREATED) no need for this
    public ResponseEntity<?> addVm(@RequestBody AddVirtualMachineRequest addRequest) {
            try {
                VirtualMachine addVm = virtualMachineService.addVirtualMachine(addRequest.osType, addRequest.ramAllocation, addRequest.status);
                return ResponseEntity.ok(addVm);
            } catch (RuntimeException e) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(e.getMessage());
            }
    }
}
