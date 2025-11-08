package com.finalproject.portal.service;

import com.finalproject.portal.client.authenticationClients.UserCredentialsClient;
import com.finalproject.portal.client.collaboratorClients.CollaboratorClient;
import com.finalproject.portal.dto.UserCredentialsRequestDTO;
import com.finalproject.portal.dto.UserCredentialsResponseDTO;
import com.finalproject.portal.enums.ErrorResponseCode;
import com.finalproject.portal.exception.CollaboratorNotFoundException;
import com.finalproject.portal.exception.ErrorMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialsServiceImpl {

    UserCredentialsClient userCredentialsClient;
    CollaboratorClient collaboratorClient;

    private UserCredentialsServiceImpl(UserCredentialsClient userCredentialsClient,
                                       CollaboratorClient collaboratorClient) {
        this.userCredentialsClient = userCredentialsClient;
        this.collaboratorClient = collaboratorClient;
    }

    public ResponseEntity<Page<UserCredentialsResponseDTO>> getAll(Pageable pageable) {
        return userCredentialsClient.getAll(pageable);
    }

    public ResponseEntity<UserCredentialsResponseDTO> getById(long id) {
        return userCredentialsClient.getById(id);
    }

    public ResponseEntity<UserCredentialsResponseDTO> create(UserCredentialsRequestDTO userCredentialsRequestDTO) throws CollaboratorNotFoundException {
        if (collaboratorClient.getById(Long.parseLong(userCredentialsRequestDTO.getCorrelationId())).hasBody()) {
            return userCredentialsClient.create(userCredentialsRequestDTO);
        } else
            throw new CollaboratorNotFoundException(ErrorResponseCode.COLLABORATOR_NOT_FOUND, 404,
                    ErrorMessage.COLLABORATOR_NOT_FOUND, userCredentialsRequestDTO.getCorrelationId());
    }

    public ResponseEntity<UserCredentialsResponseDTO> update(UserCredentialsRequestDTO userCredentialsRequestDTO)
            throws CollaboratorNotFoundException {
        if (collaboratorClient.getById(Long.parseLong(userCredentialsRequestDTO.getCorrelationId())).hasBody()) {
            return userCredentialsClient.update(userCredentialsRequestDTO);
        } else
            throw new CollaboratorNotFoundException(ErrorResponseCode.COLLABORATOR_NOT_FOUND, 404,
                    ErrorMessage.COLLABORATOR_NOT_FOUND, userCredentialsRequestDTO.getCorrelationId());

    }

    public void delete(long id) {
        userCredentialsClient.delete(id);
    }

    public ResponseEntity<Boolean> existsByUsername(String username) {
        return userCredentialsClient.existsByUsername(username);
    }

}
