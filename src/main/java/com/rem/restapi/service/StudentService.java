package com.rem.restapi.service;

import com.rem.restapi.entity.Student;
import com.rem.restapi.exception.ResourceNotFoundException;
import com.rem.restapi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student updateStudent(int id,String newName, String newDepartment, float newPercentage) {

        Student student = studentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Student ID: " + id + " not found"));

        student.setName(newName);
        student.setDepartment(newDepartment);
        student.setPercentage(newPercentage);

        return studentRepository.save(student);
    }

    public Student getStudentError(int id) {
        Student student = studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Student ID: " + id + " not found"));
        return student;
    }
}
