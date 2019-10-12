package com.zgl.springboot.async.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zgl
 * @date 2019/10/7 下午7:54
 */
public class SyncAndReentrantLockDemo {

	/**
	 * 题目:多线程之间调用问题,实现A->B->C线程启动,要求如下
	 * AA打印5次 BB打印10次 CC打印15次
	 * 来10轮
	 */
	public static void main(String[] args) {

		ShareResource shareResource = new ShareResource();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				shareResource.print5();
			}
		}, "AA").start();

		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				shareResource.print10();
			}
		}, "BB").start();

		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				shareResource.print15();
			}
		}, "CC").start();
	}

}

class ShareResource {
	/**
	 * AA1 BB2 CC3
	 */
	private int number = 1;

	private Lock lock = new ReentrantLock();
	private Condition c1 = lock.newCondition();
	private Condition c2 = lock.newCondition();
	private Condition c3 = lock.newCondition();

	public void print5() {
		lock.lock();
		try {
			//1 判断
			while (number != 1) {
				c1.await();
			}
			//2 干活
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i);
			}
			//3 通知
			number = 2;
			c2.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void print10() {
		lock.lock();
		try {
			//1 判断
			while (number != 2) {
				c2.await();
			}
			//2 干活
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i);
			}
			//3 通知
			number = 3;
			c3.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void print15() {
		lock.lock();
		try {
			//1 判断
			while (number != 3) {
				c3.await();
			}
			//2 干活
			for (int i = 0; i < 15; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i);
			}
			//3 通知
			number = 1;
			c1.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

}