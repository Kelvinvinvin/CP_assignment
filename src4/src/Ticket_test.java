import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.time.LocalTime;
import java.sql.Time;


public class TktSystem implements Runnable{
    private Museum museum;
    private entrance entrance;
    private DateTimeFormatter formatter;
    private int tktSold,tkTime;
    private String openTime,closeTime;
    private String ticketId;
    private Time buyTime;


    public TktSystem(Museum m) {
        this.museum = m;
        this.tktSold = 0;
        this.ticketId="";
        this.buyTime=Time.valueOf("08:00:00");
        this.tkTime=0;
        
     
        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.openTime = "8:00:00";
        this.closeTime = "21:00:00";
    }

	public void buyTicket() {
        if (isValid_ToBuyTicket()) {
            this.buyTime=getCurTime();
            this.tkTime=getCurTime();
            this.tktSold += 1;
            String ticketId="T"+String.format("%04d", this.tktSold);
            System.out.println(this.buyTime + " Ticket: " + ticketId + " sold");

        }else{
            System.out.println("Your ticket request denied!");
        }
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

    public String getSoldTime(){

        return (LocalTime.now()).format(formatter);
    }
    
    public int gettktSold() {
    	return tktSold;
    }

    public String getCurTime(){

        return (LocalTime.now()).format(formatter);
    }




    @Override
    public void run() {
        try {
            buyTicket();
            Rnadom random=new Random();
            int num=random.nextInt(2);
            if(num==0){
                entrance=new entrance('S',this.buyTime);
            }else{
                entrance=new entrance('N',this.buyTime);
            }
            entrance.enterMusem(this);
        } catch (Exception ex) {
        }
    }
    
}

