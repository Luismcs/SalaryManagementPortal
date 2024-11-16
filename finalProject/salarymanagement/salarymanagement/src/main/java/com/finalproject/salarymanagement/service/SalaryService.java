package com.finalproject.salarymanagement.service;

import com.finalproject.salarymanagement.dto.SalaryDTO;
import com.finalproject.salarymanagement.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SalaryService {

    Page<SalaryDTO> getAll(Pageable pageable);

    SalaryDTO getById(Long id) throws SalaryNotFoundException;

    SalaryDTO approve(Long id) throws SalaryNotFoundException, SalaryAlreadyApprovedException, SalaryCannotBeApprovedException, SalaryAlreadyActiveException;

    SalaryDTO create(SalaryDTO salaryDTO) throws ComponentTypeNotFoundException,
            ComponentTypeSubtypeNotFoundException, DuplicateSalaryForImplementationDateException;

    SalaryDTO update(SalaryDTO salaryDTO) throws ComponentTypeNotFoundException,
            ComponentTypeSubtypeNotFoundException, SalaryNotFoundException, SalaryCannotBeUpdatedException, SalaryAlreadyActiveException, DuplicateSalaryForImplementationDateException;

    void delete(Long id) throws SalaryNotFoundException;

}
