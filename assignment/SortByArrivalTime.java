public class SortByArrivalTime implements Comparable<Customer> {

    public int compares(Customer customer1, Customer customer2) {
        return customer2.getArrivalTime() - customer1.getArrivalTime();
    }

    public boolean equals(Customer customer1, Customer customer2) {
        return customer2.getArrivalTime() == customer1.getArrivalTime();
    }

}
