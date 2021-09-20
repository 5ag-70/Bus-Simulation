


import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Passanger extends Thread{
    private Passanger data;
    private final AtomicInteger waiting = new AtomicInteger(0);
    private final Semaphore passanger = new Semaphore(1);  
    private final Semaphore bus = new Semaphore(0);  
    private final Semaphore boarded = new Semaphore(0); 
    
    
    public Passanger(Passanger sharedData) {
    	this.data = sharedData;
    }
     
    public Passanger() {
    	    
    }
    @Override
    public void run(){
    	this.runMethod();
    }
     
    
    public AtomicInteger getWaiting() {
    	
        return waiting;
    }
 
    public Semaphore getRiders_mutex() {
    	return passanger;
    }
 
    public Semaphore getBus() {
    	return bus;
    }
 
    public Semaphore getBoarded() {
    	 return boarded;
    }
    
    private void runMethod(){
        try {
           
            data.getRiders_mutex().acquire();
           
            System.out.println("Acquired Lock on BUS");
             
         
        } catch (InterruptedException ex) {}
            
         
        data.getWaiting().incrementAndGet();
          
        data.getRiders_mutex().release();
        System.out.println("Released Lock on Bus");
         
        try {
            
            data.getBus().acquire();
            // number of Passanger off the Bus , when bus stop
            System.out.println("Bus semaphore acquired by Passanger: "+offPassanger());
             
        } catch (InterruptedException ex) {
            Logger.getLogger(Passanger.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Boards bus");
        data.getBoarded().release();
        // number of Passanger on the Bus , when bus stop
        System.out.println("Boarded Bus semaphore released by Passanger: "+onPassanger());
    }
    
    public static int offPassanger()
    {
    	Random r = new Random();
    	return r.nextInt((50 - 0) + 1);
    }
    
    public static int onPassanger()
    {
    	Random r = new Random();
    	return r.nextInt((30 - 0) + 1);
    }
}