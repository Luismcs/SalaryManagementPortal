package com.finalproject.authentication.service;

import com.finalproject.authentication.dto.UserCredentialsDTO;
import com.finalproject.authentication.dto.UserCredentialsRequestDTO;
import com.finalproject.authentication.dto.UserCredentialsResponseDTO;
import com.finalproject.authentication.exception.RoleNotFoundException;
import com.finalproject.authentication.exception.UserCredentialsNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCredentialsService {

    boolean existsByUsername(String username);

    Page<UserCredentialsResponseDTO> getAll(Pageable pageable);

    UserCredentialsResponseDTO getById(Long id) throws UserCredentialsNotFound;

    UserCredentialsResponseDTO create(UserCredentialsRequestDTO userCredentialsRequestDTO) throws UserCredentialsNotFound, RoleNotFoundException;

    UserCredentialsResponseDTO update(UserCredentialsRequestDTO userCredentialsRequestDTO) throws UserCredentialsNotFound, RoleNotFoundException;

    void delete(Long id) throws UserCredentialsNotFound;

}
