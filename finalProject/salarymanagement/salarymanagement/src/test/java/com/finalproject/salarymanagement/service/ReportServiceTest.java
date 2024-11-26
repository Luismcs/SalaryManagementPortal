package com.finalproject.salarymanagement.service;

import com.finalproject.salarymanagement.config.ConfigProperties;
import com.finalproject.salarymanagement.enums.SalaryState;
import com.finalproject.salarymanagement.model.ComponentType;
import com.finalproject.salarymanagement.model.ComponentTypeSubtype;
import com.finalproject.salarymanagement.model.Salary;
import com.finalproject.salarymanagement.model.SalaryComponent;
import com.finalproject.salarymanagement.rabbitmq.publisher.SalaryReportExportPublisher;
import com.finalproject.salarymanagement.repository.SalaryRepository;
import com.finalproject.salarymanagement.service.impl.ReportServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private SalaryRepository salaryRepository;

    @Mock
    private SalaryReportExportPublisher salaryReportExportPublisher;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Mock
    private ConfigProperties configProperties;

    private Salary salary;
    private List<Salary> mockSalaries;
    private List<String> emails;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void init() {
        ComponentType componentType = new ComponentType();
        componentType.setName("Component Type");
        ComponentTypeSubtype componentTypeSubtype = new ComponentTypeSubtype();
        componentTypeSubtype.setName("Component Type Subtype");
        SalaryComponent salaryComponent = new SalaryComponent();
        salaryComponent.setValue(100);
        salaryComponent.setComponentType(componentType);
        salaryComponent.setComponentTypeSubtype(componentTypeSubtype);
        salary = new Salary();
        salary.setId(1L);
        salary.setAcceptanceDate(null);
        salary.setCorrelationId("1");
        salary.setImplementationDate(LocalDate.now());
        salary.setSalaryState(SalaryState.INACTIVE);
        mockSalaries = List.of(salary);
        emails = List.of("example@example.com", "test@test.com");
        salary.setSalaryComponents(List.of(salaryComponent));

    }

    @Test
    void reportService_generateExcel_returnsNothing() throws IOException, InterruptedException {

        //Arrange
        when(salaryRepository.findAll()).thenReturn(mockSalaries);

        String mockSalaryReportFolderPath = tempDir.toString(); // Use temporary directory
        String expectedFilePrefix = "report";

        // Inject temporary folder path into the service
        ReflectionTestUtils.setField(reportService, "salaryReportFolderPath", mockSalaryReportFolderPath);

        // Act
        reportService.generateExcel(emails);

        File[] generatedFiles = new File(mockSalaryReportFolderPath).listFiles();
        assertThat(generatedFiles).isNotEmpty();

        File generatedFile = generatedFiles[0];
        assertThat(generatedFile.getName()).startsWith(expectedFilePrefix);
        assertThat(generatedFile.exists()).isTrue();

        // Verify that the workbook contains data
        try (FileInputStream fis = new FileInputStream(generatedFile);
             HSSFWorkbook workbook = new HSSFWorkbook(fis)) {
            HSSFSheet sheet = workbook.getSheetAt(0);
            assertThat(sheet).isNotNull();
            assertThat(sheet.getLastRowNum()).isEqualTo(mockSalaries.size()); // Verify row count
        }
    }

}



