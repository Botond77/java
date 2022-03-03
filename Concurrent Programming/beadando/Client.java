package beadando;

import java.util.HashMap;


public class Client {

    private  HashMap<Specialization, Integer> needs;
    private final String name;
    private Status status;

    public Client(String name, HashMap<Specialization, Integer> needs) {
        this.needs = needs;
        this.name = name;
    }

    public HashMap<Specialization, Integer> getNeeds() {
        return this.needs;
    }

    public void setNeeds (HashMap < Specialization, Integer> needs)
    {
        this.needs= needs;
    }
    public String getName() {
        return this.name;
    }

    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        // System.out.println(status);
        this.status = status;
    }
    @Override
    public String toString()
    {
        return (this.name + " " + this.needs.toString() + " " + this.status.toString()+ "\n");
    }
    
}
