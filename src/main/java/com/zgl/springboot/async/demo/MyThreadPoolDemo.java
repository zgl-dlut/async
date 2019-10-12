package com.zgl.springboot.async.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zgl
 * @date 2019/10/7 下午8:55
 */
public class MyThreadPoolDemo {

	/**
	 * 第四种使用Java多线程方法:线程池(继承Thread 实现Runnable 实现Callable)
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * 查看核数
		 */
		System.out.println(Runtime.getRuntime().availableProcessors());
		ExecutorService threadPool = Executors.newFixedThreadPool(5);
		try {
			//模拟10个用户进行请求
			for (int i = 0; i < 10; i++) {
				threadPool.execute(() -> {
					System.out.println(Thread.currentThread().getName() + "\t办理业务");
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}