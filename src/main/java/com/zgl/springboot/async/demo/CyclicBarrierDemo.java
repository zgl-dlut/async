package com.zgl.springboot.async.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zgl
 * @date 2019/9/29 下午2:52
 *
 * 集齐七颗龙珠召唤神龙(人到齐了才能开会)与countDownLatch相反
 *
 * 字面意思:可循环使用的屏障,让一组线程到达一个屏障(也可以是同步点)时被阻塞,知道最后一个线程到达该屏障时,
 * 屏障才会开门,所有被屏障拦截的线程才会继续干活,线程进入屏障通过CyclicBarrier的await()方法
 */
public class CyclicBarrierDemo {

	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> System.out.println("+++++++召唤神龙"));
		for (int i = 0; i < 7; i++) {
			final int finalI = i;
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "\t收集到" + finalI + "龙珠");
				try {
					cyclicBarrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}, String.valueOf(i)).start();
		}
	}
}