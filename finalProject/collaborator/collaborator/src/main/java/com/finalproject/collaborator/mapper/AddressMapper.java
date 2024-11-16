package com.finalproject.collaborator.mapper;

import com.finalproject.collaborator.dto.AddressDTO;
import com.finalproject.collaborator.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "collaborator.id", source = "collaboratorId")
    Address toEntity(AddressDTO addressDTO);

    @Mapping(target = "collaboratorId", source = "collaborator.id")
    AddressDTO toDTO(Address address);

    List<AddressDTO> toDTOList(List<Address> addresses);


}
