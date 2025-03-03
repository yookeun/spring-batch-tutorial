package com.example.springbatchtutorial.job.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * <pre>
 * HelloWorld
 * author : Yookeun
 * 2025-03-01
 * desc :
 * </pre>
 */
@Configuration
@RequiredArgsConstructor
public class ValidatorParamJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job validatedParamJob(Step validatedParamStep) {
        return new JobBuilder("validatedParamJob", jobRepository)
                .incrementer(new RunIdIncrementer())

                //5.x 버전에서는 아래 validator가 정상적으로 체크 못함
                .validator(new FileParamValidator())
                .start(validatedParamStep)
                .build();

    }


    @Bean
    public Step validatedParamStep(Tasklet validatedParamTasklet) {
        return new StepBuilder("validatedParamStep", jobRepository)
                .tasklet(validatedParamTasklet, transactionManager)
                .build();
    }

    @Bean
    @StepScope
    public Tasklet validatedParamTasklet(@Value("#{jobParameters['fileName']}") String fileName) {
        return (contribution, chunkContext) -> {

            System.out.println("fileName = " + fileName);
            System.out.println("ValidateParam start");
            return RepeatStatus.FINISHED;
        };
    }
}
