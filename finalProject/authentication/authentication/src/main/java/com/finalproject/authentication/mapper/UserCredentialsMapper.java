package com.finalproject.authentication.mapper;

import com.finalproject.authentication.dto.UserCredentialsResponseDTO;
import com.finalproject.authentication.model.UserCredentials;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserCredentialsMapper {

    @Mapping(target = "roles", expression = "java(mapRolesToStringList(userCredentials))")
    UserCredentialsResponseDTO toUserCredentialsResponseDTO(UserCredentials userCredentials);

    default List<String> mapRolesToStringList(UserCredentials userCredentials){
        return userCredentials.getUserCredentialsRoles().stream()
                .map(userCredentialsRole ->
                        userCredentialsRole.getRole().getName()).toList();
    }

}
