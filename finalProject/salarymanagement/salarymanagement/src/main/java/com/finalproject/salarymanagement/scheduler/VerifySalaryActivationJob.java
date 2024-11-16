package com.finalproject.salarymanagement.scheduler;

import com.finalproject.salarymanagement.service.impl.SalaryServiceImpl;
import lombok.AllArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
@AllArgsConstructor
public class VerifySalaryActivationJob implements Job {

    SalaryServiceImpl salaryService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        salaryService.verifySalaryImplementation();
    }


}
