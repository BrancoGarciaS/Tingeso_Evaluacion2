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

    // Para obtener todos los exámenes
    @GetMapping("/get")
    public List<Exam> getAll() {
        List<Exam> installments = examService.getAll();
        return installments;
    }

    // Para obtener un examen por rut
    @GetMapping("/get/{rut}")
    public ResponseEntity<List<Exam>> getByStudentId(@PathVariable("rut") String rut) {
        List<Exam> exams = examService.getByRut(rut);
        return ResponseEntity.ok(exams);
    }

    // Para crear un examen manualmente
    @PostMapping("/post")
    public ResponseEntity<Exam> save(@RequestBody Exam exam) {
        Exam installment1 = examService.saveData(exam);
        return ResponseEntity.ok(installment1);
    }

    // Para obtener los promedios de exámenes
    @GetMapping("/getMeans")
    public List<Map<String, Object>> calculateAVG() {
        return examService.getMeanExams();
    }

    // Para obtener el reporte de un estudiante (por rut)
    @GetMapping("/report/{rut}")
    public Map<String, Object> createReportByRut(@PathVariable("rut") String rut) {
        return examService.createReport(rut);
    }

    // Para obtener las cuotas de un estudiante por rut
    @GetMapping("/installments/{rut}")
    public ResponseEntity<List<Installment>> getInstallmentsByRut(@PathVariable("rut") String rut) {
        Student student = examService.getStudentByRut(rut);
        if (student == null)
            return ResponseEntity.notFound().build();
        List<Installment> installments = examService.getInstallmentsByRut(rut);
        return ResponseEntity.ok(installments);
    }

    // Para cargar excel y guardar los exámenes en la base de datos
    @PostMapping("/load_excel")
    public String subirExcel(@RequestParam("file") MultipartFile file) {
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


    // Para obtener estudiantes por rut
    @GetMapping("/getStudentByRut/{rut}")
    public Student buscarEstudiante(@RequestParam("rut") String rut){
        return examService.getStudentByRut(rut);
    }

    // Para borrar todos los exámenes
    @DeleteMapping("/delete")
    public void deleteExams(){
        examService.deleteAllExams();
    }

}