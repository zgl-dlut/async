package com.zgl.springboot.async.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zgl
 * @date 2019/7/17 下午5:18
 */
public class AtomicReferenceTest {

	public static AtomicReference<User> atomicReference = new AtomicReference<>();

	public static void main(String[] args) {
		User user = new User("zgl", 26);
		atomicReference.set(user);
		User newUser = new User("zyy", 28);
		User fakeUser = new User("james", 30);
		System.out.println(atomicReference.get().getAge());
		System.out.println(atomicReference.get().getName());
		System.out.println(atomicReference.compareAndSet(user, newUser));
		System.out.println(atomicReference.compareAndSet(fakeUser, newUser));
		System.out.println(atomicReference.get().getAge());
		System.out.println(atomicReference.get().getName());
	}
}