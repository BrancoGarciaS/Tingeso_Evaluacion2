package com.example.administrationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "exams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_exam; // atributo llave
    private String rut; // atributo que conecta con estudiante
    private LocalDate exam_date; // fecha del examen
    private float exam_score;
    private LocalDate load_date; // fecha en la que se cargó el examen
}
