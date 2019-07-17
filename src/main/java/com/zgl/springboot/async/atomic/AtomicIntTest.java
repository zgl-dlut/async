package com.zgl.springboot.async.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zgl
 * @date 2019/7/17 下午5:00
 */
public class AtomicIntTest {
	public static AtomicInteger atomicInteger = new AtomicInteger(1);

	public static void main(String[] args) {
		System.out.println(atomicInteger.getAndIncrement());
		System.out.println(atomicInteger.incrementAndGet());
		System.out.println(atomicInteger.get());
	}
}