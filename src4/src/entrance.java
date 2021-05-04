import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.sql.Time;
import java.time.LocalTime;

public class entrance{
		
		int maxSize,curVistor,ticketNum;
        String direction;
        Museum museum;
        TicketSystem ticket;
       // Turnstile[]turnstile=new Turnstile[4];
		volatile Queue <Ticket> ticketQueue=new LinkedList<>();

    
		
		public entrance(String direction,TicketSystem ticket){
			this.maxSize=4;
            this.direction=direction;
            this.ticket=ticket;
          //  this.ticketNum=ticketNum;
			this.lock=new ReentrantLock();
			this.notEmpty=lock.newCondition();
			this.notFull=lock.newCondition();
            //Turnstile turnstile[]=new turnstile(4);
			//this.readWriteLock=new ReentrantReadWriteLock();
		}


        public void enterMusem(){
        
        }

		

    class turnstile extends Thread{
        int curVistor;
        Mesume museum;
        String turnstileId;

        turnstile(Musem museum,TicketSystem ticket,String turnstileId){
            this.museum=museum;
            this.turnstileId=turnstileId;
        }
            
        @Override
        public void run() {
                try{
                    //while (mueseum.curVistor<mueseum.curMaxVisit) {
                        Time time=new Time((int) LocalTime.now().getNano());
                        System.out.println(time+" Ticket "+ticket.id+ " entered through Turnstile"+turnstileId+" Staying for "+mueseu.getVistorTime); 
                        museum.curVisitor++;
                        Thread.sleep(500);      
                   // }
                 }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    
        }		 
}


