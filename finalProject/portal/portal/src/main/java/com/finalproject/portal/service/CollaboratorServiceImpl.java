package com.finalproject.portal.service;

import com.finalproject.portal.client.collaboratorClients.CollaboratorClient;
import com.finalproject.portal.dto.CollaboratorDTO;
import com.finalproject.portal.dto.UserGeneralInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CollaboratorServiceImpl {

    private final CollaboratorClient collaboratorClient;

    public CollaboratorServiceImpl(CollaboratorClient collaboratorClient) {
        this.collaboratorClient = collaboratorClient;
    }

    public ResponseEntity<Page<CollaboratorDTO>> getAll(Pageable pageable) {
        return collaboratorClient.getAll(pageable);
    }

    public ResponseEntity<CollaboratorDTO> getById(long id) {
        return collaboratorClient.getById(id);
    }

    public ResponseEntity<CollaboratorDTO> addCollaborator(UserGeneralInfoDTO userGeneralInfoDTO) {
        return collaboratorClient.create(userGeneralInfoDTO);
    }

    public ResponseEntity<CollaboratorDTO> update(CollaboratorDTO collaboratorDTO) {
        return collaboratorClient.update(collaboratorDTO);
    }

    public ResponseEntity<Void> delete(long id) {
        collaboratorClient.delete(id);
        return ResponseEntity.ok().build();

    }

}
