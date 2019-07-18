package com.zgl.springboot.async.executor;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zgl
 * @date 2019/7/18 下午2:36
 */
public class ExchangerTest {
	private static final Exchanger<String> exgr = new Exchanger<>();
	private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

	public static void main(String[] args) {
		//获取当前设备CPU个数
		System.out.println(Runtime.getRuntime().availableProcessors());
		threadPool.execute(() -> {
			try {
				String A = "银行流水A10000";
				exgr.exchange(A);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		threadPool.execute(() -> {
			try {
				String B = "银行流水B";
				String A = exgr.exchange(B);
				System.out.println("A和B是否数据一致" + A.equals(B) + "A录入的是"
						+ A + "B录入的是" + B);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		threadPool.shutdown();
	}
}
