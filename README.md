### async

    通常开发过程中，一般上我们都是同步调用，即：程序按定义的顺序依次执行的过程，
    每一行代码执行过程必须等待上一行代码执行完毕后才执行。
    而异步调用指：程序在执行时，无需等待执行的返回值可继续执行后面的代码。
    显而易见，同步有依赖相关性，而异步没有，所以异步可并发执行，可提高执行效率，在相同的时间做更多的事情。
    
#### `两种调用方式`

* ```需要在启动类加入@EnableAsync使异步调用@Async注解生效。```

* ```JDK1.8 CompletableFuture```

    ```java
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
    ```