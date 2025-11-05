package com.finalproject.portal.client.authenticationClients;

import com.finalproject.portal.client.errorDecorders.UserCredentialsClientErrorDecoder;
import com.finalproject.portal.dto.UserCredentialsDTO;
import com.finalproject.portal.dto.UserCredentialsRequestDTO;
import com.finalproject.portal.dto.UserCredentialsResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-credentials-service", url = "${user-credentials.service.url}", configuration =
        UserCredentialsClientErrorDecoder.class)
public interface UserCredentialsClient {

    @GetMapping
    ResponseEntity<Page<UserCredentialsResponseDTO>> getAll(Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<UserCredentialsResponseDTO> getById(@PathVariable long id);

    @PostMapping()
    ResponseEntity<UserCredentialsResponseDTO> create(@RequestBody UserCredentialsRequestDTO userCredentialsRequestDTO);

    @PutMapping("/{id}")
    ResponseEntity<UserCredentialsResponseDTO> update(@RequestBody UserCredentialsRequestDTO userCredentialsRequestDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable long id);

    @GetMapping("/exists/username/{username}")
    ResponseEntity<Boolean> existsByUsername(@PathVariable String username);

}
