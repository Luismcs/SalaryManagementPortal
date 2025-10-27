package com.finalproject.portal.controller;

import com.finalproject.portal.dto.*;
import com.finalproject.portal.service.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Portal Authentication", description = "Authentication Endpoints")
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(
            summary = "Signs up a collaborator",
            description = "Returns the created collaborator",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Collaborator created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping("/sign-up")
    public UserSignUpResponseDTO signUp(@RequestBody UserGeneralInfoDTO userGeneralInfoDTO) {
        return authenticationService.signUp(userGeneralInfoDTO);
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
    public JWTResponseDTO signUp(@RequestBody SignInDTO signInDTO) {
        return authenticationService.signIn(signInDTO);
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
    public JWTResponseDTO refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        return authenticationService.refreshToken(refreshTokenDTO);
    }

}
