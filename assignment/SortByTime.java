import java.util.Comparator;

public class SortByTime implements Comparator<Customer> {

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

    public boolean equals(Customer customer1, Customer customer2) {
        return customer2.getTime() == customer1.getTime();
    }

}
