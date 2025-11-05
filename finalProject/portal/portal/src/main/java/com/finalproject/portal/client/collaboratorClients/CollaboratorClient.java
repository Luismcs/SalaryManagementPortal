package com.finalproject.portal.client.collaboratorClients;

import com.finalproject.portal.client.errorDecorders.CollaboratorClientErrorDecoder;
import com.finalproject.portal.dto.CollaboratorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "collaborator-service", url = "${collaborator.service.url}", configuration = CollaboratorClientErrorDecoder.class)
public interface CollaboratorClient {

    @GetMapping
    ResponseEntity<Page<CollaboratorDTO>> getAll(Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<CollaboratorDTO> getById(@PathVariable long id);

    @PostMapping()
    ResponseEntity<CollaboratorDTO> create(@RequestBody CollaboratorDTO collaboratorDTO);

    @PutMapping()
    ResponseEntity<CollaboratorDTO> update(@RequestBody CollaboratorDTO collaboratorDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<CollaboratorDTO> delete(@PathVariable long id);
}
