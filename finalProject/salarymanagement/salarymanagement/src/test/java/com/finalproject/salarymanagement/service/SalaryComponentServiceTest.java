package com.finalproject.salarymanagement.service;

import com.finalproject.salarymanagement.dto.ComponentTypeDTO;
import com.finalproject.salarymanagement.dto.ComponentTypeSubtypeDTO;
import com.finalproject.salarymanagement.dto.SalaryComponentDTO;
import com.finalproject.salarymanagement.enums.SalaryState;
import com.finalproject.salarymanagement.exception.ComponentTypeNotFoundException;
import com.finalproject.salarymanagement.exception.ComponentTypeSubtypeNotFoundException;
import com.finalproject.salarymanagement.exception.SalaryComponentNotFoundException;
import com.finalproject.salarymanagement.mapper.ComponentTypeMapper;
import com.finalproject.salarymanagement.mapper.SalaryComponentMapper;
import com.finalproject.salarymanagement.model.ComponentType;
import com.finalproject.salarymanagement.model.ComponentTypeSubtype;
import com.finalproject.salarymanagement.model.Salary;
import com.finalproject.salarymanagement.model.SalaryComponent;
import com.finalproject.salarymanagement.repository.ComponentTypeRepository;
import com.finalproject.salarymanagement.repository.ComponentTypeSubtypeRepository;
import com.finalproject.salarymanagement.repository.SalaryComponentRepository;
import com.finalproject.salarymanagement.repository.SalaryRepository;
import com.finalproject.salarymanagement.service.impl.ComponentTypeServiceImpl;
import com.finalproject.salarymanagement.service.impl.SalaryComponentServiceImpl;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalaryComponentServiceTest {

    @Mock
    private SalaryComponentRepository salaryComponentRepository;

    @Mock
    private ComponentTypeRepository componentTypeRepository;

    @Mock
    private ComponentTypeSubtypeRepository componentTypeSubtypeRepository;

    @Mock
    private SalaryRepository salaryRepository;

    @Mock
    private SalaryComponentMapper salaryComponentMapper;

    @InjectMocks
    private SalaryComponentServiceImpl salaryComponentService;

    private ComponentType componentType;
    private ComponentTypeSubtype componentTypeSubtype;
    private Salary salary;
    private SalaryComponent salaryComponent;
    private SalaryComponentDTO salaryComponentDTO;
    private Long salaryComponentId;

    @BeforeEach
    void init() {
        salaryComponentDTO = new SalaryComponentDTO();
        salaryComponentDTO.setComponentTypeId(1L);
        salaryComponentDTO.setComponentTypeSubtypeId(1L);
        salaryComponentDTO.setValue(123.0);
        salaryComponentDTO.setSalaryId(1L);
        componentType = new ComponentType();
        componentType.setName("Component Type");
        componentTypeSubtype = new ComponentTypeSubtype();
        componentTypeSubtype.setName("Component Type Subtype");
        salary = new Salary();
        salary.setId(1L);
        salary.setAcceptanceDate(null);
        salary.setCorrelationId("1");
        salary.setImplementationDate(LocalDate.now().plusWeeks(5));
        salary.setSalaryState(SalaryState.INACTIVE);
        salaryComponent = new SalaryComponent();
        salaryComponent.setComponentType(componentType);
        salaryComponent.setComponentTypeSubtype(componentTypeSubtype);
        salaryComponent.setValue(123.0);
        salaryComponentId = 1L;
    }

    @Test
    void salaryComponentService_getAll_returnsSalaryComponentDtoPage() {

        //Arrange
        Pageable pageable = PageRequest.of(0, 2);
        List<SalaryComponent> salaryComponents = List.of(salaryComponent);
        Page<SalaryComponent> salaryComponentPage = new PageImpl<>(salaryComponents);

        //Act
        when(salaryComponentRepository.findAll(Mockito.any(Pageable.class))).thenReturn(salaryComponentPage);
        when(salaryComponentMapper.toDto(salaryComponent)).thenReturn(salaryComponentDTO);

        Page<SalaryComponentDTO> result = salaryComponentService.getAll(pageable);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);

    }

    @Test
    void salaryComponentService_getById_returnsOneSalaryComponentDto() throws SalaryComponentNotFoundException {

        when(salaryComponentRepository.findById(salaryComponentId)).thenReturn(Optional.ofNullable(salaryComponent));
        when(salaryComponentMapper.toDto(Mockito.any(SalaryComponent.class))).thenReturn(salaryComponentDTO);

        SalaryComponentDTO foundSalaryComponentDto = salaryComponentService.getById(salaryComponentId);

        //Assert
        assertThat(foundSalaryComponentDto).isNotNull();
    }

    @Test
    void salaryComponentService_create_returnsOneSalaryComponentDto() throws ComponentTypeSubtypeNotFoundException, ComponentTypeNotFoundException {

        when(salaryComponentMapper.toEntity(salaryComponentDTO, componentTypeRepository,
                componentTypeSubtypeRepository)).thenReturn(salaryComponent);
        when(componentTypeRepository.findById(1L)).thenReturn(Optional.ofNullable(componentType));
        when(componentTypeSubtypeRepository.findById(1L)).thenReturn(Optional.ofNullable(componentTypeSubtype));
        when(salaryRepository.findById(salaryComponentDTO.getSalaryId())).thenReturn(Optional.of(salary));
        when(salaryComponentRepository.save(Mockito.any(SalaryComponent.class))).thenReturn(salaryComponent);
        when(salaryComponentMapper.toDto(Mockito.any(SalaryComponent.class))).thenReturn(salaryComponentDTO);

        SalaryComponentDTO savedSalaryComponentDto = salaryComponentService.create(salaryComponentDTO);

        //Assert
        assertThat(savedSalaryComponentDto).isNotNull();
        assertThat(savedSalaryComponentDto.getValue()).isEqualTo(salaryComponentDTO.getValue());

    }

    @Test
    void salaryComponentService_update_returnsOneSalaryComponentDto() throws ComponentTypeSubtypeNotFoundException, ComponentTypeNotFoundException {

        when(salaryComponentMapper.toEntity(salaryComponentDTO, componentTypeRepository,
                componentTypeSubtypeRepository)).thenReturn(salaryComponent);
        when(componentTypeRepository.findById(1L)).thenReturn(Optional.ofNullable(componentType));
        when(componentTypeSubtypeRepository.findById(1L)).thenReturn(Optional.ofNullable(componentTypeSubtype));
        when(salaryRepository.findById(salaryComponentDTO.getSalaryId())).thenReturn(Optional.of(salary));
        when(salaryComponentRepository.save(Mockito.any(SalaryComponent.class))).thenReturn(salaryComponent);
        when(salaryComponentMapper.toDto(Mockito.any(SalaryComponent.class))).thenReturn(salaryComponentDTO);

        SalaryComponentDTO savedSalaryComponentDto = salaryComponentService.update(salaryComponentDTO);

        //Assert
        assertThat(savedSalaryComponentDto).isNotNull();
        assertThat(savedSalaryComponentDto.getValue()).isEqualTo(salaryComponentDTO.getValue());

    }

    @Test
    void salaryComponentService_delete_returnsNothing() {

        //Act
        when(salaryComponentRepository.findById(salaryComponent.getId())).thenReturn(Optional.ofNullable(salaryComponent));
        doNothing().when(salaryComponentRepository).delete(salaryComponent);

        //Assert
        assertAll(() -> salaryComponentService.delete(salaryComponent.getId()));

    }

}
