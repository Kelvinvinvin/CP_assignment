// 1. this class is for helping visitors to purchase tickets

// 2. compared with "Mobile App",
// it's better we call it "official website" for purchasing

// 3. it is a common resource I think,
// so dont need to extends thread(runnable).
public class PurMobileApp {
    private Museum museum;

    private int tktSold;

    // I have wrote a method called getIsOpen() from Museum class
    // I'm not sure do we still need the open and close time in PurMobileApp class
    private int openTime;
    private int closeTime;


    public PurMobileApp(Museum m) {
        this.museum = m;
        this.tktSold = 0;


    }

    public synchronized void buyTicket() {
        if (isValid()) {
            this.tktSold += 1;
            System.out.println("Sold a new ticket, today's sales num: " + this.tktSold);

            // we still need to create a real ticket with a timestamps
            // some code()

        }else{
            System.out.println("Your ticket request denied!");
        }

    }

    public boolean isValid() {
        // current sold ticket number < maximum number(900) AND museum is open,
        // then customer could buy ticket, this function will return TURE
        if (this.tktSold < this.museum.getMaxVisit() && this.museum.getIsOpen()) {
            return true;
        } else {
            return false;
        }
    }
}
