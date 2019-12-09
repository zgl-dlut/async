package com.zgl.springboot.async.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zgl
 * @date 2019/12/9 下午2:16
 */
public class DemoTest {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.zgl.springboot.async.event");
		DemoPublisher demoPublisher = (DemoPublisher)applicationContext.getBean("demoPublisher");
		demoPublisher.publish("zgl准备发布消息啦!");
	}
}