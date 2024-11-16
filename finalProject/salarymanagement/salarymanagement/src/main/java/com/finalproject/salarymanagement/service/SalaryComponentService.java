package com.finalproject.salarymanagement.service;

import com.finalproject.salarymanagement.dto.SalaryComponentDTO;
import com.finalproject.salarymanagement.exception.ComponentTypeNotFoundException;
import com.finalproject.salarymanagement.exception.ComponentTypeSubtypeNotFoundException;
import com.finalproject.salarymanagement.exception.SalaryComponentNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SalaryComponentService {

    Page<SalaryComponentDTO> getAll(Pageable pageable);

    SalaryComponentDTO getById(Long id) throws SalaryComponentNotFoundException;

    SalaryComponentDTO create(SalaryComponentDTO salaryComponentDTO) throws ComponentTypeNotFoundException,
            ComponentTypeSubtypeNotFoundException;

    void delete(Long id) throws SalaryComponentNotFoundException;

}
