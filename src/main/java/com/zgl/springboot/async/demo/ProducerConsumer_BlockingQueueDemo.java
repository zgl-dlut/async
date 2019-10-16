package com.zgl.springboot.async.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zgl
 * @date 2019/9/30 上午10:11
 *
 * volatile/CAS/atomicInteger/BlockingQueue
 */
public class ProducerConsumer_BlockingQueueDemo {

	public static void main(String[] args) throws Exception{
		MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));
		new Thread(() ->{
			System.out.println(Thread.currentThread().getName() + "\t 生产线程启动");
			try {
				myResource.myProducer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		},"producer").start();

		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "\t 消费线程启动");
			try {
				myResource.myConsumer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "consumer").start();

		TimeUnit.SECONDS.sleep(10);
		System.out.println("10秒种时间到, 大老板main叫停,活动结束");
		myResource.stop();
	}
}

class MyResource {

	/**
	 * 默认开启,进行生产+消费
	 */
	private volatile boolean FLAG = true;
	private AtomicInteger atomicInteger = new AtomicInteger();

	private BlockingQueue<String> blockingQueue;

	/**
	 * 注意传的是接口
	 * @param blockingQueue
	 */
	public MyResource(BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
		System.out.println(blockingQueue.getClass().getName());
	}

	public void myProducer() throws Exception{
		String data;
		boolean returnVal;
		while (FLAG) {
			//++i
			data = atomicInteger.incrementAndGet() + "";
			returnVal = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
			if (returnVal) {
				System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "成功");
			} else {
				System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "失败");
			}
			TimeUnit.SECONDS.sleep(1);
		}
		System.out.println(Thread.currentThread().getName() + "\t大老板叫停,表示FLAG=false,生产动作结束");
	}

	public void myConsumer() throws Exception {
		String result;
		while (FLAG) {
			result = blockingQueue.poll(2L, TimeUnit.SECONDS);
			if (null == result || "".equalsIgnoreCase(result))  {
				FLAG = false;
				System.out.println(Thread.currentThread().getName() + "\t 超过2秒钟没有取到,消费动作结束");
				return;
			}
			System.out.println(Thread.currentThread().getName() + "\t 消费队列" + result + "成功");
		}
	}

	public void stop() {
		this.FLAG = false;
	}
}