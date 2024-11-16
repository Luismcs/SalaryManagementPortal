package com.finalproject.portal.client.collaboratorClients;

import com.finalproject.portal.client.errorDecorders.AddressClientErrorDecoder;
import com.finalproject.portal.dto.AddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "address-service", url = "${address.service.url}", configuration = AddressClientErrorDecoder.class)
public interface AddressClient {

    @GetMapping
    ResponseEntity<Page<AddressDTO>> getAll(Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<AddressDTO> getById(@PathVariable long id);

    @PostMapping()
    ResponseEntity<AddressDTO> create(@RequestBody AddressDTO addressDTO);

    @PutMapping()
    ResponseEntity<AddressDTO> update(@RequestBody AddressDTO addressDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable long id);

}
