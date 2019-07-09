package com.zgl.springboot.async.thread;

/**
 * @author zgl
 * @date 2019/7/8 下午10:08
 */
public class StartAndRun {
	public static void  main(String[] args) {
		Thread t1 = new Thread(() -> print());
		System.out.println(Thread.currentThread().getName());
		t1.run();
		Thread t2 = new Thread(() -> print());
		t2.start();
	}

	static void print(){
		System.out.println(Thread.currentThread().getName());
	}
}