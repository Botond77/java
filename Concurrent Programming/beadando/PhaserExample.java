package beadando;


public class PhaserExample {

    

    public static void main(String[] args) {

        long maxTimeOfExecution;
        long minTimeOfExecution;
        long lenghthOfaDay;
        Integer lenghthOfaWeek;
        int numberOfBarbers = 5;


        lenghthOfaWeek = 5;
        lenghthOfaDay = 9600;
        maxTimeOfExecution= 200 ;
        minTimeOfExecution= 20;

        // System.out.println(minTimeOfExecution);


        Barbery barbery = new Barbery(numberOfBarbers, lenghthOfaDay, lenghthOfaWeek, minTimeOfExecution, maxTimeOfExecution);

    }



}
