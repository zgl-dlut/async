package com.zgl.springboot.async.limit;

import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zgl
 * @date 2020/3/15 下午2:47
 */
public class GuavaLimiter {

	public static void main(String[] args) {
		String start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		/**
		 * 这里的10表示每秒允许处理的量为10个
		 */
		RateLimiter limiter = RateLimiter.create(10.0);
		for (int i = 1; i <= 10; i++) {
			limiter.acquire();
			System.out.println("call execute.." + i);
		}
		String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		System.out.println("start time:" + start);
		System.out.println("end time:" + end);
	}
}