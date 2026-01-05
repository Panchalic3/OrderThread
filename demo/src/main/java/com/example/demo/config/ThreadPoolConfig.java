package com.example.demo.config;

import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    private ExecutorService executorService;

    @Bean
    public ExecutorService orderExecutorService() {
        return Executors.newFixedThreadPool(3);
    }

    @PreDestroy
    public void shutdownExecutor() {
        System.out.println("Shutdown initiated. Stopping executor...");

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                System.out.println("Forcing executor shutdown...");
                executorService.shutdownNow(); // interrupt running tasks
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Executor shutdown completed.");
    }
}
