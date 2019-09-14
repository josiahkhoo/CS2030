public class Customer {

    private final int id;
    private final double arrivalTime;

    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        String output = new String();
        output += id;
        output += " arrives at ";
        output += String.format("%.3f", arrivalTime);
        return output;
    }
}
