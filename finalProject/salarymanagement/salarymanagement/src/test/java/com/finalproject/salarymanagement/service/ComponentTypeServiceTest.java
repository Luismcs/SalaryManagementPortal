package com.finalproject.salarymanagement.service;

import com.finalproject.salarymanagement.dto.ComponentTypeDTO;
import com.finalproject.salarymanagement.exception.ComponentTypeNotFoundException;
import com.finalproject.salarymanagement.mapper.ComponentTypeMapper;
import com.finalproject.salarymanagement.model.ComponentType;
import com.finalproject.salarymanagement.repository.ComponentTypeRepository;
import com.finalproject.salarymanagement.service.impl.ComponentTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ComponentTypeServiceTest {

    @Mock
    private ComponentTypeRepository componentTypeRepository;

    @Mock
    private ComponentTypeMapper componentTypeMapper;

    @InjectMocks
    private ComponentTypeServiceImpl componentTypeService;

    private ComponentType componentType;
    private ComponentTypeDTO componentTypeDTO;
    private Long componentTypeId;

    @BeforeEach
    public void init() {

        componentType = new ComponentType();
        componentType.setName("Component Type");
        componentTypeDTO = new ComponentTypeDTO();
        componentTypeDTO.setName("Component Type");
        componentTypeId = 1L;

    }

    @Test
    void componentTypeService_getAll_returnsComponentTypeDtoPage() {

        //Arrange
        Pageable pageable = PageRequest.of(0, 2);
        List<ComponentType> componentTypes = List.of(componentType);
        Page<ComponentType> componentTypePage = new PageImpl<>(componentTypes);

        //Action
        when(componentTypeRepository.findAll(Mockito.any(Pageable.class))).thenReturn(componentTypePage);
        when(componentTypeMapper.toDto(componentType)).thenReturn(componentTypeDTO);

        Page<ComponentTypeDTO> result = componentTypeService.getAll(pageable);

        //Assert
        assertThat(result).isNotNull();

    }

    @Test
    void componentTypeService_getById_returnsComponentTypeDto() throws ComponentTypeNotFoundException {

        //Act
        when(componentTypeRepository.findById(componentTypeId)).thenReturn(Optional.of(componentType));
        when(componentTypeMapper.toDto(componentType)).thenReturn(componentTypeDTO);
        ComponentTypeDTO foundAddressDto = componentTypeService.getById(componentTypeId);

        //Assert
        assertThat(foundAddressDto).isNotNull();
    }

    @Test
    void componentTypeService_create_returnsComponentTypeDto() {

        //Act
        when(componentTypeMapper.toEntity(componentTypeDTO)).thenReturn(componentType);
        when(componentTypeRepository.save(Mockito.any(ComponentType.class))).thenReturn(componentType);
        when(componentTypeMapper.toDto(componentType)).thenReturn(componentTypeDTO);

        ComponentTypeDTO savedComponentType = componentTypeService.create(componentTypeDTO);

        //Assert
        assertThat(savedComponentType).isNotNull();
        assertThat(savedComponentType.getName()).isEqualTo(componentTypeDTO.getName());
    }

    @Test
    void componentTypeService_update_returnsComponentTypeDto() throws ComponentTypeNotFoundException {

        //Act
        when(componentTypeRepository.findById(componentType.getId())).thenReturn(Optional.ofNullable(componentType));
        when(componentTypeMapper.toEntity(componentTypeDTO)).thenReturn(componentType);
        when(componentTypeRepository.save(Mockito.any(ComponentType.class))).thenReturn(componentType);
        when(componentTypeMapper.toDto(componentType)).thenReturn(componentTypeDTO);

        //Assert
        ComponentTypeDTO componentTypeDTO1 = componentTypeService.update(componentTypeDTO);

        assertThat(componentTypeDTO1).isNotNull();
    }

    @Test
    void componentTypeService_delete_returnsNothing() {

        //Act
        when(componentTypeRepository.findById(componentType.getId())).thenReturn(Optional.ofNullable(componentType));
        doNothing().when(componentTypeRepository).delete(componentType);

        //Assert
        assertAll(() -> componentTypeService.delete(componentType.getId()));

    }


}
