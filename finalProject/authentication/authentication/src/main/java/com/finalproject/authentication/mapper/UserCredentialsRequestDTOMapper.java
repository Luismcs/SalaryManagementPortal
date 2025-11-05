package com.finalproject.authentication.mapper;

import com.finalproject.authentication.dto.UserCredentialsRequestDTO;
import com.finalproject.authentication.model.UserCredentials;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserCredentialsRequestDTOMapper {

    UserCredentials toEntity(UserCredentialsRequestDTO userCredentialsRequestDTO);

}
