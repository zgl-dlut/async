package com.zgl.springboot.async.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zgl
 * @date 2019/9/29 上午10:31
 */
public class SpinLockDemo {

	AtomicReference<Thread> atomicReference = new AtomicReference<>();


	/**
	 * 自旋锁是指尝试获取锁的线程不会立即阻塞,而是采用循环的方式去尝试获取锁,这样的好处是减少线程上下文切换的消耗,缺点是循环会消耗CPU
	 */
	public void myLock() {
		Thread thread = Thread.currentThread();
		System.out.println(Thread.currentThread().getName() + "\t come in hiahia");
		while (!atomicReference.compareAndSet(null, thread)) {
			System.out.println(thread.getName() + "\t waiting!!!!!");
		}
	}

	public void unLock() {
		Thread thread = Thread.currentThread();
		atomicReference.compareAndSet(thread, null);
		System.out.println(Thread.currentThread().getName() + "\t invoked myUnLock()");
	}

	public static void main(String[] args) {

		SpinLockDemo spinLockDemo = new SpinLockDemo();
		new Thread(() -> {
			spinLockDemo.myLock();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			spinLockDemo.unLock();
		}, "AA").start();

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		new Thread(() -> {
			spinLockDemo.myLock();
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			spinLockDemo.unLock();
		}, "BB").start();
	}
}