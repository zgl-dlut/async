package com.zgl.springboot.async.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zgl
 * @date 2019/9/29 下午4:46
 *
 * 题目:一个初始值为0的变量,两个线程对其交替操作,一个加1,一个减1,来五轮
 *
 * 1 线程 操作(方法) 资源类
 * 2 判断 干活 通知
 * 3 防止虚假判断机制
 */
public class ProducerConsumerTraditionDemo {

	public static void main(String[] args) {
		//LockVersion();
		waitNotifyVersion();
	}

	public static void waitNotifyVersion() {
		WaitNotifyVersion shareData = new WaitNotifyVersion();
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				try {
					shareData.increment();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "AA").start();

		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				try {
					shareData.decrement();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "BB").start();
	}

	public static void LockVersion() {
		ShareData shareData = new ShareData();
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				try {
					shareData.increment();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "AA").start();

		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				try {
					shareData.decrement();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "BB").start();
	}
}

/**
 * 资源类
 */
class ShareData {
	private int number = 0;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	public void increment() throws Exception {
		lock.lock();
		try {
			//1 判断,多线程必须用while判断(除非是两个可以用if)
			while (number != 0) {
				// 等待, 不能生产
				condition.await();
			}
			//2 干活
			number++;
			System.out.println(Thread.currentThread().getName() + "\t" + number);
			//3 通知唤醒
			condition.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void decrement() throws Exception {
		lock.lock();
		try {
			//1 判断
			while (number == 0) {
				// 等待, 不能生产
				condition.await();
			}
			//2 干活
			number--;
			System.out.println(Thread.currentThread().getName() + "\t" + number);
			//3 通知唤醒
			condition.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}

class WaitNotifyVersion {
	private final Object LOCK = new Object();
	private int number = 0;

	public void increment() {
		synchronized (LOCK) {
			try {
				while (number != 0) {
					LOCK.wait();
				}
				number++;
				System.out.println(Thread.currentThread().getName() + "\t" + number);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				LOCK.notifyAll();
			}
		}
	}

	public void decrement() {
		synchronized (LOCK) {
			try {
				while (number == 0) {
					LOCK.wait();
				}
				number--;
				System.out.println(Thread.currentThread().getName() + "\t" + number);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				LOCK.notifyAll();
			}
		}
	}
}