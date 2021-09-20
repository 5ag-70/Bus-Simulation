

public class BusSimulation {
     
    private static final Passanger sharedData = new Passanger();
     
    public void runSimulation()
    {
    	// create bus object and calling its activity
        new Bus(sharedData).busActivity();
                 
        for(int i=1; i <= 5 ; i++){
        	
        	// creating Passanger thread in for station
        	new Passanger(sharedData).start();
        }
    }
    
    public static void main(String[] args){
    	new BusSimulation().runSimulation();
    }
}	