package com.finalproject.salarymanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.salarymanagement.dto.ExportRequestDTO;
import com.finalproject.salarymanagement.dto.FilteredExportRequestDTO;
import com.finalproject.salarymanagement.enums.SalaryState;
import com.finalproject.salarymanagement.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportServiceImpl reportService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExportRequestDTO exportRequestDTO;
    private FilteredExportRequestDTO filteredExportRequestDTO;


    @BeforeEach
    public void init() {
        exportRequestDTO = new ExportRequestDTO();
        exportRequestDTO.setEmails(List.of("john_doe@email.com", "abc@email.com"));
        filteredExportRequestDTO = new FilteredExportRequestDTO();
        filteredExportRequestDTO.setCorrelationId("1");
        filteredExportRequestDTO.setEmail("john_doe@email.com");
        filteredExportRequestDTO.setEndDate(LocalDate.now().minusMonths(5));
        filteredExportRequestDTO.setStartDate(LocalDate.now().plusMonths(5));
        filteredExportRequestDTO.setSalaryState(SalaryState.ACTIVE);

    }

    @Test
    public void reportController_exportSalaryReport_returnsNothing() throws Exception {

        // Arrange
        doNothing().when(reportService).generateExcel(exportRequestDTO.getEmails());

        // Act
        mockMvc.perform(post("/report/export-salaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exportRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        // Assert
        verify(reportService).generateExcel(exportRequestDTO.getEmails());
    }

    @Test
    public void reportController_exportOwnSalaryReport_returnsNothing() throws Exception {

        // Arrange
        doNothing().when(reportService).generateFilteredExcel(filteredExportRequestDTO);

        // Act
        mockMvc.perform(post("/report/export-own-salaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filteredExportRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        // Assert
        verify(reportService).generateFilteredExcel(Mockito.any(FilteredExportRequestDTO.class));
    }


}
