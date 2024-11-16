package com.finalproject.portal.client.salaryManagementClients;

import com.finalproject.portal.client.errorDecorders.SalaryClientErrorDecoder;
import com.finalproject.portal.client.errorDecorders.SalaryComponentClientErrorDecoder;
import com.finalproject.portal.dto.ComponentTypeSubtypeDTO;
import com.finalproject.portal.dto.SalaryComponentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "salary-component-service", url = "${salary-component-service.service.url}", configuration =
        SalaryComponentClientErrorDecoder.class)
public interface SalaryComponentClient {

    @GetMapping
    ResponseEntity<Page<SalaryComponentDTO>> getAll(Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<SalaryComponentDTO> getById(@PathVariable long id);

    @PostMapping()
    ResponseEntity<SalaryComponentDTO> create(@RequestBody SalaryComponentDTO salaryComponentDTO);

    @PutMapping()
    ResponseEntity<SalaryComponentDTO> update(@RequestBody SalaryComponentDTO salaryComponentDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<SalaryComponentDTO> delete(@PathVariable long id);

}
