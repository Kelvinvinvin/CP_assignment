import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class exit implements Runnable {
	final int max = 4;
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
				change_current_no();
				try {
					Thread.sleep(500);
					System.out.println(Thread.currentThread().getId() + " is using this exit");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//notify all the thread that waits
				
			}else {
				//wait code here
			}
		}
	}
	
	public void change_current_no() {
		lock.lock();
		current_no++;
		lock.unlock();
	}
}
