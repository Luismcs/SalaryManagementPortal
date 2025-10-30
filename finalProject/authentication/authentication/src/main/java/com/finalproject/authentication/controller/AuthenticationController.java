package com.finalproject.authentication.controller;

import com.finalproject.authentication.dto.JWTResponseDTO;
import com.finalproject.authentication.dto.RefreshTokenDTO;
import com.finalproject.authentication.dto.SignInRequestDTO;
import com.finalproject.authentication.exception.BadCredentialsException;
import com.finalproject.authentication.exception.RefreshTokenNotValid;
import com.finalproject.authentication.exception.RefreshTokenStillValidException;
import com.finalproject.authentication.service.impl.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(
            summary = "Signs is the user",
            description = "Returns the access and refresh token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User logged successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JWTResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "Bad credentials"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping("/sign-in")
    public ResponseEntity<JWTResponseDTO> signIn(@Valid @RequestBody SignInRequestDTO signInRequestDTO) throws
            BadCredentialsException {
        JWTResponseDTO jwtResponseDTO = authenticationService.signIn(signInRequestDTO);
        return ResponseEntity.ok(jwtResponseDTO);
    }

    @Operation(
            summary = "Refreshes a refresh token",
            description = "Returns the new access and refresh token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Refresh token refreshed successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JWTResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "Refresh token still valid"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<JWTResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO)
            throws RefreshTokenStillValidException, RefreshTokenNotValid {
        JWTResponseDTO jwtResponseDTO = authenticationService.refreshToken(refreshTokenDTO);
        return ResponseEntity.ok(jwtResponseDTO);
    }

}
