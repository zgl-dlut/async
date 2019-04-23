package com.zgl.springboot.async.service;

/**
 * @author zgl
 * @date 2019/4/23 下午2:39
 */
public interface AsyncService {

	void asyncEvent();

	void syncEvent();

	void autoSyncEvent();
}