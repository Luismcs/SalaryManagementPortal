package com.finalproject.portal.client.salaryManagementClients;

import com.finalproject.portal.client.errorDecorders.ComponentTypeSubtypeClientErrorDecoder;
import com.finalproject.portal.dto.ComponentTypeSubtypeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "component-type-subtype-service", url = "${component-type-subtype-service.service.url}",
        configuration = ComponentTypeSubtypeClientErrorDecoder.class)
public interface ComponentTypeSubtypeClient {

    @GetMapping
    ResponseEntity<Page<ComponentTypeSubtypeDTO>> getAll(Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<ComponentTypeSubtypeDTO> getById(@PathVariable long id);

    @PostMapping()
    ResponseEntity<ComponentTypeSubtypeDTO> create(@RequestBody ComponentTypeSubtypeDTO componentTypeSubtypeDTO);

    @PutMapping()
    ResponseEntity<ComponentTypeSubtypeDTO> update(@RequestBody ComponentTypeSubtypeDTO componentTypeSubtypeDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<ComponentTypeSubtypeDTO> delete(@PathVariable long id);

}
