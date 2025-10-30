package com.finalproject.portal.mapper;

import com.finalproject.portal.dto.CollaboratorDTO;
import com.finalproject.portal.dto.SignUpRequestDTO;
import com.finalproject.portal.dto.SignUpResponseDTO;
import com.finalproject.portal.dto.UserCredentialsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SignUpResponseDTOMapper {

    // Fields got from SignUpRequestDTO
    @Mapping(target = "username", source = "signUpRequestDTO.username")
    @Mapping(target = "email", source = "signUpRequestDTO.email")
    @Mapping(target = "roles", source = "signUpRequestDTO.roles")

    // Fields got from collaboratorDTO
    @Mapping(target = "fullName", source = "collaboratorDTO.fullName")
    @Mapping(target = "gender", source = "collaboratorDTO.gender")
    @Mapping(target = "birthDate", source = "collaboratorDTO.birthDate")
    @Mapping(target = "nif", source = "collaboratorDTO.nif")
    @Mapping(target = "addresses", source = "collaboratorDTO.addresses")

    // IDs específicos
    @Mapping(target = "collaboratorId", source = "collaboratorDTO.id")
    @Mapping(target = "userCredentialsId", source = "userCredentialsDTO.id")
    SignUpResponseDTO toSignUpResponseDTO(SignUpRequestDTO signUpRequestDTO, CollaboratorDTO collaboratorDTO,
                                          UserCredentialsDTO userCredentialsDTO);

    // Fields got from signUpRequestDTO
    @Mapping(target = "fullName", source = "signUpRequestDTO.fullName")
    @Mapping(target = "gender", source = "signUpRequestDTO.gender")
    @Mapping(target = "birthDate", source = "signUpRequestDTO.birthDate")
    @Mapping(target = "nif", source = "signUpRequestDTO.nif")
    @Mapping(target = "email", source = "signUpRequestDTO.email")
    @Mapping(target = "addresses", source = "signUpRequestDTO.addresses")
    CollaboratorDTO toCollaboratorDTO(SignUpRequestDTO signUpRequestDTO);

    // Fields got from userGeneralInfoDTO and savedCollaboratorDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "username", source = "signUpRequestDTO.username")
    @Mapping(target = "password", source = "signUpRequestDTO.password")
    @Mapping(target = "correlationId", source = "savedCollaboratorDTO.id")
    @Mapping(target = "roles", source = "signUpRequestDTO.roles")
    UserCredentialsDTO toUserCredentialsDTO(SignUpRequestDTO signUpRequestDTO,
                                            CollaboratorDTO savedCollaboratorDTO);

}
