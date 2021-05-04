import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Museum {
    //for daily open time
    private String openTime;
    private String closeTime;
    DateTimeFormatter formatter;


    //for daily maximum visitors limitation
    private int maxVisit;
    private int curMaxVisit;


    //for current visitor count
    private int curVisitor;
    private int maxVisitor;

    
    public Museum() {
        this.maxVisit = 900;
        this.curMaxVisit = 100;

        this.maxVisitor = 0;
        this.curVisitor = 0;

        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.openTime = "9:00:00";
        this.closeTime = "21:00:00";

    }
    public int getCurVisitor() {
        return curVisitor;
    }

    public int getMaxVisitor() {
        return maxVisitor;
    }

    public int getCurMaxVisit() {
        return curMaxVisit;
    }

    public int getMaxVisit() {
        return this.maxVisit;
    }

    public boolean isOpen() {
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

   

    public String getCurTime(){
        return (LocalTime.now()).format(formatter);
    }
    
    //exit 
    exit QuitEastExit1 = new exit();
    exit QuitEastExit2 = new exit();
    exit QuitEastExit3 = new exit();
    exit QuitEastExit4 = new exit();
    

}



