package com.finalproject.authentication.mapper;

import com.finalproject.authentication.dto.UserCredentialsDTO;
import com.finalproject.authentication.model.UserCredentials;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserCredentialsMapper {

    UserCredentials toEntity(UserCredentialsDTO userCredentialsDTO);

    UserCredentialsDTO toDTO(UserCredentials userCredentials);

}
