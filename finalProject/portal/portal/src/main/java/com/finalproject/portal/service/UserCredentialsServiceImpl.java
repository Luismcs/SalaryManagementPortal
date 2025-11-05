package com.finalproject.portal.service;

import com.finalproject.portal.client.authenticationClients.UserCredentialsClient;
import com.finalproject.portal.dto.CollaboratorDTO;
import com.finalproject.portal.dto.UserCredentialsDTO;
import com.finalproject.portal.dto.UserCredentialsRequestDTO;
import com.finalproject.portal.dto.UserCredentialsResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialsServiceImpl {

    UserCredentialsClient userCredentialsClient;

    private UserCredentialsServiceImpl(UserCredentialsClient userCredentialsClient) {
        this.userCredentialsClient = userCredentialsClient;
    }

    public ResponseEntity<Page<UserCredentialsResponseDTO>> getAll(Pageable pageable) {
        return userCredentialsClient.getAll(pageable);
    }

    public ResponseEntity<UserCredentialsResponseDTO> getById(long id) {
        return userCredentialsClient.getById(id);
    }

    public ResponseEntity<UserCredentialsResponseDTO> create(UserCredentialsRequestDTO userCredentialsRequestDTO) {
        return userCredentialsClient.create(userCredentialsRequestDTO);
    }

    public ResponseEntity<UserCredentialsResponseDTO> update(UserCredentialsRequestDTO userCredentialsRequestDTO) {
        return userCredentialsClient.update(userCredentialsRequestDTO);
    }

    public void delete(long id) {
        userCredentialsClient.delete(id);
    }

    public ResponseEntity<Boolean> existsByUsername(String username) {
        return userCredentialsClient.existsByUsername(username);
    }

}
