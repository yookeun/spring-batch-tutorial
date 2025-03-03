package com.example.springbatchtutorial.job.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * <pre>
 * JobLoggerListener
 * author : Yookeun
 * 2025-03-03
 * desc :
 * </pre>
 */
@Slf4j
public class JobLoggerListener implements JobExecutionListener {

    private static final String BEFORE_MESSAGE = "{} Job is running";
    private static final String AFTER_MESSAGE = "{} Job is done. (Status : {})";

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(BEFORE_MESSAGE, jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info(AFTER_MESSAGE, jobExecution.getJobInstance().getJobName(),
                jobExecution.getStatus());

        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.error("Job failed. Exception : {}", jobExecution.getAllFailureExceptions());
        }
    }
}


