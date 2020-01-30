package com.zgl.springboot.async.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zgl
 * @date 2020/1/28 下午10:09
 */
public class TimeLockDemo implements Runnable {

	static ReentrantLock lock = new ReentrantLock(false);

	@Override
	public void run() {
		try {
			// 最多等待5秒，超过5秒返回false，若获得锁，则返回true
			if (lock.tryLock(5, TimeUnit.SECONDS)) {
				// 锁住 6 秒，让下一个线程无法获取锁
				System.out.println(Thread.currentThread().getName() + " 锁住 6 秒，让下一个线程无法获取锁");
				Thread.sleep(6000);
			} else {
				System.out.println(Thread.currentThread().getName() + " get lock failed");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		TimeLockDemo tl = new TimeLockDemo();
		Thread t1 = new Thread(tl);
		Thread t2 = new Thread(tl);
		t1.setName("t1");
		t2.setName("t2");
		t1.start();
		t2.start();
	}
}