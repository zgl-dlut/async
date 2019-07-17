package com.zgl.springboot.async.blocking;

/**
 * @author zgl
 * @date 2019/7/17 下午2:30
 */
public interface Consumer {
	  void consume() throws InterruptedException;
}
