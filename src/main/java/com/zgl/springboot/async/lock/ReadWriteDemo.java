package com.zgl.springboot.async.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zgl
 * @date 2019/9/29 上午11:15
 *
 * 多个线程同时读一个资源类没有任何问题,所以为了满足并发性,读取共享变量资源应该可以同时进行
 * 但是
 * 如果有一个线程去写共享资源,就不应该再有其他线程可以对该资源及您读或者写
 * 小总结:
 *     读-读共存
 *     读-写不共存
 *     写-写不共存
 *
 *     写操作:原子 + 独占,整个过程必须是一个完整的统一体,中间不许被分割,被打断
 */
public class ReadWriteDemo {

	public static void main(String[] args) {
		MyCache myCache = new MyCache();
		for (int i = 0; i < 5; i++) {
			final int temp = i;
			new Thread(() -> {
				myCache.put(temp + "", temp +"");
			}, String.valueOf(temp)).start();
		}

		for (int i = 0; i < 5; i++) {
			final int temp = i;
			new Thread(() -> {
				myCache.get(temp + "");
			}, String.valueOf(temp)).start();
		}
	}
}

class MyCache {
	private volatile Map<String, Object> map = new HashMap<>();
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

	public void put(String key, Object value) {

		rwLock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t 正在写入" + key);
			try {
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			map.put(key, value);
			System.out.println(Thread.currentThread().getName() + "\t 写入完成");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	public void get(String key) {
		rwLock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t 正在读取");
			try {
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Object value = map.get(key);
			System.out.println(Thread.currentThread().getName() + "\t 读取完成" + value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rwLock.readLock().unlock();
		}
	}

	public void clear() {
		map.clear();
	}
}