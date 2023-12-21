package com.mh.SpringBatch.Config;

import com.mh.SpringBatch.Chunk.BookAuthorProcessor;
import com.mh.SpringBatch.Chunk.BookReader;
import com.mh.SpringBatch.Chunk.BookTitleProcessor;
import com.mh.SpringBatch.Chunk.BookWriter;
import com.mh.SpringBatch.Entity.BookEntity;
import com.mh.SpringBatch.Tasklet.sayHello;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class BatchConfig {

    @Bean
    public Job TestJob(JobRepository jobRepository, PlatformTransactionManager manager){
        return new JobBuilder("TestJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(TestStep(jobRepository, manager))
                .build();
    }


    // Tasklet
    @Bean
    public Step TestStep(JobRepository jobRepository, PlatformTransactionManager manager){
        return new StepBuilder("TestStep", jobRepository)
                .tasklet(new sayHello(), manager)
                .build();
    }

    // Chunk
    @Bean
    public Step TestChunkStep(JobRepository jobRepository, PlatformTransactionManager manager){
        return new StepBuilder("TestChunkStep", jobRepository)
                .<BookEntity, BookEntity>chunk(10, manager)
                .reader(ChunkReader())
                .processor(Chunkprocessor())
                .writer(ChunkWriter())
                .build();
    }

    @Bean
    // Step-scoped bean을 위한 편리한 어노테이션
    // @Bean을 @StepScope로 표시하는 것은 @Scope(value="step"), proxyMode=TARGET_CLASS)로 표시하는 것과 같습니다.
    @StepScope
    public ItemReader<BookEntity> ChunkReader(){
        return new BookReader("http://localhost:8080/book/findall", new RestTemplate());
    }

    @Bean
    @StepScope
    public ItemProcessor<BookEntity, BookEntity> Chunkprocessor() {
        CompositeItemProcessor<BookEntity, BookEntity> process = new CompositeItemProcessor<>();
        process.setDelegates(List.of(new BookAuthorProcessor(), new BookTitleProcessor()));
        return process;
    }

    @Bean
    @StepScope
    public ItemWriter<BookEntity> ChunkWriter() {
        return new BookWriter();
    }

}
