package com.finalproject.salarymanagement.repository;

import com.finalproject.salarymanagement.model.ComponentTypeSubtype;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ComponentTypeSubtypeRepositoryTest {

    @Autowired
    private ComponentTypeSubtypeRepository componentTypeSubtypeRepository;
    private ComponentTypeSubtype componentTypeSubtype;

    @BeforeEach
    void init() {
        componentTypeSubtype = new ComponentTypeSubtype();
        componentTypeSubtype.setName("Component Type Subtype");
    }

    @Test
    void componentTypeSubtypeRepository_getAll_returnsModeThanComponentType() {

        componentTypeSubtypeRepository.save(componentTypeSubtype);
        List<ComponentTypeSubtype> componentTypeSubtypes = componentTypeSubtypeRepository.findAll();

        assertThat(componentTypeSubtypes).isNotNull();
        assertThat(componentTypeSubtypes.size()).isEqualTo(1);

    }

    @Test
    void componentTypeSubtypeRepository_getById_returnsComponentType() {

        ComponentTypeSubtype savedComponentTypeSubtype = componentTypeSubtypeRepository.save(componentTypeSubtype);
        Optional<ComponentTypeSubtype> foundComponentTypeSubtype =
                componentTypeSubtypeRepository.findById(savedComponentTypeSubtype.getId());

        assertThat(foundComponentTypeSubtype).isNotNull();
    }

    @Test
    void componentTypeSubtypeRepository_create_returnsOneComponentType() {

        ComponentTypeSubtype savedComponentTypeSubtype = componentTypeSubtypeRepository.save(componentTypeSubtype);
        Optional<ComponentTypeSubtype> fetchedComponentTypeSubtype =
                componentTypeSubtypeRepository.findById(savedComponentTypeSubtype.getId());

        assertThat(fetchedComponentTypeSubtype).isPresent();
        assertThat(componentTypeSubtypeRepository.findById(fetchedComponentTypeSubtype.get().getId())).isNotNull();
    }

    @Test
    void componentTypeSubtypeRepository_update_returnsComponentType() {

        ComponentTypeSubtype savedComponentTypeSubtype = componentTypeSubtypeRepository.save(componentTypeSubtype);
        Optional<ComponentTypeSubtype> foundComponentTypeSubtype =
                componentTypeSubtypeRepository.findById(savedComponentTypeSubtype.getId());
        foundComponentTypeSubtype.get().setName("Component Type Subtype Update");
        ComponentTypeSubtype updatedComponentType =
                componentTypeSubtypeRepository.save(foundComponentTypeSubtype.get());

        assertThat(updatedComponentType.getName()).isEqualTo("Component Type Subtype Update");
    }

    @Test
    void componentTypeSubtypeRepository_delete_returnsNothing() {

        ComponentTypeSubtype savedComponentTypeSubtype = componentTypeSubtypeRepository.save(componentTypeSubtype);
        componentTypeSubtypeRepository.deleteById(savedComponentTypeSubtype.getId());
        Optional<ComponentTypeSubtype> foundComponentType =
                componentTypeSubtypeRepository.findById(savedComponentTypeSubtype.getId());

        assertThat(foundComponentType).isEmpty();
    }

}
