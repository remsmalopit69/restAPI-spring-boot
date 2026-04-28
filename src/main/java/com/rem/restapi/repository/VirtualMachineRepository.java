package com.rem.restapi.repository;

import com.rem.restapi.entity.VirtualMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Integer> {
}
