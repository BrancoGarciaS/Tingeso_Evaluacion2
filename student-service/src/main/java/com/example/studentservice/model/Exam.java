package com.example.studentservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
    private String rut; // atributo que conecta con estudiante
    private LocalDate exam_date; // fecha del examen
    private float exam_score;
    private LocalDate load_date; // fecha en la que se cargó el examen
}
