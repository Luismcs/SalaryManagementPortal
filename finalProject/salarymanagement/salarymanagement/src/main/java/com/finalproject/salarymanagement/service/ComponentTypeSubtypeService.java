package com.finalproject.salarymanagement.service;

import com.finalproject.salarymanagement.dto.ComponentTypeDTO;
import com.finalproject.salarymanagement.dto.ComponentTypeSubtypeDTO;
import com.finalproject.salarymanagement.enums.ErrorResponseCode;
import com.finalproject.salarymanagement.exception.ComponentTypeNotFoundException;
import com.finalproject.salarymanagement.exception.ComponentTypeSubtypeNotFoundException;
import com.finalproject.salarymanagement.exception.ErrorMessage;
import com.finalproject.salarymanagement.model.ComponentType;
import com.finalproject.salarymanagement.model.ComponentTypeSubtype;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComponentTypeSubtypeService {

    Page<ComponentTypeSubtypeDTO> getAll(Pageable pageable);

    ComponentTypeSubtypeDTO getById(Long id) throws ComponentTypeSubtypeNotFoundException;

    ComponentTypeSubtypeDTO create(ComponentTypeSubtypeDTO componentTypeSubtypeDTO) throws ComponentTypeNotFoundException;

    ComponentTypeSubtypeDTO update(ComponentTypeSubtypeDTO componentTypeSubtypeDTO) throws ComponentTypeNotFoundException;

    void delete(Long id) throws ComponentTypeSubtypeNotFoundException;

}
