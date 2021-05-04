import java.util.Random;

public class Visitors extends Thread {
    private Museum museum;
    private TktSystem tktSystem;
    private boolean canBuyTck;
    private String tkt_timestamp;


    public Visitors(TktSystem ts, Museum m) {
        this.museum = m;
        this.tktSystem = ts;
        this.tkt_timestamp = "NULL";
        this.canBuyTck = false;
    }
    // baogang halo
    //this method is for purchasing tickets
    public void buyTkt() {
        String ticket_timestamp = this.tktSystem.buyTicket();
        if (ticket_timestamp != "NULL") {
            //Buy successfully
            canBuyTck = true;
            this.tkt_timestamp = ticket_timestamp;
            System.out.println(tktSystem.getCurTime() + " Ticket: " + this.tkt_timestamp + " sold");
        } else {
            //Buy unsuccessfully
            System.out.println("Your purchase request was rejected.");
            // destroy this visitor thread;
            interrupt();

        }
    }

    public String getTkt_timestamp() {
        return this.tkt_timestamp;
    }

    public void enter(int randomEnter) {
        switch (randomEnter) {
            case 1 -> {
                try {
                    museum.EnterNorthEntrance1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            case 2 -> {
                try {
                    museum.EnterNorthEntrance2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            case 3 -> {
                try {
                    museum.EnterNorthEntrance3();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            case 4 -> {
                try {
                    museum.EnterNorthEntrance4();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            case 5 -> {
                try {
                    museum.EnterSouthEntrance1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            case 6 -> {
                try {
                    museum.EnterSouthEntrance2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            case 7 -> {
                try {
                    museum.EnterSouthEntrance3();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            case 8 -> {
                try {
                    museum.EnterSouthEntrance4();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void leave(int randomLeave) {
        switch (randomLeave) {
            case 1 -> museum.QuitWestExit1();
            case 2 -> museum.QuitWestExit2();
            case 3 -> museum.QuitWestExit3();
            case 4 -> museum.QuitWestExit4();
            case 5 -> museum.QuitEastExit1();
            case 6 -> museum.QuitEastExit2();
            case 7 -> museum.QuitEastExit3();
            case 8 -> museum.QuitEastExit4();
        }
    }

    public int stayTime_inMuseum() {
        int stayTime;
        Random r = new Random();
        // you can change this line for sleep time(Time of staying museum)
        stayTime = r.nextInt(40000) + 10000;
        return stayTime;
    }


    @Override
    public void run() {
        // Buy successfully or destroy directly
        buyTkt();

        // run the Visitors class' public functions inside
        // the run() method
        Random r = new Random();
        int randomEnter = r.nextInt(7) + 1;
        int randomLeave = r.nextInt(7) + 1;

        int stayTime = stayTime_inMuseum();

        if (canBuyTck) {
            enter(randomEnter);
            System.out.println(Thread.currentThread().getName() + " staying for " + stayTime * 0.001 + "seconds");
            try {
                sleep(stayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            leave(randomLeave);
        }

        interrupt();

    }

}

//class clock extends Thread{
//    Museum m;
//    int date;
//
//
//    public clock(Museum m){
//        this.m = m;
//        date = 12;
//    }
//
//    @Override
//    public void run() {
//        while(date > 18){
//            this.m.closeMuseum;
//        }
//    }
//}
