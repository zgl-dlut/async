package com.zgl.springboot.async.demo;

import java.util.concurrent.CountDownLatch;

/**
 * @author zgl
 * @date 2019/9/29 下午2:02
 *
 *
 * 让一些线程阻塞直到另一些线程完成一系列操作后才被唤醒
 *
 * 主要有两个方法,当一个或多个线程调用await()方法时,调用线程会被阻塞,其他线程调用countDown()方法会将计数器减1(调用countDown方法的线程不会被阻塞),
 * 当计数器的值变为0时,因调用await()方法被阻塞的线程会被唤醒,继续执行.
 */
public class CountDownLatchDemo {

	public static void main(String[] args) {

	}


	public static void closeDoor() {
		CountDownLatch countDownLatch = new CountDownLatch(6);

		for (int i = 0; i < 6; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "\t上完自习,离开教室");
				countDownLatch.countDown();
			}, String.valueOf(i)).start();
		}
		try {
			//从6减到0,await解除
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "\t =============>班长最后关门走人");
	}
}