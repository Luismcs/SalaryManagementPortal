package com.finalproject.portal.service;

import com.finalproject.portal.client.authenticationClients.AuthenticationClient;
import com.finalproject.portal.client.authenticationClients.UserCredentialsClient;
import com.finalproject.portal.client.collaboratorClients.CollaboratorClient;
import com.finalproject.portal.dto.*;
import com.finalproject.portal.enums.ErrorResponseCode;
import com.finalproject.portal.exception.ErrorMessage;
import com.finalproject.portal.exception.UsernameAlreadyExistsException;
import com.finalproject.portal.mapper.SignUpResponseDTOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationServiceImpl {

    private final CollaboratorClient collaboratorClient;
    private final UserCredentialsClient userCredentialsClient;
    private final AuthenticationClient authenticationClient;
    private final SignUpResponseDTOMapper signUpResponseDTOMapper;

    public AuthenticationServiceImpl(CollaboratorClient collaboratorClient
            , UserCredentialsClient userCredentialsClient, AuthenticationClient authenticationClient,
                                     SignUpResponseDTOMapper signUpResponseDTOMapper) {
        this.collaboratorClient = collaboratorClient;
        this.userCredentialsClient = userCredentialsClient;
        this.authenticationClient = authenticationClient;
        this.signUpResponseDTOMapper = signUpResponseDTOMapper;
    }

    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) throws UsernameAlreadyExistsException {
        if (Boolean.TRUE.equals(userCredentialsClient.existsByUsername(signUpRequestDTO.getUsername()).getBody())) {
            throw new UsernameAlreadyExistsException(ErrorResponseCode.USERNAME_ALREADY_EXISTS, 409,
                    ErrorMessage.USER_ALREADY_EXISTS, signUpRequestDTO.getUsername());
        }

        CollaboratorDTO collaboratorDTO = signUpResponseDTOMapper.toCollaboratorDTO(signUpRequestDTO);
        ResponseEntity<CollaboratorDTO> savedCollaborator = collaboratorClient.create(collaboratorDTO);

        UserCredentialsRequestDTO userCredentialsRequestDTO =
                signUpResponseDTOMapper.toUserCredentialsRequestDTO(signUpRequestDTO);
        ResponseEntity<UserCredentialsResponseDTO> savedUserCredentialsDTO = userCredentialsClient.create(userCredentialsRequestDTO);

        return signUpResponseDTOMapper.toSignUpResponseDTO(signUpRequestDTO,
                savedCollaborator.getBody(), savedUserCredentialsDTO.getBody());
    }

    public JWTResponseDTO signIn(SignInRequestDTO signInRequestDTO) {
        return authenticationClient.signIn(signInRequestDTO).getBody();
    }

    public JWTResponseDTO refreshToken(RefreshTokenDTO refreshTokenDTO) {
        return authenticationClient.refreshToken(refreshTokenDTO).getBody();
    }

}
