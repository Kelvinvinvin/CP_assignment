import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Driver {
    public static void main(String[] args) {
        Museum museum = new Museum();
        Random rand = new Random(); 
        TktSystem ts = new TktSystem(museum);
        
        int ticket_times = 1+rand.nextInt(4); // buy random number of ticket
        int subsequent_times = 1+rand.nextInt(4); // buy random number of ticket
        System.out.println(ticket_times);
        System.out.println(subsequent_times);
        
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (ts.isValid_ToBuyTicket() && ts.gettktSold() <= 900 ){
        	for (int i=1; i<=ticket_times; i++) {
        		if(ts.isValid_ToBuyTicket()) {
            		ts = new TktSystem(museum);
            		executorService.execute(ts);
            		System.out.println("hi");
            	}
        	}
        	try {
        		System.out.println("sleep for "+ subsequent_times*1000);
				Thread.sleep(subsequent_times*1000); // to simulate subsequent purchase
				ticket_times = 1+rand.nextInt(4);
				subsequent_times = 1+rand.nextInt(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        executorService.shutdown();
    }
}
