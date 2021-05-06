package com.micro.standard.module.core.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.micro.standard.module.core.config.task.MdcTaskDecorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ThreadPoolConfig {

	// 获取到服务器的cpu内核
	private static final int core = Runtime.getRuntime().availableProcessors();
	static {
		log.info("当前系统availableProcessors数量为:{}", core);
	}

	@Bean("threadPoolTaskExecutor") // 业务线程池
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(core);
		executor.setMaxPoolSize(core * 2);
		executor.setQueueCapacity(2000);
		executor.setThreadNamePrefix("thread-pool-task-");
		executor.setTaskDecorator(new MdcTaskDecorator());
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();

		return executor;
	}

	@Bean("threadPoolJobExecutor") // 调度任务线程池
	public ThreadPoolTaskExecutor threadPoolJobExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(core);
		executor.setMaxPoolSize(core * 3);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("thread-pool-job-");
		executor.setTaskDecorator(new MdcTaskDecorator());
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();

		return executor;
	}

	@Bean("threadPoolFutureExecutor") // 异步调用
	public ThreadPoolTaskExecutor threadPoolFutureExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(core);
		executor.setMaxPoolSize(core * 10);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("thread-pool-future-");
		executor.setTaskDecorator(new MdcTaskDecorator());
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();

		return executor;
	}

}
