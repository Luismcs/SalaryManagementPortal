package com.finalproject.collaborator.mapper;

import com.finalproject.collaborator.dto.AddressDTO;
import com.finalproject.collaborator.model.Address;
import com.finalproject.collaborator.model.Collaborator;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "collaborator.id", source = "collaboratorId")
    Address toEntity(AddressDTO addressDTO);

    @Mapping(target = "collaboratorId", source = "collaborator.id")
    AddressDTO toDTO(Address address);

    List<AddressDTO> toDTOList(List<Address> addresses);

    //Update Address methods
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "collaborator", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateAddressFromDTO(AddressDTO dto, @MappingTarget Address existingAddress, @Context Collaborator collaborator);

    @AfterMapping
    default void setCollaborator(@MappingTarget Address existingAddress, @Context Collaborator collaborator) {
        existingAddress.setCollaborator(collaborator);
    }
}
