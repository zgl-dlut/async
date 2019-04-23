package com.zgl.springboot.async.service.impl;

import com.zgl.springboot.async.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zgl
 * @date 2019/4/23 下午2:39
 */
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

	@Async(value = "asyncPoolTaskExecutor")
	@Override
	public void asyncEvent() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("异步方法内部线程名称：{}!", Thread.currentThread().getName());
		log.info("{}",System.currentTimeMillis());
	}

	@Override
	public void syncEvent() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("异步方法内部线程名称：{}!", Thread.currentThread().getName());
		log.info("{}",System.currentTimeMillis());
	}

	@Override
	public void autoSyncEvent() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("异步方法内部线程名称：{}!", Thread.currentThread().getName());
		log.info("{}",System.currentTimeMillis());
	}
}