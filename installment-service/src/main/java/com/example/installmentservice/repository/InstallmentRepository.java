package com.example.installmentservice.repository;
import com.example.installmentservice.entity.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, Long> {
    List<Installment> findByIdStudent(Long idStudent);
    @Query("SELECT c FROM Installment c WHERE c.rut_installment = :rut_installment")
    List<Installment> findInstallmentByRut(@Param("rut_installment") String rut_installment);
}
