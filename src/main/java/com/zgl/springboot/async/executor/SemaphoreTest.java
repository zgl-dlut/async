package com.zgl.springboot.async.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author zgl
 * @date 2019/7/18 下午2:13
 */
public class SemaphoreTest {

	private static final int THREAD_COUNT = 30;
	private static ExecutorService executorPool = Executors.newFixedThreadPool(THREAD_COUNT);
	private static Semaphore semaphore = new Semaphore(10);

	public static void main(String[] args) {
		for (int i = 0; i < THREAD_COUNT; i++) {
			executorPool.execute(() -> {
				try {
					semaphore.acquire();
					System.out.println("信号量当前可用许可证数:" + semaphore.availablePermits());
					System.out.println("save data");
					semaphore.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
	}
}