package com.finalproject.salarymanagement.repository;

import com.finalproject.salarymanagement.model.ComponentType;
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
public class ComponentTypeRepositoryTest {

    @Autowired
    private ComponentTypeRepository componentTypeRepository;
    private ComponentType componentType;

    @BeforeEach
    void init() {
        componentType = new ComponentType();
        componentType.setName("Component Type");
    }

    @Test
    void componentTypeRepository_getAll_returnsModeThanComponentType() {

        componentTypeRepository.save(componentType);
        List<ComponentType> componentTypes = componentTypeRepository.findAll();

        assertThat(componentTypes).isNotNull();
        assertThat(componentTypes.size()).isEqualTo(1);

    }

    @Test
    void componentTypeRepository_getById_returnsComponentType() {

        ComponentType savedComponentType = componentTypeRepository.save(componentType);
        Optional<ComponentType> foundComponentType = componentTypeRepository.findById(savedComponentType.getId());

        assertThat(foundComponentType).isNotNull();
    }

    @Test
    void componentTypeRepository_create_returnsOneComponentType() {

        ComponentType savedComponentType = componentTypeRepository.save(componentType);
        Optional<ComponentType> fetchedComponentType = componentTypeRepository.findById(savedComponentType.getId());

        assertThat(fetchedComponentType).isPresent();
        assertThat(componentTypeRepository.findById(fetchedComponentType.get().getId())).isNotNull();
    }

    @Test
    void componentTypeRepository_update_returnsComponentType() {

        ComponentType savedComponentType = componentTypeRepository.save(componentType);
        Optional<ComponentType> foundComponentType = componentTypeRepository.findById(savedComponentType.getId());
        foundComponentType.get().setName("Component Type Update");
        ComponentType updatedComponentType = componentTypeRepository.save(foundComponentType.get());

        assertThat(updatedComponentType.getName()).isEqualTo("Component Type Update");
    }

    @Test
    void componentTypeRepository_delete_returnsNothing() {

        ComponentType savedComponentType = componentTypeRepository.save(componentType);
        componentTypeRepository.deleteById(savedComponentType.getId());
        Optional<ComponentType> foundComponentType = componentTypeRepository.findById(savedComponentType.getId());

        assertThat(foundComponentType).isEmpty();
    }

}
