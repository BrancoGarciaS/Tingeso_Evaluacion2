package com.example.installmentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "installments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Installment {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_installment; // atributo llave

    private String rut_installment;
    private Integer installmentState; // estado de la cuota, 0 pendiente, 1 pagado
    private float payment_amount;  // pago involucrado
    private float interest_payment_amount; // pago con intereses por meses de atrasos
    private LocalDate due_date; // fecha de vencimiento
    private LocalDate start_date;  // fecha de inicio
    private LocalDate payment_date;  // fecha de pago
    private Long idStudent;  // conexión con entidad estudiante
}