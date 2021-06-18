package PhaseTwo;

public class MasterControl {
	//Entrance
	public final static String entranceOpen = "09:00:00"; 
	public final static String entranceClose = "18:00:00"; 

	public final static int staytime = 60000; //this one is the milisecond

	//ticket
	public final static String ticketOpen = "08:00:00"; 
	public final static String ticketClose = "17:00:00"; 
	
	public final static int subsequentPurchase = 60000; //this one is the milisecond
	public final static int maxTicketPerDay = 900; //Max ticket sold per day
	
	//museum
	public final static int maxVisitorInMuseum = 100; //not more than 100 in museum
	public final static int maxVisitorPerday = 900; // Max visitor per day
	
	
	////GUI size edit:: Purpose is to automatically adjust the other GUI location when adjusting their settings
	//GUI label size
	public final static int textLabelWidth = 120; // Label only width cause there are no word wrapping
	
	//GUI input size (exclude text area)
	public final static int textFieldWidth = 150, textFieldheight = 25; // Text field has the option to change width and height as the output might be long
	
}
