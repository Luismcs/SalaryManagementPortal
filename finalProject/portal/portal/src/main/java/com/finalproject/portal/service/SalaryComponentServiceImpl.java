package com.finalproject.portal.service;

import com.finalproject.portal.client.salaryManagementClients.SalaryComponentClient;
import com.finalproject.portal.dto.SalaryComponentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SalaryComponentServiceImpl {

    private final SalaryComponentClient salaryComponentClient;

    public SalaryComponentServiceImpl(SalaryComponentClient salaryComponentClient) {
        this.salaryComponentClient = salaryComponentClient;
    }

    public ResponseEntity<Page<SalaryComponentDTO>> getAll(Pageable pageable) {
        return salaryComponentClient.getAll(pageable);
    }

    public ResponseEntity<SalaryComponentDTO> getById(Long id) {
        return salaryComponentClient.getById(id);
    }

    public ResponseEntity<SalaryComponentDTO> create(SalaryComponentDTO salaryComponentDTO) {
        return salaryComponentClient.create(salaryComponentDTO);
    }

    public ResponseEntity<SalaryComponentDTO> update(SalaryComponentDTO salaryComponentDTO) {
        return salaryComponentClient.update(salaryComponentDTO);
    }

    public ResponseEntity<Void> delete(Long id) {
        salaryComponentClient.delete(id);
        return ResponseEntity.ok().build();
    }

}
