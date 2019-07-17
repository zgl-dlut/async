package com.zgl.springboot.async.blocking;

/**
 * @author zgl
 * @date 2019/7/17 下午2:31
 */
public class AbstractConsumer implements Consumer, Runnable {
	@Override
	public void consume() throws InterruptedException {

	}

	@Override
	public void run() {
		while (true) {
			try {
				consume();
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}