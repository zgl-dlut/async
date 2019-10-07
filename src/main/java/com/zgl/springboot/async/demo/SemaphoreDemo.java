package com.zgl.springboot.async.demo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author zgl
 * @date 2019/9/29 下午3:05
 *
 * 信号量(争车位) 小米秒杀
 *
 * 信号量主要用于两个目的,一个是用于多个共享资源的互斥使用,另一个用于并发线程的控制
 *
 * 聚集了countDowLatch的减和CyclicBarrier的加
 */
public class SemaphoreDemo {

	public static void main(String[] args) {
		//3个车位
		Semaphore semaphore = new Semaphore(3);
		for (int i = 0; i < 6; i++) {
			new Thread(() -> {
				try {
					semaphore.acquire();
					System.out.println(Thread.currentThread().getName() + "\t抢到车位");
					TimeUnit.SECONDS.sleep(3);
					System.out.println(Thread.currentThread().getName() + "\t停车三秒后离开车位");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					semaphore.release();
				}
			}, String.valueOf(i)).start();
		}
	}

 }