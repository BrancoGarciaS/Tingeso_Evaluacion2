package com.example.administrationservice.service;

import com.example.administrationservice.entity.Exam;
import com.example.administrationservice.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExamService {
    @Autowired
    ExamRepository examRepository;

    // Para obtener todos los examenes
    public List<Exam> getAll() {
        return examRepository.findAll();
    }

    // Obtener una examen por id
    public Exam getExamById(Long id_exam) {
        return examRepository.findById(id_exam).orElse(null);
    }

    // Para guardar una examen
    public Exam saveData(Exam exam) {
        exam.setLoad_date(LocalDate.now());
        return examRepository.save(exam);
    }

    // Para calcular la media de examenes por rut
    public List<Object[]> getMeanExams() {
        return examRepository.groupMean();
    }

    public List<Exam> getByRut(String rut){
        return examRepository.findByStudentRut(rut);
    }


}
