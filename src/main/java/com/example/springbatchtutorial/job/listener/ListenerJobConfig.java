package com.example.springbatchtutorial.job.listener;

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
public class ListenerJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job listenerJob(Step listenerStep) {
        return new JobBuilder("listenerJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(listenerStep)
                .build();

    }

    @Bean
    public Step listenerStep() {
        return new StepBuilder("listenerStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("ListenerStep is running.");
                    return RepeatStatus.FINISHED;
                    //throw new Exception("Failed!!!.");
                }, transactionManager)
                .build();
    }
}
