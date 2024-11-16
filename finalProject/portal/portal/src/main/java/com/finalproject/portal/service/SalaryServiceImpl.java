package com.finalproject.portal.service;

import com.finalproject.portal.client.salaryManagementClients.SalaryClient;
import com.finalproject.portal.client.salaryManagementClients.SalaryComponentClient;
import com.finalproject.portal.dto.SalaryComponentDTO;
import com.finalproject.portal.dto.SalaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SalaryServiceImpl {

    private final SalaryClient salaryClient;

    public SalaryServiceImpl(SalaryClient salaryClient) {
        this.salaryClient = salaryClient;
    }

    public ResponseEntity<Page<SalaryDTO>> getAll(Pageable pageable) {
        return salaryClient.getAll(pageable);
    }

    public ResponseEntity<SalaryDTO> getById(Long id) {
        return salaryClient.getById(id);
    }

    public ResponseEntity<SalaryDTO> approve(Long id) {
        return salaryClient.approve(id);
    }

    public ResponseEntity<SalaryDTO> create(SalaryDTO salaryDTO) {
        return salaryClient.create(salaryDTO);
    }

    public ResponseEntity<SalaryDTO> update(SalaryDTO salaryDTO) {
        return salaryClient.update(salaryDTO);
    }

    public ResponseEntity<Void> delete(Long id) {
        salaryClient.delete(id);
        return ResponseEntity.ok().build();
    }

}
