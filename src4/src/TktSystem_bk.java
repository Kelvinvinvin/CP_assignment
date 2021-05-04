
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class TktSystem_bk{
    private Museum_bk museum;
    private DateTimeFormatter formatter;
    private int tktSold;

    private String openTime;
    private String closeTime;


    public TktSystem_bk(Museum_bk m) {
        this.museum = m;
        this.tktSold = 0;

        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
         this.openTime = "8:00:00";
         this.closeTime = "21:00:00";

    }

    public synchronized String buyTicket() {
        Random r = new Random();
        int buyTckDuration = r.nextInt(3000)+1000;
        String timestamp = "NULL";
        if (isValid_ToBuyTicket()) {
            this.tktSold += 1;
            try {
                Thread.sleep(buyTckDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            String postfix = getPostfix();
            timestamp = "T" + postfix;
            return timestamp;

        }else{
            System.out.println("Your ticket request denied!");
            return timestamp;
        }

    }

    public String getPostfix(){
        int temPostfix = this.tktSold;
        String postfix = "";
        if (temPostfix < 10) {
            postfix = "000" + temPostfix;
        } else if (temPostfix >= 10 && temPostfix < 100) {
            postfix = "00" + temPostfix;
        } else if (temPostfix >= 100 && temPostfix < 1000) {
            postfix = "0" + temPostfix;
        } else if(temPostfix >= 1000){
            // this is not possible because everyday's limit is 999
            postfix = ""+temPostfix;
        }
        return postfix;
    }

    public boolean isValid_ToBuyTicket() {
 
        if (this.tktSold < this.museum.getMaxVisit() && isNowTime_in_period()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNowTime_in_period(){
        if (isTimeWith_in_Interval((LocalTime.now()).format(formatter),openTime,closeTime)) {
            // System.out.println("hi this works");
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
}
