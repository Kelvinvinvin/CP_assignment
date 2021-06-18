package PhaseTwo;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class objQueue<E> {
	public volatile Queue<E> visitor_queue = new LinkedList<E>();
	int times;
	ReentrantLock lock;
	Condition condition;
	
	public synchronized void add (E ticket) {
		visitor_queue.add(ticket);
		times++;
	}
	
	public synchronized E remove() {
		return visitor_queue.remove();
	}
	
	public synchronized int getSize() {
		return visitor_queue.size();
	}
	
	public synchronized E peek () {
		return visitor_queue.peek();
	}
	
	public int getTimes() {
		return times;
	}
	
	public boolean isEmpty() {
		return visitor_queue.isEmpty();
	}
	
	
	public objQueue() {
		lock = new ReentrantLock();
		condition = lock.newCondition();
	}
}
