package com.finalproject.collaborator.service;

import com.finalproject.collaborator.dto.CollaboratorDTO;
import com.finalproject.collaborator.exception.CollaboratorNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollaboratorService {

    Page<CollaboratorDTO> getAll(Pageable pageable);

    CollaboratorDTO addCollaborator(CollaboratorDTO collaboratorDTO);

    CollaboratorDTO getById(Long id) throws CollaboratorNotFoundException;

    CollaboratorDTO updateCollaborator(CollaboratorDTO collaboratorDTO) throws CollaboratorNotFoundException;

    void deleteCollaborator(Long id) throws CollaboratorNotFoundException;

}
