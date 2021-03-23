package com.himart.restservicecors.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@Configuration
@EnableAsync //비동기 활성화
public class AsyncServiceConfig extends AsyncConfigurerSupport {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2); // 기본 대기 thread 개수
        executor.setMaxPoolSize(10); // 동시동작 최대 thread 개수
        executor.setQueueCapacity(500);// thread초과 시 queue한계
        executor.setThreadNamePrefix("hisis-async-"); //접두사 지정
        executor.initialize();
        return executor;
    }
}