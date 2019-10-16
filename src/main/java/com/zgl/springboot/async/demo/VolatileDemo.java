package com.zgl.springboot.async.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zgl
 * @date 2019/9/25 下午2:14
 */

/**
 * 1 验证volatile的可见性
 * 1.1 假如int number = 0, number变量之前根本没有添加volatile关键字修饰,没有可见性
 * 1.2 添加volatile可以解决可见性问题
 *
 * 2 验证volatile不保证可见性
 * 2.1 原子性指什么?
 *     不可分割,完整性,也即某个线程在做某个业务时,中间不可以被加塞或者被分割,需要完整性
 *     要么同时成功,要么同时失败
 * 2.2 volatile不保证原子性案例
 *
 * 2.3 why?
 */
public class VolatileDemo {
	public static void main(String[] args) {
		seeOkByVolatile();
		//atomicNotOkByVolatile();
	}

	/**
	 * volatile不保证原子性案例
	 */
	public static void atomicNotOkByVolatile() {
		MyData myData = new MyData();
		for (int i = 0 ; i < 20; i++) {
			new Thread(() -> {
				for (int j = 0; j < 1000; j++) {
					myData.addMyAtomic();
				}
			}, String.valueOf(i)).start();
		}

		//需要等待上面20个线程全部计算完成后, 再用main线程取得最终的结果值是多少?
		//暂停一会
		//默认main线程和gc线程
		while (Thread.activeCount() > 2) {
			Thread.yield();
		}
		System.out.println(Thread.currentThread().getName() + "\t finally number value :" + myData.atomicInteger.get());
	}


	/**
	 * volatile可以保证可见性,及时通知其他线程,主物理内存已经修改
	 */
	public static void seeOkByVolatile() {
		MyData myData = new MyData();
		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "\t come in");
			try {
				//线程暂停
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			myData.addT060();
			System.out.println(Thread.currentThread().getName() + "\t updated number value :" + myData.number);
		}, "zgl").start();

		while (myData.number == 0) {
			//main线程就一直循环等待,直到number值不再等于0
		}
		System.out.println(Thread.currentThread().getName() + "\t mission is over, main get number value:" + myData.number);
	}
}
class MyData  //MyData.java ===> MyData.class  ===> JVM字节码
{
	 volatile int number = 0;
	AtomicInteger atomicInteger = new AtomicInteger();
	public void addT060() {
		this.number = 60;
	}

	/**
	 * 此时number前面加上了volatile关键字了
	 */
	public /*synchronized*/ void addPlusPlus() {
		number++;
	}

	public void addMyAtomic() {
		atomicInteger.getAndIncrement();
	}
}