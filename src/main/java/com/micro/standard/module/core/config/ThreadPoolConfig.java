package com.micro.standard.module.core.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.micro.standard.module.common.component.MdcTaskDecorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ThreadPoolConfig {

	@Bean("threadPoolTaskExecutor") // 业务线程池
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 获取到服务器的cpu内核
		int core = Runtime.getRuntime().availableProcessors();
		log.info("初始化threadPoolJobExecutor,核心线程数量:{}", core);

		executor.setCorePoolSize(core);
		executor.setMaxPoolSize(core * 3);
		executor.setQueueCapacity(200);
		executor.setThreadNamePrefix("thread-pool-task-");
		executor.setTaskDecorator(new MdcTaskDecorator());
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();

		return executor;
	}

	@Bean("threadPoolJobExecutor") // 调度任务线程池
	public ThreadPoolTaskExecutor threadPoolJobExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 获取到服务器的cpu内核
		int core = Runtime.getRuntime().availableProcessors();
		log.info("初始化threadPoolJobExecutor,核心线程数量:{}", core);

		executor.setCorePoolSize(core);
		executor.setMaxPoolSize(core * 3);
		executor.setQueueCapacity(200);
		executor.setThreadNamePrefix("thread-pool-job-");
		executor.setTaskDecorator(new MdcTaskDecorator());
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();

		return executor;
	}

	@Bean("threadPoolBatchExecutor") // 跑批
	public ThreadPoolTaskExecutor threadPoolBatchExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 获取到服务器的cpu内核
		int core = Runtime.getRuntime().availableProcessors();
		log.info("初始化threadPoolJobExecutor,核心线程数量:{}", core);

		executor.setCorePoolSize(core);
		executor.setMaxPoolSize(core * 10);
		executor.setQueueCapacity(500000);
		executor.setThreadNamePrefix("thread-pool-batch-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();

		return executor;
	}

}
