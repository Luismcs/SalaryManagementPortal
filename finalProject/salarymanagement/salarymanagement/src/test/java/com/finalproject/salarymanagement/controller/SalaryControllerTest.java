package com.finalproject.salarymanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.salarymanagement.dto.SalaryDTO;
import com.finalproject.salarymanagement.service.impl.SalaryServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest
@AutoConfigureMockMvc
public class SalaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaryServiceImpl salaryService;

    @Autowired
    private ObjectMapper objectMapper;

    private SalaryDTO salaryDTO;
    private Long salaryId;


    @BeforeEach
    public void init() {
        salaryId = 1L;
        salaryDTO = new SalaryDTO();
        salaryDTO.setAcceptanceDate(null);
        salaryDTO.setCorrelationId("1");
        salaryDTO.setImplementationDate(LocalDate.now());
        salaryDTO.setSalaryState("INACTIVE");
    }

    @Test
    public void salaryController_getAll_returnCollaboratorDtoPage() throws Exception {

        //Arrange
        Pageable pageable = PageRequest.of(0, 2);
        Page<SalaryDTO> salaryPage = new PageImpl<>(List.of(salaryDTO), pageable, 1);

        //Act
        when(salaryService.getAll(pageable)).thenReturn(salaryPage);
        ResultActions response = mockMvc.perform(get("/salaries").
                param("page", "0").
                param("size", "2").contentType(MediaType.APPLICATION_JSON));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.content[0].correlationId",
                        CoreMatchers.is(salaryDTO.getCorrelationId())));
    }

    @Test
    public void salaryController_getById_returnsSalaryDto() throws Exception {

        //Act
        when(salaryService.getById(salaryId)).thenReturn(salaryDTO);
        ResultActions response = mockMvc.perform(get("/salaries/1").
                contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(salaryDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.correlationId",
                        CoreMatchers.is(salaryDTO.getCorrelationId())));
    }

    @Test
    public void salaryController_create_returnsAddressDto() throws Exception {

        //Act
        given(salaryService.create(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));
        ResultActions response = mockMvc.perform(post("/salaries").
                contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(salaryDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.correlationId",
                        CoreMatchers.is(salaryDTO.getCorrelationId())));
    }

    @Test
    public void salaryController_update_returnAddressDto() throws Exception {

        //Act
        given(salaryService.update(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(put("/salaries").
                contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(salaryDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.correlationId",
                        CoreMatchers.is(salaryDTO.getCorrelationId())));
    }

    @Test
    public void salaryController_delete_returnsNothing() throws Exception {

        //Act
        doNothing().when(salaryService).delete(salaryId);
        ResultActions response = mockMvc.perform(delete("/salaries/1")
                .contentType(MediaType.APPLICATION_JSON));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

}
