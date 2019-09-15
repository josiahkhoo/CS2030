public class Customer {

    private final int id;
    private final double arrivalTime;
    
    /** Constructor to initialise a Customer */
    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }
    
    /** Getter for arrivalTime */
    public double getArrivalTime() {
        return this.arrivalTime;
    }

    @Override
    public String toString() {
        String output = new String();
        output += id;
        output += " arrives at ";
        output += String.format("%.3f", arrivalTime);
        return output;
    }
}
