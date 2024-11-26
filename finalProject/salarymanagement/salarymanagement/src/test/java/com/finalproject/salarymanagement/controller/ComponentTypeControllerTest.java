package com.finalproject.salarymanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.salarymanagement.dto.ComponentTypeDTO;
import com.finalproject.salarymanagement.service.impl.ComponentTypeServiceImpl;
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
public class ComponentTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComponentTypeServiceImpl componentTypeService;

    @Autowired
    private ObjectMapper objectMapper;

    private ComponentTypeDTO componentTypeDTO;
    private Long componentTypeId;


    @BeforeEach
    public void init() {
        componentTypeId = 1L;
        componentTypeDTO = new ComponentTypeDTO();
        componentTypeDTO.setName("Component Type");
    }

    @Test
    public void componentTypeController_getAll_returnsComponentTypeDtoPage() throws Exception {

        //Arrange
        Pageable pageable = PageRequest.of(0, 2);
        Page<ComponentTypeDTO> componentTypePage = new PageImpl<>(List.of(componentTypeDTO), pageable, 1);

        //Act
        when(componentTypeService.getAll(pageable)).thenReturn(componentTypePage);
        ResultActions response = mockMvc.perform(get("/component-types").
                param("page", "0").
                param("size", "2").contentType(MediaType.APPLICATION_JSON));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name",
                        CoreMatchers.is(componentTypeDTO.getName())));
    }

    @Test
    public void componentTypeController_getById_returnsOneComponentTypeDto() throws Exception {

        //Act
        when(componentTypeService.getById(componentTypeId)).thenReturn(componentTypeDTO);
        ResultActions response = mockMvc.perform(get("/component-types/1").
                contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(componentTypeDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(componentTypeDTO.getName())));
    }

    @Test
    public void componentTypeController_create_returnsOneComponentTypeDto() throws Exception {

        //Act
        given(componentTypeService.create(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));
        ResultActions response = mockMvc.perform(post("/component-types").
                contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(componentTypeDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(componentTypeDTO.getName())));
    }

    @Test
    public void componentTypeController_update_returnsOneComponentTypeDto() throws Exception {

        //Act
        given(componentTypeService.update(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(put("/component-types").
                contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(componentTypeDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(componentTypeDTO.getName())));
    }

    @Test
    public void componentTypeController_delete_returnsNothing() throws Exception {

        //Act
        doNothing().when(componentTypeService).delete(componentTypeId);
        ResultActions response = mockMvc.perform(delete("/component-types/1")
                .contentType(MediaType.APPLICATION_JSON));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

}
