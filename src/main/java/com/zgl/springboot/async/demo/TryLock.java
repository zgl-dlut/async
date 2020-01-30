package com.zgl.springboot.async.demo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zgl
 * @date 2020/1/28 下午10:16
 */
public class TryLock implements Runnable {
	static ReentrantLock lock1 = new ReentrantLock();
	static ReentrantLock lock2 = new ReentrantLock();
	int lock;

	public TryLock(int lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		// 线程1
		if (lock == 1) {
			while (true) {
				// 获取1的锁
				if (lock1.tryLock()) {
					try {
						// 尝试获取2的锁
						if (lock2.tryLock()) {
							try {
								System.out.println(Thread.currentThread().getName() + " : My Job done");
								return;
							} finally {
								lock2.unlock();
							}
						} else {
							System.out.println(Thread.currentThread().getName() + " 重新尝试获取lock2");
						}
					} finally {
						lock1.unlock();
					}
				} else {
					System.out.println(Thread.currentThread().getName() + " 重新尝试获取lock1");
				}
			}
		} else {
			// 线程2
			while (true) {
				// 获取2的锁
				if (lock2.tryLock()) {
					try {
						// 尝试获取1的锁
						if (lock1.tryLock()) {
							try {
								System.out.println(Thread.currentThread().getName() + ": My Job done");
								return;
							} finally {
								lock1.unlock();
							}
						} else {
							System.out.println(Thread.currentThread().getName() + " 重新尝试获取lock1");
						}
					} finally {
						lock2.unlock();
					}
				} else {
					System.out.println(Thread.currentThread().getName() + " 重新尝试获取lock2");
				}
			}
		}
	}

	/**
	 * 这段代码如果使用 synchronized 肯定会引起死锁，但是由于使用 tryLock，他会不断的尝试， 当第一次失败了，他会放弃，然后执行完毕，并释放外层的锁，这个时候就是
	 * 另一个线程抢锁的好时机。
	 * @param args
	 */
	public static void main(String[] args) {
		TryLock r1 = new TryLock(1);
		TryLock r2 = new TryLock(2);
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.setName("t1");
		t2.setName("t2");
		t1.start();
		t2.start();
	}
}