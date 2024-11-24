package com.finalproject.collaborator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.collaborator.dto.AddressDTO;
import com.finalproject.collaborator.dto.CollaboratorDTO;
import com.finalproject.collaborator.service.impl.CollaboratorServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = CollaboratorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CollaboratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollaboratorServiceImpl collaboratorService;

    @Autowired
    private ObjectMapper objectMapper;

    private CollaboratorDTO collaboratorDTO;
    private AddressDTO addressDto;
    private Long collaboratorId;

    @BeforeEach
    public void init() {
        collaboratorDTO = new CollaboratorDTO();
        collaboratorDTO.setId(1L);
        collaboratorDTO.setFullName("John Doe");
        collaboratorDTO.setEmail("john.doe@example.com");
        collaboratorDTO.setGender("M");
        collaboratorDTO.setBirthDate(LocalDate.now());
        collaboratorDTO.setNif("123123123");
        addressDto = new AddressDTO();
        addressDto.setStreet("Rua nova");
        addressDto.setPostalCode("4450-450");
        addressDto.setCity("Porto");
        addressDto.setCountry("Portugal");
        addressDto.setCollaboratorId(1L);
        List<AddressDTO> addressDTOS = List.of(addressDto);
        collaboratorDTO.setAddresses(addressDTOS);
        collaboratorId = 1L;
    }

    @Test
    public void CollaboratorController_GetAll_ReturnCollaboratorDtoPage() throws Exception {
        Pageable pageable = PageRequest.of(0, 2);
        Page<CollaboratorDTO> collaboratorPage = new PageImpl<>(List.of(collaboratorDTO), pageable, 1);

        // Act
        when(collaboratorService.getAll(pageable)).thenReturn(collaboratorPage);
        ResultActions response = mockMvc.perform(get("/collaborators")
                .param("page", "0")
                .param("size", "2")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].fullName", CoreMatchers.is(collaboratorDTO.getFullName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].email", CoreMatchers.is(collaboratorDTO.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].addresses[0].street",
                        CoreMatchers.is(addressDto.getStreet())));
    }

    @Test
    public void CollaboratorController_FindById_ReturnCollaboratorDto() throws Exception {

        when(collaboratorService.getById(collaboratorId)).thenReturn(collaboratorDTO);

        ResultActions response = mockMvc.perform(get("/collaborators/1", collaboratorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(collaboratorDTO)));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", CoreMatchers.is(collaboratorDTO.getFullName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(collaboratorDTO.getEmail())));
    }

    @Test
    public void CollaboratorController_AddCollaborator_ReturnCollaboratorDto() throws Exception {

        //Action
        given(collaboratorService.addCollaborator(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/collaborators", collaboratorDTO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(collaboratorDTO)));

        //Asserts
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", CoreMatchers.is(collaboratorDTO.getFullName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(collaboratorDTO.getEmail())));
    }

    @Test
    public void CollaboratorController_UpdateCollaborator_ReturnCollaboratorDto() throws Exception {

        //Action
        given(collaboratorService.updateCollaborator(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(put("/collaborators", collaboratorDTO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(collaboratorDTO)));

        //Asserts
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", CoreMatchers.is(collaboratorDTO.getFullName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(collaboratorDTO.getEmail())));
    }

    @Test
    public void CollaboratorController_Delete_ReturnNothing() throws Exception {

        doNothing().when(collaboratorService).deleteCollaborator(collaboratorId);

        //Action
        ResultActions response = mockMvc.perform(delete("/collaborators/1", collaboratorId)
                .contentType(MediaType.APPLICATION_JSON));

        //Asserts
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }


}
