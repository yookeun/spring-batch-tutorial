package com.example.springbatchtutorial.job;

import com.example.springbatchtutorial.entity.Accounts;
import com.example.springbatchtutorial.entity.Orders;
import com.example.springbatchtutorial.repository.AccountsRepository;
import com.example.springbatchtutorial.repository.OrdersRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * <pre>
 * TrMigrationConfig
 * author : Yookeun
 * 2025-03-08
 * desc : 주문 테이브렝서 정상 테이블에서
 * </pre>
 */
@Configuration
@RequiredArgsConstructor
public class TrMigrationConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final OrdersRepository ordersRepository;
    private final AccountsRepository accountsRepository;


    @Bean
    public Job trMigrationJob(Step trMigrationStep) {
        return new JobBuilder("trMigrationJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(trMigrationStep)
                .build();
    }

    @Bean
    @JobScope
    public Step trMigrationStep(
            ItemReader<Orders> trOrdersReader,
            ItemProcessor<Orders, Accounts> trOrdersProcessor,
            ItemWriter<Accounts> trOrdersWriter
    ) {
        return new StepBuilder("trMigrationStep", jobRepository)
                .<Orders, Accounts>chunk(5, transactionManager)
                .reader(trOrdersReader)
                //.writer(chunk -> chunk.forEach(System.out::println))
                .processor(trOrdersProcessor)
                .writer(trOrdersWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<Orders> trOrdersReader() {
        return new RepositoryItemReaderBuilder<Orders>()
                .name("trOrdersReader")
                .repository(ordersRepository)
                .methodName("findAll")
                .pageSize(5)
                .arguments(List.of())
                .sorts(Collections.singletonMap("id", Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Orders, Accounts> trOrdersProcessor() {
        return new ItemProcessor<Orders, Accounts>() {
            @Override
            public Accounts process(Orders item) throws Exception {
                return new Accounts(item);
            }
        };
    }

//    @Bean
//    @StepScope
//    public ItemWriter<Accounts> trOrdersWriter() {
//        return new RepositoryItemWriterBuilder<Accounts>()
//                .repository(accountsRepository)
//                .methodName("save")
//                .build();
//    }

    @Bean
    @StepScope
    public ItemWriter<Accounts> trOrdersWriter() {
        return new ItemWriter<Accounts>() {
            @Override
            public void write(Chunk<? extends Accounts> items) throws Exception {
                items.forEach(item -> accountsRepository.save(item));
            }
        };
    }
}
