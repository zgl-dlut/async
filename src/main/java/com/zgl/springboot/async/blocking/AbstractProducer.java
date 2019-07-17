package com.zgl.springboot.async.blocking;

/**
 * @author zgl
 * @date 2019/7/17 下午2:33
 */
public class AbstractProducer implements Producer, Runnable {
	@Override
	public void produce() throws InterruptedException {

	}

	@Override
	public void run() {
		while (true) {
			try {
				produce();
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}