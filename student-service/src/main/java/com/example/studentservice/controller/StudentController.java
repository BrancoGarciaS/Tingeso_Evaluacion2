package com.example.studentservice.controller;

import com.example.studentservice.entity.Student;
import com.example.studentservice.model.Exam;
import com.example.studentservice.model.Installment;
import com.example.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    // Para obtener todos los estudiantes
    @GetMapping("/get")
    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = studentService.getAll();
        if(students.isEmpty()){ // si no hay estudiantes
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }

    // Para guardar a un estudiante
    @PostMapping("/post")
    public ResponseEntity<Student> save(@RequestBody Student student) {
        Student s1 = studentService.saveData(student);
        return ResponseEntity.ok(s1);
    }

    // Para guardar a un estudiante
    @PostMapping("/post2")
    public void save2(@RequestBody Student student){
        studentService.saveData_2(student);
    }

    // Para obtener las cuotas de un estudiante por rut
    @GetMapping("/installments/{rut}")
    public ResponseEntity<List<Installment>> getInstallmentsByRut(@PathVariable("rut") String rut) {
        Student student = studentService.getStudentByRut(rut);
        if(student == null)
            return ResponseEntity.notFound().build();
        List<Installment> installments = studentService.getInstallmentsByRut(rut);
        return ResponseEntity.ok(installments);
    }

    // Para obtener los exámenes por el id de un estudiante
    @GetMapping("/exams/{studentId}")
    public ResponseEntity<List<Exam>> getExams(@PathVariable("studentId") Long studentId) {
        Student student = studentService.getStudentById(studentId);
        if(student == null)
            return ResponseEntity.notFound().build();
        List<Exam> exams = studentService.getExams(studentId);
        return ResponseEntity.ok(exams);
    }

    // Para guardar las cuotas de un estudiante por id
    @PostMapping("/saveInstallment/{studentId}")
    public ResponseEntity<Installment> saveBook(@PathVariable("studentId") Long studentId, @RequestBody Installment ins) {
        if(studentService.getStudentById(studentId) == null)
            return ResponseEntity.notFound().build();
        Installment ins_new = studentService.saveInstallment(studentId, ins);
        return ResponseEntity.ok(ins_new);
    }

    // Para obtener un estudiante por rut
    @GetMapping("/get/{rut}")
    public ResponseEntity<Student> getByRut(@PathVariable("rut") String rut) {
        Student student = studentService.getStudentByRut(rut);
        if(student == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(student);
    }

    @GetMapping("/get2/{rut}")
    public ResponseEntity<Student> getByRutStudent(@PathVariable("rut") String rut) {
        Student student = studentService.getStudentByRut(rut);
        if (student == null) {
            // Devuelve una respuesta 200 con cuerpo nulo si el estudiante no se encuentra
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(student);
    }

    // Para calcular el promedio de los exámenes de cada estudiante (rut)
    @GetMapping("/exams_avg")
    public List<Map<String, Object>> getExamsAVG(){
        return studentService.getAVG_exams();
    }

    // Para guardar los promedios de exámenes de cada estudiante en la base de datos
    @GetMapping("/saveExams")
    public List<Map<String, Object>> saveExamsAVG(){
        return studentService.saveMean();
    }

    // Para borrar estudiante por id
    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
    }
}