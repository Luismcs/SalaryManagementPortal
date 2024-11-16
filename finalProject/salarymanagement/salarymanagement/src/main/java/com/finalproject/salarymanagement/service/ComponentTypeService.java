package com.finalproject.salarymanagement.service;

import com.finalproject.salarymanagement.dto.ComponentTypeDTO;
import com.finalproject.salarymanagement.exception.ComponentTypeNotFoundException;
import com.finalproject.salarymanagement.model.ComponentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComponentTypeService {

    Page<ComponentTypeDTO> getAll(Pageable pageable);

    ComponentTypeDTO getById(Long id) throws ComponentTypeNotFoundException;

    ComponentTypeDTO create(ComponentTypeDTO componentTypeDTO);

    ComponentTypeDTO update(ComponentTypeDTO componentTypeDTO) throws ComponentTypeNotFoundException;

    void delete(Long id) throws ComponentTypeNotFoundException;

}
