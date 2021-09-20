

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bus implements Runnable{
    
	
	// number of seats in bus
    private final int SEAT = 50;
    
     
    private final Passanger passanger;
    private final ScheduledExecutorService scheduler;
     
    
    public Bus(Passanger data) {
    	// using threadpool for creating object
    	this.scheduler = Executors.newScheduledThreadPool(5);
        this.passanger = data;
    }
     
   // In this calling inbuild run method of thread class and then call another method 
    public void busActivity(){
    	final Runnable senateBus = new Runnable() {
       public void run() {
    	   runMethod();
       }
     };
      
    
     final ScheduledFuture<?> busHandle =
       scheduler.scheduleWithFixedDelay(senateBus, 10, 20, TimeUnit.SECONDS);
      
    
     scheduler.schedule(new Runnable(){ 
    	 
         public void run() {
         busHandle.cancel(true);
             System.exit(0);
         }
     }, 20, TimeUnit.SECONDS);
    }
 
    @Override
    public void run() {
    	this.runMethod();
    }
     
    
    // Passanger getting off and on the Bus, acquired and release lock on Bus
    private void runMethod(){
        try {
           
        	passanger.getRiders_mutex().acquire();
            System.out.println("Passanger Accquire off the Bus");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Passanger waiting for Bus 
        int boarding_riders_count = Math.min(passanger.getWaiting().get(), SEAT);
        Random r = new Random();
        int rr = r.nextInt((50 - 0) + 1) + 0;
        
        System.out.format("\nWaiting Passanger: %d\n",rr);
        System.out.format("\nBoarding count: %d\n", SEAT-rr);
        for(int i=0; i < boarding_riders_count; i++){
           
        	passanger.getBus().release();
            System.out.println("Bus semaphore released by Bus");
            try {
            	passanger.getBoarded().acquire();
               System.out.println("Boarded semaphore aquired by Bus");
            } catch (InterruptedException ex) {
                Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
 
        int n = passanger.getWaiting().get();
        passanger.getWaiting().set(Math.max(n - 50, 0));
        passanger.getRiders_mutex().release();
       System.out.println("Bus Departs");
    }
}