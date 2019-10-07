package com.zgl.springboot.async.demo;

import org.apache.tomcat.jni.Time;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zgl
 * @date 2019/9/29 下午4:13
 *
 * 不存储元素的阻塞队列
 * 每一个put都等待一个take
 */
public class SynchronousQueueDemo {

	public static void main(String[] args) {

		BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

		new Thread(() -> {
			try {
				System.out.println(Thread.currentThread().getName() + "\t put 1");
				blockingQueue.put("1");
				System.out.println(Thread.currentThread().getName() + "\t put 2");
				blockingQueue.put("2");
				System.out.println(Thread.currentThread().getName() + "\t put 3");
				blockingQueue.put("3");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "AAA").start();

		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(5);
				System.out.println(Thread.currentThread().getName() + "\t" + blockingQueue.take());
				TimeUnit.SECONDS.sleep(5);
				System.out.println(Thread.currentThread().getName() + "\t" + blockingQueue.take());
				TimeUnit.SECONDS.sleep(5);
				System.out.println(Thread.currentThread().getName() + "\t" + blockingQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "BBB").start();
	}
}