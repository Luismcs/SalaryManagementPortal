package com.finalproject.authentication.service;

import com.finalproject.authentication.dto.JWTResponseDTO;
import com.finalproject.authentication.dto.RefreshTokenDTO;
import com.finalproject.authentication.dto.SignInRequestDTO;
import com.finalproject.authentication.exception.BadCredentialsException;
import com.finalproject.authentication.exception.RefreshTokenNotValid;
import com.finalproject.authentication.exception.RefreshTokenStillValidException;
import com.finalproject.authentication.model.RefreshToken;
import com.finalproject.authentication.model.UserCredentials;
import com.finalproject.authentication.repository.RefreshTokenRepository;
import com.finalproject.authentication.repository.UserCredentialsRepository;
import com.finalproject.authentication.service.impl.AuthenticationServiceImpl;
import com.finalproject.authentication.service.impl.JWTServiceImpl;
import com.finalproject.authentication.utils.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthenticationServiceTest {

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JWTServiceImpl jwtService;

    @Mock
    private PasswordUtils passwordUtils;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private SignInRequestDTO signInRequestDTO;
    private UserCredentials userCredentials;
    private String[] roles;
    private String refreshToken;
    private String accessToken;
    private JWTResponseDTO responseDTO;
    private RefreshToken refreshTokenModel;
    private RefreshTokenDTO refreshTokenDTO;

    @BeforeEach
    void init() {

        signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setUsername("johndoe");
        signInRequestDTO.setPassword("securePassword123");
        userCredentials = new UserCredentials();
        userCredentials.setUsername("johndoe");
        userCredentials.setPassword("$2a$10$TSwblC5u/WXhazgbTgNO/uUAsKKlDfecvupV41KvQ8vEwlyvEBe1.");
        userCredentials.setCorrelationId("1");
        roles = new String[]{"ADMIN", "USER"};
        refreshToken = "123456789abcdefg";
        accessToken = "123456789abcdefg";
        responseDTO = new JWTResponseDTO(accessToken, refreshToken);
        refreshTokenModel = new RefreshToken();
        refreshTokenModel.setRefreshToken(refreshToken);
        refreshTokenModel.setUserCredentials(userCredentials);
        refreshTokenDTO = new RefreshTokenDTO();
        refreshTokenDTO.setRefreshToken(refreshToken);

    }

    @Test
    void authenticationService_signIn_returnsJWTResponseDTO() throws BadCredentialsException {

        //Act
        when(userCredentialsRepository.findByUsername(signInRequestDTO.getUsername())).thenReturn(Optional.ofNullable(userCredentials));
        when(passwordUtils.matches(signInRequestDTO.getPassword(), userCredentials.getPassword())).thenReturn(true);
        when(jwtService.generateRefreshToken(signInRequestDTO.getUsername(), roles)).thenReturn(refreshToken);
        when(jwtService.generateToken(signInRequestDTO.getUsername(), roles)).thenReturn(String.valueOf(responseDTO));
        JWTResponseDTO jwtResponseDTO = authenticationService.signIn(signInRequestDTO);

        //Assert
        assertThat(jwtResponseDTO).isNotNull();

    }

    @Test
    void authenticationService_refreshToken_returnsJWTResponseDTO() throws RefreshTokenNotValid,
            RefreshTokenStillValidException {

        //Act
        when(refreshTokenRepository.findByRefreshToken(refreshToken)).thenReturn(Optional.ofNullable(refreshTokenModel));
        when(jwtService.isExpired(refreshToken)).thenReturn(true);
        when(jwtService.generateToken(userCredentials.getUsername(), roles)).thenReturn(accessToken);
        when(jwtService.generateRefreshToken(userCredentials.getUsername(), roles)).thenReturn(refreshToken);
        JWTResponseDTO jwtResponseDTO = authenticationService.refreshToken(refreshTokenDTO);

        //Assert
        assertThat(jwtResponseDTO).isNotNull();

    }

}
