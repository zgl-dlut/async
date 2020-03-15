package com.zgl.springboot.async.limit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author zgl
 * @date 2020/3/15 下午2:38
 */
public class SemaphoreLimit {

	private static ExecutorService executorService = Executors.newCachedThreadPool();

	public static void main(String[] args) {
		//定义一个信号量，限制3个同时执行线程
		Semaphore semaphore = new Semaphore(10);
		//模拟多线程
		for (int i = 0; i < 10; i++) {
			executorService.submit(() -> {
				try {
					//尝试获取信号量
					semaphore.acquire();
					System.out.println(Thread.currentThread().getName()+":开始执行");
					//模拟负责业务操作-休眠2秒
					TimeUnit.SECONDS.sleep(2);
					System.out.println(Thread.currentThread().getName()+":执行完成");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					//释放信号量
					semaphore.release();
					System.out.println(Thread.currentThread().getName()+":----------释放");
				}

			});
		}
	}
}