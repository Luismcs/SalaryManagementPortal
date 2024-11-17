package com.finalproject.portal.client.authenticationClients;

import com.finalproject.portal.client.errorDecorders.UserCredentialsClientErrorDecoder;
import com.finalproject.portal.dto.UserCredentialsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-credentials-service", url = "${user-credentials.service.url}", configuration =
        UserCredentialsClientErrorDecoder.class)
public interface UserCredentialsClient {

    @GetMapping
    ResponseEntity<Page<UserCredentialsDTO>> getAll(Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<UserCredentialsDTO> getById(@PathVariable long id);

    @PostMapping()
    ResponseEntity<UserCredentialsDTO> create(@RequestBody UserCredentialsDTO userCredentialsDTO);

    @PutMapping("/{id}")
    ResponseEntity<UserCredentialsDTO> update(@RequestBody UserCredentialsDTO userCredentialsDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable long id);

}
