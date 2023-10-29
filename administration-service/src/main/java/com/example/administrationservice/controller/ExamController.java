package com.example.administrationservice.controller;
import com.example.administrationservice.entity.Exam;
import com.example.administrationservice.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    ExamService examService;

    @GetMapping("/get")
    public ResponseEntity<List<Exam>> getAll() {
        List<Exam> installments = examService.getAll();
        if(installments.isEmpty()) { // si no hay examenes en la base de datos
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(installments);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Exam> getById(@PathVariable("id") Long id) {
        Exam exam = examService.getExamById(id);
        if(exam == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(exam);
    }

    @GetMapping("/getByRut/{rut}")
    public ResponseEntity<List<Exam>> getByStudentId(@PathVariable("rut") String rut) {
        List<Exam> installments = examService.getByRut(rut);
        return ResponseEntity.ok(installments);
    }

    @PostMapping("/post")
    public ResponseEntity<Exam> save(@RequestBody Exam exam) {
        Exam installment1 = examService.saveData(exam);
        return ResponseEntity.ok(installment1);
    }

    @GetMapping("/getMeans")
    public ResponseEntity<List<Object[]>> calculateAVG(){
        List<Object[]> means = examService.getMeanExams();
        return ResponseEntity.ok(means);
    }

}
