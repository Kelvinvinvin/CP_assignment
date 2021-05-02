public class Museum {
    //for daily open time
    private int openTime;
    private int closeTime;
    private boolean isOpen;

    //for daily maximum visitors and current tickets sold
    private int maxVisit;
    private int curVisit;


    // one time maximum visitors (100)
    private int cap;

    // Entrance and Exit
    private SouthEntrance se;
    private NorthEntrance ne;
    private EastExit ee;
    private WestExit we;

    public Museum() {

    }

    public int getCurVisit() {
        return this.curVisit;
    }

    public int getMaxVisit() {
        return this.maxVisit;
    }

    public void setIsOpen(int CurTime) {
        if (CurTime < closeTime && CurTime > openTime) {
            this.isOpen = true;
        } else {
            this.isOpen = false;
        }
    }

    public boolean getIsOpen() {
        // I just set it up to "9" optionally,
        // normally we need to use StringFormat to get the PC time
        int CurTime = 9;
        setIsOpen(CurTime);
        return this.isOpen;
    }


}

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