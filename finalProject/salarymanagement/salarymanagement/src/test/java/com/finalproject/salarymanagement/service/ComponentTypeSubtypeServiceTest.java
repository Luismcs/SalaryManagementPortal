package com.finalproject.salarymanagement.service;

import com.finalproject.salarymanagement.dto.ComponentTypeDTO;
import com.finalproject.salarymanagement.dto.ComponentTypeSubtypeDTO;
import com.finalproject.salarymanagement.exception.ComponentTypeNotFoundException;
import com.finalproject.salarymanagement.exception.ComponentTypeSubtypeNotFoundException;
import com.finalproject.salarymanagement.mapper.ComponentTypeMapper;
import com.finalproject.salarymanagement.mapper.ComponentTypeSubtypeMapper;
import com.finalproject.salarymanagement.model.ComponentType;
import com.finalproject.salarymanagement.model.ComponentTypeSubtype;
import com.finalproject.salarymanagement.repository.ComponentTypeRepository;
import com.finalproject.salarymanagement.repository.ComponentTypeSubtypeRepository;
import com.finalproject.salarymanagement.service.impl.ComponentTypeServiceImpl;
import com.finalproject.salarymanagement.service.impl.ComponentTypeSubtypeServiceImpl;
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
public class ComponentTypeSubtypeServiceTest {

    @Mock
    private ComponentTypeSubtypeRepository componentTypeSubtypeRepository;

    @Mock
    private ComponentTypeRepository componentTypeRepository;

    @Mock
    private ComponentTypeSubtypeMapper componentTypeSubtypeMapper;

    @InjectMocks
    private ComponentTypeSubtypeServiceImpl componentTypeSubtypeService;

    private ComponentTypeSubtype componentTypeSubtype;
    private ComponentType componentType;
    private ComponentTypeSubtypeDTO componentTypeSubtypeDTO;
    private Long componentTypeSubtypeId;
    private Long componentTypeId;

    @BeforeEach
    void init() {

        componentTypeSubtypeDTO = new ComponentTypeSubtypeDTO();
        componentTypeSubtypeDTO.setComponentTypeId(1L);
        componentTypeSubtypeDTO.setName("Component Type Subtype");
        componentTypeSubtype = new ComponentTypeSubtype();
        componentTypeSubtype.setName("Component Type Subtype");
        componentType = new ComponentType();
        componentType.setId(1L);
        componentType.setName("Component Type");
        componentTypeSubtype.setComponentType(componentType);
        componentTypeSubtype.setId(1L);
        componentTypeSubtypeId = 1L;
        componentTypeId = 1L;
        componentTypeRepository.save(componentType);

    }

    @Test
    void componentTypeService_getAll_returnsComponentTypeDtoPage() {

        //Arrange
        Pageable pageable = PageRequest.of(0, 2);
        List<ComponentTypeSubtype> componentTypeSubtypes = List.of(componentTypeSubtype);
        Page<ComponentTypeSubtype> componentTypeSubtypePage = new PageImpl<>(componentTypeSubtypes);

        //Act
        when(componentTypeSubtypeRepository.findAll(Mockito.any(Pageable.class))).thenReturn(componentTypeSubtypePage);
        when(componentTypeSubtypeMapper.toDto(componentTypeSubtype)).thenReturn(componentTypeSubtypeDTO);

        Page<ComponentTypeSubtypeDTO> result = componentTypeSubtypeService.getAll(pageable);

        //Assert
        assertThat(result).isNotNull();

    }

    @Test
    void componentTypeService_getById_returnsComponentTypeDtoPage() throws ComponentTypeSubtypeNotFoundException {

        //Act
        when(componentTypeSubtypeRepository.findById(componentTypeSubtypeId)).thenReturn(Optional.ofNullable(componentTypeSubtype));
        when(componentTypeSubtypeMapper.toDto(componentTypeSubtype)).thenReturn(componentTypeSubtypeDTO);

        ComponentTypeSubtypeDTO foundComponentTypeSubtype = componentTypeSubtypeService.getById(componentTypeSubtypeId);

        //Assert
        assertThat(foundComponentTypeSubtype).isNotNull();
        assertThat(foundComponentTypeSubtype.getName()).isEqualTo("Component Type Subtype");

    }

    @Test
    void componentTypeService_create_returnsComponentTypeDtoPage() throws ComponentTypeNotFoundException {

        //Act
        when(componentTypeRepository.findById(componentTypeId)).thenReturn(Optional.ofNullable(componentType));
        when(componentTypeSubtypeMapper.toEntity(componentTypeSubtypeDTO)).thenReturn(componentTypeSubtype);
        when(componentTypeSubtypeRepository.save(Mockito.any(ComponentTypeSubtype.class))).thenReturn(componentTypeSubtype);
        when(componentTypeSubtypeMapper.toDto(Mockito.any(ComponentTypeSubtype.class))).thenReturn(componentTypeSubtypeDTO);

        ComponentTypeSubtypeDTO savedComponentTypeSubtypeDTO = componentTypeSubtypeService.create(componentTypeSubtypeDTO);

        //Assert
        assertThat(savedComponentTypeSubtypeDTO).isNotNull();
        assertThat(savedComponentTypeSubtypeDTO.getName()).isEqualTo(componentTypeSubtype.getName());

    }

    @Test
    void componentTypeService_update_returnsComponentTypeDtoPage() throws ComponentTypeNotFoundException {

        //Act
        when(componentTypeRepository.findById(componentTypeId)).thenReturn(Optional.ofNullable(componentType));
        when(componentTypeSubtypeMapper.toEntity(componentTypeSubtypeDTO)).thenReturn(componentTypeSubtype);
        when(componentTypeSubtypeRepository.save(Mockito.any(ComponentTypeSubtype.class))).thenReturn(componentTypeSubtype);
        when(componentTypeSubtypeMapper.toDto(Mockito.any(ComponentTypeSubtype.class))).thenReturn(componentTypeSubtypeDTO);
        ComponentTypeSubtypeDTO savedComponentTypeSubtypeDTO = componentTypeSubtypeService.update(componentTypeSubtypeDTO);

        //Assert
        assertThat(savedComponentTypeSubtypeDTO).isNotNull();
        assertThat(savedComponentTypeSubtypeDTO.getName()).isEqualTo(componentTypeSubtype.getName());

    }

    @Test
    void componentTypeService_delete_returnsNothing() {

        //Act
        when(componentTypeSubtypeRepository.findById(componentTypeSubtype.getId()))
                .thenReturn(Optional.ofNullable(componentTypeSubtype));
        doNothing().when(componentTypeSubtypeRepository).delete(componentTypeSubtype);

        //Assert
        assertAll(() -> componentTypeSubtypeService.delete(componentTypeSubtype.getId()));

    }

}
