package com.zgl.springboot.async.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author zgl
 * @date 2019/12/9 下午2:15
 */
@Component
public class DemoPublisher {

	@Autowired
	private ApplicationContext applicationContext;

	public void publish(String message) {
		applicationContext.publishEvent(new DemoEvent(this, message));
	}
}