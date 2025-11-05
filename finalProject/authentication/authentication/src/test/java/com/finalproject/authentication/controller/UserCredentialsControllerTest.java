package com.finalproject.authentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.authentication.dto.UserCredentialsDTO;
import com.finalproject.authentication.dto.UserCredentialsResponseDTO;
import com.finalproject.authentication.service.impl.UserCredentialsServiceImpl;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(controllers = UserCredentialsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserCredentialsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserCredentialsServiceImpl userCredentialsService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserCredentialsResponseDTO userCredentialsResponseDTO;
    private Long userCredentialsId;


    @BeforeEach
    public void init() {
        userCredentialsId = 1L;
        userCredentialsResponseDTO = new UserCredentialsResponseDTO();
        userCredentialsResponseDTO.setUsername("johndoe");
        userCredentialsResponseDTO.setPassword("$2a$10$TSwblC5u/WXhazgbTgNO/uUAsKKlDfecvupV41KvQ8vEwlyvEBe1.");
        userCredentialsResponseDTO.setCorrelationId("1");
    }

    @Test
    public void userCredentialsController_findById_returnsAddressDto() throws Exception {

        //Act
        when(userCredentialsService.getById(userCredentialsId)).thenReturn(userCredentialsResponseDTO);
        ResultActions response = mockMvc.perform(get("/user-credentials/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCredentialsResponseDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(userCredentialsResponseDTO.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password",
                        CoreMatchers.is(userCredentialsResponseDTO.getPassword())));
    }

    @Test
    public void userCredentialsController_create_returnsUserCredentialsDto() throws Exception {

        //Act
        given(userCredentialsService.create(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));
        ResultActions response = mockMvc.perform(post("/user-credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCredentialsResponseDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(userCredentialsResponseDTO.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is(userCredentialsResponseDTO.getPassword())));
    }

    @Test
    public void userCredentialsController_delete_returnsNothing() throws Exception {

        //Arrange
        doNothing().when(userCredentialsService).delete(userCredentialsId);

        //Act
        ResultActions response = mockMvc.perform(delete("/user-credentials/1")
                .contentType(MediaType.APPLICATION_JSON));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

}
