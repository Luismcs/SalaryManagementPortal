package com.finalproject.authentication.service;

import com.finalproject.authentication.dto.UserCredentialsDTO;
import com.finalproject.authentication.dto.UserCredentialsResponseDTO;
import com.finalproject.authentication.exception.UserCredentialsNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCredentialsService {

    boolean existsByUsername(String username);

    Page<UserCredentialsResponseDTO> getAll(Pageable pageable);

    UserCredentialsResponseDTO getById(Long id) throws UserCredentialsNotFound;

    UserCredentialsDTO create(UserCredentialsDTO userCredentialsDTO) throws UserCredentialsNotFound;

    UserCredentialsDTO update(UserCredentialsDTO userCredentialsDTO) throws UserCredentialsNotFound;

    void delete(Long id) throws UserCredentialsNotFound;

}
