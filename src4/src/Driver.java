import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Driver {
    public static void main(String[] args) {
      
        Museum_bk m = new Museum_bk();

        
        TktSystem ts = new TktSystem(m);
        Visitors visitors;

        int i = 0;
        ExecutorService executorService = Executors.newCachedThreadPool();
        //Just modify the time here to execute the thread in loop with the time
        while (i<4){
            visitors = new Visitors(ts,m);
            executorService.execute(visitors);
            i++;
        }
        executorService.shutdown();


    }
}
