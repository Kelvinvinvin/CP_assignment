package PhaseTwo;

import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Exit {
	private Museum m;
	
	//exitMuseum 
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	//exitMuseumComplete
	private ReentrantLock lock2 = new ReentrantLock();
	private Condition condition2 = lock2.newCondition();
		
	private objQueue<String> mq;
	private volatile int currPerson = 0;
	private volatile Queue <String> turnstile = new LinkedList<>();
	private volatile ArrayList<String> exitLog = new ArrayList<String>();
	private String type;
	private GUI gui;
	final int maxPerson = 4;
	
	public Exit(objQueue<String> mq, Museum m, String type, GUI gui) {
		this.mq = mq;
		this.m = m;
		this.type = type;
		this.gui = gui;
		
		if(type == "W") {
//			System.out.println("West Exit Open");
			this.turnstile.add("WET1");
			this.turnstile.add("WET2");
			this.turnstile.add("WET3");
			this.turnstile.add("WET4");
		}else {
//		    System.out.println("East Exit Open");
			this.turnstile.add("EET1");
			this.turnstile.add("EET2");
			this.turnstile.add("EET3");
			this.turnstile.add("EET4");
		}
	}
	
	public void exitMuseum(String ticketID) {
		try {
			lock.lock();
			while(currPerson >= maxPerson || turnstile.size() == 0 ) {
				System.out.println(ticketID + " want to exit but visitor is using all exit turnstile at " + typeName(type));
				condition.await();
			}
			if(turnstile.size() != 0 ) {
				String whichTurnstile = turnstile.remove();
				exitLog.add(ticketID+":"+whichTurnstile); // T0001:WhichTurnstile
				this.currPerson++;
//				System.out.println("\t" + ticketID + " is using this Turnstile to exit " + whichTurnstile);
				condition.signal();
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
//	public void exitMuseum(String ticketID) {
//		try {
//			lock.lock();
//			while(currPerson >= maxPerson || turnstile.size() == 0 || mq.isEmpty()) {
//				if(mq.isEmpty()) {
////					System.out.println("no one wants to get out");
//				}else {
//					System.out.println(ticketID + " want to exit but visitor is using all exit turnstile at " + typeName(type));
//				}
//				condition.await();
//			}
//			if(turnstile.size() != 0 && !mq.isEmpty()) {
//				String museumQueue = mq.remove();
//				String[] sleep = museumQueue.split(":", 0);
//				Thread.sleep(Integer.parseInt(sleep[1]));
//				String whichTurnstile = turnstile.remove();
//				exitLog.add(ticketID+":"+whichTurnstile); // T0001:WhichTurnstile
//				this.currPerson++;
////				System.out.println("\t" + ticketID + " is using this Turnstile to exit " + whichTurnstile);
//				condition.signal();
//			}
//			
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}finally {
//			lock.unlock();
//		}
//	}
	
	

	public void exitMuseumComplete(String ticketID) {
		try {
			lock2.lock();
			while(exitLog.size() == 0) {
				condition2.await();
			}
			@SuppressWarnings("unlikely-arg-type")
			String check = (exitLog.get(exitLog.indexOf(new Object() {
				@Override
				public boolean equals(Object obj) {
					return obj.toString().contains(ticketID);
				}
			})));
			String[] arrOfStr = check.split(":", 0);
			m.changeValue("d");
			String displayMessage = (ticketID + " exited through Turnstile " + arrOfStr[1]);
			System.out.println("          " +displayMessage);
			gui.setbroadcastExit("("+DateFormat.getDateTimeInstance().format(new Date())+") " +displayMessage);
			this.turnstile.add(arrOfStr[1]);
			this.currPerson--;
			condition2.signal();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock2.unlock();
		}
	}
	
	public String typeName (String type) {
		if(type=="E") {
			return "East";			
		}else {
			return "West";			
		}
		
	}
}
