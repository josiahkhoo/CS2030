import java.util.Comparator;

/**
 * This is a SortByTime class that wlil be used in the PriorityQueue
 * in Simulator.
 */
public class SortByTime implements Comparator<Customer> {

    /**
     * This method implements the compare method in Comparator.
     * @param   customer1   first customer
     * @param   customer2   second customer
     */
    public int compare(Customer customer1, Customer customer2) {
        if (customer2.getTime() < customer1.getTime()) {
            return 1;
        } else if (customer2.getTime() > customer1.getTime()) {
            return -1;
        } else if (customer2.getId() > customer1.getId()) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * This method implements the equals method in Comparator.
     * @param   customer1   first customer
     * @param   customer2   second customer
     */
    public boolean equals(Customer customer1, Customer customer2) {
        return customer2.getTime() == customer1.getTime();
    }

}
