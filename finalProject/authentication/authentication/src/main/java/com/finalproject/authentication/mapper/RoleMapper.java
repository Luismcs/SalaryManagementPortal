package com.finalproject.authentication.mapper;

import com.finalproject.authentication.dto.RoleDTO;
import com.finalproject.authentication.model.Role;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toEntity(RoleDTO roleDTO);

    RoleDTO toDTO(Role role);

    Set<Role> toEntityList(Set<RoleDTO> roleDTOS);

}
