package com.zgl.springboot.async.demo;

/**
 * @author zgl
 * @date 2019/9/25 下午4:38
 */
public class SingletonDemo {

	private volatile static SingletonDemo instance;

	private SingletonDemo(){}

	//DCL版单例模式
	public static SingletonDemo getInstance() {
		if (instance == null) {
			synchronized (SingletonDemo.class) {
				if (instance == null) {
					instance = new SingletonDemo();
				}
			}
		}
		return instance;
	}
}