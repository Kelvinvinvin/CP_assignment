package assignment;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Date;
import java.util.Queue;
import java.util.Random;
import java.time.LocalTime;
import java.sql.Time;




class ticketSystem implements Runnable{
    private Museum museum;
    private entrance entrance;
    private DateTimeFormatter formatter;
    private int tktSold;
    private String openTime,closeTime;
    private String ticketId;

    final int maxTicket;
    Queue<Ticket> ticketQueue;
	Random r = new Random();


    public ticketSystem(Museum m, Queue<Ticket> ticketQueue2) {
        this.museum = m;
        this.ticketQueue=ticketQueue2;
        this.tktSold = 0;
        maxTicket = 900;
        
        this.ticketId="";
        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.openTime = "08:00:00";
        this.closeTime = "17:00:00";
    }
    
   public class Ticket{

        private String ticketId;
        private int ticketNum;
    
        public Ticket(int ticketNum) {
            this.ticketNum=ticketNum;
            this.ticketId=getTicketId();
        }
    
        public synchronized String getTicketId() {
                String ticketId="T"+String.format("%04d", this.ticketNum);
                return ticketId;
        }
        
        public synchronized Time getSoldTime(){
            Time soldTime=new Time((int) LocalTime.now().getNano());
            return soldTime;
        }
        
        
        public String toString() {
			return ticketId;
        	
        }
    }
   
   
	public synchronized void buyTicket() {
		if(this.tktSold < maxTicket && isNowTime_in_period()) {
            int random_num_ticket = 1 + r.nextInt(4); 
           // Time ti=getSoldTime();
           String build="";
           for(int a=0;a<random_num_ticket;a++){
                this.tktSold++;
                Ticket ticket=new Ticket(this.tktSold);
                ticketQueue.add(ticket);
                if(a<random_num_ticket-1) {
                	build += (ticket.getTicketId()+ ",");
                }else {
                	build += (ticket.getTicketId());
                }
           }
           System.out.println((LocalTime.now()).format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " Ticket: " + build+" sold");
        }else{
            System.out.println("Your ticket request denied!");
        }
    }
 
    
    public Time getSoldTime(){
        Time soldTime=new Time((int) LocalTime.now().getNano());
        return soldTime;
    }
    
    public int getTicket_sold() {
		return this.tktSold;
	}
	


    public String getCurTime(){
        return (LocalTime.now()).format(formatter);
    }

    // this is to check Can customer buy ticket at now(is it in the time range 8:00-5:00)
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

 


    @Override
    public void run() {
        try {
            buyTicket();
            if(!ticketQueue.isEmpty()) {
         
        	entrance southEntrance1 = new entrance(this.museum, "SET1", ticketQueue); //create south entrance 1
    		entrance southEntrance2 = new entrance(this.museum, "SET2", ticketQueue); //create south entrance 2
    		entrance southEntrance3 = new entrance(this.museum, "SET3", ticketQueue); //create south entrance 3
    		entrance southEntrance4 = new entrance(this.museum, "SET4", ticketQueue); //create south entrance 4

    		entrance northEntrance1 = new entrance(this.museum, "NET1", ticketQueue); //create north entrance 1
    		entrance northEntrance2 = new entrance(this.museum, "NET2", ticketQueue); //create north entrance 2
    		entrance northEntrance3 = new entrance(this.museum, "NET3", ticketQueue); //create north entrance 3
    		entrance northEntrance4 = new entrance(this.museum, "NET4", ticketQueue); //create north entrance 4
    		
    		ExecutorService ex = Executors.newCachedThreadPool();
    		Random random=new Random();
            int num=random.nextInt(2);
            
            //System.out.println((LocalTime.now()).format(DateTimeFormatter.ofPattern("HHmm")) + " " +ticketQueue.iterator());

        	for (int i=1; i<= this.tktSold; i++) {
				int which_turnstile = r.nextInt(8);
				
				switch (which_turnstile) {
					case(0)-> {
//						exSouth.execute(southEntrance1);
						ex.execute(southEntrance1);
					}
					case(1)-> {
//						exSouth.execute(southEntrance2);
						ex.execute(southEntrance2);
					}
					case(2)-> {
//						exSouth.execute(southEntrance3);
						ex.execute(southEntrance3);
					}
					case(3)-> {
//						exSouth.execute(southEntrance4);
						ex.execute(southEntrance4);
					}
					case(4)-> {
//						exNorth.execute(northEntrance1);
						ex.execute(northEntrance1);
					}
					case(5)-> {
//						exNorth.execute(northEntrance2);
						ex.execute(northEntrance2);
					}
					case(6)-> {
//						exNorth.execute(northEntrance3);
						ex.execute(northEntrance3);
					}
					case(7)-> {
//						exNorth.execute(northEntrance4);
						ex.execute(northEntrance4);
					}
				}
				
			}
			ex.shutdown();
        }
        }catch (Exception ex) {
        }
        
    }


    
}
