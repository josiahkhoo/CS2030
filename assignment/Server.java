import java.util.PriorityQueue;
import java.util.Comparator;

public class Server {
    
    private Customer customer;
    private Customer waiting;
    
    /** Constructor to initalise Server */
    public Server() {
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
        waitingCustomer.setServed();
    }
    
    public void serve(Customer customer) {
       if (this.waiting == customer) {
            System.out.println("called serve");  
            this.waiting = null;
            this.customer = customer;
        } else {
        this.customer = customer;
        customer.setServed();
        }
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

    public void makeDone(Customer customer) {
        customer.setDone();
    }

    public void makeWait(Customer customer) {
        this.waiting = customer;
        customer.setWait();
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

    
