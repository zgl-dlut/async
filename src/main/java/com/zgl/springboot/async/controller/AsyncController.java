package com.zgl.springboot.async.controller;

import com.zgl.springboot.async.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @author zgl
 * @date 2019/4/23 下午2:42
 */
@RestController
@Slf4j
public class AsyncController {

	@Autowired
	private AsyncService asyncService;

	@GetMapping("/async")
	public String doAsync() {
		long start = System.currentTimeMillis();
		log.info("方法执行开始:{}!",start);

		/**
		 * 调用同步方法-消耗4s
		 */
		asyncService.syncEvent();
		long syncTime = System.currentTimeMillis();
		log.info("同步方法用时：{}ms", (syncTime - start) / 1000.0);

		/**
		 * 调用异步步方法(2个@Async方法和CompletableFuture异步,共四个方法)-消耗4s
		 */
		asyncService.asyncEvent();
		long asyncTime = System.currentTimeMillis();
		log.info("异步方法用时：{}ms", (asyncTime - syncTime) / 1000.0);

		asyncService.asyncEvent();
		long asyncTime2 = System.currentTimeMillis();
		log.info("异步方法用时：{}ms", (asyncTime2 - syncTime) / 1000.0);

		CompletableFuture<Void> async1Future = CompletableFuture.runAsync(() -> asyncService.syncEvent());
		CompletableFuture<Void> async2Future = CompletableFuture.runAsync(() -> asyncService.autoSyncEvent());
		CompletableFuture.allOf(async1Future,async2Future).join();

		/**
		 * 一共8s
		 */
		log.info("方法执行完成:{}ms!", (System.currentTimeMillis() - start) / 1000.0);
		return "async!!!";
	}
}