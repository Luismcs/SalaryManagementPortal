package com.finalproject.salarymanagement.service.impl;

import com.finalproject.salarymanagement.dto.SalaryComponentDTO;
import com.finalproject.salarymanagement.enums.ErrorResponseCode;
import com.finalproject.salarymanagement.exception.ComponentTypeNotFoundException;
import com.finalproject.salarymanagement.exception.ComponentTypeSubtypeNotFoundException;
import com.finalproject.salarymanagement.exception.ErrorMessage;
import com.finalproject.salarymanagement.exception.SalaryComponentNotFoundException;
import com.finalproject.salarymanagement.mapper.SalaryComponentMapper;
import com.finalproject.salarymanagement.model.ComponentType;
import com.finalproject.salarymanagement.model.ComponentTypeSubtype;
import com.finalproject.salarymanagement.model.Salary;
import com.finalproject.salarymanagement.model.SalaryComponent;
import com.finalproject.salarymanagement.repository.ComponentTypeRepository;
import com.finalproject.salarymanagement.repository.ComponentTypeSubtypeRepository;
import com.finalproject.salarymanagement.repository.SalaryComponentRepository;
import com.finalproject.salarymanagement.repository.SalaryRepository;
import com.finalproject.salarymanagement.service.SalaryComponentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalaryComponentServiceImpl implements SalaryComponentService {

    private final SalaryComponentRepository salaryComponentRepository;
    private final ComponentTypeRepository componentTypeRepository;
    private final ComponentTypeSubtypeRepository componentTypeSubtypeRepository;
    private final SalaryRepository salaryRepository;
    private final SalaryComponentMapper salaryComponentMapper;

    public SalaryComponentServiceImpl(SalaryComponentRepository salaryComponentRepository,
                                      ComponentTypeRepository componentTypeRepository,
                                      ComponentTypeSubtypeRepository componentTypeSubtypeRepository,
                                      SalaryComponentMapper salaryComponentMapper,
                                      SalaryRepository salaryRepository) {
        this.salaryComponentRepository = salaryComponentRepository;
        this.componentTypeRepository = componentTypeRepository;
        this.componentTypeSubtypeRepository = componentTypeSubtypeRepository;
        this.salaryComponentMapper = salaryComponentMapper;
        this.salaryRepository = salaryRepository;
    }

    public Page<SalaryComponentDTO> getAll(Pageable pageable) {
        Page<SalaryComponent> componentTypeSubtype = salaryComponentRepository.findAll(pageable);

        return componentTypeSubtype.map(salaryComponentMapper::toDto);
    }

    public SalaryComponentDTO getById(Long id) throws SalaryComponentNotFoundException {
        SalaryComponent salaryComponent =
                salaryComponentRepository.findById(id).orElseThrow(() -> new SalaryComponentNotFoundException
                        (ErrorResponseCode.SALARY_COMPONENT_NOT_FOUND, 404, ErrorMessage.SALARY_COMPONENT_NOT_FOUND,
                                id));

        return salaryComponentMapper.toDto(salaryComponent);
    }

    public SalaryComponentDTO create(SalaryComponentDTO salaryComponentDTO) throws ComponentTypeNotFoundException,
            ComponentTypeSubtypeNotFoundException {
        SalaryComponent salaryComponent = salaryComponentMapper.toEntity(salaryComponentDTO, componentTypeRepository,
                componentTypeSubtypeRepository);
        Optional<ComponentType> componentType =
                componentTypeRepository.findById(salaryComponentDTO.getComponentTypeId());

        if (componentType.isEmpty()) {
            throw new ComponentTypeNotFoundException(ErrorResponseCode.COMPONENT_TYPE_NOT_FOUND,
                    404, ErrorMessage.COMPONENT_TYPE_NOT_FOUND,
                    salaryComponentDTO.getComponentTypeId());
        }

        Optional<ComponentTypeSubtype> componentTypeSubtype =
                componentTypeSubtypeRepository.findById(salaryComponentDTO.getComponentTypeSubtypeId());

        if (componentTypeSubtype.isEmpty()) {
            throw new ComponentTypeSubtypeNotFoundException(ErrorResponseCode.COMPONENT_TYPE_SUBTYPE_NOT_FOUND, 404,
                    ErrorMessage.COMPONENT_TYPE_SUBTYPE_NOT_FOUND,
                    salaryComponentDTO.getComponentTypeSubtypeId());
        }

        Optional<Salary> salary =
                salaryRepository.findById(salaryComponentDTO.getSalaryId());

        if (salary.isEmpty()) {
            throw new ComponentTypeSubtypeNotFoundException(ErrorResponseCode.SALARY_NOT_FOUND, 404,
                    ErrorMessage.SALARY_NOT_FOUND,
                    salaryComponentDTO.getSalaryId());
        }

        salaryComponent.setComponentType(componentType.get());
        salaryComponent.setComponentTypeSubtype(componentTypeSubtype.get());
        salaryComponent.setSalary(salary.get());
        salaryComponentRepository.save(salaryComponent);

        return salaryComponentMapper.toDto(salaryComponent);
    }

    public SalaryComponentDTO update(SalaryComponentDTO salaryComponentDTO) throws ComponentTypeNotFoundException,
            ComponentTypeSubtypeNotFoundException {

        SalaryComponent salaryComponent = salaryComponentMapper.toEntity(salaryComponentDTO, componentTypeRepository,
                componentTypeSubtypeRepository);

        Optional<ComponentType> componentType =
                componentTypeRepository.findById(salaryComponentDTO.getComponentTypeId());

        if (componentType.isEmpty()) {
            throw new ComponentTypeNotFoundException(ErrorResponseCode.COMPONENT_TYPE_NOT_FOUND,
                    404, ErrorMessage.COMPONENT_TYPE_NOT_FOUND,
                    salaryComponentDTO.getComponentTypeId());
        }

        Optional<ComponentTypeSubtype> componentTypeSubtype =
                componentTypeSubtypeRepository.findById(salaryComponentDTO.getComponentTypeSubtypeId());

        if (componentTypeSubtype.isEmpty()) {
            throw new ComponentTypeSubtypeNotFoundException(ErrorResponseCode.COMPONENT_TYPE_SUBTYPE_NOT_FOUND, 404,
                    ErrorMessage.COMPONENT_TYPE_SUBTYPE_NOT_FOUND,
                    salaryComponentDTO.getComponentTypeSubtypeId());
        }

        Optional<Salary> salary =
                salaryRepository.findById(salaryComponentDTO.getSalaryId());

        if (salary.isEmpty()) {
            throw new ComponentTypeSubtypeNotFoundException(ErrorResponseCode.SALARY_NOT_FOUND, 404,
                    ErrorMessage.SALARY_NOT_FOUND,
                    salaryComponentDTO.getSalaryId());
        }

        salaryComponent.setComponentType(componentType.get());
        salaryComponent.setComponentTypeSubtype(componentTypeSubtype.get());
        salaryComponent.setSalary(salary.get());
        SalaryComponent updatedSalaryComponent = salaryComponentRepository.save(salaryComponent);

        return salaryComponentMapper.toDto(updatedSalaryComponent);
    }

    public void delete(Long id) throws SalaryComponentNotFoundException {
        SalaryComponent salaryComponent =
                salaryComponentRepository.findById(id).orElseThrow(() -> new SalaryComponentNotFoundException
                        (ErrorResponseCode.SALARY_COMPONENT_NOT_FOUND, 404, ErrorMessage.SALARY_COMPONENT_NOT_FOUND,
                                id));

        salaryComponentRepository.delete(salaryComponent);
    }

}
