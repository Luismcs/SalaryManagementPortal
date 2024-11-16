package com.finalproject.portal.client.salaryManagementClients;

import com.finalproject.portal.client.errorDecorders.SalaryClientErrorDecoder;
import com.finalproject.portal.dto.SalaryComponentDTO;
import com.finalproject.portal.dto.SalaryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "salary-service", url = "${salary-service.service.url}", configuration = SalaryClientErrorDecoder.class)
public interface SalaryClient {

    @GetMapping
    ResponseEntity<Page<SalaryDTO>> getAll(Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<SalaryDTO> getById(@PathVariable Long id);

    @GetMapping("/approve/{id}")
    ResponseEntity<SalaryDTO> approve(@PathVariable Long id);

    @PostMapping()
    ResponseEntity<SalaryDTO> create(@RequestBody SalaryDTO salaryDTO);

    @PutMapping()
    ResponseEntity<SalaryDTO> update(@RequestBody SalaryDTO salaryDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<SalaryDTO> delete(@PathVariable Long id);

}
