import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Driver {
    public static void main(String[] args) {

        Museum m = new Museum();
        TktSystem ts = new TktSystem(m);
        Visitors visitors = new Visitors(ts,m);

        int i = 0;
        ExecutorService executorService = Executors.newCachedThreadPool();
        //Just modify the time here to execute the thread in loop with the time
        while (i<20){
            visitors = new Visitors(ts,m);
            executorService.execute(visitors);
            i++;
        }
        executorService.shutdown();

//       HI i want to just play around with this thingssssssssssss
//        w.start();
//        x.start();
//        y.start();

//        System.out.println(ts.buyTicket());


    }
}
