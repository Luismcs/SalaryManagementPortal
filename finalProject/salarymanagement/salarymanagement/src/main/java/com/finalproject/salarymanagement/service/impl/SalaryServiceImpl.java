package com.finalproject.salarymanagement.service.impl;

import com.finalproject.salarymanagement.dto.SalaryDTO;
import com.finalproject.salarymanagement.enums.ErrorResponseCode;
import com.finalproject.salarymanagement.enums.SalaryState;
import com.finalproject.salarymanagement.exception.*;
import com.finalproject.salarymanagement.mapper.SalaryMapper;
import com.finalproject.salarymanagement.model.Salary;
import com.finalproject.salarymanagement.repository.ComponentTypeRepository;
import com.finalproject.salarymanagement.repository.ComponentTypeSubtypeRepository;
import com.finalproject.salarymanagement.repository.SalaryRepository;
import com.finalproject.salarymanagement.service.SalaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final ComponentTypeRepository componentTypeRepository;
    private final ComponentTypeSubtypeRepository componentTypeSubtypeRepository;
    private final SalaryMapper salaryMapper;

    public SalaryServiceImpl(SalaryRepository salaryRepository,
                             SalaryMapper salaryMapper,
                             ComponentTypeRepository componentTypeRepository,
                             ComponentTypeSubtypeRepository componentTypeSubtypeRepository) {
        this.salaryRepository = salaryRepository;
        this.componentTypeRepository = componentTypeRepository;
        this.componentTypeSubtypeRepository = componentTypeSubtypeRepository;
        this.salaryMapper = salaryMapper;
    }

    public Page<SalaryDTO> getAll(Pageable pageable) {
        Page<Salary> componentTypeSubtype = salaryRepository.findAll(pageable);

        return componentTypeSubtype.map(salaryMapper::toDto);
    }

    public SalaryDTO getById(Long id) throws SalaryNotFoundException {
        Salary salary =
                salaryRepository.findById(id).orElseThrow(() ->
                        new SalaryNotFoundException(ErrorResponseCode.SALARY_NOT_FOUND,
                                404, ErrorMessage.SALARY_NOT_FOUND, id));

        log.info("Salary with id {} returned successfully", id);

        return salaryMapper.toDto(salary);
    }

    public SalaryDTO approve(Long id) throws SalaryNotFoundException,
            SalaryAlreadyApprovedException,
            SalaryAlreadyActiveException, SalaryCannotBeApprovedException {

        Salary salary =
                salaryRepository.findById(id).orElseThrow(() ->
                        new SalaryNotFoundException(ErrorResponseCode.SALARY_NOT_FOUND,
                                404, ErrorMessage.SALARY_NOT_FOUND, id));

        if (salary.getSalaryState() == SalaryState.ACTIVE) {
            throw new SalaryAlreadyActiveException(ErrorResponseCode.SALARY_IS_ALREADY_ACTIVE, 409,
                    ErrorMessage.SALARY_IS_ALREADY_ACTIVE, salary.getId());
        }

        if (salary.getAcceptanceDate() != null) {
            throw new SalaryAlreadyApprovedException(ErrorResponseCode.SALARY_IS_ALREADY_APPROVED, 409,
                    ErrorMessage.SALARY_IS_ALREADY_APPROVED, salary.getId());
        }

        if (salary.getSalaryState() == SalaryState.INACTIVE && !salary.getImplementationDate().isAfter(LocalDate.now())) {
            throw new SalaryCannotBeApprovedException(ErrorResponseCode.SALARY_CANNOT_BE_APPROVED_ANYMORE, 409,
                    ErrorMessage.SALARY_CANNOT_BE_APPROVED_ANYMORE, salary.getId());
        }

        salary.setAcceptanceDate(LocalDate.now());
        salaryRepository.save(salary);

        log.info("Salary with id {} approved successfully", id);

        return salaryMapper.toDto(salary);
    }

    public SalaryDTO create(SalaryDTO salaryDTO) throws ComponentTypeNotFoundException,
            ComponentTypeSubtypeNotFoundException,
            DuplicateSalaryForImplementationDateException {
        Optional<Salary> salaryWithImplementationDate =
                salaryRepository.findByCorrelationIdAndImplementationDate(salaryDTO.getCorrelationId(),
                        salaryDTO.getImplementationDate());

        if (salaryWithImplementationDate.isPresent()) {
            throw new DuplicateSalaryForImplementationDateException(
                    ErrorResponseCode.DUPLICATED_SALARY_FOR_IMPLEMENTATION_DATE,
                    409, ErrorMessage.DUPLICATED_SALARY_FOR_IMPLEMENTATION_DATE, salaryDTO.getImplementationDate());
        }

        Salary salary = salaryMapper.toEntity(salaryDTO, componentTypeRepository, componentTypeSubtypeRepository);
        Salary createdSalary = salaryRepository.save(salary);

        log.info("Salary with id {} created successfully", createdSalary.getId());

        return salaryMapper.toDto(createdSalary);
    }

    public SalaryDTO update(SalaryDTO salaryDTO) throws
            ComponentTypeNotFoundException,
            ComponentTypeSubtypeNotFoundException,
            SalaryNotFoundException,
            SalaryCannotBeUpdatedException,
            SalaryAlreadyActiveException,
            DuplicateSalaryForImplementationDateException {

        Optional<Salary> salaryWithImplementationDate =
                salaryRepository.findByCorrelationIdAndImplementationDate(salaryDTO.getCorrelationId(),
                        salaryDTO.getImplementationDate());

        if (salaryWithImplementationDate.isPresent()) {
            throw new DuplicateSalaryForImplementationDateException(
                    ErrorResponseCode.DUPLICATED_SALARY_FOR_IMPLEMENTATION_DATE,
                    409, ErrorMessage.DUPLICATED_SALARY_FOR_IMPLEMENTATION_DATE, salaryDTO.getImplementationDate());
        }

        Salary existingSalary =
                salaryRepository.findById(salaryDTO.getId()).orElseThrow(() ->
                        new SalaryNotFoundException(ErrorResponseCode.SALARY_NOT_FOUND
                                , 404, ErrorMessage.SALARY_NOT_FOUND, salaryDTO.getId()));

        if (existingSalary.getSalaryState() == SalaryState.ACTIVE) {
            throw new SalaryAlreadyActiveException(ErrorResponseCode.SALARY_IS_ALREADY_ACTIVE, 409,
                    ErrorMessage.SALARY_IS_ALREADY_ACTIVE, salaryDTO.getId());
        }

        if (existingSalary.getSalaryState() == SalaryState.INACTIVE &&
                existingSalary.getImplementationDate().isBefore(LocalDate.now()) ||
                existingSalary.getImplementationDate().isEqual(LocalDate.now())) {
            throw new SalaryCannotBeUpdatedException(ErrorResponseCode.SALARY_CANNOT_BE_UPDATED_ANYMORE, 409,
                    ErrorMessage.SALARY_CANNOT_BE_UPDATED_ANYMORE, salaryDTO.getId());
        }

        Salary updatedSalary = salaryMapper.toEntity(salaryDTO,
                componentTypeRepository, componentTypeSubtypeRepository);
        Salary updated = salaryRepository.save(updatedSalary);

        log.info("Salary with id {} updated successfully", updated.getId());

        return salaryMapper.toDto(updated);

    }

    public void delete(Long id) throws SalaryNotFoundException {
        Salary salary = salaryRepository.findById(id).orElseThrow(() ->
                new SalaryNotFoundException(ErrorResponseCode.SALARY_NOT_FOUND, 404, ErrorMessage.SALARY_NOT_FOUND, id));

        salaryRepository.delete(salary);

        log.info("Salary with id {} deleted successfully", id);
    }

    public void verifySalaryImplementation() {
        List<Long> oldSalaryIds = getOldSalariesIds();
        List<Long> newSalaryIds = getNewSalariesIds();

        updateOldSalaries(oldSalaryIds);
        updateNewSalaries(newSalaryIds);
    }

    public List<Long> getOldSalariesIds() {
        List<Long> oldSalaryIds = new ArrayList<>();
        List<Salary> salaries = salaryRepository.findAll();

        salaries.forEach(newSalary -> {
            if (Objects.equals(newSalary.getImplementationDate(), LocalDate.now()) &&
                    newSalary.getAcceptanceDate() != null
                    && newSalary.getSalaryState() == SalaryState.INACTIVE) {
                Optional<Salary> oldSalary =
                        salaryRepository.findByCorrelationIdAndSalaryState(newSalary.getCorrelationId(),
                                SalaryState.ACTIVE);
                oldSalary.ifPresent(salary -> oldSalaryIds.add(salary.getId()));
            }
        });

        return oldSalaryIds;
    }

    public List<Long> getNewSalariesIds() {
        List<Long> newSalaryIds = new ArrayList<>();
        List<Salary> salaries = salaryRepository.findAll();

        salaries.forEach(newSalary -> {
            if (Objects.equals(newSalary.getImplementationDate(), LocalDate.now()) && newSalary.getAcceptanceDate() != null
                    && newSalary.getSalaryState() == SalaryState.INACTIVE) {
                newSalaryIds.add(newSalary.getId());
            }
        });

        return newSalaryIds;
    }


    public void updateOldSalaries(List<Long> oldSalaryIds) {
        List<Salary> oldSalaries = salaryRepository.findAllById(oldSalaryIds);

        oldSalaries.forEach(salary -> salary.setSalaryState(SalaryState.INACTIVE));
        salaryRepository.saveAll(oldSalaries);
    }

    public void updateNewSalaries(List<Long> newSalaryIds) {
        List<Salary> newSalaries = salaryRepository.findAllById(newSalaryIds);

        newSalaries.forEach(salary -> salary.setSalaryState(SalaryState.ACTIVE));
        salaryRepository.saveAll(newSalaries);
    }


}
