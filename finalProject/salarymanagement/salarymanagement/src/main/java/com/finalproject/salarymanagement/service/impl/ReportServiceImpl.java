package com.finalproject.salarymanagement.service.impl;

import com.finalproject.salarymanagement.model.Salary;
import com.finalproject.salarymanagement.model.SalaryComponent;
import com.finalproject.salarymanagement.publisher.SalaryReportExportPublisher;
import com.finalproject.salarymanagement.repository.SalaryRepository;
import com.finalproject.salarymanagement.service.ReportService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final SalaryRepository salaryRepository;
    private final SalaryReportExportPublisher salaryReportExportPublisher;

    public ReportServiceImpl(SalaryRepository salaryRepository, SalaryReportExportPublisher salaryReportExportPublisher) {
        this.salaryRepository = salaryRepository;
        this.salaryReportExportPublisher = salaryReportExportPublisher;
    }

    private HSSFFont getArialBoldFont(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();

        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setBold(true);

        return font;
    }

    private HSSFCellStyle getLightGreenStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();

        style.setFont(getArialBoldFont(workbook));
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return style;
    }

    private HSSFCellStyle getLightYellowStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();

        style.setFont(getArialBoldFont(workbook));
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return style;
    }

    private void addLightGreenStyledCell(HSSFWorkbook workbook, HSSFRow row, String text, int column) {
        HSSFCell cell = row.createCell(column);
        cell.setCellStyle(getLightGreenStyle(workbook));
        cell.setCellValue(text);
    }

    private void addLightYellowStyledCell(HSSFWorkbook workbook, HSSFRow row, String text, int column) {
        HSSFCell cell = row.createCell(column);
        cell.setCellStyle(getLightYellowStyle(workbook));
        cell.setCellValue(text);
    }

    private void addSalaryHeaderRowInfoHeader(HSSFWorkbook workbook, HSSFSheet sheet, int dataRowIndex) {
        HSSFRow salaryHeader = sheet.createRow(dataRowIndex);

        addLightGreenStyledCell(workbook, salaryHeader, "Correlation ID", 0);
        addLightGreenStyledCell(workbook, salaryHeader, "Acceptance Date", 1);
        addLightGreenStyledCell(workbook, salaryHeader, "Implementation Date", 2);
    }

    private void addSalaryHeaderRowInfo(HSSFSheet sheet, int dataRowIndex, Salary salary) {
        HSSFRow salaryComponentHeader = sheet.createRow(dataRowIndex);

        addSalaryComponentInfoCell(salaryComponentHeader, 0, salary.getCorrelationId());

        if (salary.getAcceptanceDate() != null) {
            addSalaryComponentInfoCell(salaryComponentHeader, 1, salary.getAcceptanceDate().toString());
        } else {
            addSalaryComponentInfoCell(salaryComponentHeader, 1, "");
        }

        addSalaryComponentInfoCell(salaryComponentHeader, 2, salary.getImplementationDate().toString());
    }

    private void addSalaryComponentListHeader(HSSFWorkbook workbook, HSSFSheet sheet, int dataRowIndex) {
        HSSFRow salaryComponentHeader = sheet.createRow(dataRowIndex);

        addLightGreenStyledCell(workbook, salaryComponentHeader, "Component Type", 0);
        addLightGreenStyledCell(workbook, salaryComponentHeader, "Component Type Subtype", 1);
        addLightGreenStyledCell(workbook, salaryComponentHeader, "Value($)", 2);
        addLightGreenStyledCell(workbook, salaryComponentHeader, "Present on Receipt", 3);
    }

    private void addSalaryComponentRowInfo(HSSFSheet sheet, int dataRowIndex, SalaryComponent salaryComponent) {
        HSSFRow salaryComponentInfo = sheet.createRow(dataRowIndex);

        addSalaryComponentInfoCell(salaryComponentInfo, 0, salaryComponent.getComponentType().getName());
        addSalaryComponentInfoCell(salaryComponentInfo, 1, salaryComponent.getComponentTypeSubtype().getName());
        addSalaryComponentInfoCell(salaryComponentInfo, 2, String.valueOf(salaryComponent.getValue()));
        addSalaryComponentInfoCell(salaryComponentInfo, 3, String.valueOf(salaryComponent.isPresentOnReceipt()));
    }

    private void addSalaryComponentInfoCell(HSSFRow salaryComponentInfoRow, int column, String text) {
        salaryComponentInfoRow.createCell(column).setCellValue(text);
    }

    private HSSFWorkbook generateWorkbook() {
        List<Salary> salaries = salaryRepository.findAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Salaries Info");
        int dataRowIndex = 1;

        for (Salary salary : salaries) {
            addSalaryHeaderRowInfoHeader(workbook, sheet, dataRowIndex);
            dataRowIndex++;

            addSalaryHeaderRowInfo(sheet, dataRowIndex, salary);
            dataRowIndex++;

            HSSFRow salaryComponentTitle = sheet.createRow(dataRowIndex);
            addLightYellowStyledCell(workbook, salaryComponentTitle, "Salary Components List", 0);
            dataRowIndex++;

            addSalaryComponentListHeader(workbook, sheet, dataRowIndex);
            dataRowIndex++;

            for (SalaryComponent salaryComponent : salary.getSalaryComponents()) {
                addSalaryComponentRowInfo(sheet, dataRowIndex, salaryComponent);
                dataRowIndex++;
            }

            dataRowIndex++;
        }

        return workbook;
    }

    public void generateExcel(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = generateWorkbook();
        String sharedFolderPath = "/app/shared";
        String fileName = "report" + LocalDateTime.now() + ".xls";
        File file = new File(sharedFolderPath, fileName);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }

        response.setContentType("application/vnd.ms-excel");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=salaryReport.xls";
        response.setHeader(headerKey, headerValue);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } finally {
            workbook.close();
        }

        salaryReportExportPublisher.publishExportEvent(fileName);

    }

}
