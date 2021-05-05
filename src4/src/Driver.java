import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import assignmnet.ticketSystem.Ticket;

public class entrance implements Runnable{
//public class entrance{
	private Museum m;
	Queue<Ticket> eq;
	Random rand = new Random();
	
	ReentrantLock lock = new ReentrantLock();
	Condition enterCondition = lock.newCondition();
	Condition leaveCondition = lock.newCondition();

	private volatile int using = 0;
	final int maxUse = 1; 
	
	String turnstile_name;
	Ticket nameTicket;
	private int stayDuration;
	
	public entrance(Museum museum, String turnstile_name, Queue<Ticket>eq) {
		this.m = museum;
		this.turnstile_name = turnstile_name;
		this.eq = eq;
		this.stayDuration=0;
	}

	public void executeEnter() throws InterruptedException {
		
		try {
			lock.lock();
			while(m.getCurrentVisitor()>=5||eq.isEmpty()||eq==null) {
				//Thread.sleep((1+rand.nextInt(2))*1000);
				enterCondition.await();	
			}
			if(!eq.isEmpty()&&eq!=null) {
					this.stayDuration = (50+rand.nextInt(101));
					nameTicket = eq.remove();
					m.changeValue("i");
					System.out.println("\t "+nameTicket.getTicketId()+ " entered through Turnstile " + turnstile_name + " .Staying for " + this.stayDuration + " minutes");
					enterCondition.signalAll();			
			}
		} finally {
			lock.unlock();
		}
	}	
	
	public void executeLeave() throws InterruptedException {
		
		try {
			lock.lock();
				//leaveCondition.signalAll();			
				exit eastEntrance1 = new exit(m , "EET1", nameTicket); //create East Exit 1
				exit eastEntrance2 = new exit(m , "EET2", nameTicket); //create East Exit 2
				exit eastEntrance3 = new exit(m , "EET3", nameTicket); //create East Exit 3
				exit eastEntrance4 = new exit(m , "EET4", nameTicket); //create East Exit 4
				
				exit westEntrance1 = new exit(m , "WET1", nameTicket); //create West Exit 1
				exit westEntrance2 = new exit(m , "WET2", nameTicket); //create West Exit 2
				exit westEntrance3 = new exit(m , "WET3", nameTicket); //create West Exit 3
				exit westEntrance4 = new exit(m , "WET4", nameTicket); //create West Exit 4
				
				ExecutorService ex = Executors.newCachedThreadPool();
				
				int which_turnstile = rand.nextInt(8);
				
				switch (which_turnstile) {
					case(0)-> {
						ex.execute(eastEntrance1);
					}
					case(1)-> {
						ex.execute(eastEntrance2);
					}
					case(2)-> {
						ex.execute(eastEntrance3);
					}
					case(3)-> {
						ex.execute(eastEntrance4);
					}
					case(4)-> {
						ex.execute(westEntrance1);
					}
					case(5)-> {
						ex.execute(westEntrance2);
					}
					case(6)-> {
						ex.execute(westEntrance3);
					}
					case(7)-> {
						ex.execute(westEntrance4);
					}
				}
				ex.shutdown();
			
		} finally {
			lock.unlock();
		}
		
	}
	
	public void run() {
		
		try {
			executeEnter();
			Thread.sleep(stayDuration*100);
			executeLeave();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
