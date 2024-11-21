package com.finalproject.salarymanagement.service;

import java.io.IOException;
import java.util.List;

public interface ReportService {

    void generateExcel(List<String> emails) throws IOException, InterruptedException;

}
