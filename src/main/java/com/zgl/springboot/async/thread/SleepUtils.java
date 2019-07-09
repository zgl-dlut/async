package com.zgl.springboot.async.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author zgl
 * @date 2019/7/8 下午9:51
 */
public class SleepUtils {
	public static final void second(long seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}