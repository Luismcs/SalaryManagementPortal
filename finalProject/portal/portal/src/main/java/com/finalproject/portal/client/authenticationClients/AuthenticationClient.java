package com.finalproject.portal.client.authenticationClients;

import com.finalproject.portal.client.errorDecorders.AuthenticationClientErrorDecoder;
import com.finalproject.portal.dto.JWTResponseDTO;
import com.finalproject.portal.dto.RefreshTokenDTO;
import com.finalproject.portal.dto.SignInDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authentication-service", url = "${authentication.service.url}", configuration = AuthenticationClientErrorDecoder.class)
public interface AuthenticationClient {

    @PostMapping("/sign-in")
    ResponseEntity<JWTResponseDTO> signIn(@RequestBody SignInDTO signInDTO);

    @PostMapping("/refresh-token")
    ResponseEntity<JWTResponseDTO> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO);
}
