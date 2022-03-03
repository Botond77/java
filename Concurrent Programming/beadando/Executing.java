package beadando;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

class Executing extends Thread {
    private  Barber barber;
    private  Client client;
    private  long maxTimeOfExecution;
    private  long minTimeOfExecution;
    private static AtomicLong elapsedTime ; 
    private static AtomicLong minTime ; 
    private static AtomicLong maxTime ; 
    private AtomicInteger countOfSuccesses;

    public Executing(Barber barber, Client client, long maxTimeOfExecution, long minTimeOfExecution) {
        this.barber = barber;
        this.client = client;
        this.maxTimeOfExecution = maxTimeOfExecution;
        this.minTimeOfExecution = minTimeOfExecution;
        this.client.setStatus(Status.IN_PROGRESS);

        minTime= new AtomicLong(maxTimeOfExecution);
        maxTime= new AtomicLong(maxTimeOfExecution);
        countOfSuccesses = new AtomicInteger(0);
        elapsedTime = new AtomicLong(0);
    }
    public Executing(){
        
        countOfSuccesses = new AtomicInteger(0);
        minTime= new AtomicLong(maxTimeOfExecution);
        maxTime= new AtomicLong(maxTimeOfExecution);
        elapsedTime = new AtomicLong(0);
    }
    public void setValues (Barber barber, Client client, long minTimeOfExecution, long maxTimeOfExecution)
    {
        this.barber = barber;
        this.client = client;
        minTime= new AtomicLong(maxTimeOfExecution);
        maxTime= new AtomicLong(maxTimeOfExecution);
        this.maxTimeOfExecution = maxTimeOfExecution;
        this.minTimeOfExecution = minTimeOfExecution;

        if(client!=null)
        this.client.setStatus(Status.IN_PROGRESS);
        
       
    }


    @Override
    public void start() {

        if( client == null)
        {
            System.out.println(this);
            return ;
        }
        else 
        {
  
            try {
                
                Random rand = new Random();
                long workTime = (rand.nextInt(10)+1) * minTimeOfExecution ;
                if( minTime.get() > maxTime.get())
                {
                    
                    maxTime.set(minTime.get()+workTime);
                }
                // Thread.sleep(workTime);
                    
                
                elapsedTime.set(elapsedTime.get()+workTime);
                countOfSuccesses.incrementAndGet();
                //   System.out.println(this+ " --- " + workTime);

                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            client = null;
    }
    public AtomicInteger getCountOfSuccesses()
    {
        return this.countOfSuccesses;
    }
    public Barber getBarber() {
        return this.barber;
    }

    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public long getMaxTimeOfExecution() {
        return this.maxTimeOfExecution;
    }

    public void setMaxTimeOfExecution(long maxTimeOfExecution) {
        this.maxTimeOfExecution = maxTimeOfExecution;
    }

    public long getMinTimeOfExecution() {
        return this.minTimeOfExecution;
    }

    public void setMinTimeOfExecution(long minTimeOfExecution) {
        this.minTimeOfExecution = minTimeOfExecution;
    }

    public static AtomicLong getLengthOfaWork()
    {
        return elapsedTime;
    }
    public void setElapsedTime(long time )
    {
        this.elapsedTime.set(time);
    }
    @Override
    public String toString() {

        if( client ==null)
            return (barber.getName() + " is taking a nap ");

        return (barber.getName() + " is currently giving " +barber.getService().toString()+" service  to " + client.getName()+ 
        " that needs "+ client.getNeeds().toString() + " - clock "+ this.elapsedTime.get());

    }
}
