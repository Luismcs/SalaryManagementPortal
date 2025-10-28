package com.finalproject.portal.service;

import com.finalproject.portal.client.authenticationClients.AuthenticationClient;
import com.finalproject.portal.client.authenticationClients.UserCredentialsClient;
import com.finalproject.portal.client.collaboratorClients.CollaboratorClient;
import com.finalproject.portal.dto.*;
import com.finalproject.portal.enums.ErrorResponseCode;
import com.finalproject.portal.exception.ErrorMessage;
import com.finalproject.portal.exception.UsernameAlreadyExistsException;
import com.finalproject.portal.mapper.UserSignUpResponseDTOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationServiceImpl {

    private final CollaboratorClient collaboratorClient;
    private final UserCredentialsClient userCredentialsClient;
    private final AuthenticationClient authenticationClient;
    private final UserSignUpResponseDTOMapper userSignUpResponseDTOMapper;

    public AuthenticationServiceImpl(CollaboratorClient collaboratorClient
            , UserCredentialsClient userCredentialsClient, AuthenticationClient authenticationClient,
                                     UserSignUpResponseDTOMapper userSignUpResponseDTOMapper) {
        this.collaboratorClient = collaboratorClient;
        this.userCredentialsClient = userCredentialsClient;
        this.authenticationClient = authenticationClient;
        this.userSignUpResponseDTOMapper = userSignUpResponseDTOMapper;
    }

    public UserSignUpResponseDTO signUp(UserGeneralInfoDTO userGeneralInfoDTO) throws UsernameAlreadyExistsException {
        if(Boolean.TRUE.equals(userCredentialsClient.existsByUsername(userGeneralInfoDTO.getUsername()).getBody())){
            throw new UsernameAlreadyExistsException(ErrorResponseCode.USERNAME_ALREADY_EXISTS, 409, ErrorMessage.USER_ALREADY_EXISTS, userGeneralInfoDTO.getUsername());
        }

        CollaboratorDTO collaboratorDTO = userSignUpResponseDTOMapper.toCollaboratorDTO(userGeneralInfoDTO);
        ResponseEntity<CollaboratorDTO> savedCollaborator = collaboratorClient.create(collaboratorDTO);
        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO(userGeneralInfoDTO.getUsername(),
                userGeneralInfoDTO.getPassword(),
                savedCollaborator.getBody().getId().toString(), userGeneralInfoDTO.getRoles());
        ResponseEntity<UserCredentialsDTO> savedUserCredentialsDTO = userCredentialsClient.create(userCredentialsDTO);
        return userSignUpResponseDTOMapper.toResponseDTO(userGeneralInfoDTO,
                savedCollaborator.getBody(), savedUserCredentialsDTO.getBody());
    }

    public JWTResponseDTO signIn(SignInDTO signInDTO) {
        return authenticationClient.signIn(signInDTO).getBody();
    }

    public JWTResponseDTO refreshToken(RefreshTokenDTO refreshTokenDTO) {
        return authenticationClient.refreshToken(refreshTokenDTO).getBody();
    }

}
