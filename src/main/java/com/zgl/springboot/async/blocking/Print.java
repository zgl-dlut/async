package com.zgl.springboot.async.blocking;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zgl
 * @date 2020/2/16 下午3:50
 */
class Print {

	public static void main(String[] args) {
		ShareData shareData = new ShareData();
		for (; ;) {
			new Thread(() -> shareData.printOdd(), "printer1").start();
			new Thread(() -> shareData.printEven(),"printer2").start();
		}
	}
}
class ShareData {
	private AtomicInteger ai = new AtomicInteger(1);
	private Lock lock = new ReentrantLock();
	public ShareData() {}
	private Condition condition1 = lock.newCondition();
	private Condition condition2 = lock.newCondition();
	public void printOdd() {
		lock.lock();
		try {
			if (ai.get() > 100) {
				return;
			}
			while (ai.get() % 2 == 0) {
				condition1.await();
			}
			System.out.println(Thread.currentThread().getName() + "-" + ai.get());
			ai.incrementAndGet();
			condition2.signal();
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
	}

	public void printEven() {
		lock.lock();
		try {
			if (ai.get() > 100) {
				return;
			}
			while (ai.get() % 2 != 0) {
				condition2.await();
			}
			System.out.println(Thread.currentThread().getName() + "-" + ai.get());
			ai.incrementAndGet();
			condition1.signal();
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
	}
}