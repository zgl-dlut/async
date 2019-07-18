package com.zgl.springboot.async.executor;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author zgl
 * @date 2019/7/18 下午1:57
 */
public class CyclicBarrierTest implements Runnable {

	private CyclicBarrier cyclicBarrier = new CyclicBarrier(20, this);
	private Executor executor = Executors.newFixedThreadPool(30);
	private static ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
	private void count() {
		for (int i = 0; i < 20; i++) {
			executor.execute(() -> {
				concurrentHashMap.put(Thread.currentThread().getName(), 1);
				try {
					cyclicBarrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			});
		}
	}




	@Override
	public void run() {
		int count = 0;
		for (Map.Entry<String, Integer> entry : concurrentHashMap.entrySet()) {
			count += entry.getValue();
		}
		System.out.println(concurrentHashMap);
		System.out.println(count);
	}

	public static void main(String[] args) {
		new CyclicBarrierTest().count();
	}
}