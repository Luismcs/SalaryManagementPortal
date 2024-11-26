package com.finalproject.salarymanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.salarymanagement.dto.SalaryComponentDTO;
import com.finalproject.salarymanagement.service.impl.SalaryComponentServiceImpl;
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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
public class SalaryComponentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaryComponentServiceImpl salaryComponentService;

    @Autowired
    private ObjectMapper objectMapper;

    private SalaryComponentDTO salaryComponentDTO;
    private Long salaryComponentId;

    @BeforeEach
    public void init() {
        salaryComponentId = 1L;
        salaryComponentDTO = new SalaryComponentDTO();
        salaryComponentDTO.setComponentTypeId(1L);
        salaryComponentDTO.setComponentTypeSubtypeId(1L);
        salaryComponentDTO.setPresentOnReceipt(true);
        salaryComponentDTO.setSalaryId(1L);
        salaryComponentDTO.setValue(120.0);
    }

    @Test
    public void salaryComponentController_getAll_returnsSalaryComponentDtoPage() throws Exception {

        //Arrange
        Pageable pageable = PageRequest.of(0, 2);
        Page<SalaryComponentDTO> componentTypePage = new PageImpl<>(List.of(salaryComponentDTO), pageable, 1);

        //Act
        when(salaryComponentService.getAll(pageable)).thenReturn(componentTypePage);
        ResultActions response = mockMvc.perform(get("/salary-components").
                param("page", "0").
                param("size", "2").contentType(MediaType.APPLICATION_JSON));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.content[0].value",
                        CoreMatchers.is(salaryComponentDTO.getValue())));
    }

    @Test
    public void salaryComponentController_getById_returnsOneSalaryComponentDto() throws Exception {

        //Act
        when(salaryComponentService.getById(salaryComponentId)).thenReturn(salaryComponentDTO);
        ResultActions response = mockMvc.perform(get("/salary-components/1").
                contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(salaryComponentDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.value",
                        CoreMatchers.is(salaryComponentDTO.getValue())));
    }

    @Test
    public void salaryComponentController_create_returnsOneSalaryComponentDto() throws Exception {

        //Act
        given(salaryComponentService.create(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));
        ResultActions response = mockMvc.perform(post("/salary-components").
                contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(salaryComponentDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.value",
                        CoreMatchers.is(salaryComponentDTO.getValue())));
    }

    @Test
    public void salaryComponentController_update_returnsOneSalaryComponentDto() throws Exception {

        //Act
        given(salaryComponentService.update(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(put("/salary-components").
                contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(salaryComponentDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.value",
                        CoreMatchers.is(salaryComponentDTO.getValue())));
    }

    @Test
    public void salaryComponentController_delete_returnsNothing() throws Exception {

        //Act
        doNothing().when(salaryComponentService).delete(salaryComponentId);
        ResultActions response = mockMvc.perform(delete("/salary-components/1")
                .contentType(MediaType.APPLICATION_JSON));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

}
