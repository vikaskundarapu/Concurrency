package com.vikas.concurrency.chp13;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayBlockingQueue is an unbounded BlockingQueue which implements Delayed
 * Interface. DelayQueue keeps the elements internally until a certain delay has
 * expired. The item can be taken from the queue only after the delay has
 * expired. Null items cannot be stored in the queue. The queue is sorted to
 * have the object at the head with delay expired for the longest time. If the
 * delay is not expired, it means there is no head element so poll() returns
 * null. size() returns the count of both expired and unexpired items. a
 * DelayQueue, a BlockingQueue implementation that provides the scheduling
 * functionality of ScheduledThreadPoolExecutor. A DelayQueue manages a
 * collection of Delayed objects. A Delayed has a delay time associated with it:
 * DelayQueue lets you take an element only if its delay has expired. Objects
 * are returned from a DelayQueue ordered by the time associated with their
 * delay
 */
public class DelayedQueueExample {

	public static void main(String[] args) {

		DelayQueue<DelayedWorker> delayQueue = new DelayQueue<>();

		delayQueue.put(new DelayedWorker(1000, "This the First thread"));
		delayQueue.put(new DelayedWorker(10000, "This the Second thread"));
		delayQueue.put(new DelayedWorker(4000, "This the Third thread"));

		while (!delayQueue.isEmpty()) {
			try {
				DelayedWorker take = delayQueue.take();
				System.out.println(take);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class DelayedWorker implements Delayed {

	private long duration;
	private String message;

	public DelayedWorker(long duration, String message) {
		super();
		this.duration = duration + System.currentTimeMillis();
		this.message = message;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int compareTo(Delayed delay) {
		if (this.duration < ((DelayedWorker) delay).getDuration())
			return -1;
		if (this.duration > ((DelayedWorker) delay).getDuration())
			return 1;
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	@Override
	public String toString() {

		return this.message.toString();
	}
}
