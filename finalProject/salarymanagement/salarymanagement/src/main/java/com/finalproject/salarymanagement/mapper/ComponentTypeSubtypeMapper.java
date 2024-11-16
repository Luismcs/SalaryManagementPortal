package com.finalproject.salarymanagement.mapper;

import com.finalproject.salarymanagement.dto.ComponentTypeDTO;
import com.finalproject.salarymanagement.dto.ComponentTypeSubtypeDTO;
import com.finalproject.salarymanagement.model.ComponentType;
import com.finalproject.salarymanagement.model.ComponentTypeSubtype;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComponentTypeSubtypeMapper {

    @Mapping(source = "componentTypeId", target = "componentType.id")
    ComponentTypeSubtype toEntity(ComponentTypeSubtypeDTO componentTypeSubtypeDTO);

    @Mapping(source = "componentType.id", target = "componentTypeId")
    ComponentTypeSubtypeDTO toDto(ComponentTypeSubtype componentTypeSubtype);

    @Mapping(source = "componentTypeId", target = "componentType.id")
    List<ComponentTypeSubtype> toEntityList(List<ComponentTypeSubtypeDTO> componentTypeSubtypeDTOList);

    List<ComponentTypeSubtypeDTO> toDtoList(List<ComponentTypeSubtype> componentTypeSubtypeList);

}
