package com.zgl.springboot.async.demo;

/**
 * @author zgl
 * @date 2020/1/28 下午9:39
 */
public class SynchronizedLockDemo implements Runnable {

	private final String lock1 = "lock1";
	private final String lock2 = "lock2";
	private volatile int lock;

	public SynchronizedLockDemo(int lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		try {
			if (lock == 1) {
				System.out.println(Thread.currentThread().getName() + "尝试获取lock1");
				synchronized (lock1) {
					System.out.println("lock1被成功获取" + Thread.currentThread().getName());
					Thread.sleep(500);
					synchronized (lock2) {
						System.out.println("lock2被成功获取" + Thread.currentThread().getName());
					}
					System.out.println("t1执行完毕");
				}
			} else {
				System.out.println(Thread.currentThread().getName() + "尝试获取lock2");
				synchronized (lock2) {
					System.out.println("lock2被成功获取" + Thread.currentThread().getName());
					Thread.sleep(500);
					synchronized (lock1) {
						System.out.println("lock1被成功获取" + Thread.currentThread().getName());
					}
				}
				System.out.println("t2执行完毕");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println(Thread.currentThread().getName() + ": 线程退出");
		}
	}


	public static void main(String[] args) throws InterruptedException {

		SynchronizedLockDemo r1 = new SynchronizedLockDemo(1);
		SynchronizedLockDemo r2 = new SynchronizedLockDemo(2);
		Thread t1 = new Thread(r1);
		t1.setName("t1");
		Thread t2 = new Thread(r2);
		t2.setName("t2");
		t1.start();
		t2.start();
		Thread.sleep(1000);
		// 中断其中一个线程（只有线程在等待锁的过程中才有效）
		// 如果线程已经拿到了锁，中断是不起任何作用的。
		// 注意：这点 synchronized 是不能实现此功能的，synchronized 在等待过程中无法中断
		//t2.interrupt();
		// t2 线程中断，抛出异常，并放开锁。没有完成任务
		// t1 顺利完成任务。
	}
}