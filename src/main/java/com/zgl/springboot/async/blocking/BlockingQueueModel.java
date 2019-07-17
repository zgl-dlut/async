package com.zgl.springboot.async.blocking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zgl
 * @date 2019/7/17 下午2:37
 */
public class BlockingQueueModel implements Model{

	private final BlockingQueue<Task> queue;
	private static final AtomicInteger taskNo = new AtomicInteger(0);
	public BlockingQueueModel(int cap) {
		this.queue = new LinkedBlockingQueue<>(cap);
	}
	@Override
	public Runnable newRunnableProducer() {
		return new ProducerImpl();
	}

	@Override
	public Runnable newRunnableConsumer() {
		return new ConsumerImpl();
	}

	private class ProducerImpl extends AbstractProducer implements Producer, Runnable {
		@Override
		public void produce() throws InterruptedException {
			Thread.sleep(1000 + (long) (Math.random() * 1000));
			Task task = new Task(taskNo.getAndIncrement());
			queue.put(task);
			System.out.println("produce: " + task.no);
		}

		@Override
		public void run() {
			super.run();
		}
	}

	private class ConsumerImpl extends AbstractConsumer implements Consumer, Runnable {
		@Override
		public void consume() throws InterruptedException {
			Task task = queue.take();
			Thread.sleep(500 + (long) (Math.random() * 500));
			System.out.println("consume: " + task.no);
		}

		@Override
		public void run() {
			super.run();
		}
	}

	public static void main(String[] args) {
		Model model = new BlockingQueueModel(5);
		for (int i = 0; i < 5; i++) {
			new Thread(model.newRunnableProducer()).start();
		}
		for (int i = 0; i < 2; i++) {
			new Thread(model.newRunnableConsumer()).start();
		}
	}
}