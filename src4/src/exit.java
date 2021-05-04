import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class exit implements Runnable {
	final int max = 1;
	private int current_no = 0;
	Lock lock;
	Condition condition;
	
	public exit() {
		lock = new ReentrantLock();
		condition = lock.newCondition();	
	}
	
	
	@Override
	public void run() {
		while (true) {
			if (current_no <= max) {	
				try {
					increase_current_no();
					Thread.sleep(500);
					System.out.println(Thread.currentThread().getId() + " is using this exit");
					decrease_current_no();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				//wait code here
			}
		}
	}
	
	public void increase_current_no() throws InterruptedException {
		lock.lock();
		condition.await(); // wait other from using this door
		current_no++;
		lock.unlock();
	}
	
	public void decrease_current_no() throws InterruptedException {
		lock.lock();
		current_no--;
		condition.signalAll(); // wait other from using this door
		lock.unlock();
	}
	
	public int getCurrent() {
		return current_no;
	}
}
