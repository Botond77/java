package beadando;

import java.util.HashMap;
import java.util.concurrent.Phaser;


public class Barber extends Phaser {
    private final HashMap<Specialization, Integer> service;
    private final String name;
    private int ServicesDone;

    public Barber(String name, HashMap<Specialization, Integer> service) {
        this.name = name;
        this.service = service;

    }

    public HashMap<Specialization, Integer> getService() {
        return this.service;
    }

    public String getName() {
        return this.name;
    }

    public int getServicesDone() {
        return this.ServicesDone;
    }

    public void setServicesDone(int ServicesDone) {
        this.ServicesDone = ServicesDone;
    }

}
