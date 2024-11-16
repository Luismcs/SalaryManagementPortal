package com.finalproject.portal.service;

import com.finalproject.portal.client.authenticationClients.AuthenticationClient;
import com.finalproject.portal.client.authenticationClients.UserCredentialsClient;
import com.finalproject.portal.client.collaboratorClients.CollaboratorClient;
import com.finalproject.portal.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl {

    private final CollaboratorClient collaboratorClient;
    private final UserCredentialsClient userCredentialsClient;
    private final AuthenticationClient authenticationClient;

    public AuthenticationServiceImpl(CollaboratorClient collaboratorClient
            , UserCredentialsClient userCredentialsClient, AuthenticationClient authenticationClient) {
        this.collaboratorClient = collaboratorClient;
        this.userCredentialsClient = userCredentialsClient;
        this.authenticationClient = authenticationClient;
    }

    public UserGeneralInfoDTO signUp(UserGeneralInfoDTO userGeneralInfoDTO) {
        ResponseEntity<CollaboratorDTO> savedCollaborator = collaboratorClient.create(userGeneralInfoDTO);
        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO(userGeneralInfoDTO.getUsername(), userGeneralInfoDTO.getPassword(),
                savedCollaborator.getBody().getId().toString(), userGeneralInfoDTO.getRoles());
        userCredentialsClient.create(userCredentialsDTO);
        return userGeneralInfoDTO;
    }

    public JWTResponseDTO signIn(SignInDTO signInDTO) {
        return authenticationClient.signIn(signInDTO).getBody();
    }

    public JWTResponseDTO refreshToken(RefreshTokenDTO refreshTokenDTO) {
        return authenticationClient.refreshToken(refreshTokenDTO).getBody();
    }

}
