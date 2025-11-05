package com.finalproject.authentication.mapper;

import com.finalproject.authentication.dto.UserCredentialsDTO;
import com.finalproject.authentication.dto.UserCredentialsResponseDTO;
import com.finalproject.authentication.model.UserCredentials;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserCredentialsMapper {

    UserCredentials toEntity(UserCredentialsDTO userCredentialsDTO);

    UserCredentialsDTO toDTO(UserCredentials userCredentials);

    @Mapping(target = "roles", expression = "java(mapRolesToStringList(userCredentials))")
    UserCredentialsResponseDTO toUserCredentialsResponseDTO(UserCredentials userCredentials);

    default List<String> mapRolesToStringList(UserCredentials userCredentials){
        return userCredentials.getUserCredentialsRoles().stream()
                .map(userCredentialsRole ->
                        userCredentialsRole.getRole().getName()).toList();
    }

}
