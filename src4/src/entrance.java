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
        Ticket ticket;
       // Turnstile[]turnstile=new Turnstile[4];
        private ArrayList<Ticket> ticketWaitList = new ArrayList<>();

        private static Lock lock = new ReentrantLock();
        private final Condition LeaveCondition = lock.newCondition();
        private final Condition turnStileEmptyCondition = lock.newCondition();
        private final Condition TurnStileUsingCondition = lock.newCondition();
    

    
    
		//directio (S,N)
		public entrance(String direction,String timestamp){
			this.maxSize=4;
            this.direction=direction;
//          this.ticket=ticket;
          //  this.ticketNum=ticketNum;
			this.lock=new ReentrantLock();
			this.notEmpty=lock.newCondition();
			this.notFull=lock.newCondition();
            //Turnstile turnstile[]=new turnstile(4);
			//this.readWriteLock=new ReentrantReadWriteLock();
		}


        // public void enterMusem(ticket ticket){
        //     this.ticket=ticket;

        //     Random random =new Random();
        //     int turnStileId=random.nextInt(4)+1;
        //     String id=direction+"E"+turnStileId;

        //     try {
		// 		if(museum.checkOpen) {

        //             while(turnStileQueue.size()<this.maxSize){
        //                 turnStile turnstile=new turnstile(museum,ticket, id);
        //                 turnStileQueue.add(turnstile);
        //             }
                  

        //             for(int a=0;a<this.ticketQueue;a++){
        //                 turnStile turnstile=new turnstile(museum,ticketQueue.deque, id);
        //             }

		// 		}
	
        //     }catch(InterruptedException e){

        //     }
        // }

        public ArrayList<Ticket> ticketWaitList() {
            return ticketWaitList;
        }

        public ArrayList<Ticket> sortVistorListByTime(ArrayList<> Ticket) {
            for (int i = 0; i < ticketWaitList.size(); i++) {
                for (int j = 0; j < ticketWaitList.size() - 1; j++) {
                    if (ticketWaitList.get(j).buyTime().before( ticketWaitList.get(j + 1).buyTime)) {
                        Ticket temp = ticketWaitList.get(j);
                        ticketWaitList.set(j, ticketWaitList.get(j + 1));
                        ticketWaitList.set(j + 1, temp);
                    }
                }
            }
            return ticketWaitList;
        }

        public void vistMuseum(Ticket ticket) throws Exception {
            lock.lock();
            try {
                LeaveCondition.awaitUninterruptibly();
                lock.unlock();
                assignPatient(p);
                lock.lock();
            } finally {
                lock.unlock();
            }
        }

    

		

    // class turnstile extends Thread{
    //     int curVistor;
    //     Mesume museum;
    //     String turnstileId;

    //     turnstile(Musem museum,TicketSystem ticket,String turnstileId){
    //         this.museum=museum;
    //         this.turnstileId=turnstileId;
    //     }
            
    //     @Override
    //     public void run() {
    //             try{
    //                 //while (mueseum.curVistor<mueseum.curMaxVisit) {
    //                     Time time=new Time((int) LocalTime.now().getNano());
    //                     System.out.println(time+" Ticket "+ticket.id+ " entered through Turnstile"+turnstileId+" Staying for "+mueseu.getVistorTime); 
    //                     museum.curVisitor++;
    //                     //Thread.sleep(muesem.getVistorTime);      
    //                // }
    //              }catch (InterruptedException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    
    //     }		 
}


