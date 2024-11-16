package com.finalproject.salarymanagement.mapper;

import com.finalproject.salarymanagement.dto.SalaryComponentDTO;
import com.finalproject.salarymanagement.enums.ErrorResponseCode;
import com.finalproject.salarymanagement.exception.ComponentTypeNotFoundException;
import com.finalproject.salarymanagement.exception.ComponentTypeSubtypeNotFoundException;
import com.finalproject.salarymanagement.exception.ErrorMessage;
import com.finalproject.salarymanagement.model.ComponentType;
import com.finalproject.salarymanagement.model.ComponentTypeSubtype;
import com.finalproject.salarymanagement.model.SalaryComponent;
import com.finalproject.salarymanagement.repository.ComponentTypeRepository;
import com.finalproject.salarymanagement.repository.ComponentTypeSubtypeRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ComponentTypeRepository.class, ComponentTypeSubtypeRepository.class})
public interface SalaryComponentMapper {

    @Mapping(target = "componentType", source = "componentTypeId", qualifiedByName = "componentTypeIdToComponentType")
    @Mapping(target = "componentTypeSubtype", source = "componentTypeSubtypeId", qualifiedByName = "componentTypeSubtypeIdToComponentTypeSubtype")
    SalaryComponent toEntity(SalaryComponentDTO salaryComponentDTO, @Context ComponentTypeRepository componentTypeRepository, @Context ComponentTypeSubtypeRepository componentTypeSubtypeRepository);

    @Mapping(source = "componentType.id", target = "componentTypeId")
    @Mapping(source = "componentTypeSubtype.id", target = "componentTypeSubtypeId")
    @Mapping(source = "salary.id", target = "salaryId")
    SalaryComponentDTO toDto(SalaryComponent salaryComponent);

    @Named("componentTypeIdToComponentType")
    default ComponentType componentTypeIdToComponentType(Long componentTypeId, @Context ComponentTypeRepository componentTypeRepository) throws ComponentTypeNotFoundException {
        return componentTypeRepository.findById(componentTypeId)
                .orElseThrow(() -> new ComponentTypeNotFoundException(ErrorResponseCode.COMPONENT_TYPE_NOT_FOUND, 404, ErrorMessage.COMPONENT_TYPE_NOT_FOUND, componentTypeId));
    }

    @Named("componentTypeSubtypeIdToComponentTypeSubtype")
    default ComponentTypeSubtype componentTypeSubtypeIdToComponentTypeSubtype(Long componentTypeSubtypeId, @Context ComponentTypeSubtypeRepository componentTypeSubtypeRepository) throws ComponentTypeSubtypeNotFoundException {
        return componentTypeSubtypeRepository.findById(componentTypeSubtypeId)
                .orElseThrow(() -> new ComponentTypeSubtypeNotFoundException(ErrorResponseCode.COMPONENT_TYPE_SUBTYPE_NOT_FOUND, 404, ErrorMessage.COMPONENT_TYPE_SUBTYPE_NOT_FOUND, componentTypeSubtypeId));
    }
}
