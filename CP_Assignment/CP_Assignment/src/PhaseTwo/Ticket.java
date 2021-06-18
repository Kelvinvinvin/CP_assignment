package PhaseTwo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;


public class Ticket implements Runnable{
//	private Museum m;
	objQueue <String> tq;
	final int maxTicket = MasterControl.maxTicketPerDay;
	private int ticket_sold = 0;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	private String openTime = MasterControl.ticketOpen, closeTime = MasterControl.ticketClose;
	private volatile int fail = 0;
	private GUI gui;
   
	Random rand = new Random();

	@Override
	public void run() {
		buyTicket();
	}
	
	public Ticket(objQueue <String> tq, GUI gui) {
		this.tq = tq;
		this.gui = gui;
	}
	
	public void buyTicket() { //return how many ticket bought
		if(ticket_sold < maxTicket && isNowTime_in_period()) {
			gui.settktText("Open For Ticket Purchasing");
			System.out.println(createTicketID());
		}else {
			System.out.println("Ticket Sold Out/ Now is not the time");
			gui.settktText("Ticket Sold Out/ Now is not the time");
			fail++;
		}	
	}
	
	public String createTicketID() {
		int generateNum = 1 + rand.nextInt(4);
		String build = "";
		for(int i=1 ; i<=generateNum ; i++) {
			if(ticket_sold < maxTicket) {
				ticket_sold++;
				gui.settktSoldText(this.ticket_sold);
				int ticketID = tq.getTimes()+1;
				String ticketId = "T"+String.format("%04d", ticketID);
				tq.add(ticketId); //Store the ticket
				build += ticketId + ", ";
			}else {
				break;
			}
			
		}
		if (build != null && build.length() > 0 && build.charAt(build.length() - 2) == ',') {
	        build = build.substring(0, build.length() - 2);
	    }
		return build + " Sold";
	}
	
	public int getTicket_sold() {
		return ticket_sold;
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

	
	public synchronized int fail() {
		return this.fail;
	}

}
