package com.zgl.springboot.async.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zgl
 * @date 2019/9/29 下午3:20
 *
 * 在多线程领域:所谓阻塞,在某些情况下会挂起线程(即阻塞wait()),一旦条件满足,被挂起的线程又会自动被唤醒
 *
 * ArrayBlockingQueue 基于数组的有界阻塞队列,FIFO
 * LinkedBlockingQueue 基于链表的(默认)无界阻塞队列 FIFO, 吞吐量通常高于ArrayBlockingQueue
 * SynchronousQueue 不存储元素的阻塞队列,每个插入操作必须等到另一个线程调用移除操作,否则插入操作一直处于阻塞状态, 吞吐量通常高于LinkedBlockingQueue
 *
 * 阻塞队列(手动挡换成自动挡 ========> 无需关心wait, notify)
 * 1 阻塞队列有没有好的一面
 * 2 不得不阻塞,你如何管理
 */
public class BlockingQueueDemo {

	public static void main(String[] args) {
		throwException();
		noThrowException();
		blocking();
		overtime();
	}

	public static void overtime() {
		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
		try {
			System.out.println(blockingQueue.offer("a", 2, TimeUnit.SECONDS));
			System.out.println(blockingQueue.offer("a", 2, TimeUnit.SECONDS));
			System.out.println(blockingQueue.offer("a", 2, TimeUnit.SECONDS));
			/**
			 * 队列满的时候,只阻塞一段时间,插入不成功返回false
			 */
			System.out.println(blockingQueue.offer("a", 2, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void blocking() {
		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
		try {
			/**
			 * 队列满的时候,put阻塞
			 */
			blockingQueue.put("a");
			blockingQueue.put("a");
			blockingQueue.put("a");
			System.out.println("=============");
			//blockingQueue.put("x");
			/**
			 * 队列空的时候,take阻塞
			 */
			blockingQueue.take();
			blockingQueue.take();
			blockingQueue.take();
			blockingQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void noThrowException() {
		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

		/**
		 * offer队列满时,返回false,不抛异常
		 */
		System.out.println(blockingQueue.offer("a"));
		System.out.println(blockingQueue.offer("b"));
		System.out.println(blockingQueue.offer("c"));
		System.out.println(blockingQueue.offer("a"));

		/**
		 * 返回队列头部
		 */
		System.out.println(blockingQueue.peek());
		/**
		 * 队列为空进行poll操作,返回null,不抛出异常
		 */
		System.out.println(blockingQueue.poll());
		System.out.println(blockingQueue.poll());
		System.out.println(blockingQueue.poll());
		System.out.println(blockingQueue.poll());
	}

	public static void throwException() {
		//List list = new ArrayList<>();
		/**
		 * 当队列满时,调用add方法会抛出队列满的异常
		 * Exception in thread "main" java.lang.IllegalStateException: Queue full
		 */
		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
		System.out.println(blockingQueue.add("a"));
		System.out.println(blockingQueue.add("b"));
		System.out.println(blockingQueue.add("c"));

		/**
		 * 队首元素
		 */
		System.out.println(blockingQueue.element());
		/**
		 * 队列为空,再往队列remove时抛出异常
		 * Exception in thread "main" java.util.NoSuchElementException
		 */
		System.out.println(blockingQueue.remove());
		System.out.println(blockingQueue.remove());
		System.out.println(blockingQueue.remove());
		//System.out.println(blockingQueue.remove());
	}
}