package com.finalproject.authentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.authentication.dto.*;
import com.finalproject.authentication.service.impl.AuthenticationServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    private SignInDTO signInDTO;
    private RefreshTokenDTO refreshTokenDTO;
    private JWTResponseDTO jwtResponseDTO;

    @BeforeEach
    public void init() {
        jwtResponseDTO = new JWTResponseDTO("abcdefg123456789", "987654321abcdefg");
        jwtResponseDTO.setAccessToken("abcdefg123456789");
        jwtResponseDTO.setRefreshToken("987654321abcdefg");
        refreshTokenDTO = new RefreshTokenDTO();
        refreshTokenDTO.setRefreshToken("abcdefg123456789");
        signInDTO = new SignInDTO();
        signInDTO.setUsername("johndoe");
        signInDTO.setPassword("abcdefg");

    }

    @Test
    public void authenticationController_signIn_returnsJwtResponseDTO() throws Exception {

        //Act
        when(authenticationService.signIn(any(SignInDTO.class))).thenReturn(jwtResponseDTO);
        ResultActions response = mockMvc.perform(post("/authentication/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken",
                        CoreMatchers.is(jwtResponseDTO.getAccessToken())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken",
                        CoreMatchers.is(jwtResponseDTO.getRefreshToken())));
    }

    @Test
    public void authenticationController_refreshToken_returnsJwtResponseDTO() throws Exception {

        //Act
        when(authenticationService.refreshToken(any(RefreshTokenDTO.class))).thenReturn(jwtResponseDTO);
        ResultActions response = mockMvc.perform(post("/authentication/refresh-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshTokenDTO)));

        //Assert
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken",
                        CoreMatchers.is(jwtResponseDTO.getAccessToken())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken",
                        CoreMatchers.is(jwtResponseDTO.getRefreshToken())));
    }

}
