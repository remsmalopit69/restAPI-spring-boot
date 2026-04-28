package com.rem.restapi.controller;

import com.rem.restapi.entity.Reservation;
import com.rem.restapi.entity.Student;
import com.rem.restapi.repository.StudentRepository;
import com.rem.restapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class StudentController {
    //get all students
    @Autowired
    StudentRepository repository;
    @Autowired
    StudentService studentService;

    public static class UpdateStudentRequest { //need static
        public String name;
        public String department;
        public float percentage;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents(){
        List<Student> students = repository.findAll();
        return students;
    }


    @GetMapping("/students/{student_number}")//fetching data
    public Student getStudent(@PathVariable int student_number) {
        return repository.findById(student_number).get();//used repository with findByID method
    }

    @PostMapping("/students/add")//adding data
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createStudent(@RequestBody Student student) {
        repository.save(student);
    }

//    @PutMapping("/students/update/{id}")//updating/editing
//    public Student updateStudent(@PathVariable int id) {
//        Student student = repository.findById(id).get();
//        student.setName("Glenn");
//        student.setDepartment("FOE");
//        repository.save(student);
//        return student;
//    }
@PutMapping("/students/update/{id}")
public ResponseEntity<?> updateStudent(
        @PathVariable int id,
        @RequestBody UpdateStudentRequest updateRequest) {

    try {
        Student updatedStudent = studentService.updateStudent(
                id,
                updateRequest.name,
                updateRequest.department,
                updateRequest.percentage
        );

        return ResponseEntity.ok(updatedStudent);

    } catch (RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}

    @DeleteMapping("students/{id}")//deleting
    public void deleteStudent(@PathVariable int id) {
       Student student = repository.findById(id).get();
       repository.delete(student);
    }
}
