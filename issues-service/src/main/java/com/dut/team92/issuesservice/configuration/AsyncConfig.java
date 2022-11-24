package com.dut.team92.issuesservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "threadPoolTaskExecutor1")
    public ThreadPoolTaskExecutor asyncThreadPoolTaskExecutor1(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("AsynchThread1::");

        executor.initialize();
        return executor;
    }

    @Bean(name = "threadPoolTaskExecutor2")
    public ThreadPoolTaskExecutor asyncThreadPoolTaskExecutor2(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("AsynchThread2::");

        executor.initialize();
        return executor;
    }

    @Bean(name = "threadPoolTaskExecutor3")
    public ThreadPoolTaskExecutor asyncThreadPoolTaskExecutor3(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("AsynchThread3::");

        executor.initialize();
        return executor;
    }
}
