package com.finalproject.authentication.service.impl;

import com.finalproject.authentication.dto.JWTResponseDTO;
import com.finalproject.authentication.dto.RefreshTokenDTO;
import com.finalproject.authentication.dto.SignInDTO;
import com.finalproject.authentication.enums.ErrorResponseCode;
import com.finalproject.authentication.exception.BadCredentialsException;
import com.finalproject.authentication.exception.ErrorMessage;
import com.finalproject.authentication.exception.RefreshTokenNotValid;
import com.finalproject.authentication.exception.RefreshTokenStillValidException;
import com.finalproject.authentication.model.RefreshToken;
import com.finalproject.authentication.model.UserCredentials;
import com.finalproject.authentication.model.UserCredentialsRole;
import com.finalproject.authentication.repository.RefreshTokenRepository;
import com.finalproject.authentication.repository.UserCredentialsRepository;
import com.finalproject.authentication.service.AuthenticationService;
import com.finalproject.authentication.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserCredentialsRepository userCredentialsRepository;
    private final PasswordUtils passwordUtils;
    private final JWTServiceImpl jwtServiceImpl;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthenticationServiceImpl(UserCredentialsRepository userCredentialsRepository, PasswordUtils passwordUtils
            , JWTServiceImpl jwtServiceImpl, RefreshTokenRepository refreshTokenRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.passwordUtils = passwordUtils;
        this.jwtServiceImpl = jwtServiceImpl;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public JWTResponseDTO signIn(SignInDTO signInDTO) throws BadCredentialsException {
        Optional<UserCredentials> userCredentials = userCredentialsRepository.findByUsername(signInDTO.getUsername());

        if (userCredentials.isEmpty()) {
            throw new BadCredentialsException(ErrorResponseCode.BAD_CREDENTIALS, 409, ErrorMessage.BAD_CREDENTIALS,
                    signInDTO.getUsername());
        }
        if (!passwordUtils.matches(signInDTO.getPassword(), userCredentials.get().getPassword())) {
            throw new BadCredentialsException(ErrorResponseCode.BAD_CREDENTIALS, 409, ErrorMessage.BAD_CREDENTIALS,
                    signInDTO.getPassword());
        }

        String refreshToken = jwtServiceImpl.generateRefreshToken(signInDTO.getUsername(), getRoles(userCredentials.get()));

        saveRefreshToken(refreshToken, userCredentials.get());

        log.info("{} logged in successfully", userCredentials.get().getUsername());

        return new JWTResponseDTO(jwtServiceImpl.generateToken(signInDTO.getUsername(), getRoles(userCredentials.get())),
                refreshToken);
    }

    public String[] getRoles(UserCredentials userCredentials) {
        List<String> rolesList = new ArrayList<>();

        for (UserCredentialsRole userCredentialsRole : userCredentials.getUserCredentialsRoles()) {
            rolesList.add(userCredentialsRole.getRole().getName());
        }

        return rolesList.toArray(new String[0]);

    }

    private void saveRefreshToken(String refreshToken, UserCredentials userCredentials) {
        RefreshToken savedRefreshToken = new RefreshToken();

        savedRefreshToken.setRefreshToken(refreshToken);
        savedRefreshToken.setUserCredentials(userCredentials);
        refreshTokenRepository.save(savedRefreshToken);
    }

    public JWTResponseDTO refreshToken(RefreshTokenDTO refreshTokenDTO) throws RefreshTokenStillValidException, RefreshTokenNotValid {

        Optional<RefreshToken> refreshToken =
                refreshTokenRepository.findByRefreshToken(refreshTokenDTO.getRefreshToken());


        if (refreshToken.isEmpty()) {
            throw new RefreshTokenNotValid(ErrorResponseCode.REFRESH_TOKEN_INVALID, 409,
                    ErrorMessage.REFRESH_TOKEN_INVALID, refreshTokenDTO.getRefreshToken());
        } else if (!jwtServiceImpl.isExpired(refreshTokenDTO.getRefreshToken())) {
            throw new RefreshTokenStillValidException(ErrorResponseCode.TOKEN_STILL_VALID, 409,
                    ErrorMessage.REFRESH_TOKEN_STILL_VALID, refreshTokenDTO.getRefreshToken());
        } else {
            UserCredentials userCredentials = refreshToken.get().getUserCredentials();

            return new JWTResponseDTO(jwtServiceImpl.generateToken(userCredentials.getUsername(), getRoles(userCredentials)),
                    jwtServiceImpl.generateRefreshToken(userCredentials.getUsername(), getRoles(userCredentials)));
        }


    }

}
