package com.finalproject.salarymanagement.service;

import com.finalproject.salarymanagement.dto.SalaryDTO;
import com.finalproject.salarymanagement.enums.SalaryState;
import com.finalproject.salarymanagement.exception.*;
import com.finalproject.salarymanagement.mapper.SalaryMapper;
import com.finalproject.salarymanagement.model.Salary;
import com.finalproject.salarymanagement.repository.ComponentTypeRepository;
import com.finalproject.salarymanagement.repository.ComponentTypeSubtypeRepository;
import com.finalproject.salarymanagement.repository.SalaryRepository;
import com.finalproject.salarymanagement.service.impl.SalaryServiceImpl;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalaryServiceTest {

    @Mock
    private SalaryRepository salaryRepository;

    @Mock
    private ComponentTypeRepository componentTypeRepository;

    @Mock
    private ComponentTypeSubtypeRepository componentTypeSubtypeRepository;

    @Mock
    private SalaryMapper salaryMapper;

    @InjectMocks
    private SalaryServiceImpl salaryService;

    private Salary salary;
    private SalaryDTO salaryDTO;
    private Salary salaryToUpdate;
    private SalaryDTO salaryDTOToUpdate;
    private Salary toApproveSalary;
    private SalaryDTO toApproveSalaryDTO;
    private Long salaryId;

    @BeforeEach
    public void init() {

        salary = new Salary();
        salary.setId(1L);
        salary.setAcceptanceDate(null);
        salary.setCorrelationId("1");
        salary.setImplementationDate(LocalDate.now());
        salary.setSalaryState(SalaryState.INACTIVE);

        salaryDTO = new SalaryDTO();
        salaryDTO.setId(1L);
        salaryDTO.setAcceptanceDate(null);
        salaryDTO.setCorrelationId("1");
        salaryDTO.setImplementationDate(LocalDate.now());
        salaryDTO.setSalaryState("INACTIVE");

        toApproveSalary = new Salary();
        toApproveSalary.setId(1L);
        toApproveSalary.setAcceptanceDate(null);
        toApproveSalary.setCorrelationId("1");
        toApproveSalary.setImplementationDate(LocalDate.now().plusYears(2));
        toApproveSalary.setSalaryState(SalaryState.INACTIVE);

        toApproveSalaryDTO = new SalaryDTO();
        toApproveSalaryDTO.setCorrelationId("1");
        toApproveSalaryDTO.setAcceptanceDate(LocalDate.now());
        toApproveSalaryDTO.setImplementationDate(LocalDate.now().plusYears(2));
        toApproveSalaryDTO.setSalaryState("ACTIVE");

        salaryToUpdate = new Salary();
        salaryToUpdate.setId(1L);
        salaryToUpdate.setAcceptanceDate(null);
        salaryToUpdate.setCorrelationId("1");
        salaryToUpdate.setImplementationDate(LocalDate.now().plusYears(2));
        salaryToUpdate.setSalaryState(SalaryState.INACTIVE);

        salaryDTOToUpdate = new SalaryDTO();
        salaryDTOToUpdate.setId(1L);
        salaryDTOToUpdate.setAcceptanceDate(null);
        salaryDTOToUpdate.setCorrelationId("1");
        salaryToUpdate.setImplementationDate(LocalDate.now().plusYears(2));
        salaryDTOToUpdate.setSalaryState("INACTIVE");

        salaryId = 1L;
    }

    @Test
    void salaryService_getAll_returnsSalaryDtoPage() {

        //Arrange
        Pageable pageable = PageRequest.of(0, 2);
        List<Salary> salaries = List.of(salary);
        Page<Salary> salaryPage = new PageImpl<>(salaries);

        //Act
        when(salaryRepository.findAll(Mockito.any(Pageable.class))).thenReturn(salaryPage);
        when(salaryMapper.toDto(salary)).thenReturn(salaryDTO);

        Page<SalaryDTO> result = salaryService.getAll(pageable);

        //Assert
        assertThat(result).isNotNull();

    }

    @Test
    void salaryService_getById_returnsSalaryDto() throws SalaryNotFoundException {

        //Act
        when(salaryRepository.findById(salaryId)).thenReturn(Optional.ofNullable(salary));
        when(salaryMapper.toDto(Mockito.any(Salary.class))).thenReturn(salaryDTO);

        SalaryDTO foundSalary = salaryService.getById(salaryId);

        //Assert
        assertThat(foundSalary).isNotNull();

    }

    @Test
    void salaryService_approve_returnsSalaryDto() throws SalaryNotFoundException, SalaryAlreadyApprovedException, SalaryAlreadyActiveException, SalaryCannotBeApprovedException {

        //Act
        when(salaryRepository.findById(salaryId)).thenReturn(Optional.ofNullable(toApproveSalary));
        when(salaryMapper.toDto(Mockito.any(Salary.class))).thenReturn(toApproveSalaryDTO);

        SalaryDTO salaryDTO1 = salaryService.approve(salaryId);

        //Assert
        assertThat(salaryDTO1.getSalaryState()).isEqualTo("ACTIVE");

    }

    @Test
    void salaryService_create_returnsSalaryDto() throws DuplicateSalaryForImplementationDateException, ComponentTypeSubtypeNotFoundException, ComponentTypeNotFoundException {

        //Act
        when(salaryMapper.toEntity(Mockito.any(SalaryDTO.class), Mockito.any(ComponentTypeRepository.class),
                Mockito.any(ComponentTypeSubtypeRepository.class))).thenReturn(salary);
        when(salaryRepository.findByCorrelationIdAndImplementationDate(
                Mockito.anyString(), Mockito.any(LocalDate.class))).thenReturn(Optional.empty());
        when(salaryRepository.save(Mockito.any(Salary.class))).thenReturn(salary);
        when(salaryMapper.toDto(Mockito.any(Salary.class))).thenReturn(salaryDTO);

        SalaryDTO createdSalary = salaryService.create(salaryDTO);

        //Assert
        assertThat(createdSalary).isNotNull();
        assertThat(createdSalary.getCorrelationId()).isEqualTo(salary.getCorrelationId());


    }

    @Test
    void salaryService_update_returnsSalaryDto() throws DuplicateSalaryForImplementationDateException,
            ComponentTypeSubtypeNotFoundException, ComponentTypeNotFoundException, SalaryNotFoundException, SalaryAlreadyActiveException, SalaryCannotBeUpdatedException {

        //Act
        when(salaryRepository.findByCorrelationIdAndImplementationDate(
                "1", salaryDTOToUpdate.getImplementationDate())).thenReturn(Optional.empty());

        when(salaryRepository.findById(salaryDTOToUpdate.getId())).thenReturn(Optional.ofNullable(salaryToUpdate));
        when(salaryMapper.toEntity(Mockito.any(SalaryDTO.class), Mockito.any(ComponentTypeRepository.class),
                Mockito.any(ComponentTypeSubtypeRepository.class))).thenReturn(salaryToUpdate);
        when(salaryRepository.save(Mockito.any(Salary.class))).thenReturn(salaryToUpdate);
        when(salaryMapper.toDto(Mockito.any(Salary.class))).thenReturn(salaryDTOToUpdate);

        SalaryDTO updatedSalary = salaryService.update(salaryDTOToUpdate);

        //Assert
        assertThat(updatedSalary).isNotNull();
        assertThat(updatedSalary.getCorrelationId()).isEqualTo(salaryToUpdate.getCorrelationId());

    }

    @Test
    void salaryService_delete_returnsNothing() {

        //Act
        when(salaryRepository.findById(salary.getId())).thenReturn(Optional.ofNullable(salary));
        doNothing().when(salaryRepository).delete(salary);

        //Assert
        assertAll(() -> salaryService.delete(salary.getId()));

    }

    @Test
    void salaryService_getNewSalariesIds_returnsNothing() {

        //Arrange
        Salary salaryGet = new Salary();
        salaryGet.setId(1L);
        salaryGet.setAcceptanceDate(LocalDate.now());
        salaryGet.setCorrelationId("1");
        salaryGet.setImplementationDate(LocalDate.now());
        salaryGet.setSalaryState(SalaryState.INACTIVE);
        List<Long> newSalaryIds = new ArrayList<>();
        List<Salary> salaries = List.of(salaryGet);

        when(salaryRepository.findAll()).thenReturn(salaries);

        List<Long> finalNewSalaryIds = newSalaryIds;
        salaries.forEach(newSalary -> {
            if (Objects.equals(newSalary.getImplementationDate(), LocalDate.now()) && newSalary.getAcceptanceDate() != null
                    && newSalary.getSalaryState() == SalaryState.INACTIVE) {
                finalNewSalaryIds.add(newSalary.getId());
            }
        });

        newSalaryIds = salaryService.getNewSalariesIds();

        assertThat(newSalaryIds.size()).isEqualTo(1);

    }

    @Test
    void salaryService_getOldSalariesIds_returnsNothing() {

        //Arrange
        Salary salaryGet = new Salary();
        salaryGet.setId(1L);
        salaryGet.setAcceptanceDate(LocalDate.now());
        salaryGet.setCorrelationId("1");
        salaryGet.setImplementationDate(LocalDate.now());
        salaryGet.setSalaryState(SalaryState.INACTIVE);
        List<Long> newSalaryIds = new ArrayList<>();
        List<Salary> salaries = List.of(salaryGet);

        when(salaryRepository.findAll()).thenReturn(salaries);

        List<Long> finalNewSalaryIds = newSalaryIds;
        salaries.forEach(newSalary -> {
            if (Objects.equals(newSalary.getImplementationDate(), LocalDate.now()) &&
                    newSalary.getAcceptanceDate() != null
                    && newSalary.getSalaryState() == SalaryState.INACTIVE) {
                Optional<Salary> oldSalary =
                        salaryRepository.findByCorrelationIdAndSalaryState(newSalary.getCorrelationId(),
                                SalaryState.ACTIVE);
                oldSalary.ifPresent(salary -> finalNewSalaryIds.add(salary.getId()));
            }
        });

        newSalaryIds = salaryService.getNewSalariesIds();

        assertThat(newSalaryIds.size()).isEqualTo(1);

    }

    @Test
    void salaryService_updateOldSalaries_returnsNothing() {

        //Arrange
        List<Salary> oldSalaries = List.of(salary);
        List<Long> salaryIds = List.of(1L);

        when(salaryRepository.findAllById(salaryIds)).thenReturn(oldSalaries);

        salaryService.updateOldSalaries(salaryIds);
    }

    @Test
    void salaryService_updateNewSalaries_returnsNothing() {

        //Arrange
        List<Salary> newSalaries = List.of(salary);
        List<Long> salaryIds = List.of(1L);

        when(salaryRepository.findAllById(salaryIds)).thenReturn(newSalaries);

        salaryService.updateOldSalaries(salaryIds);
    }

}
