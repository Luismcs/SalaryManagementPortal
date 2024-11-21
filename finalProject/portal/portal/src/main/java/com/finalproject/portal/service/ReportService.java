package com.finalproject.portal.service;

import com.finalproject.portal.client.salaryManagementClients.ReportClient;
import com.finalproject.portal.dto.CollaboratorDTO;
import com.finalproject.portal.dto.ExportRequestDTO;
import com.finalproject.portal.dto.FilteredExportRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportService {

    private final ReportClient reportClient;
    private final JwtServiceImpl jwtService;
    private final CollaboratorServiceImpl collaboratorService;

    public ReportService(ReportClient reportClient, JwtServiceImpl jwtService, CollaboratorServiceImpl collaboratorService) {
        this.reportClient = reportClient;
        this.jwtService = jwtService;
        this.collaboratorService = collaboratorService;
    }

    public ResponseEntity<Void> exportSalaryReport(ExportRequestDTO exportRequestDTO) {
        reportClient.exportSalaryReport(exportRequestDTO);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> exportOwnSalaryReport(FilteredExportRequestDTO filteredExportRequestDTO) {
        String token = jwtService.getCurrentToken();
        String correlationId = jwtService.extractCorrelationId(token);
        CollaboratorDTO collaboratorDTO = collaboratorService.getById(Long.parseLong(correlationId)).getBody();
        if (collaboratorDTO.getEmail() != null) {
            filteredExportRequestDTO.setEmail(collaboratorDTO.getEmail());
        }
        filteredExportRequestDTO.setCorrelationId(correlationId);
        reportClient.exportOwnSalaryReport(filteredExportRequestDTO);
        return ResponseEntity.ok().build();
    }

}
