/**
 * This is a customer class that will be used in the simulator.
 */
public class Customer {

    private final int id;
    private double time;
    private Status status;
    private int serverId;
    
    /**
     * Creates a Customer with the specified ID and arrival time.
     * @param  id           id assigned to the customer
     * @param  arrivalTime  initalise time assigned to the customer
     */
    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.time = arrivalTime;
        this.status = Status.ARRIVES;
        this.serverId = 0;
    }
    
    /**
     * This method returns the current time assigned to this customer.
     * @return  the time assigned to this customer
     */
    public double getTime() {
        return this.time;
    }

    /**
     * This method returns this customer's ID.
     * @return  id assigned to this customer
     */
    public double getId() {
        return this.id;
    }
    
    /**
     * This method return this customer's status.
     * @return  the status of this customer
     */
    public Status getStatus() {
        return this.status;
    }
    
    /**
     * This method sets the status of this customer to {@link Status#ARRIVES}.
     */
    public void setArrive() {
        this.status = Status.ARRIVES;
    }
    
    /**
     * This method sets the status of this customer to {@link Status#WAITS} and
     * assigns the specified serverId to the customer.
     * @param   serverId    id of the server
     */
    public void setWait(int serverId) {
        this.serverId = serverId;
        this.status = Status.WAITS;
    }

    /**
     * This method sets the time variable of this customer to an
     * absolute {@link double}.
     * @param   time    the new time to set on the customer
     */
    public void setTime(double time) {
        this.time = time;
    }
    
    /**
     * This method returns this customer's serverId.
     * @return  the serverId of this customer
     */
    public int getServerId() {
        return this.serverId;
    }

    /**
     * This method sets the status of this customer to {@link Status#SERVED}
     * and takes a serverId as a parameter and assigned this customer's
     * serverId to the serverId parameter.
     * @param   serverId    serverId of the serverId
     */
    public void setServed(int serverId) {
        this.serverId = serverId;
        this.status = Status.SERVED;
    }
    
    /**
     * This method sets the status of this customer to {@link Status#DONE}
     * and increases this customer's time by 1.0.
     */
    public void setDone() {
        this.time += 1.0;
        this.status = Status.DONE;
    }
    
    /**
     * This method sets the status of this customer to {@link Status#LEAVES}.
     */
    public void setLeaves() {
        this.status = Status.LEAVES;
    }
    
    /**
     * This method returns true if the status of this customer is {@link Status#SERVED}
     * else it will return false.
     */
    public Boolean isServed() {
        if (this.status == Status.SERVED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method returns true if the status of this customer is {@link Status#DONE}
     * else it will return false.
     */
    public Boolean isDone() {
        if (this.status == Status.DONE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method returns true if the status of this customer is
     * {@link Status#DONE} else it will return false.
     */
    public Boolean isArrive() {
        if (this.status == Status.ARRIVES) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method overrides the toString() method of Object and returns a 
     * string in the format of {@link Customer#time} {@link Customer#id} 
     * {@link Customer#status} and appends {@link Customer#serverId} if it is
     * not null to the front of the string.
     * @return  String
     */
    @Override
    public String toString() {
        String output = new String();
        output = String.format("%.3f %d %s", this.time, this.id, 
            this.status.getMessage());
        if (this.serverId != 0) {
            output += String.format(" %d", this.serverId);
        }
        return output;
    }
}
