package com.finalproject.collaborator.service.impl;

import com.finalproject.collaborator.dto.CollaboratorDTO;
import com.finalproject.collaborator.enums.ErrorResponseCode;
import com.finalproject.collaborator.exception.CollaboratorNotFoundException;
import com.finalproject.collaborator.exception.ErrorMessage;
import com.finalproject.collaborator.mapper.CollaboratorMapper;
import com.finalproject.collaborator.model.Collaborator;
import com.finalproject.collaborator.repository.CollaboratorRepository;
import com.finalproject.collaborator.service.CollaboratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CollaboratorServiceImpl implements CollaboratorService {

    private final CollaboratorRepository collaboratorRepository;
    private final CollaboratorMapper collaboratorMapper;

    public CollaboratorServiceImpl(CollaboratorRepository collaboratorRepository,
                                   CollaboratorMapper collaboratorMapper) {
        this.collaboratorRepository = collaboratorRepository;
        this.collaboratorMapper = collaboratorMapper;
    }

    public Page<CollaboratorDTO> getAll(Pageable pageable) {
        Page<Collaborator> collaborators = collaboratorRepository.findAll(pageable);

        return collaborators.map(collaboratorMapper::toDTO);
    }

    public CollaboratorDTO getById(Long id) throws CollaboratorNotFoundException {
        Collaborator collaborator =
                collaboratorRepository.findById(id).orElseThrow(() ->
                        new CollaboratorNotFoundException(ErrorResponseCode.COLLABORATOR_NOT_FOUND
                                , 404, ErrorMessage.COLLABORATOR_NOT_FOUND, id));

        log.info("Collaborator with id {} returned successfully", id);

        return collaboratorMapper.toDTO(collaborator);
    }

    public CollaboratorDTO add(CollaboratorDTO collaboratorDTO) {
        Collaborator collaborator = collaboratorMapper.toEntity(collaboratorDTO);
        Collaborator savedCollaborator = collaboratorRepository.save(collaborator);

        return collaboratorMapper.toDTO(savedCollaborator);
    }

    public CollaboratorDTO update(CollaboratorDTO collaboratorDTO) throws
            CollaboratorNotFoundException {
        collaboratorRepository.findById(collaboratorDTO.getId()).orElseThrow(() ->
                new CollaboratorNotFoundException(ErrorResponseCode.COLLABORATOR_NOT_FOUND
                        , 404, ErrorMessage.COLLABORATOR_NOT_FOUND, collaboratorDTO.getId()));

        Collaborator collaborator = collaboratorMapper.toEntity(collaboratorDTO);
        Collaborator updatedCollaborator = collaboratorRepository.save(collaborator);

        return collaboratorMapper.toDTO(updatedCollaborator);
    }

    public void delete(Long id) throws CollaboratorNotFoundException {
        collaboratorRepository.findById(id).orElseThrow(() -> new
                CollaboratorNotFoundException(ErrorResponseCode.COLLABORATOR_NOT_FOUND,
                404, ErrorMessage.COLLABORATOR_NOT_FOUND, id));
        collaboratorRepository.deleteById(id);

        log.info("Collaborator with id {} deleted successfully", id);

    }

}
