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

    public Server serve(Customer customer) {
        if (this.customer != null) {
            double currentCompletion= this.customer.getArrivalTime() + 1.0;
            double newArrival = customer.getArrivalTime();
            if (newArrival < currentCompletion) {
                System.out.println("Customer leaves");
                return new Server(this.customer);
            }
        }
        String string = new String("Customer served; next service @ ");
        string += (customer.getArrivalTime() + 1.0);
        System.out.println(string);
        return new Server(customer);
    }
}

        

    


