package com.finalproject.salarymanagement.repository;

import com.finalproject.salarymanagement.model.SalaryComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class SalaryComponentRepositoryTest {

    @Autowired
    private SalaryComponentRepository salaryComponentRepository;
    private SalaryComponent salaryComponent;

    @BeforeEach
    void init() {
        salaryComponent = new SalaryComponent();
        salaryComponent.setValue(120.0);
    }

    @Test
    void salaryComponentRepository_getAll_returnsModeThanSalaryComponent() {

        salaryComponentRepository.save(salaryComponent);
        List<SalaryComponent> salaryComponents = salaryComponentRepository.findAll();

        assertThat(salaryComponents).isNotNull();
        assertThat(salaryComponents.size()).isEqualTo(1);

    }

    @Test
    void salaryComponentRepository_getById_returnsSalaryComponent() {

        SalaryComponent savedSalaryComponent = salaryComponentRepository.save(salaryComponent);
        Optional<SalaryComponent> foundSalaryComponent =
                salaryComponentRepository.findById(savedSalaryComponent.getId());

        assertThat(foundSalaryComponent).isNotNull();
    }

    @Test
    void salaryComponentRepository_create_returnsOneSalaryComponent() {

        SalaryComponent savedSalaryComponent = salaryComponentRepository.save(salaryComponent);
        Optional<SalaryComponent> fetchedSalaryComponent =
                salaryComponentRepository.findById(savedSalaryComponent.getId());

        assertThat(fetchedSalaryComponent).isPresent();
        assertThat(salaryComponentRepository.findById(fetchedSalaryComponent.get().getId())).isNotNull();
    }

    @Test
    void salaryComponentRepository_update_returnsSalaryComponent() {

        SalaryComponent savedSalaryComponent = salaryComponentRepository.save(salaryComponent);
        Optional<SalaryComponent> foundComponentTypeSubtype =
                salaryComponentRepository.findById(savedSalaryComponent.getId());
        foundComponentTypeSubtype.get().setValue(200);
        SalaryComponent updatedComponentType = salaryComponentRepository.save(foundComponentTypeSubtype.get());

        assertThat(updatedComponentType.getValue()).isEqualTo(200);
    }

    @Test
    void salaryComponentRepository_delete_returnsNothing() {

        SalaryComponent savedSalaryComponent = salaryComponentRepository.save(salaryComponent);
        salaryComponentRepository.deleteById(savedSalaryComponent.getId());
        Optional<SalaryComponent> foundSalaryComponent =
                salaryComponentRepository.findById(savedSalaryComponent.getId());

        assertThat(foundSalaryComponent).isEmpty();
    }

}
