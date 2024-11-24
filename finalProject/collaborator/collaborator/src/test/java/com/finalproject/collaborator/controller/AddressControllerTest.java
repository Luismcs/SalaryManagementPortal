package com.finalproject.collaborator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.collaborator.dto.AddressDTO;
import com.finalproject.collaborator.service.impl.AddressServiceImpl;
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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = AddressController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressServiceImpl addressService;

    @Autowired
    private ObjectMapper objectMapper;

    private AddressDTO addressDTO;
    private Long addressId;

    @BeforeEach
    public void init() {
        addressId = 1L;
        addressDTO = new AddressDTO();
        addressDTO.setStreet("Rua nova");
        addressDTO.setPostalCode("4450-450");
        addressDTO.setCity("Porto");
        addressDTO.setCountry("Portugal");
        addressDTO.setCollaboratorId(1L);
    }

    @Test
    public void AddressController_GetAll_ReturnAddressDtoPage() throws Exception {

        //Arrange
        Pageable pageable = PageRequest.of(0, 2);
        Page<AddressDTO> addressPage = new PageImpl<>(List.of(addressDTO), pageable, 1);

        when(addressService.getAll(pageable)).thenReturn(addressPage);

        //Action
        ResultActions response = mockMvc.perform(get("/addresses")
                .param("page", "0")
                .param("size", "2")
                .contentType(MediaType.APPLICATION_JSON));

        //Asserts
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].city", CoreMatchers.is(addressDTO.getCity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].country",
                        CoreMatchers.is(addressDTO.getCountry())));
    }

    @Test
    public void AddressController_FindById_ReturnAddressDto() throws Exception {

        //Action
        when(addressService.getById(addressId)).thenReturn(addressDTO);

        ResultActions response = mockMvc.perform(get("/addresses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressDTO)));

        //Asserts
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", CoreMatchers.is(addressDTO.getCity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(addressDTO.getCountry())));
    }

    @Test
    public void AddressController_AddCollaborator_ReturnAddressDto() throws Exception {

        //Action
        given(addressService.addAddress(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressDTO)));

        //Asserts
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", CoreMatchers.is(addressDTO.getCity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(addressDTO.getCountry())));
    }

    @Test
    public void AddressController_UpdateAddress_ReturnAddressDto() throws Exception {

        //Action
        given(addressService.updateAddress(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(put("/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressDTO)));

        //Asserts
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", CoreMatchers.is(addressDTO.getCity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(addressDTO.getCountry())));
    }

    @Test
    public void AddressController_Delete_ReturnNothing() throws Exception {

        //Arrange
        doNothing().when(addressService).deleteAddress(addressId);

        //Action
        ResultActions response = mockMvc.perform(delete("/addresses/1")
                .contentType(MediaType.APPLICATION_JSON));

        //Asserts
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

}
