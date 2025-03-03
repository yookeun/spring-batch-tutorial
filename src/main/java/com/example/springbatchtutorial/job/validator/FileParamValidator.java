package com.example.springbatchtutorial.job.validator;

import ch.qos.logback.core.util.StringUtil;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

/**
 * <pre>
 * FileParamValidator
 * author : Yookeun
 * 2025-03-03
 * desc :
 * </pre>
 */
public class FileParamValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String fileName = parameters.getString("fileName");

        if (!StringUtils.endsWithIgnoreCase(fileName,"csv")) {
            throw new JobParametersInvalidException("fileName is not csv");
        }
    }
}
