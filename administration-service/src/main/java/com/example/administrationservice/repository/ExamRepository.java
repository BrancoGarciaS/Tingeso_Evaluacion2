package com.example.administrationservice.repository;

import com.example.administrationservice.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Query("SELECT rut, AVG(exam_score), COUNT(*) FROM Exam GROUP BY rut")
    List<Object[]> groupMean();

    @Query("SELECT e FROM Exam e WHERE e.rut = :rut")
    List<Exam> findByStudentRut(@Param("rut") String rut);
}
