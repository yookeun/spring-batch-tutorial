package com.example.springbatchtutorial.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
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
public class HelloWorldJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;



    @Bean
    public Job helloWorldJob(Step helloWorldStep) {
        return new JobBuilder("helloWorldJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(helloWorldStep)
                .build();

    }

    @Bean
    public Step helloWorldStep() {
        return new StepBuilder("helloWorldStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Hello, World! Batch is running.");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
