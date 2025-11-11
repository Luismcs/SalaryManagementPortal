package com.finalproject.collaborator.service;

import com.finalproject.collaborator.dto.CollaboratorDTO;
import com.finalproject.collaborator.exception.CollaboratorNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollaboratorService {

    Page<CollaboratorDTO> getAll(Pageable pageable);

    CollaboratorDTO add(CollaboratorDTO collaboratorDTO);

    CollaboratorDTO getById(Long id) throws CollaboratorNotFoundException;

    CollaboratorDTO update(CollaboratorDTO collaboratorDTO) throws CollaboratorNotFoundException;

    void delete(Long id) throws CollaboratorNotFoundException;

}
