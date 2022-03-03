package beadando;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.JSpinner.ListEditor;

public class Barbery {
    private HashMap<ServiceAborted, Integer> servicesAborted;
    private final long lenghthOfaDay;
    private final Integer lenghthOfaWeek;
    private final int numberOfBarbers;
    private final ArrayList<Barber> barbers;
    private final long maxTimeOfExecution;
    private final long minTimeOfExecution;
    private ConcurrentLinkedDeque<Client> clients;
    private ConcurrentLinkedDeque<Client> abortedClients;
    private final Integer availableSeats;

    public Barbery(int numberOfBarbers, long lenghtOfaDay, Integer lengthOfaWeek, long minTimeOfExecution,
            long maxTimeOfExecution) {
        this.numberOfBarbers = numberOfBarbers;
        this.lenghthOfaDay = lenghtOfaDay;
        this.lenghthOfaWeek = lengthOfaWeek;
        this.maxTimeOfExecution = maxTimeOfExecution;
        this.minTimeOfExecution = minTimeOfExecution;
        availableSeats = 5;
        barbers = getBarbers();
        ArrayList<Executing> listOfExecutions = new ArrayList<Executing>();
        abortedClients = new ConcurrentLinkedDeque<Client>();
        clients = new ConcurrentLinkedDeque<Client>();

        for (Barber b : barbers) {
            b.register();
            b.arriveAndAwaitAdvance();
            b.arriveAndDeregister();

            listOfExecutions.add(new Executing());
        }

        long minutes = 0;
        int days = 0;

        clients = getClientQueue(4);
        HashMap<String, Integer> dayliStats = new HashMap<String, Integer>();
        long elapsedTime  = 0 ;
        int success= 0 ;
        while (days < lengthOfaWeek) {
            clients = getClientQueue(4);
            clients = throwClients(Status.CLOSED);
            while (minutes + maxTimeOfExecution < lenghtOfaDay + 1) {

                clients = getClientQueue(10);
                clients = throwClients(Status.ABORTED);
                for (int j = 0; j < listOfExecutions.size() && minutes + maxTimeOfExecution < lenghtOfaDay + 1; j++) {

                    if (!clients.isEmpty()) {
                        Client client = clients.pop();
                        if (checkServiceAndNeedsMatch(barbers.get(j).getService(), client.getNeeds())
                                && client.getStatus().equals(Status.AWAITING)) {
                            listOfExecutions.get(j).setValues(barbers.get(j), client, minTimeOfExecution,
                                    maxTimeOfExecution);
                            listOfExecutions.get(j).start();
                        } else {
                            if (!clients.isEmpty()) {

                                clients.addLast(client);
                            } else
                                clients.add(client);

                        }

                    } else {
                        // listOfExecutions.get(j).setValues(barbers.get(j), null, minTimeOfExecution,
                        // maxTimeOfExecution);
                    }
                    minutes = listOfExecutions.get(0).getLengthOfaWork().get();
                }

            }
            dayliStats.put(" Day "+ (int)(days+1) , listOfExecutions.get(0).getCountOfSuccesses().get());
            success += listOfExecutions.get(0).getCountOfSuccesses().get();
            //  System.out.println("Day " + days + " is done");
            days++;
            elapsedTime += listOfExecutions.get(0).getLengthOfaWork().get();
            listOfExecutions.get(0).setElapsedTime(0);
            minutes = 0;
        }

        HashMap<Status, Integer> failedServices = new HashMap<Status, Integer>();
        failedServices.put(Status.CLOSED, clients.size());
        failedServices.put(Status.ABORTED, 0);
        
        
        for (int i = 0; i < abortedClients.size(); i++) {

            if(abortedClients.getFirst().getStatus().equals(Status.ABORTED))
            {
                failedServices.put(Status.ABORTED, failedServices.get(Status.ABORTED)+1);
            }
            else
            {
                failedServices.put(Status.CLOSED, failedServices.get(Status.CLOSED)+1);

            }
            abortedClients.remove();
        }

        System.out.println("1. Number of sucess > " + listOfExecutions.get(0).getCountOfSuccesses());
        System.out.println("2. Number of fails and reasons " + "\n "+ failedServices.toString());
        System.out.println("3. Dayli stats > " + dayliStats.toString());
        if( days != 0 )
        {
           
            System.out.println("4. AVG Elapsed Time > " +(float) (elapsedTime/ (success)) );
        }

    }

    private ConcurrentLinkedDeque<Client> getClientQueue(int percent) {
        Random rand = new Random();
        int clientNUm = rand.nextInt(percent);
        // System.out.println(" ClientNUm " + clientNUm);
        for (int i = 0; i < clientNUm; i++) {
            Client c = randomClient(i);
            if (clients.size() != 0)
                this.clients.addLast(c);
            else
                this.clients.add(c);
        }

        return clients;
    }

    private ConcurrentLinkedDeque<Client> throwClients(Status status) {
        int i = 0;
        while (this.clients.size() >= availableSeats && i < this.clients.size()) {
            Client client = this.clients.pop();
            if (i < availableSeats) {
                clients.addLast(client);

            } else {
                client.setStatus(status);
                abortedClients.add(client);
            }
            i++;
        }
        return this.clients;
    }

    private Boolean checkServiceAndNeedsMatch(HashMap<Specialization, Integer> serviceMap,
            HashMap<Specialization, Integer> needMap) {
        for (HashMap.Entry<Specialization, Integer> sm : serviceMap.entrySet()) {
            for (HashMap.Entry<Specialization, Integer> nm : needMap.entrySet()) {
                if (!nm.getKey().equals(sm.getKey())) {
                    return false;
                }
            }

        }
        return true;
    }

    private ArrayList<Barber> getBarbers() {
        ArrayList<Barber> barbers = new ArrayList<Barber>();
        for (int i = 0; i < numberOfBarbers; i++) {
            barbers.add(getBarber(i));

        }
        return barbers;
    }

    private Barber getBarber(int idx) {

        Random rand = new Random();
        int i = rand.nextInt(2);
        HashMap<Specialization, Integer> service = new HashMap<Specialization, Integer>();
        service.put(Specialization.values()[i], (i + 2) * 300);

        Barber barber = new Barber("Barber " + idx, service);
        return barber;
    }

    public HashMap<ServiceAborted, Integer> getServicesAborted() {
        return this.servicesAborted;
    }

    public void setServicesAborted(HashMap<ServiceAborted, Integer> servicesAborted) {
        this.servicesAborted = servicesAborted;
    }

    private Client randomClient(int i) {

        HashMap<Specialization, Integer> need = new HashMap<Specialization, Integer>();

        Random rand = new Random();
        int idx = rand.nextInt(2);
        need.put(Specialization.values()[idx], (idx + 2) * 300);

        String NameOfTheClient = "Client " + i;
        Client client = new Client(NameOfTheClient, need);
        client.setStatus(Status.AWAITING);
        return client;
    }

}
