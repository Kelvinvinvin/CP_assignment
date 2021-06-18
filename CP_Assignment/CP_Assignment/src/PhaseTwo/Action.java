package PhaseTwo;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Action extends Thread {

    Museum m;
    objQueue<String> tq; //ticket queue (store ticket generated)
    objQueue<String> mq; //museum queue (store ticket that is ready to leave)
    private GUI gui;
    private boolean stopProcess = false;

    public Action(Museum m, objQueue<String> tq, objQueue<String> mq, GUI gui) {
        this.tq = tq;
        this.m = m;
        this.mq = mq;
        this.gui = gui;
    }

    @Override
    public void run() {
        Entrance SE = new Entrance(tq, mq, m, "S", gui);
        Entrance NE = new Entrance(tq, mq, m, "N", gui);

        Exit EE = new Exit(mq, m, "E", gui);
        Exit WE = new Exit(mq, m, "W", gui);

        Random rand = new Random();
        ExecutorService ex = Executors.newCachedThreadPool();
        while (!m.maxVisitorInMuseum() && !stopProcess) {
            while (!tq.isEmpty()) {
                String ticket = tq.remove();
                int which_Entrance = 1 + rand.nextInt(2);
                int stayHowLong = 50 + rand.nextInt(101);
                switch (which_Entrance) { //random choose entrance
                    case (1) -> {
                        Runnable ent = new SouthEntrance(SE, ticket, stayHowLong);
                        int which_Exit = 1 + rand.nextInt(2);
                        switch (which_Exit) {
                            case (1) -> {
                                Runnable ext = new EastExit(EE, ticket);
                                ex.execute(new Visitor(ent, ext, stayHowLong, ticket));
                            }
                            case (2) -> {
                                Runnable ext = new WestExit(WE, ticket);
                                ex.execute(new Visitor(ent, ext, stayHowLong, ticket));
                            }
                        }
                    }
                    case (2) -> {
                        Runnable ent = new NorthEntrance(NE, ticket, stayHowLong);
                        int which_Exit = 1 + rand.nextInt(2);
                        switch (which_Exit) { //random choose exit
                            case (1) -> {
                                Runnable ext = new EastExit(EE, ticket);
                                ex.execute(new Visitor(ent, ext, stayHowLong, ticket));
                            }
                            case (2) -> {
                                Runnable ext = new WestExit(WE, ticket);
                                ex.execute(new Visitor(ent, ext, stayHowLong, ticket));
                            }
                        }
                    }
                }
            }
        }
        ex.shutdown();
        System.out.println("Not accepting anymore");
        // System.exit(0);
    }

    public void closeThis() {
        this.stopProcess = true;
    }
}

class SouthEntrance implements Runnable {

    Entrance se;
    String ticket;
    int stayHowLong;

    Random rand = new Random();
    @Override
    public void run() {
        ExecutorService ex = Executors.newCachedThreadPool();
        try {
            se.pickTurnstile(ticket, stayHowLong);
            Thread.sleep(500); //Simulate time needed to push the turnstile and enter the museum
            se.enterMuseum(ticket, stayHowLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ex.shutdown();
        }
    }

    public SouthEntrance(Entrance se, String ticket, int stayHowLong) {
        this.se = se;
        this.ticket = ticket;
        this.stayHowLong = stayHowLong;
    }
}

class NorthEntrance implements Runnable {

    Entrance ne;
    String ticket;
    int stayHowLong;

    Random rand = new Random();

    @Override
    public void run() {
        ExecutorService ex = Executors.newCachedThreadPool();
        try {
            ne.pickTurnstile(ticket, stayHowLong);
            Thread.sleep(500); //Simulate time needed to push the turnstile and enter the museum
            ne.enterMuseum(ticket, stayHowLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ex.shutdown();
        }
    }

    public NorthEntrance(Entrance ne, String ticket, int stayHowLong) {
        this.ne = ne;
        this.ticket = ticket;
        this.stayHowLong = stayHowLong;
    }
}

class EastExit implements Runnable {

    Exit ee;
    String ticket;

    Random rand = new Random();

    @Override
    public void run() {
        try {
            ee.exitMuseum(ticket);
            Thread.sleep(500); //Simulate time needed to push the turnstile and enter the museum
            ee.exitMuseumComplete(ticket);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public EastExit(Exit ee, String ticket) {
        this.ee = ee;
        this.ticket = ticket;

    }
}

class WestExit implements Runnable {

    Exit we;
    String ticket;
    Random rand = new Random();

    @Override
    public void run() {
        try {
            we.exitMuseum(ticket);
            Thread.sleep(500); //Simulate time needed to push the turnstile and enter the museum
            we.exitMuseumComplete(ticket);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WestExit(Exit we, String ticket) {
        this.we = we;
        this.ticket = ticket;
    }
}

