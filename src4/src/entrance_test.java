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
		volatile Queue <Ticket> ticketWaitQueue=new LinkedList<>();

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

        public Queue<Ticket> ticketWaitQueue() {
            return ticketWaitQueue;
        }

        public Queue<ticketWaitQueue> sortVistorListByTime(LinkedList<> ticketWaitQueue) {
            for (int i = 0; i < ticketWaitQueue.size(); i++) {
                for (int j = 0; j < ticketWaitQueue.size() - 1; j++) {
                    if (ticketWaitQueue.get(j).buyTime()> docList.get(j + 1).getWaitingList().size()) {
                        Doctor temp = docList.get(j);
                        docList.set(j, docList.get(j + 1));
                        docList.set(j + 1, temp);
                    }
                }
            }
            return docList;
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

        public void waitingListPatientOperation(Doctor doc, Patient p) throws Exception { // should waiting patient lock ?
            lock.lock();
            try {
                while (doc.isConsulting()) {
                    doctorWaitingListPatient.awaitUninterruptibly();
                }
                doc.getWaitingList().remove(p);
                doc.setIsConsulting(true);
                System.out.println("[ " + doc.getDoctorID() + " start consult] ; " + p.getPatientID() + " start consultation by " + doc.getDoctorID());
                p.setEndOfWaitingTime(System.currentTimeMillis() - startTime);
                lock.unlock();
                Thread.sleep(p.getConsultationTime() * 1000);
                lock.lock();
                System.out.println("[ " + doc.getDoctorID() + " finished giving consultation ] ; " + p.getPatientID() + " finish consulting by " + doc.getDoctorID());
                doc.setIsConsulting(false);
                doc.incrementTotalConsultationTime(p.getConsultationTime());
                doctorOperationCondition.signal();
                patientLeaveCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }


        public void TurnStileUsingCondition(TurnStile turnStile) throws Exception {
            lock.lock();
            try {
                while (!turnStile.getWaitingList().isEmpty()) {
                    turnStileEmptyCondition.signalAll();
                    doctorOperationCondition.awaitUninterruptibly();
                }
                if (doc.getTotalNumberOfPatient() % 8 == 0 && doc.getTotalNumberOfPatient() != 0
                        && doc.getWaitingList().isEmpty() && !doc.getIsAvailable()) {
                    System.out.println("[ ------ " + doc.getDoctorID() + " START Resting -------]");
                    lock.unlock();
                    Thread.sleep(15 * 1000);
                    lock.lock();
                    doc.setIsAvailable(true);
                    patientLeaveCondition.signalAll();
                    System.out.println("[ ~~~~~~ " + doc.getDoctorID() + " END Rest ~~~~~ ]");
                }
            } finally {
                lock.unlock();
            }
        }
    


        public void enterMusem(Ticket ticket) throws Exception {
            lock.lock();
            try {
                boolean consul = false;
                getNewAvailableDoctorListOfTheDay(); // this.currentAvailableDoctorList ady sorted according total patient number
                if (isAllClinicWaitingListFull() == true || (!this.commonWaitingList.isEmpty() && p.isIsFirstTime() == true)) {
                    if (p.isIsFirstTime() == true) {
                        this.commonWaitingList.add(p);
                        System.out.println(p.getPatientID() + "enter common waiting list");
                        p.setIsFirstTime(false);
                    }
    
                    // PRINT COMMON WAITING LIST
    //                System.out.println("\n The common waiting list : ");
    //                this.commonWaitingList.stream().forEach((patient) -> {
    //                    System.out.println(patient.getPatientID() + " , ");
    //                });
                    
                    lock.unlock();
                    commonListPatientOperation(p);
                    lock.lock();
                } else {
                    int searchIndex = 0;
                    while (true) {
                        ArrayList<Doctor> doctorListHavingMinTotalNumberPatient = findMinTotalPatientNumberDoctorList(
                                searchIndex);
    
                        // Print DOCTOR THAT HAVE MIN PATIENT
    //                    System.out.println("\n The doc list that have min patient : " + doctorListHavingMinTotalNumberPatient.size() + " ppl");
    //                    doctorListHavingMinTotalNumberPatient.stream().forEach((doctor) -> {
    //                        System.out.println(doctor.getDoctorID() + " , ");
    //                    });
    //                    System.out.println("");
    
                        //HAVE MIN TOTAL # PATIENT DR LIST == 1
                        if (doctorListHavingMinTotalNumberPatient.size() == 1) {
    
                            // IF DR WAITING LIST < 3 & DR. PATIENT # RANGE < 3 || COMMON WAITING LIST ! empty & PATIENT JUST WALK IN 
                            if (doctorListHavingMinTotalNumberPatient.get(0).getWaitingList().size() < 3
                                    && isRangeDifferenceInThree(doctorListHavingMinTotalNumberPatient.get(0).getTotalNumberOfPatient())) {
                                Doctor inchargeDoctor = doctorListHavingMinTotalNumberPatient.get(0);
                                if (!p.isIsFirstTime()) {
                                    this.commonWaitingList.remove(p);
                                } else {
                                    p.setIsFirstTime(false);
                                }
                                inchargeDoctor.addPatient(p);
    
                                //Check and Update Dr availability status after add a patient
                                if (inchargeDoctor.getTotalNumberOfPatient() % 8 == 0) {
                                    inchargeDoctor.setIsAvailable(false);
                                }
                                System.out.println(inchargeDoctor.getDoctorID() + " has min patient");
                                System.out.println(p.getPatientID() + " is assigned to " + inchargeDoctor.getDoctorID());
                                lock.unlock();
                                waitingListPatientOperation(inchargeDoctor, p);
                                lock.lock();
                                consulted = true;
                                break;
                            } else if (!isRangeDifferenceInThree(doctorListHavingMinTotalNumberPatient.get(0).getTotalNumberOfPatient())) {
                                 // DR. PATIENT # RANGE > 3
    //                            System.out.println("Opps Doctor reach the max quota! Wait for other available doctor");
                                if (p.isIsFirstTime()) {
                                    this.commonWaitingList.add(p);
                                    System.out.println(p.getPatientID() + "enter common waiting list");
                                    p.setIsFirstTime(false);
                                }
                                lock.unlock();
                                commonListPatientOperation(p);
                                lock.lock();
                                break;
                            } else {
                                 // DR. PATIENT # RANGE < 3 &  DR WAITING LIST < 3 
    //                            System.out.println("Opps waiting list is full but Range in 3. Go find next doctor!!!!!!!");
                                if (searchIndex + 1 < this.currentAvailableDoctorList.size() && consulted == false) {
                                    searchIndex += 1;
                                } else {
                                    // THIS IS THE LAST AVAILABLE DOCTOR
                                    if (consulted == false) {
                                        if (p.isIsFirstTime() == true) {
                                            this.commonWaitingList.add(p);
                                            System.out.println(p.getPatientID() + "enter common waiting list");
                                            p.setIsFirstTime(false);
                                        }
                                        lock.unlock();
                                        commonListPatientOperation(p);
                                        lock.lock();
                                    }
                                    break;
                                }
                            }
                        } else {
                            //HAVE MIN TOTAL # PATIENT DR LIST > 1
    
                            doctorListHavingMinTotalNumberPatient = sortMinPatientDoctorListByWaitingListNumber(
                                    doctorListHavingMinTotalNumberPatient);
                            
                            if (isRangeDifferenceInThree(
                                    doctorListHavingMinTotalNumberPatient.get(0).getTotalNumberOfPatient())) {
                                for (int i = 0; i < doctorListHavingMinTotalNumberPatient.size(); i++) {
                                    if (doctorListHavingMinTotalNumberPatient.get(i).getWaitingList().size() < 3) {
                                        Doctor inchargeDoctor = doctorListHavingMinTotalNumberPatient.get(i);
                                        if (p.isIsFirstTime() == false) {
                                            this.commonWaitingList.remove(p);
                                        } else {
                                            p.setIsFirstTime(false);
                                        }
                                        inchargeDoctor.addPatient(p);
                                        if (inchargeDoctor.getTotalNumberOfPatient() % 8 == 0) {
                                            inchargeDoctor.setIsAvailable(false);
                                        }
                                        System.out.println(
                                                p.getPatientID() + " is assigned to " + inchargeDoctor.getDoctorID());
                                        lock.unlock();
                                        waitingListPatientOperation(inchargeDoctor, p);
                                        lock.lock();
                                        consulted = true;
                                        break;
                                    }
                                }
                                if (searchIndex
                                        + doctorListHavingMinTotalNumberPatient.size() < this.currentAvailableDoctorList
                                        .size()
                                        && consulted == false) {
                                    searchIndex += doctorListHavingMinTotalNumberPatient.size();
                                } else {
                                    if (consulted == false) {
                                        if (p.isIsFirstTime() == true) {
                                            this.commonWaitingList.add(p);
                                            System.out.println(p.getPatientID() + "enter common waiting list");
                                            p.setIsFirstTime(false);
                                        }
                                        lock.unlock();
                                        commonListPatientOperation(p);
                                        lock.lock();
                                    }
                                    break;
                                }
                            } else {
                                //When all doctor reach max quota
                                if (p.isIsFirstTime()) {
                                    this.commonWaitingList.add(p);
                                    p.setIsFirstTime(false);
                                }
    //                            System.out.println("Opps ALL Doctor reach the max quota! Wait for other available doctor");
                                lock.unlock();
                                commonListPatientOperation(p);
                                lock.lock();
                                break;
                            }
                        }
                    }
                }
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


