package com.finalproject.portal.controller;

import com.finalproject.portal.dto.ExportRequestDTO;
import com.finalproject.portal.dto.FilteredExportRequestDTO;
import com.finalproject.portal.service.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Portal Report", description = "Report Endpoints")
@RestController
@RequestMapping("/report")
@SecurityRequirement(name = "bearerAuth")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Secured("ADMIN")
    @PostMapping("/export-salaries")
    public ResponseEntity<Void> exportSalaryReport(@RequestBody @Valid ExportRequestDTO exportRequestDTO) {
        return reportService.exportSalaryReport(exportRequestDTO);
    }

    @Secured("USER")
    @PostMapping("/export-own-salaries")
    public ResponseEntity<Void> exportOwnSalaryReport(@RequestBody @Valid FilteredExportRequestDTO filteredExportRequestDTO) {
        return reportService.exportOwnSalaryReport(filteredExportRequestDTO);
    }

}
