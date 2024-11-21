package com.finalproject.portal.client.salaryManagementClients;

import com.finalproject.portal.dto.ExportRequestDTO;
import com.finalproject.portal.dto.FilteredExportRequestDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "report-service", url = "${report-service.service.url}")
public interface ReportClient {

    @PostMapping("/export-salaries")
    ResponseEntity<Void> exportSalaryReport(@RequestBody @Valid ExportRequestDTO exportRequestDTO);

    @PostMapping("/export-own-salaries")
    ResponseEntity<Void> exportOwnSalaryReport(@RequestBody @Valid FilteredExportRequestDTO filteredExportRequestDTO);

}
