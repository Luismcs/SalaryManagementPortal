package com.finalproject.salarymanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.salarymanagement.dto.ComponentTypeSubtypeDTO;
import com.finalproject.salarymanagement.service.impl.ComponentTypeSubtypeServiceImpl;
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

@SpringBootTest
@AutoConfigureMockMvc
public class ComponentTypeSubtypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComponentTypeSubtypeServiceImpl componentTypeSubtypeService;

    @Autowired
    private ObjectMapper objectMapper;

    private ComponentTypeSubtypeDTO componentTypeSubtypeDTO;
    private Long componentTypeSubtypeId;


    @BeforeEach
    public void init() {
        componentTypeSubtypeId = 1L;
        componentTypeSubtypeDTO = new ComponentTypeSubtypeDTO();
        componentTypeSubtypeDTO.setComponentTypeId(1L);
        componentTypeSubtypeDTO.setName("Component Type Subtype");
    }

    @Test
    public void componentTypeSubtypeController_getAll_returnsComponentTypeSubtypeDtoPage() throws Exception {

        //Arrange
        Pageable pageable = PageRequest.of(0, 2);
        Page<ComponentTypeSubtypeDTO> componentTypeSubtypePage = new PageImpl<>(List.of(componentTypeSubtypeDTO),
                pageable, 1);

        //Act
        when(componentTypeSubtypeService.getAll(pageable)).thenReturn(componentTypeSubtypePage);
        ResultActions response = mockMvc.perform(get("/component-type-subtypes").
                param("page", "0").
                param("size", "2").contentType(MediaType.APPLICATION_JSON));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name",
                        CoreMatchers.is(componentTypeSubtypeDTO.getName())));
    }

    @Test
    public void componentTypeSubtypeController_getById_returnsOneComponentTypeSubtypeDto() throws Exception {

        //Act
        when(componentTypeSubtypeService.getById(componentTypeSubtypeId)).thenReturn(componentTypeSubtypeDTO);
        ResultActions response = mockMvc.perform(get("/component-type-subtypes/{id}", componentTypeSubtypeId).
                contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(componentTypeSubtypeDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(componentTypeSubtypeDTO.getName())));
    }

    @Test
    public void componentTypeSubtypeController_create_returnsOneComponentTypeSubtypeDto() throws Exception {

        //Act
        given(componentTypeSubtypeService.create(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));
        ResultActions response = mockMvc.perform(post("/component-type-subtypes").
                contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(componentTypeSubtypeDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(componentTypeSubtypeDTO.getName())));
    }

    @Test
    public void componentTypeSubtypeController_update_returnsOneComponentTypeSubtypeDto() throws Exception {

        //Act
        given(componentTypeSubtypeService.update(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(put("/component-type-subtypes").
                contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(componentTypeSubtypeDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(componentTypeSubtypeDTO.getName())));
    }

    @Test
    public void componentTypeSubtypeController_delete_returnsNothing() throws Exception {

        //Act
        doNothing().when(componentTypeSubtypeService).delete(componentTypeSubtypeId);
        ResultActions response = mockMvc.perform(delete("/component-type-subtypes/{id}",
                componentTypeSubtypeId)
                .contentType(MediaType.APPLICATION_JSON));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

}
