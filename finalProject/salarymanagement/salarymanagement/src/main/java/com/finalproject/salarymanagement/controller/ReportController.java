package com.finalproject.salarymanagement.controller;

import com.finalproject.salarymanagement.dto.ExportRequestDTO;
import com.finalproject.salarymanagement.dto.FilteredExportRequestDTO;
import com.finalproject.salarymanagement.service.impl.ReportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportServiceImpl reportServiceImpl;

    public ReportController(ReportServiceImpl reportServiceImpl) {
        this.reportServiceImpl = reportServiceImpl;
    }

    @Operation(
            summary = "Builds the excel file then produces to a RabbitMQ queue",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "File Built successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping("/export-salaries")
    public ResponseEntity<Void> exportSalaryReport(@RequestBody @Valid ExportRequestDTO exportRequestDTO) throws IOException, InterruptedException {
        reportServiceImpl.generateExcel(exportRequestDTO.getEmails());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Builds the excel file then produces to a RabbitMQ queue",
            description = "Returns nothing",
            responses = {
                    @ApiResponse(responseCode = "200", description = "File Built successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping("/export-own-salaries")
    public ResponseEntity<Void> exportSalaryReport(@RequestBody @Valid FilteredExportRequestDTO filteredExportRequestDTO) throws IOException {
        reportServiceImpl.generateFilteredExcel(filteredExportRequestDTO);
        return ResponseEntity.ok().build();
    }

}
