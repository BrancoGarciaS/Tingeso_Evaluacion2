package com.example.studentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    // Atributos de la entidad

    // Atributo llave
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String rut; // rut del estudiante

    private String name; // nombre del estudiante
    private String last_name; // apellido del estudiante
    private String email; // email del estudiante
    private Integer age; // año de nacimiento
    private String school_name; // nombre de la escuela
    private Long school_type; // tipo de escuela (1: municipal, 2: subvencionado, 3: privado)
    private Integer senior_year; // año de egreso de la escuela
    private Long num_exams; // numero de examenes realizados por el estudiante (inicializa en 0)
    private float score; // promedio del estudiante (se actualiza a través del excel de examenes)
    private Integer payment_type; // tipo de pago para el arancel(0: contado, 1: en cuotas)
    private Integer num_installments; // numero de cuotas (si es 0, significa que será pago al contado)
    private Integer tariff; // arancel total a pagar considerando descuentos
    private Integer tuition; // matrícula
    private LocalDate birthdate; // fecha de nacimiento

}
