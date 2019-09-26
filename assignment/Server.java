import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * This is a server class that will be used in the simulator.
 */
public class Server {

    private int id;    
    private Customer customer;
    private Customer waiting;
    
    /**
     * Creates a Server with the specified ID.
     * @param   id  id assigned to the server
     */
    public Server(int id) {
        this.id = id;
        this.customer = null;
        this.waiting = null;
    }
    
    /**
     * This method returns the current customer of this server.
     * @return  {@link Server#customer} 
     */
    public Customer getCustomer() {
        return this.customer;
    }

    /**
     * This method returns the current waiting customer of this server.
     * @return {@link Server#waiting}
     */
    public Customer getWaiting() {
        return this.waiting;
    }
    
    /**
     * This method adjusts the waiting time of the specified waiting customer 
     * to the finished time of the current customer.
     * @param   waitingCustomer the waiting customer
     */
    public Double adjustWaitingTime(Customer waitingCustomer) {
        Double waitingTime = this.customer.getTime() - waitingCustomer.getTime();
        waitingCustomer.setTime(this.customer.getTime());
        return waitingTime;
    }
    
    /**
     * This method serves the specified waiting customer
     * in the future.
     * @param   waitingCustomer the waiting customer
     */
    public void serveFuture(Customer waitingCustomer) {
        waitingCustomer.setServed(this.id);
    }
 
    /**
     * This method serves the specified customer.
     * @param   customer    the specified customer
     */
    public void serve(Customer customer) {
        this.customer = customer;
        customer.setServed(this.id);
    }
    
    /**
     * This method makes the specified customer leave.
     * @param   customer    the specified customer
     */
    public void makeLeave(Customer customer) {
        release(customer);
        customer.setLeaves();
    }
    
    /**
     * This method returns true if the specified customer is
     * being served by this server else return false.
     * @param   customer    the specified customer
     */
    public Boolean isServing(Customer customer) {
        if (this.customer == customer) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method returns true if the specified customer is
     * waiting to be served by this server else return false.
     * @param   customer    the specified customer
     */
    public Boolean isWaiting(Customer customer) {
        if (this.waiting == customer) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * This method returns the id of this server.
     * @return {link Server#id}
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * This method makes the specified customer done.
     * @param   customer    the specified customer
     */
    public void makeDone(Customer customer) {
        customer.setDone();
    }
    
    /**
     * This method makes the specified customer wait.
     * @param   customer    the specified customer
     */
    public void makeWait(Customer customer) {
        this.waiting = customer;
        customer.setWait(this.id);
    }
    
    /**
     * This method releases the specified customer from
     * this server.
     * @param   customer    the specified customr
     */
    public void release(Customer customer) {
        if (isServing(customer)) {
            this.customer = null;
            if (! isWaiting(null)) {
                this.customer = this.waiting;
                this.waiting = null;
            }
        } else if (isWaiting(customer)) {
            this.waiting = null;
        }
    }
}

    
