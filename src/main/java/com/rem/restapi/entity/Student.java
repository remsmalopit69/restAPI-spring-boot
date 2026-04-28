package com.rem.restapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    @Column(name = "studentName")
    private String name;

    @Column
    private float percentage;

    @Column
    private String department;

    public Student(){
    }

    public Student(String name, float percentage, String department) {
        this.name = name;
        this.percentage = percentage;
        this.department = department;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public float getPercentage() { return percentage; }
    public void setPercentage(float percentage) { this.percentage = percentage; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}