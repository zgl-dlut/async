package com.zgl.springboot.async.event;

import org.springframework.context.ApplicationEvent;

/**
 * 定义一个事件,继承自ApplicationEvent并且写相应的构造函数
 * @author zgl
 * @date 2019/12/9 下午1:56
 */
public class DemoEvent extends ApplicationEvent {

	private String message;

	public DemoEvent(Object source, String message) {
		super(source);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}