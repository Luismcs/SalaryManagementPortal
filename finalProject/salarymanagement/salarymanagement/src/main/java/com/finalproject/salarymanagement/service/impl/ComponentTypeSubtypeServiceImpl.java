package com.finalproject.salarymanagement.service.impl;

import com.finalproject.salarymanagement.dto.ComponentTypeSubtypeDTO;
import com.finalproject.salarymanagement.enums.ErrorResponseCode;
import com.finalproject.salarymanagement.exception.ComponentTypeNotFoundException;
import com.finalproject.salarymanagement.exception.ComponentTypeSubtypeNotFoundException;
import com.finalproject.salarymanagement.exception.ErrorMessage;
import com.finalproject.salarymanagement.mapper.ComponentTypeSubtypeMapper;
import com.finalproject.salarymanagement.model.ComponentType;
import com.finalproject.salarymanagement.model.ComponentTypeSubtype;
import com.finalproject.salarymanagement.repository.ComponentTypeRepository;
import com.finalproject.salarymanagement.repository.ComponentTypeSubtypeRepository;
import com.finalproject.salarymanagement.service.ComponentTypeSubtypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ComponentTypeSubtypeServiceImpl implements ComponentTypeSubtypeService {

    private final ComponentTypeSubtypeRepository componentTypeSubtypeRepository;
    private final ComponentTypeRepository componentTypeRepository;
    private final ComponentTypeSubtypeMapper componentTypeSubtypeMapper;

    ComponentTypeSubtypeServiceImpl(ComponentTypeSubtypeRepository componentTypeSubtypeRepository,
                                    ComponentTypeSubtypeMapper componentTypeSubtypeMapper,
                                    ComponentTypeRepository componentTypeRepository) {
        this.componentTypeSubtypeRepository = componentTypeSubtypeRepository;
        this.componentTypeSubtypeMapper = componentTypeSubtypeMapper;
        this.componentTypeRepository = componentTypeRepository;
    }

    public Page<ComponentTypeSubtypeDTO> getAll(Pageable pageable) {
        Page<ComponentTypeSubtype> componentTypeSubtype = componentTypeSubtypeRepository.findAll(pageable);

        return componentTypeSubtype.map(componentTypeSubtypeMapper::toDto);
    }

    public ComponentTypeSubtypeDTO getById(Long id) throws ComponentTypeSubtypeNotFoundException {
        ComponentTypeSubtype componentTypeSubtype =
                componentTypeSubtypeRepository.findById(id).orElseThrow(() -> new ComponentTypeSubtypeNotFoundException
                        (ErrorResponseCode.COMPONENT_TYPE_SUBTYPE_NOT_FOUND,
                                404, ErrorMessage.COMPONENT_TYPE_SUBTYPE_NOT_FOUND, id));

        log.info("Component Type Subtype with id {} returned successfully", id);

        return componentTypeSubtypeMapper.toDto(componentTypeSubtype);
    }

    public ComponentTypeSubtypeDTO create(ComponentTypeSubtypeDTO componentTypeSubtypeDTO) throws
            ComponentTypeNotFoundException {
        ComponentType componentType =
                componentTypeRepository.findById(componentTypeSubtypeDTO.getComponentTypeId()).orElseThrow(() ->
                        new ComponentTypeNotFoundException(ErrorResponseCode.COMPONENT_TYPE_NOT_FOUND,
                                404,
                                ErrorMessage.COMPONENT_TYPE_NOT_FOUND, componentTypeSubtypeDTO.getComponentTypeId()));

        ComponentTypeSubtype componentTypeSubtype = componentTypeSubtypeMapper.toEntity(componentTypeSubtypeDTO);
        componentTypeSubtype.setComponentType(componentType);
        ComponentTypeSubtype savedComponentTypeSubtype = componentTypeSubtypeRepository.save(componentTypeSubtype);

        log.info("Component Type Subtype with id {} created successfully", savedComponentTypeSubtype.getId());

        return componentTypeSubtypeMapper.toDto(savedComponentTypeSubtype);
    }

    public ComponentTypeSubtypeDTO update(ComponentTypeSubtypeDTO componentTypeSubtypeDTO) throws
            ComponentTypeNotFoundException {
        ComponentType componentType =
                componentTypeRepository.findById(componentTypeSubtypeDTO.getComponentTypeId()).orElseThrow(() ->
                        new ComponentTypeNotFoundException(ErrorResponseCode.COMPONENT_TYPE_NOT_FOUND,
                                404,
                                ErrorMessage.COMPONENT_TYPE_NOT_FOUND,
                                componentTypeSubtypeDTO.getComponentTypeId()));

        ComponentTypeSubtype componentTypeSubtype = componentTypeSubtypeMapper.toEntity(componentTypeSubtypeDTO);
        componentTypeSubtype.setComponentType(componentType);
        ComponentTypeSubtype savedComponentTypeSubtype = componentTypeSubtypeRepository.save(componentTypeSubtype);

        log.info("Component Type Subtype with id {} updated successfully", savedComponentTypeSubtype.getId());

        return componentTypeSubtypeMapper.toDto(savedComponentTypeSubtype);
    }

    public void delete(Long id) throws ComponentTypeSubtypeNotFoundException {
        ComponentTypeSubtype componentTypeSubtype =
                componentTypeSubtypeRepository.findById(id).orElseThrow(() -> new ComponentTypeSubtypeNotFoundException
                        (ErrorResponseCode.COMPONENT_TYPE_SUBTYPE_NOT_FOUND,
                                404, ErrorMessage.COMPONENT_TYPE_SUBTYPE_NOT_FOUND, id));

        componentTypeSubtypeRepository.delete(componentTypeSubtype);

        log.info("Component Type Subtype with id {} deleted successfully", id);

    }

}
