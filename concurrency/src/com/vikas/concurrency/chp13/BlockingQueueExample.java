package com.vikas.concurrency.chp13;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Blocking queues provide blocking put and take methods as well as the timed
 * equivalents offer and poll. If the queue is full, put blocks until space
 * becomes available; if the queue is empty, take blocks until an element is
 * available. Queues can be bounded or unbounded; unbounded queues are never
 * full, so a put on an unbounded queue never blocks. Blocking queues support
 * the producer-consumer design pattern. A producerconsumer design separates the
 * identification of work to be done from the execution of that work by placing
 * work items on a “to do” list for later processing, rather than processing
 * them immediately as they are identified. The producerconsumer pattern
 * simplifies development because it removes code dependencies between producer
 * and consumer classes, and simplifies workload management by decoupling
 * activities that may produce or consume data at different or variable rates.
 * In a producer-consumer design built around a blocking queue, producers place
 * data onto the queue as it becomes available, and consumers retrieve data from
 * the queue when they are ready to take the appropriate action. Producers don’t
 * need to know anything about the identity or number of consumers, or even
 * whether they are the only producer—all they have to do is place data items on
 * the queue. Similarly, consumers need not know who the producers are or where
 * the work came from. BlockingQueue simplifies the implementation of
 * producerconsumer designs with any number of producers and consumers The
 * familiar division of labor for two people washing the dishes is an example of
 * a producer-consumer design: one person washes the dishes and places them in
 * the dish rack, and the other person retrieves the dishes from the rack and
 * dries them. In this scenario, the dish rack acts as a blocking queue; if
 * there are no dishes in the rack, the consumer waits until there are dishes to
 * dry, and if the rack fills up, the producer has to stop washing until there
 * is more space. This analogy extends to multiple producers (though there may
 * be contention for the sink) and multiple consumers; each worker interacts
 * only with the dish rack. No one needs to know how many producers or consumers
 * there are, or who produced a given item of work. The labels “producer” and
 * “consumer” are relative; an activity that acts as a consumer in one context
 * may act as a producer in another. Drying the dishes “consumes” clean wet
 * dishes and “produces” clean dry dishes. A third person wanting to help might
 * put away the dry dishes, in which case the drier is both a consumer and a
 * producer, and there are now two shared work queues (each of which may block
 * the drier from proceeding.) Blocking queues simplify the coding of consumers,
 * since take blocks until data is available. If the producers don’t generate
 * work fast enough to keep the consumers busy, the consumers just wait until
 * more work is available. Sometimes this is perfectly acceptable (as in a
 * server application when no client is requesting service), and sometimes it
 * indicates that the ratio of producer threads to consumer threads should be
 * adjusted to achieve better utilization (as in a web crawler or other
 * application in which there is effectively infinite work to do). If the
 * producers consistently generate work faster than the consumers can process
 * it, eventually the application will run out of memory because work items will
 * queue up without bound. Again, the blocking nature of put greatly simplifies
 * coding of producers; if we use a bounded queue, then when the queue fills up
 * the producers block, giving the consumers time to catch up because a blocked
 * producer cannot generate more work. Blocking queues also provide an offer
 * method, which returns a failure status if the item cannot be enqueued. This
 * enables you to create more flexible policies for dealing with overload, such
 * as shedding load, serializing excess work items and writing them to disk,
 * reducing the number of producer threads, or throttling producers in some
 * other manner. While the producer-consumer pattern enables producer and
 * consumer code to be decoupled from each other, their behavior is still
 * coupled indirectly through the shared work queue. It is tempting to assume
 * that the consumers will always keep up, so that you need not place any bounds
 * on the size of work queues, but this is a prescription for rearchitecting
 * your system later. Build resource management into your design early using
 * blocking queues—it is a lot easier to do this up front than to retrofit it
 * later. Blocking queues make this easy for a number of situations, but if
 * blocking queues don’t fit easily into your design, you can create other
 * blocking data structures using Semaphore (see Section 5.5.3). The class
 * library contains several implementations of BlockingQueue.
 * LinkedBlockingQueue and ArrayBlockingQueue are FIFO queues, analogous to
 * LinkedList and ArrayList but with better concurrent performance than a
 * synchronized List. PriorityBlockingQueue is a priority-ordered queue, which
 * is useful when you want to process elements in an order other than FIFO. Just
 * like other sorted collections, PriorityBlockingQueue can compare elements
 * according to their natural order (if they implement Comparable) or using a
 * Comparator. The last BlockingQueue implementation, SynchronousQueue, is not
 * really a queue at all, in that it maintains no storage space for queued
 * elements. Instead, it maintains a list of queued threads waiting to enqueue
 * or dequeue an element. In the dish-washing analogy, this would be like having
 * no dish rack, but instead handing the washed dishes directly to the next
 * available dryer. While this may seem a strange way to implement a queue, it
 * reduces the latency associated with moving data from producer to consumer
 * because the work can be handed off directly. (In a traditional queue, the
 * enqueue and dequeue operations must complete sequentially before a unit of
 * work can be handed off.) The direct handoff also feeds back more information
 * about the state of the task to the producer; when the handoff is accepted, it
 * knows a consumer has taken responsibility for it, rather than simply letting
 * it sit on a queue somewhere—much like the difference between handing a
 * document to a colleague and merely putting it in her mailbox and hoping she
 * gets it soon. Since a SynchronousQueue has no storage capacity, put and take
 * will block unless another thread is already waiting to participate in the
 * handoff. Synchronous queues are generally suitable only when there are enough
 * consumers that there nearly always will be one ready to take the handoff.
 */
public class BlockingQueueExample {

	public static void main(String[] args) {
		BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);

		new Thread(new Worker(blockingQueue)).start();
		new Thread(new Worker1(blockingQueue)).start();
	}

}

class Worker implements Runnable {

	private BlockingQueue<Integer> queue;

	public Worker(BlockingQueue<Integer> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		int counter = 0;
		while (true) {
			try {
				queue.put(counter);
				System.out.println("Putting items to the queue::" + counter);
				counter++;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class Worker1 implements Runnable {

	private BlockingQueue<Integer> queue;

	public Worker1(BlockingQueue<Integer> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Integer take = queue.take();
				System.out.println("taking out item:::" + take);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
