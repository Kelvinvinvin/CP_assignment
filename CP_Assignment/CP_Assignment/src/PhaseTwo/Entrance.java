package PhaseTwo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


//public class Entrance implements Runnable{
public class Entrance {
	private Museum m;
	private objQueue<String> tq;
	private objQueue<String> mq;
	private GUI gui;
	
	
	//enterMuseum 
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	//enterMuseumComplete
	private ReentrantLock lock2 = new ReentrantLock();
	private Condition condition2 = lock2.newCondition();
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	private String openTime = MasterControl.entranceOpen, closeTime = MasterControl.entranceClose, type;
	private volatile int currPerson = 0;
	private volatile LinkedList <String> turnstile = new LinkedList<>();
	private volatile ArrayList<String> entranceLog = new ArrayList<String>();
	final int maxPerson = 4;
	
	public Entrance(objQueue<String> tq, objQueue<String> mq, Museum m, String type, GUI gui) {
		this.tq = tq;
		this.mq = mq;
		this.m = m;
		this.type = type;
		this.gui = gui;
		
		if(type == "S") {
//			System.out.println("South Entrance Open");
			this.turnstile.add("SET1");
			this.turnstile.add("SET2");
			this.turnstile.add("SET3");
			this.turnstile.add("SET4");
		}else {
//		    System.out.println("North Entrance Open");
			this.turnstile.add("NET1");
			this.turnstile.add("NET2");
			this.turnstile.add("NET3");
			this.turnstile.add("NET4");
		}
		
//		this.turnstile.add(type);
	}
	
	public void pickTurnstile(String ticketID, int stayHowLong) {
		try {
			lock.lock();
			while(!isNowTime_in_period() || currPerson >= maxPerson || turnstile.size() == 0 || m.maxCurrentVisitorInMuseum()) {
				if(!isNowTime_in_period() ) {
					System.out.println("Museum Not yet open");
				}else if(!m.maxCurrentVisitorInMuseum()) {
					
					System.out.println("Museum currently Full");
				}
				else {
					System.out.println(ticketID + " want to enter but visitors is using all entrance turnstile at " + typeName(type));
				}
				condition.await();
			}
			
			if(isNowTime_in_period()) {
				
				int turnstile_size = turnstile.size();
				Random r = new Random();
				String whichTurnstile = turnstile.remove(r.nextInt(turnstile_size));
				entranceLog.add(ticketID+":"+whichTurnstile); // T0001:WhichTurnstile
				this.currPerson++;
				System.out.println("     "+ ticketID + " is using this Turnstile to enter " + whichTurnstile);
				
//				System.out.println("     " +ticketID + " entered through Turnstile " +whichTurnstile + ". Staying for " + stayHowLong  +" minutes");
//				this.currPerson--;
//				mq.add(ticketID+":"+stayHowLong);
				
				condition.signalAll();

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	
	public void enterMuseum(String ticketID, int stayHowLong) {
		try {
			lock2.lock();
			while(entranceLog.size() == 0) {
				condition2.await();
			}
			m.changeValue("i");

			@SuppressWarnings("unlikely-arg-type")
			String check = (entranceLog.get(entranceLog.indexOf(new Object() {
				@Override
				public boolean equals(Object obj) {
					return obj.toString().contains(ticketID);
				}
			})));
			String[] arrOfStr = check.split(":", 0);
			
			String displayMessage = (ticketID + " entered through Turnstile " + arrOfStr[1] + ". Staying for " + stayHowLong  +" minutes");
			System.out.println("     " +displayMessage);
			gui.setbroadcastEntrance("("+DateFormat.getDateTimeInstance().format(new Date())+") " + displayMessage);
			
			this.turnstile.add(arrOfStr[1]);
			this.currPerson--;
			mq.add(ticketID+":"+stayHowLong);
			
			condition2.signalAll();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock2.unlock();
		}
	}
	
	public String typeName (String type) {
		if(type=="S") {
			return "South";			
		}else {
			return "North";			
		}
		
	}
	
	public boolean isNowTime_in_period(){
        if (isTimeWith_in_Interval((LocalTime.now()).format(formatter),openTime,closeTime)) {
            return true;
        } else {
            return false;
        }
    }
	
	public static boolean isTimeWith_in_Interval(String valueToCheck, String startTime, String endTime) {
        boolean isBetween = false;
        try {
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(startTime);

            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(endTime);

            Date d = new SimpleDateFormat("HH:mm:ss").parse(valueToCheck);

            if (time1.before(d) && time2.after(d)) {
                isBetween = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isBetween;
    }

	
}
