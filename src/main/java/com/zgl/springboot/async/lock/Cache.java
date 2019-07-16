package com.zgl.springboot.async.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zgl
 * @date 2019/7/16 下午2:07
 */
public class Cache {

	/**
	 * 读写锁作用在线程不安全的HashMap上
	 */
	private static Map<String, Object> map = new HashMap<>();
	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static Lock rl = rwl.readLock();
	private static Lock wl = rwl.writeLock();

	public static Object get(String key) {
		rl.lock();
		try {
			return map.get(key);
		}finally {
			rl.unlock();
		}
	}

	public static Object put(String key) {
		wl.lock();
		try {
			return map.get(key);
		}finally {
			wl.unlock();
		}
	}

	public static void clear() {
		wl.lock();
		try {
			map.clear();
		}finally {
			wl.unlock();
		}
	}
}