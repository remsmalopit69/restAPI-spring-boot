package com.rem.restapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "VirtualMachine")
public class VirtualMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vmId;
    @Column
    private String osType;
    @Column
    private int ramAllocation;
    @Column
    private String status;

    public VirtualMachine() {

    }

    public VirtualMachine(String osType, int ramAllocation, String status) {
        this.osType = osType;
        this.ramAllocation = ramAllocation;
        this.status = status;
    }

    public int getVmId() {
        return vmId;
    }

    public void setVmId(int vmId) {
        this.vmId = vmId;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public int getRamAllocation() {
        return ramAllocation;
    }

    public void setRamAllocation(int ramAllocation) {
        this.ramAllocation = ramAllocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
