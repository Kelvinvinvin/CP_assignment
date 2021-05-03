import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Driver {
    public static void main(String[] args) {

        Museum m = new Museum();
        TktSystem ts = new TktSystem(m);
        Visitors w = new Visitors(ts, m);

        w.start();

//        System.out.println(ts.buyTicket());


    }
}
