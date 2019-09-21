import java.util.PriorityQueue;
import java.util.Comparator;

public class Server {

    private int id;    
    private Customer customer;
    private Customer waiting;
    
    /** Constructor to initalise Server */
    public Server(int id) {
        this.id = id;
        this.customer = null;
        this.waiting = null;
    }
    
    public Customer getCustomer() {
        return this.customer;
    }

    public Customer getWaiting() {
        return this.waiting;
    }

    public Double adjustWaitingTime(Customer waitingCustomer) {
        Double waitingTime = this.customer.getTime() - waitingCustomer.getTime();
        waitingCustomer.setTime(this.customer.getTime());
        return waitingTime;
    }

    public void serveFuture(Customer waitingCustomer) {
        waitingCustomer.setServed(this.id);
    }
    
    public void serve(Customer customer) {
        this.customer = customer;
        customer.setServed(this.id);
    }

    public void makeLeave(Customer customer) {
        release(customer);
        customer.setLeaves();
    }

    public Boolean isServing(Customer customer) {
        if (this.customer == customer) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isWaiting(Customer customer) {
        if (this.waiting == customer) {
            return true;
        } else {
            return false;
        }
    }

    public int getId() {
        return this.id;
    }

    public void makeDone(Customer customer) {
        customer.setDone();
    }

    public void makeWait(Customer customer) {
        this.waiting = customer;
        customer.setWait(this.id);
    }

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

    
