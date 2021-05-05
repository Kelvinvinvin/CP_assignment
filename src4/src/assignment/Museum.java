package assignment;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Museum {
	private volatile int currentVisitor = 0;
	final int maxVisitor = 100;
	Lock lock;
	Condition condition;
	
	public Museum() {
		lock = new ReentrantLock();
		condition = lock.newCondition();
	}

	public int changeValue(String n) {
		lock.lock();
		if(n == "d") {
			currentVisitor--;
		}else if (n == "i") {
			currentVisitor++;
		}
		
		lock.unlock();
		return currentVisitor;
	}
	
	public int getCurrentVisitor () {
		return currentVisitor;
	}
}
