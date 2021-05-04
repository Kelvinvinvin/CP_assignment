
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
        MuseumCon museum;
        TicketSystem ticket;
       // Turnstile[]turnstile=new Turnstile[4];
		volatile Queue <turnstile> turnStileQueue=new Queue<>(4);

       // openTime= (int) Time.valueOf("09:00:00").getTime();
		//closeTime= (int) Time.valueOf("18:00:00").getTime();
        
		
		public entrance(int ticketNum,String direction){
			this.maxSize=4;
            this.direction=direction;
            this.ticketNum=ticketNum;
			this.lock=new ReentrantLock();
			this.notEmpty=lock.newCondition();
			this.notFull=lock.newCondition();
            //Turnstile turnstile[]=new turnstile(4);
			//this.readWriteLock=new ReentrantReadWriteLock();
		}


        public void enterMusem(){
            Random random =new Random();
            int turnStileId=random.nextInt(4)+1;
            String id=direction+"E"+turnStileId;


            while(turnStileQueue.size()){

            }

            for(int a=0;a<this.ticketNum;a++){
                turnstile=new turnstile(museum,ticket, id);
            }

			try {
				
				while(stack.size()>=maxSize) {
			        System.out.println("Thread " + Thread.currentThread().getId() + ": stack is full, waiting to read.stack:"+stack);
					notFull.await();
				}
				
				stack.push(o);
				System.out.println("Thread " + Thread.currentThread().getId()+": finished writing "+o+" to the stack. satch:"+stack);
				notEmpty.signal();
				
			}finally {
				lock.unlock();

			}
        }

		

 
    class turnstile extends Thread{
        int curVistor;
        Mesume museum;
        String turnstileId;

        turnstile(Musem museum,TicketSystem ticket,String turnstileId){
            this.museum=mueseum;
            this.turnstileId=turnstileId;
        }
            
        @Override
        public void run() {
                try{
                    while (mueseum.curVistor<mueseum.curMaxVisit) {
                        Time time=new Time((int) LocalTime.now().getNano());
                        System.out.println(time+" Ticket "+ticket.id+ " entered through Turnstile"+turnstileId+" Staying for "+mueseu.getVistorTime); 
                        museum.curVisitor++;
                        Thread.sleep(500);      
                    }
                 }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    
        }		 
}


