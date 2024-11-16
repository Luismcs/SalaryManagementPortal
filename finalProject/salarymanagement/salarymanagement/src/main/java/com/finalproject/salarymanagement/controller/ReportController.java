package com.finalproject.salarymanagement.controller;

import com.finalproject.salarymanagement.service.impl.ReportServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
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

    @GetMapping("/export-salaries")
    public ResponseEntity<Void> exportSalaryReport(HttpServletResponse response) throws IOException {
        reportServiceImpl.generateExcel(response);
        return ResponseEntity.ok().build();
    }

}
