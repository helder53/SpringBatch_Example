package com.mh.SpringBatch.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class SchedulerConfig {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    // fixedDelay - 이전 실행이 종료된 시점부터의 간격
    // initialDelay - 스케줄링이 시작되기전 초기 지연 상태
    @Scheduled(fixedDelay = 10000, initialDelay = 2000)
    public void scheduleJob() throws Exception {
        log.info("Job scheduler 시작");
        jobLauncher.run(job, new JobParametersBuilder()
                .addLong("TestJobLauncher", System.nanoTime()).toJobParameters());
        log.info("Job scheduler 끝");
    }
}
