package com.finalproject.portal.mapper;

import com.finalproject.portal.dto.CollaboratorDTO;
import com.finalproject.portal.dto.UserCredentialsDTO;
import com.finalproject.portal.dto.UserGeneralInfoDTO;
import com.finalproject.portal.dto.UserSignUpResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserSignUpResponseDTOMapper {

    // Fields got from userGeneralInfoDTO
    @Mapping(target = "username", source = "userGeneralInfoDTO.username")
    @Mapping(target = "email", source = "userGeneralInfoDTO.email")
    @Mapping(target = "roles", source = "userGeneralInfoDTO.roles")

    // Fields got from collaboratorDTO
    @Mapping(target = "fullName", source = "collaboratorDTO.fullName")
    @Mapping(target = "gender", source = "collaboratorDTO.gender")
    @Mapping(target = "birthDate", source = "collaboratorDTO.birthDate")
    @Mapping(target = "nif", source = "collaboratorDTO.nif")
    @Mapping(target = "addresses", source = "collaboratorDTO.addresses")

    // IDs específicos
    @Mapping(target = "collaboratorId", source = "collaboratorDTO.id")
    @Mapping(target = "userCredentialsId", source = "userCredentialsDTO.id")
    UserSignUpResponseDTO toResponseDTO(UserGeneralInfoDTO userGeneralInfoDTO, CollaboratorDTO collaboratorDTO,
                                        UserCredentialsDTO userCredentialsDTO);

    // Fields got from userGeneralInfoDTO
    @Mapping(target = "fullName", source = "userGeneralInfoDTO.fullName")
    @Mapping(target = "gender", source = "userGeneralInfoDTO.gender")
    @Mapping(target = "birthDate", source = "userGeneralInfoDTO.birthDate")
    @Mapping(target = "nif", source = "userGeneralInfoDTO.nif")
    @Mapping(target = "email", source = "userGeneralInfoDTO.email")
    @Mapping(target = "addresses", source = "userGeneralInfoDTO.addresses")
    CollaboratorDTO toCollaboratorDTO(UserGeneralInfoDTO userGeneralInfoDTO);

    // Fields got from userGeneralInfoDTO and savedCollaboratorDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "username", source = "userGeneralInfoDTO.username")
    @Mapping(target = "password", source = "userGeneralInfoDTO.password")
    @Mapping(target = "correlationId", source = "savedCollaboratorDTO.id")
    @Mapping(target = "roles", source = "userGeneralInfoDTO.roles")
    UserCredentialsDTO toUserCredentialsDTO(UserGeneralInfoDTO userGeneralInfoDTO,
                                            CollaboratorDTO savedCollaboratorDTO);

}
