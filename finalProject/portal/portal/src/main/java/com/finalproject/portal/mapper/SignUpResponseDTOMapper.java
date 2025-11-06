package com.finalproject.portal.mapper;

import com.finalproject.portal.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SignUpResponseDTOMapper {

    // Fields got from SignUpRequestDTO
    @Mapping(target = "username", source = "signUpRequestDTO.username")
    @Mapping(target = "email", source = "signUpRequestDTO.email")
    @Mapping(target = "roles", source = "userCredentialsResponseDTO.roles")

    // Fields got from collaboratorDTO
    @Mapping(target = "fullName", source = "collaboratorDTO.fullName")
    @Mapping(target = "gender", source = "collaboratorDTO.gender")
    @Mapping(target = "birthDate", source = "collaboratorDTO.birthDate")
    @Mapping(target = "nif", source = "collaboratorDTO.nif")
    @Mapping(target = "addresses", source = "collaboratorDTO.addresses")

    // IDs específicos
    @Mapping(target = "collaboratorId", source = "collaboratorDTO.id")
    @Mapping(target = "userCredentialsId", source = "userCredentialsResponseDTO.id")
    SignUpResponseDTO toSignUpResponseDTO(SignUpRequestDTO signUpRequestDTO, CollaboratorDTO collaboratorDTO,
                                          UserCredentialsResponseDTO userCredentialsResponseDTO);

    // Fields got from signUpRequestDTO
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "fullName", source = "signUpRequestDTO.fullName")
    @Mapping(target = "gender", source = "signUpRequestDTO.gender")
    @Mapping(target = "birthDate", source = "signUpRequestDTO.birthDate")
    @Mapping(target = "nif", source = "signUpRequestDTO.nif")
    @Mapping(target = "email", source = "signUpRequestDTO.email")
    @Mapping(target = "addresses", source = "signUpRequestDTO.addresses")
    CollaboratorDTO toCollaboratorDTO(SignUpRequestDTO signUpRequestDTO);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    UserCredentialsRequestDTO toUserCredentialsRequestDTO(SignUpRequestDTO signUpRequestDTO);

}
