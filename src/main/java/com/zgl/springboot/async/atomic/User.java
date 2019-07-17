package com.zgl.springboot.async.atomic;

/**
 * @author zgl
 * @date 2019/7/17 下午5:31
 */
public class User {
	private String name;
	public volatile int age;

	public User(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}
}