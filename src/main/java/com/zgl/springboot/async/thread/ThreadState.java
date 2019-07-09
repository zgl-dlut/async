package com.zgl.springboot.async.thread;

/**
 * @author zgl
 * @date 2019/7/8 下午9:52
 */
public class ThreadState {

	//等待Waiting类的实例
	static class Waiting implements Runnable{
		@Override
		public void run() {
			while (true) {
				synchronized (Waiting.class) {
					try {
						Waiting.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	//线程不断地休眠
	static class TimeWaiting implements Runnable {
		@Override
		public void run() {
			while (true) {
				SleepUtils.second(100);
			}
		}
	}

	//在Blocked实例加上锁后,不会释放该锁
	static class Blocked implements Runnable {
		@Override
		public void run() {
			synchronized (Blocked.class) {
				while (true) {
					SleepUtils.second(100);
				}
			}
		}
	}

	public static void main(String[] args) {
		new Thread(new Waiting(), "Waiting thread").start();
		new Thread(new TimeWaiting(), "TimeWaiting thread").start();
		new Thread(new Blocked(), "Blocked-1 thread").start();
		new Thread(new Blocked(), "Blocked-2 thread").start();
	}
}