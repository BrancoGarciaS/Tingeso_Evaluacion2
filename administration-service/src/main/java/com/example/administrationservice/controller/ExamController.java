package com.example.administrationservice.controller;
import com.example.administrationservice.entity.Exam;
import com.example.administrationservice.model.Installment;
import com.example.administrationservice.model.Student;
import com.example.administrationservice.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

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

    /*
    @GetMapping("/get/{id}")
    public ResponseEntity<Exam> getById(@PathVariable("id") Long id) {
        Exam exam = examService.getExamById(id);
        if(exam == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(exam);
    }

     */

    @GetMapping("/get/{rut}")
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
    public List<Map<String, Object>> calculateAVG(){
        return examService.getMeanExams();
    }

    @GetMapping("/report/{rut}")
    public Map<String, Object> createReportByRut(@PathVariable("rut") String rut){
        return examService.createReport(rut);
    }

    @GetMapping("/installments/{rut}")
    public ResponseEntity<List<Installment>> getInstallmentsByRut(@PathVariable("rut") String rut) {
        Student student = examService.getStudentByRut(rut);
        if(student == null)
            return ResponseEntity.notFound().build();
        List<Installment> installments = examService.getInstallmentsByRut(rut);
        return ResponseEntity.ok(installments);
    }

    @PostMapping("/load_excel")
    public String subirExcel(@RequestParam("file") MultipartFile file){
        try {
            examService.saveFile(file);
            String filename = file.getOriginalFilename();
            examService.readCsv(filename);
            String m = "Archivo cargado con éxito";
            return m;
        } catch (Exception e) {
            // En caso de error, se manda mensaje de error
            String m = "Error, problema al cargar archivo";
            return m;
        }
    }

}
