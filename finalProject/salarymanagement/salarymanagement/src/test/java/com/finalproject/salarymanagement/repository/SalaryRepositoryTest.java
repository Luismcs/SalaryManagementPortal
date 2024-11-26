package com.finalproject.salarymanagement.repository;

import com.finalproject.salarymanagement.enums.SalaryState;
import com.finalproject.salarymanagement.model.Salary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class SalaryRepositoryTest {

    @Autowired
    private SalaryRepository salaryRepository;
    private Salary salary;

    @BeforeEach
    void init() {
        salary = new Salary();
        salary.setAcceptanceDate(null);
        salary.setCorrelationId("1");
        salary.setImplementationDate(LocalDate.now());
        salary.setSalaryState(SalaryState.INACTIVE);
    }

    @Test
    void salaryRepository_getAll_returnsModeThanSalary() {

        salaryRepository.save(salary);
        List<Salary> salaries = salaryRepository.findAll();

        assertThat(salaries).isNotNull();
        assertThat(salaries.size()).isEqualTo(1);

    }

    @Test
    void salaryRepository_getById_returnsSalary() {

        Salary savedSalary = salaryRepository.save(salary);
        Optional<Salary> foundSalary = salaryRepository.findById(savedSalary.getId());

        assertThat(foundSalary).isNotNull();
    }

    @Test
    void salaryRepository_create_returnsOneSalary() {

        Salary savedSalary = salaryRepository.save(salary);
        Optional<Salary> fetchedSalary = salaryRepository.findById(savedSalary.getId());

        assertThat(fetchedSalary).isPresent();
        assertThat(salaryRepository.findById(fetchedSalary.get().getId())).isNotNull();
    }

    @Test
    void salaryRepository_update_returnsSalary() {

        Salary savedSalary = salaryRepository.save(salary);
        Optional<Salary> foundSalary = salaryRepository.findById(savedSalary.getId());
        foundSalary.get().setSalaryState(SalaryState.ACTIVE);
        Salary updatedSalary = salaryRepository.save(foundSalary.get());

        assertThat(updatedSalary.getSalaryState()).isEqualTo(SalaryState.ACTIVE);
    }

    @Test
    void salaryRepository_delete_returnsNothing() {

        Salary savedSalary = salaryRepository.save(salary);
        salaryRepository.deleteById(savedSalary.getId());
        Optional<Salary> foundSalary = salaryRepository.findById(savedSalary.getId());

        assertThat(foundSalary).isEmpty();
    }

}
