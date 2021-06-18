package PhaseTwo;

public class Visitor implements Runnable{
	Thread entrance;
	Thread exit;
	int stayHowLong;
	String ticket;
	
	private int stayTimeMiliseconds = MasterControl.staytime;
	
	@Override
	public void run() {
		try {
			entrance.start();
			entrance.join(); //wait until enter then start count the time stay in museum
			Thread.sleep(stayHowLong * stayTimeMiliseconds); //simulate stay time in museum
			exit.start();
			exit.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Visitor(Runnable entrance, Runnable exit , int stayHowLong, String ticket) {
		this.entrance = new Thread(entrance);
		this.exit = new Thread (exit);
		this.stayHowLong = stayHowLong;
		this.ticket = ticket;
	}
	
}
