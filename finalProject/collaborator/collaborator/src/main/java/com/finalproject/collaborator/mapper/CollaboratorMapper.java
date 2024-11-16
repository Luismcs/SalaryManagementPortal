package com.finalproject.collaborator.mapper;

import com.finalproject.collaborator.dto.AddressDTO;
import com.finalproject.collaborator.dto.CollaboratorDTO;
import com.finalproject.collaborator.model.Address;
import com.finalproject.collaborator.model.Collaborator;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface CollaboratorMapper {

    @Mapping(target = "addresses", qualifiedByName = "addressesDtoToAddressesEntity")
    Collaborator toEntity(CollaboratorDTO collaboratorDTO);

    CollaboratorDTO toDTO(Collaborator collaborator);

    List<CollaboratorDTO> toDTOList(List<Collaborator> collaborators);

    @IterableMapping(qualifiedByName = "collaboratorAddressDtoToCollaboratorAddress")
    @Named("addressesDtoToAddressesEntity")
    List<Address> addressesDtoToAddressesEntity(List<AddressDTO> addressDTOList);

    @Named("collaboratorAddressDtoToCollaboratorAddress")
    Address collaboratorAddressDtoToCollaboratorAddress(AddressDTO addressDTO);

    @AfterMapping
    default void setCollaborator(@MappingTarget Collaborator collaborator) {
        Optional.ofNullable(collaborator.getAddresses())
                .ifPresent(addresses -> addresses.forEach(address ->
                        address.setCollaborator(collaborator)
                ));
    }

}
