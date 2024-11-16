package com.finalproject.salarymanagement.repository;

import com.finalproject.salarymanagement.model.SalaryComponent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryComponentRepository extends JpaRepository<SalaryComponent, Long> {
}
