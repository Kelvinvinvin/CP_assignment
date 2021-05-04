public class turnstile extends Thread{
    int curVistor;
    Mesume museum;
    String turnstileId;

    turnstile(Musem museum,TicketSystem ticket,String turnstileId){
        this.museum=museum;
        this.turnstileId=turnstileId;
    }

    public void enter(){

    }

    public void exit(){
        
    }
        
    @Override
    public void run() {
            try{
                //while (mueseum.curVistor<mueseum.curMaxVisit) {
                    Time time=new Time((int) LocalTime.now().getNano());
                    System.out.println(time+" Ticket "+ticket.id+ " entered through Turnstile"+turnstileId+" Staying for "+mueseu.getVistorTime); 
                    museum.curVisitor++;
                    //Thread.sleep(muesem.getVistorTime);      
               // }
             }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }	