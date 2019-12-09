package com.zgl.springboot.async.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 定义一个事件监听者,实现ApplicationListener接口，重写 onApplicationEvent() 方法
 * @author zgl
 * @date 2019/12/9 下午2:14
 */
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {

	@Override
	public void onApplicationEvent(DemoEvent event) {
		String msg = event.getMessage();
		System.out.println("接收到的信息是："+msg);
	}
}