package PhaseTwo;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Buyer extends Thread{
	private Museum m;
	private objQueue<String> tq;
	private Ticket t;
    private boolean stopProcess = false;
    private GUI gui;
    private int subsequentPurchaseMiliseconds = MasterControl.subsequentPurchase;
	final int maxticket = MasterControl.maxTicketPerDay;
	
	public Buyer(Museum m, objQueue<String> tq, Ticket t, GUI gui) {
		this.m = m;
		this.tq = tq;
		this.t = t;
		this.gui = gui;
	}

	@Override
	public void run() {
		Random rand = new Random();
		ExecutorService ex = Executors.newFixedThreadPool(1);
		while(t.getTicket_sold() < maxticket && t.fail() < 3 && !stopProcess) {
			try {
				ex.execute(t);
				Thread.sleep((1 + rand.nextInt(4)) * subsequentPurchaseMiliseconds);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(t.fail() >= 3) {
			System.out.println("fail three times");
		}
		if(t.getTicket_sold() >= maxticket) {
			System.out.println("Max ticket sold for the day");
		}
		ex.shutdown();
//        System.exit(0);
	}
        
        public void closeThis (){
            this.stopProcess = true;
        }
	
}
