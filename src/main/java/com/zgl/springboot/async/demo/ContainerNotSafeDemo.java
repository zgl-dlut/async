package com.zgl.springboot.async.demo;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zgl
 * @date 2019/9/27 上午11:27
 */
public class ContainerNotSafeDemo {
	/**
	 * 集合类不安全的问题ArrayList HashSet HashMap
	 */
	public static void main(String[] args) {
		ListNotSafe();
	}

	/**
	 * ArrayList
	 */
	public static void ListNotSafe() {
		List<String> list =
				/*new Vector<>()*/
				new ArrayList<>()
				/*Collections.synchronizedList(new ArrayList<>())*/
				/*new CopyOnWriteArrayList<>()*/;
		for (int i = 0; i < 30; i++) {
			new Thread(() -> {
				list.add(UUID.randomUUID().toString().substring(0, 8));
				/**
				 * System.out.println(list)通过iterator进行迭代器遍历,如果有并发修改就会报错
				 */
				System.out.println(list);
			}, String.valueOf(i)).start();
		}
		while (Thread.activeCount() > 2) {
			Thread.yield();
		}
		System.out.println("========================");
		list.forEach(System.out::print);

		/**
		 * 1 故障现象
		 *   java.util.ConcurrentModificationException
		 *
		 * 2 导致原因
		 *   并发修改导致,一个正在线程写,另一个线程发生争抢
		 *
		 * 3 解决方案
		 *   3.1 用Vector????(jdk1.0,当然加锁了)   ArrayList(jdk1.2 牺牲安全性来提高并发性)
		 *   3.2 用Collections.synchronizedList(new ArrayList<>());
		 *   3.3 用CopyOnWriteList(写时复制,读写分离的思想)
		 *
		 * 4 优化建议
		 * */
		/*List<Integer> integerList =new ArrayList<>();
		integerList.add(1);
		integerList.add(2);
		integerList.add(3);
		ListIterator iterator = integerList.listIterator();
		while (iterator.hasNext()) {
			Integer integer = (Integer) iterator.next();
			if (integerList.contains(3)) {
				integerList.remove(3);
				*//*iterator = integerList.listIterator();*//*
			}
		}*/
	}
}