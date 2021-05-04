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


    /*// one time maximum visitors (100)
    private int cap; -->  curMaxVisit
    !! DELETE ALREADY !!
    */

    /*//for daily maximum visitors and current tickets sold
    private int maxVisit;
    private int curVisit;
    !! DELETE ALREADY !!
    */

    /*// Entrance and Exit
    private SouthEntrance se;
    private NorthEntrance ne;
    private EastExit ee;
    private WestExit we;
    !! DELETE ALREADY !!
    */

    public Museum() {
        this.maxVisit = 900;
        this.curMaxVisit = 100;


        // count For today Maximum -- 900
        this.maxVisitor = 0;
        // count For current in museum -- 100
        this.curVisitor = 0;

        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // this.openTime = "9:00:00";
        // this.closeTime = "21:00:00";
        this.openTime = "00:00:00";
        this.closeTime = "09:00:00";
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
        // (LocalTime.now()).format(formatter) is important template to use
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
        // can enter today but museum is full or close
        while (curVisitor >= 100 || !isOpen()){
            wait();
        }

        /*// cannot enter because 900 limit reached, break this method directly
        if(getMaxVisitor() >= 900){
            System.out.println();
        }*/


        // can enter
        while (curVisitor < getCurMaxVisit()) {
            curVisitor++;
            maxVisitor++;
//            canUsedCurrentEntrance++;
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



    /*public int getCurVisit() {
        return this.curVisit;
    }
    !! DELETE ALREADY !!
    */


    /*public int getMaxVisit() {
        return this.maxVisit;
    }
    !! DELETE ALREADY !!
    */

}


/*

class SouthEntrance {
    // 4 turnstiles
    // sensor for entering, maybe a new class called "Sensor"
}

class NorthEntrance {
    // 4 turnstiles
    // sensor for entering
}

class EastExit {
    // 4 turnstiles
    // sensor for leaving
}

class WestExit {
    // 4 turnstiles
    // sensor for leaving
}
*/

