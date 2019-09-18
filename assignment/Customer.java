public class Customer {

    private final int id;
    private final double arrivalTime;
    private Status status;
    
    /** Constructor to initialise a Customer */
    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.status = Status.ARRIVES;
    }
    
    /** Getter for arrivalTime */
    public double getArrivalTime() {
        return this.arrivalTime;
    }

    /** Getter for ID */
    public double getId() {
        return this.id;
    }
    
    /** Setter for status of this instant of Customer to be SERVED */
    public void setServed() {
        this.status = Status.SERVED;
    }
    
    /** Setter for status of this instant of Customer to be LEAVES */
    public void setLeaves() {
        this.status = Status.LEAVES;
    }

    @Override
    public String toString() {
        String output = new String();
        output = String.format("%.3f %d %s", this.arrivalTime, this.id,
               this.status); 
        return output;
    }
}
