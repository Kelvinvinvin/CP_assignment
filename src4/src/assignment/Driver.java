package assignment;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import assignment.ticketSystem.Ticket;


public class Driver {
	
	
    public static void main(String[] args) {

        
    	Queue<Ticket> ticketQueue=new LinkedList<>();

        Museum museum = new Museum();
        Random r = new Random(); 
        ticketSystem ts = new ticketSystem(museum,ticketQueue);
        
        ExecutorService executorService = Executors.newCachedThreadPool();

        while (ts.getTicket_sold() <= 900 && ts.isNowTime_in_period() ){
            try {
                executorService.execute(ts);
				Thread.sleep(1+r.nextInt(4) * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        executorService.shutdown();
    }
}