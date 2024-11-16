package com.finalproject.portal.client.salaryManagementClients;

import com.finalproject.portal.client.errorDecorders.ComponentTypeClientErrorDecoder;
import com.finalproject.portal.dto.ComponentTypeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "component-type-service", url = "${component-type-service.service.url}", configuration =
        ComponentTypeClientErrorDecoder.class)
public interface ComponentTypeClient {

    @GetMapping
    ResponseEntity<Page<ComponentTypeDTO>> getAll(Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<ComponentTypeDTO> getById(@PathVariable long id);

    @PostMapping()
    ResponseEntity<ComponentTypeDTO> create(@RequestBody ComponentTypeDTO componentTypeDTO);

    @PutMapping()
    ResponseEntity<ComponentTypeDTO> update(@RequestBody ComponentTypeDTO componentTypeDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<ComponentTypeDTO> delete(@PathVariable long id);

}
