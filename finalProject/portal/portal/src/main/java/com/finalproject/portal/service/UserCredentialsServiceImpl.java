package com.finalproject.portal.service;

import com.finalproject.portal.client.authenticationClients.UserCredentialsClient;
import com.finalproject.portal.dto.CollaboratorDTO;
import com.finalproject.portal.dto.UserCredentialsDTO;
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

    public ResponseEntity<Page<UserCredentialsDTO>> getAll(Pageable pageable) {
        return userCredentialsClient.getAll(pageable);
    }

    public ResponseEntity<UserCredentialsDTO> getById(long id) {
        return userCredentialsClient.getById(id);
    }

    public ResponseEntity<UserCredentialsDTO> update(UserCredentialsDTO userCredentialsDTO) {
        return userCredentialsClient.update(userCredentialsDTO);
    }

    public void delete(long id) {
        userCredentialsClient.delete(id);
    }

    public ResponseEntity<Boolean> existsByUsername(String username) {
        return userCredentialsClient.existsByUsername(username);
    }

}
