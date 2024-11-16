package com.finalproject.salarymanagement.mapper;

import com.finalproject.salarymanagement.dto.ComponentTypeDTO;
import com.finalproject.salarymanagement.dto.ComponentTypeSubtypeDTO;
import com.finalproject.salarymanagement.model.ComponentType;
import com.finalproject.salarymanagement.model.ComponentTypeSubtype;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = ComponentTypeSubtypeMapper.class)
public interface ComponentTypeMapper {

    @Mapping(target = "componentTypeSubtypes", qualifiedByName =
            "componentTypeSubtypesDtoToComponentTypeSubtypesEntity")
    ComponentType toEntity(ComponentTypeDTO componentTypeDTO);

    ComponentTypeDTO toDto(ComponentType componentType);

    @IterableMapping(qualifiedByName = "componentTypeComponentTypeSubtypeDtoToComponentTypeComponentTypeSubtype")
    @Named("componentTypeSubtypesDtoToComponentTypeSubtypesEntity")
    List<ComponentTypeSubtype> componentTypeSubtypesDtoToComponentTypeSubtypesEntity(List<ComponentTypeSubtypeDTO> componentTypeSubtypeDTOS);

    @Named("componentTypeComponentTypeSubtypeDtoToComponentTypeComponentTypeSubtype")
    ComponentTypeSubtype componentTypeComponentTypeSubtypeDtoToComponentTypeComponentTypeSubtype(ComponentTypeSubtypeDTO componentTypeSubtypeDTO);

    @AfterMapping
    default void setComponentType(@MappingTarget ComponentType componentType) {
        Optional.ofNullable(componentType.getComponentTypeSubtypes())
                .ifPresent(componentTypeSubtypes -> componentTypeSubtypes.forEach(componentTypeSubtype ->
                        componentTypeSubtype.setComponentType(componentType)
                ));
    }


}
