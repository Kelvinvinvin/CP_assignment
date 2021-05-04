import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;


public class TktSystem implements Runnable{
    private Museum museum;
    private DateTimeFormatter formatter;
    private int tktSold;
    private String openTime;
    private String closeTime;
    private volatile LocalTime batch_time;


    public TktSystem(Museum m, LocalTime batch_time) {
        this.museum = m;
        this.tktSold = 0;
        this.batch_time = batch_time;
        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.openTime = "8:00:00";
        this.closeTime = "21:00:00";
    }


	public void buyTicket() {

        String timestamp = "NULL";
        if (isValid_ToBuyTicket()) {
            this.tktSold += 1;
            String postfix = getPostfix();
            timestamp = "T" + postfix;
            System.out.println(batch_time + " Ticket: " + timestamp + " sold");
        }else{
            System.out.println("Your ticket request denied!");
     
        }

    }

    // public String getPostfix(){
    //     int temPostfix = this.tktSold;
    //     String postfix = "";
    //     if (temPostfix < 10) {
    //         postfix = "000" + temPostfix;
    //     } else if (temPostfix >= 10 && temPostfix < 100) {
    //         postfix = "00" + temPostfix;
    //     } else if (temPostfix >= 100 && temPostfix < 1000) {
    //         postfix = "0" + temPostfix;
    //     } else if(temPostfix >= 1000){
    //         // this is not possible because everyday's limit is 999
    //         postfix = ""+temPostfix;
    //     }
    //     return postfix;
    // }


    	
	public String getTicketId(int num) {
        int temPostfix = this.tktSold;
		ticketId=String.format("%04d", temPostfix);
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
    
    public int gettktSold() {
    	return tktSold;
    }


	@Override
	public void run() {
//		System.out.println(Thread.currentThread().getName() + " has entered");
		buyTicket();
		
	}
    
}


