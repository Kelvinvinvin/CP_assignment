// 1. this class is for helping visitors to purchase tickets

// 2. compared with "Mobile App",
// it's better we call it "official website" for purchasing

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

// 3. it is a common resource I think,
// so dont need to extends thread(runnable).
public class TktSystem{
    private Museum museum;
    private DateTimeFormatter formatter;
    private int tktSold;

    // I have wrote a method called getIsOpen() from Museum class
    // I'm not sure do we still need the open and close time in PurMobileApp class
    private String openTime;
    private String closeTime;


    public TktSystem(Museum m) {
        this.museum = m;
        this.tktSold = 0;

        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // this.openTime = "8:00:00";
        // this.closeTime = "21:00:00";
        this.openTime = "00:00:00";
        this.closeTime = "09:00:00";
    }

    // maybe we will change this void to a class "Ticket"
    // because Ticket is a real object with timestamps
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
//            System.out.println("Sold a new ticket, today's sales num: " + this.tktSold);

            // we still need to create a real ticket with a timestamps
            // some code()

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
        // current sold ticket number < maximum number(900) AND museum is open,
        // then customer could buy ticket, this function will return TURE
        if (this.tktSold < this.museum.getMaxVisit() && isNowTime_in_period()) {
            return true;
        } else {
            return false;
        }
    }

    // this is to check Can customer buy ticket at now(is it in the time range 8:00-5:00)
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
