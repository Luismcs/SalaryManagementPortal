package com.finalproject.collaborator.service;

import com.finalproject.collaborator.dto.CollaboratorDTO;
import com.finalproject.collaborator.enums.Gender;
import com.finalproject.collaborator.exception.CollaboratorNotFoundException;
import com.finalproject.collaborator.mapper.CollaboratorMapper;
import com.finalproject.collaborator.model.Collaborator;
import com.finalproject.collaborator.repository.CollaboratorRepository;
import com.finalproject.collaborator.service.impl.CollaboratorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CollaboratorServiceTest {

    @Mock
    private CollaboratorRepository collaboratorRepository;

    @Mock
    private CollaboratorMapper collaboratorMapper;

    @InjectMocks
    private CollaboratorServiceImpl collaboratorService;

    private Collaborator collaborator1;
    private Collaborator collaborator2;
    private CollaboratorDTO collaboratorDTO1;
    private CollaboratorDTO collaboratorDTO2;
    private Long collaboratorId;

    @BeforeEach
    void init() {
        collaborator1 = new Collaborator();
        collaborator1.setId(1L);
        collaborator1.setFullName("John Doe");
        collaborator1.setEmail("john.doe@example.com");
        collaborator1.setGender(Gender.M);
        collaborator1.setBirthDate(LocalDate.now());
        collaborator1.setNif("123123123");
        collaborator2 = new Collaborator();
        collaborator2.setId(2L);
        collaborator2.setFullName("Jane Smith");
        collaborator2.setEmail("jane.smith@example.com");
        collaborator2.setGender(Gender.F);
        collaborator2.setBirthDate(LocalDate.now());
        collaborator2.setNif("456456456");
        collaboratorDTO1 = new CollaboratorDTO();
        collaboratorDTO1.setId(1L);
        collaboratorDTO1.setFullName("John Doe");
        collaboratorDTO1.setEmail("john.doe@example.com");
        collaboratorDTO1.setGender("M");
        collaboratorDTO1.setBirthDate(LocalDate.now());
        collaboratorDTO1.setNif("123123123");
        collaboratorDTO2 = new CollaboratorDTO();
        collaboratorDTO2.setId(2L);
        collaboratorDTO2.setFullName("Jane Smith");
        collaboratorDTO2.setEmail("jane.smith@example.com");
        collaboratorDTO2.setGender("F");
        collaboratorDTO2.setBirthDate(LocalDate.now());
        collaboratorDTO2.setNif("456456456");
        collaboratorId = 1L;

    }

    @Test
    void collaboratorService_getAll_returnsCollaboratorDtoPage() {

        //Arrange
        List<Collaborator> collaboratorList = List.of(collaborator1, collaborator2);
        Page<Collaborator> collaboratorsPage = new PageImpl<>(collaboratorList);
        Pageable pageable = PageRequest.of(0, 2);

        //Act
        when(collaboratorRepository.findAll(Mockito.any(Pageable.class))).thenReturn(collaboratorsPage);
        when(collaboratorMapper.toDTO(collaborator1)).thenReturn(collaboratorDTO1);
        when(collaboratorMapper.toDTO(collaborator2)).thenReturn(collaboratorDTO2);
        Page<CollaboratorDTO> result = collaboratorService.getAll(pageable);

        //Assert
        assertThat(result).isNotNull();

    }


    @Test
    void collaboratorService_getById_returnsOneCollaboratorDto() throws CollaboratorNotFoundException {

        //Act
        when(collaboratorRepository.findById(collaboratorId)).thenReturn(Optional.of(collaborator1));
        when(collaboratorMapper.toDTO(collaborator1)).thenReturn(collaboratorDTO1);

        CollaboratorDTO foundCollaborator = collaboratorService.getById(collaboratorId);

        //Assert
        assertThat(foundCollaborator).isNotNull();
    }

    @Test
    void collaboratorService_create_returnsOneCollaboratorDto() {

        //Act
        when(collaboratorMapper.toEntity(collaboratorDTO1)).thenReturn(collaborator1);
        when(collaboratorMapper.toDTO(collaborator1)).thenReturn(collaboratorDTO1);
        when(collaboratorRepository.save(Mockito.any(Collaborator.class))).thenReturn(collaborator1);
        CollaboratorDTO savedCollaborator = collaboratorService.addCollaborator(collaboratorDTO1);

        //Asset
        assertThat(savedCollaborator).isNotNull();
    }

    @Test
    void collaboratorService_update_returnsOneCollaboratorDto() throws CollaboratorNotFoundException {

        //Act
        when(collaboratorRepository.findById(collaboratorId)).thenReturn(Optional.of(collaborator1));
        when(collaboratorMapper.toEntity(collaboratorDTO1)).thenReturn(collaborator1);
        when(collaboratorRepository.save(Mockito.any(Collaborator.class))).thenReturn(collaborator1);
        when(collaboratorMapper.toDTO(collaborator1)).thenReturn(collaboratorDTO1);
        CollaboratorDTO foundCollaborator = collaboratorService.updateCollaborator(collaboratorDTO1);

        //Assert
        assertThat(foundCollaborator).isNotNull();
    }

    @Test
    void collaboratorService_delete_returnsNothing() {

        //Act
        when(collaboratorRepository.findById(collaboratorId)).thenReturn(Optional.of(collaborator1));
        doNothing().when(collaboratorRepository).deleteById(collaboratorId);

        //Assert
        assertAll(() -> collaboratorService.deleteCollaborator(collaboratorId));
    }

}
