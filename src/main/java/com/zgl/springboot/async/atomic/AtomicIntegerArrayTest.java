package com.zgl.springboot.async.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author zgl
 * @date 2019/7/17 下午5:10
 */
public class AtomicIntegerArrayTest {
	private static int[] value = new int[]{1, 2, 3};
	public static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(value);

	public static void main(String[] args) {
		atomicIntegerArray.getAndSet(0, 0);
		System.out.println(atomicIntegerArray.get(0));
		atomicIntegerArray.getAndAdd(2, 4);
		System.out.println(value[0]);
		System.out.println(atomicIntegerArray.get(2));
		System.out.println(value[2]);
	}
}