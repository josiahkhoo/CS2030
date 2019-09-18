import java.util.PriorityQueue;
import java.util.Comparator;

public class Server {
    
    private final Customer customer;
    
    /** Constructor to initalise Server */
    public Server() {
        this.customer = null;
    }
    
    /** Constructor to initalise Server with customer */
    public Server(Customer customer) {
        this.customer = customer;
    }
    
    /** Serve method that serves a customer by taking a customer as in input
     * and returning a new instance of the current Server
     * If it is currently serving a customer, then it will check if the current
     * customer + 1.0 will be less than the newArrival, if it is, then we will
     * serve the new customer and toggle the customer to served, else, we will
     * toggle the customer to leave */
    public Server serve(Customer customer) {
        if (this.customer != null) {
            //gets current completion for current customer
            double currentCompletion= this.customer.getArrivalTime() + 1.0;
            double newArrival = customer.getArrivalTime();
            if (newArrival < currentCompletion) {
                customer.setLeaves();
                return new Server(this.customer);
            }
        }
        customer.setServed();
        return new Server(customer);
    }
}
    
