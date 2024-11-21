package com.finalproject.salarymanagement.service.impl;

import com.finalproject.salarymanagement.dto.ComponentTypeDTO;
import com.finalproject.salarymanagement.enums.ErrorResponseCode;
import com.finalproject.salarymanagement.exception.ComponentTypeNotFoundException;
import com.finalproject.salarymanagement.exception.ErrorMessage;
import com.finalproject.salarymanagement.mapper.ComponentTypeMapper;
import com.finalproject.salarymanagement.model.ComponentType;
import com.finalproject.salarymanagement.repository.ComponentTypeRepository;
import com.finalproject.salarymanagement.service.ComponentTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ComponentTypeServiceImpl implements ComponentTypeService {

    private final ComponentTypeRepository componentTypeRepository;
    private final ComponentTypeMapper componentTypeMapper;

    ComponentTypeServiceImpl(ComponentTypeRepository componentTypeRepository, ComponentTypeMapper componentTypeMapper) {
        this.componentTypeRepository = componentTypeRepository;
        this.componentTypeMapper = componentTypeMapper;
    }

    public Page<ComponentTypeDTO> getAll(Pageable pageable) {
        Page<ComponentType> componentTypes = componentTypeRepository.findAll(pageable);
        return componentTypes.map(componentTypeMapper::toDto);
    }

    public ComponentTypeDTO getById(Long id) throws ComponentTypeNotFoundException {
        ComponentType componentType =
                componentTypeRepository.findById(id).orElseThrow(() -> new ComponentTypeNotFoundException
                        (ErrorResponseCode.COMPONENT_TYPE_NOT_FOUND, 404, ErrorMessage.COMPONENT_TYPE_NOT_FOUND, id));

        log.info("Component Type with id {} returned successfully", id);

        return componentTypeMapper.toDto(componentType);
    }

    public ComponentTypeDTO create(ComponentTypeDTO componentTypeDTO) {
        ComponentType toSave = componentTypeMapper.toEntity(componentTypeDTO);
        ComponentType savedComponentType = componentTypeRepository.save(toSave);
        return componentTypeMapper.toDto(savedComponentType);
    }

    public ComponentTypeDTO update(ComponentTypeDTO componentTypeDTO) throws ComponentTypeNotFoundException {
        componentTypeRepository.findById(componentTypeDTO.getId()).orElseThrow(() ->
                new ComponentTypeNotFoundException(ErrorResponseCode.COMPONENT_TYPE_NOT_FOUND
                        , 404, ErrorMessage.COMPONENT_TYPE_NOT_FOUND, componentTypeDTO.getId()));
        ComponentType updated = componentTypeMapper.toEntity(componentTypeDTO);
        ComponentType saved = componentTypeRepository.save(updated);

        log.info("Component Type with id {} created successfully", saved.getId());

        return componentTypeMapper.toDto(saved);
    }

    public void delete(Long id) throws ComponentTypeNotFoundException {
        ComponentType componentType =
                componentTypeRepository.findById(id).orElseThrow(() ->
                        new ComponentTypeNotFoundException(ErrorResponseCode.COMPONENT_TYPE_NOT_FOUND
                                , 404, ErrorMessage.COMPONENT_TYPE_NOT_FOUND, id));
        componentTypeRepository.delete(componentType);

        log.info("Component Type with id {} deleted successfully", id);
    }

}
