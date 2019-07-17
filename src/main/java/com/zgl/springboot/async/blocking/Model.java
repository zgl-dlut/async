package com.zgl.springboot.async.blocking;

/**
 * @author zgl
 * @date 2019/7/17 下午2:36
 */
public interface Model {

	Runnable newRunnableProducer();
	Runnable newRunnableConsumer();
}
