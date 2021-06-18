package PhaseTwo;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Museum {
	private volatile int currentVisitor = 0;
	private volatile int countVisitor = 0;
	final int maxVisitorPerday = MasterControl.maxVisitorPerday;
	final int maxVisitorInMuseum = MasterControl.maxVisitorInMuseum;
	ReentrantLock lock = new ReentrantLock();;
	Condition condition = lock.newCondition();
	final int max_entranceS_occupied = 4;
	final int max_entranceN_occupied = 4;
	private GUI gui;
	
	
	public Museum(GUI gui) {
		this.gui = gui;
	}

	public void changeValue(String n) {
		try {
			lock.lock();
			
			if(n == "d") {
				currentVisitor--;
				condition.signalAll();
			}else if (n == "i") {
				while(currentVisitor == maxVisitorInMuseum) {
					System.out.println("max people in museum");
					condition.await();
				}
				currentVisitor++;
				countVisitor++;
				
			}
			
			gui.setmText(currentVisitor);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			lock.unlock();
		}
		
	}
	
	
	
	public synchronized boolean maxVisitorInMuseum () {
		return countVisitor == maxVisitorPerday;
	}
	
	public synchronized int getTotalVisitor (){
		return countVisitor;
	}
	public synchronized int getCurrentVisitorCount (){
		return currentVisitor;
	}
	
	
	public synchronized boolean maxCurrentVisitorInMuseum (){
		return currentVisitor > maxVisitorInMuseum;
	}
}