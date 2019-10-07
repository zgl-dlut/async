package com.zgl.springboot.async.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zgl
 * @date 2019/9/27 下午5:44
 */
public class ReenterLockDemo {

	/**
	 * 可重入锁指的是同一个线程外层函数获得锁之后,内层递归函数仍然能获取该锁的代码
	 * 在同一个线程在外层方法获得锁的时候,在进入内层方法会自动获取锁
	 */
	public static void main(String[] args) {

		Phone phone = new Phone();
		new Thread(() -> {
			try {
				phone.sendSMS();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "t1").start();

		new Thread(() -> {
			try {
				phone.sendSMS();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "t2").start();

		new Thread(() -> {
			try {
				phone.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "t3").start();

		new Thread(() -> {
			try {
				phone.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "t4").start();
	}
}

class Phone implements Runnable {
	public synchronized void sendSMS() throws Exception {
		System.out.println(Thread.currentThread().getName() + "\t invoke sendSMS()");
		sendMail();
	}

	public synchronized void sendMail() throws Exception {
		System.out.println(Thread.currentThread().getName() + "\t invoke sendMail()");
	}

	private Lock reentrantLock = new ReentrantLock();

	@Override
	public void run() {
		get();
	}
	public void get() {
		reentrantLock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t invoke get()");
			set();
		} finally {
			reentrantLock.unlock();
		}
	}

	public void set() {
		reentrantLock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t invoke set()");
		} finally {
			reentrantLock.unlock();
		}
	}
}