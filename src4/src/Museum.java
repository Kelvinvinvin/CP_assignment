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

    public synchronized void EnterSouthEntrance1() throws InterruptedException {
        while (curVisitor >= 100 || !isOpen()){
            wait();
        }

        while (curVisitor < getCurMaxVisit()) {
            curVisitor++;
            maxVisitor++;
        }
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() + " " + "entered through SE turnstile SET-1");
    }

    public synchronized void EnterSouthEntrance2() throws InterruptedException {
        while (curVisitor >= 100 || !isOpen()){
            wait();
        }
        while (curVisitor < getCurMaxVisit()) {
            curVisitor++;
            maxVisitor++;
        }
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() + " " + "entered through SE turnstile SET-2");
    }

    public synchronized void EnterSouthEntrance3() throws InterruptedException {
        while (curVisitor >= 100 || !isOpen()) {
            wait();
        }
        while (curVisitor < getCurMaxVisit()) {
            curVisitor++;
            maxVisitor++;
        }
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() + " " + "entered through SE turnstile SET-3");
    }

    public synchronized void EnterSouthEntrance4() throws InterruptedException {
        while (curVisitor >= 100 || !isOpen() ){
            wait();
        }
        while (curVisitor < getCurMaxVisit()) {
            curVisitor++;
            maxVisitor++;
        }
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() + " " + "entered through SE turnstile SET-4");
    }

    public synchronized void EnterNorthEntrance1() throws InterruptedException {
        while (curVisitor >= 100 || !isOpen()){
            wait();
        }
        while (curVisitor < getCurMaxVisit()) {
            curVisitor++;
            maxVisitor++;
        }
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() + " " + "entered through NE turnstile SET-1");
    }

    public synchronized void EnterNorthEntrance2() throws InterruptedException {
        while (curVisitor >= 100|| !isOpen()){
            wait();
        }
        while (curVisitor < getCurMaxVisit()) {
            curVisitor++;
            maxVisitor++;
        }
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() + " " + "entered through NE turnstile SET-2");
    }

    public synchronized void EnterNorthEntrance3() throws InterruptedException {
        while (curVisitor >= 100 || !isOpen()){
            wait();
        }
        while (curVisitor < getCurMaxVisit()) {
            curVisitor++;
            maxVisitor++;
        }
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() + " " + "entered through NE turnstile SET-3");
    }

    public synchronized void EnterNorthEntrance4() throws InterruptedException {
        while (curVisitor >= 100 || !isOpen() ){
            wait();
        }
        while (curVisitor < getCurMaxVisit()) {
            curVisitor++;
            maxVisitor++;
        }
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() + " " + "entered through NE turnstile SET-4");
    }

    public synchronized void QuitEastExit1(){
        while (curVisitor > 0) {
            curVisitor--;
        }
        notifyAll();
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() +" " + "exit through EE turnstile SET-1");
    }

    public synchronized void QuitEastExit2(){
        while (curVisitor > 0) {
            curVisitor--;
        }
        notifyAll();
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() +" " + "exit through EE turnstile SET-2");
    }

    public synchronized void QuitEastExit3(){
        while (curVisitor > 0) {
            curVisitor--;
        }
        notifyAll();
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() +" " + "exit through EE turnstile SET-3");
    }

    public synchronized void QuitEastExit4(){
        while (curVisitor > 0) {
            curVisitor--;
        }
        notifyAll();
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() +" " + "exit through EE turnstile SET-4");
    }

    public synchronized void QuitWestExit1(){
        while (curVisitor > 0) {
            curVisitor--;
        }
        notifyAll();
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() +" " + "exit through WE turnstile SET-1");
    }

    public synchronized void QuitWestExit2(){
        while (curVisitor > 0) {
            curVisitor--;
        }
        notifyAll();
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() +" " + "exit through WE turnstile SET-2");
    }

    public synchronized void QuitWestExit3(){
        while (curVisitor > 0) {
            curVisitor--;
        }
        notifyAll();
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() +" " + "exit through WE turnstile SET-3");
    }

    public synchronized void QuitWestExit4(){
        while (curVisitor > 0) {
            curVisitor--;
        }
        notifyAll();
        System.out.println(getCurTime() + " " + Thread.currentThread().getName() +" " + "exit through WE turnstile SET-4");
    }

    public String getCurTime(){
        return (LocalTime.now()).format(formatter);
    }

}



