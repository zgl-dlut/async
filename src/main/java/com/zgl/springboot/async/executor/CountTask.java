package com.zgl.springboot.async.executor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author zgl
 * @date 2019/7/17 下午3:29
 */
public class CountTask extends RecursiveTask<Integer> {

	private static final int THRESHOLD = 2;
	private int start;
	private int end;

	public CountTask(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int sum = 0;
		boolean canCompute = (end - start) <= THRESHOLD;
		System.out.println(Thread.currentThread().getName());
		if (canCompute) {
			for (int i = start; i <= end; i++) {
				sum += i;
			}
		} else {
			int middle = (start + end) / 2;
			CountTask leftTask = new CountTask(start, middle);
			CountTask rightTask = new CountTask(middle + 1, end);
			leftTask.fork();
			rightTask.fork();
			int leftSum = leftTask.join();
			int rightSum = rightTask.join();
			sum = leftSum + rightSum;
		}
		return sum;
	}

	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		CountTask countTask = new CountTask(1, 100);
		Future<Integer> result = forkJoinPool.submit(countTask);
		try {
			System.out.println(result.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}