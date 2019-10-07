package com.zgl.springboot.async.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author zgl
 * @date 2019/9/30 上午11:27
 */
public class CallableDemo {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
		Thread thread = new Thread(futureTask);
		thread.start();
		//建议放在最后
		while (!futureTask.isDone()) {
			System.out.println("+++++++++++waiting");
		}
		int result = futureTask.get();
		System.out.println(result);
	}
}

class MyThread1 implements Runnable {
	@Override
	public void run() {

	}
}

class MyThread2 extends Thread {
	@Override
	public void run() {
		super.run();
	}
}

class MyThread implements Callable<Integer> {
	@Override
	public Integer call() throws Exception {
		System.out.println("********* come in callable");
		TimeUnit.SECONDS.sleep(5);
		return 1024;
	}
}