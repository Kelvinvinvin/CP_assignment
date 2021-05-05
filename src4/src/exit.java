import assignmnet.ticketSystem.Ticket;

public class exit implements Runnable{
	private Museum m;
	String turnstile_name;
	String ticket_id;
	
	public exit(Museum m, String turnstile_name, Ticket nameTicket) {
		this.m = m;
		this.turnstile_name = turnstile_name;
		this.ticket_id = nameTicket.getTicketId();
	}
	
	public synchronized void  decreaseVisitor() {
		m.changeValue("d");
		System.out.println("\t "+ticket_id + " exited through Turnstile " + turnstile_name);
	}
	
	
	
	public void run() {
		decreaseVisitor();
	}

}