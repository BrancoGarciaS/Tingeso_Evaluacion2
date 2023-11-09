package com.example.studentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Installment {
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

