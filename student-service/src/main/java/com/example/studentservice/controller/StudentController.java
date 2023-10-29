package com.example.studentservice.controller;

import com.example.studentservice.entity.Student;
import com.example.studentservice.model.Exam;
import com.example.studentservice.model.Installment;
import com.example.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/get")
    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = studentService.getAll();
        if(students.isEmpty()){ // si no hay estudiantes
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getById(@PathVariable("id") Long id) {
        Student student = studentService.getStudentById(id);
        if(student == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(student);
    }

    @PostMapping("/post")
    public ResponseEntity<Student> save(@RequestBody Student student) {
        Student s1 = studentService.saveData(student);
        return ResponseEntity.ok(s1);
    }

    @GetMapping("/installments/{studentId}")
    public ResponseEntity<List<Installment>> getInstallments(@PathVariable("studentId") Long studentId) {
        Student student = studentService.getStudentById(studentId);
        if(student == null)
            return ResponseEntity.notFound().build();
        List<Installment> installments = studentService.getInstallments(studentId);
        return ResponseEntity.ok(installments);
    }

    @GetMapping("/exams/{studentId}")
    public ResponseEntity<List<Exam>> getExams(@PathVariable("studentId") Long studentId) {
        Student student = studentService.getStudentById(studentId);
        if(student == null)
            return ResponseEntity.notFound().build();
        List<Exam> exams = studentService.getExams(studentId);
        return ResponseEntity.ok(exams);
    }


}
