package com.finalproject.salarymanagement.repository;

import com.finalproject.salarymanagement.enums.SalaryState;
import com.finalproject.salarymanagement.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

    Optional<Salary> findByCorrelationIdAndSalaryState(String correlationId, SalaryState salaryState);

    Optional<Salary> findByCorrelationIdAndImplementationDate(String correlationId, LocalDate implementationDate);

    List<Salary> findByCorrelationIdAndImplementationDateBetweenAndSalaryState(String correlationId, LocalDate startingDate,
                                                                               LocalDate endingDate, SalaryState state);
}
