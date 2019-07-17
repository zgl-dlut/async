package com.zgl.springboot.async.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author zgl
 * @date 2019/7/17 下午5:32
 */
public class AtomicIntegerFieldUpdateTest {

	public static AtomicIntegerFieldUpdater<User> updater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

	public static void main(String[] args) {
		User user = new User("James Harden", 29);
		updater.getAndIncrement(user);
		System.out.println(user.getAge());
		System.out.println(updater.get(user));
	}
}