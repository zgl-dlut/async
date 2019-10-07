package com.zgl.springboot.async.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author zgl
 * @date 2019/9/25 下午5:07
 */
public class CASDemo {

	/**
	 * 1 原理
	 * 谈谈对UnSafe类的理解(底层汇编,CPU原语实现        ==========>习大大从中南海出来,五辆专车必须是连续出现,原子性的,不允许加塞,硬性规定)
	 * CAS自旋,不需要加锁
	 *
	 * 2 缺点
	 * 2.1 循环时间长,开销大,do-while循环,如果CAS失败,会一直进行尝试,没有加锁(保证一致性和并发性),虽然并发性加强,可能会给CPU带来很大的开销
	 * ===>synchronize虽然会导致并发性下降,但是不会重试,最终并发性会降下来
	 *
	 * 2.2 只能保证一个共享变量的原子操作
	 * ===>加锁可以保证多个变量甚至一段代码进行原子操作
	 *
	 * 2.3 引出来ABA问题   ===>狸猫换太子
	 * 重要的前提是需要取出内存中某个时刻的数据并在当下时刻比较并交换,那么在这个时间差会导致数据的变化     ====> 1线程取出的数据是A,需要处理10s,2线程取出的也是A,
	 * 但是操作只需要两秒,先改成了B,接着又改回了A,1线程认为数据没有变化,进行了更新.
	 *
	 * 2.4 原子引用更新
	 *
	 * 2.5 如何规避ABA问题   ===> 时间戳原子引用
	 * 理解原子引用 + 新增一种机制,修改版本号(类似时间戳)
	 */
	public static void main(String[] args) {

		AtomicInteger atomicInteger = new AtomicInteger(5);
		atomicInteger.getAndIncrement();
		System.out.println(atomicInteger.compareAndSet(atomicInteger.get(),7));

		/**
		 * AtomicReference原子引用demo
		 */
		AtomicReference<User> atomicReference = new AtomicReference<>();
		User z3 = new User("z3", 22);
		User l4 = new User("l4", 25);
		atomicReference.set(z3);
		System.out.println(atomicReference.compareAndSet(z3, l4) + "\t" + atomicReference.get().toString());
		System.out.println(atomicReference.compareAndSet(z3, l4) + "\t" + atomicReference.get().toString());

		/**
		 * AtomicStampedReference时间戳原子引用
		 */
	}

}
@Getter
@ToString
@AllArgsConstructor
class User {

	String userName;
	int age;
}

class ABADemo {
	static AtomicReference<Integer> atomicInteger = new AtomicReference<>(100);
	static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 0);
	public static void main(String[] args) {
		new Thread(() -> {
			atomicInteger.compareAndSet(100, 101);
			atomicInteger.compareAndSet(101, 100);
		}, "t1").start();

		new Thread(() -> {
			//暂停1秒钟t2线程,保证t1线程完成1次ABA操作
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println(atomicInteger.compareAndSet(100, 2019) + "\t" + atomicInteger.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "t2").start();

		new Thread(() -> {
			int stamp = atomicStampedReference.getStamp();
			System.out.println(Thread.currentThread().getName() + "\t第1次版本号" + stamp);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
			System.out.println(Thread.currentThread().getName() + "\t第2次版本号" + atomicStampedReference.getStamp());
			atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
			System.out.println(Thread.currentThread().getName() + "\t第3次版本号" + atomicStampedReference.getStamp());
		}, "t3").start();


		new Thread(() -> {
			int stamp = atomicStampedReference.getStamp();
			System.out.println(Thread.currentThread().getName() + "\t第1次版本号" + stamp);
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolean result = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
			System.out.println(Thread.currentThread().getName() + "\t修改成功否" + result + "\t当前最新实际版本号" + atomicStampedReference.getStamp());
		}, "t4").start();
	}
}