package cs2030.simulator;

/**
 * The Customer class encapsulates information and methods pertaining to a
 * Customer in a simulation.
 */
class Customer {

    /** The unique ID of the last created customer. */
    private static int lastCustomerId = 1;

    /** The unique ID of this customer. */
    private final int id;

    /** The time this customer arrives. */
    private final double timeArrived;

    /**
     * Create and initalize a new customer. The {@code id} of the customer is set.
     *
     * @param timeArrived The time this customer arrived in the simulation.
     */
    public Customer(double timeArrived) {
        this.timeArrived = timeArrived;
        this.id = Customer.lastCustomerId;
        Customer.lastCustomerId++;
    }

    /**
     * Return the waiting time of this customer.
     * 
     * @return The waiting time of this customer.
     */
    public double timeWaited(double t) {
        return t - timeArrived;
    }

    /**
     * Return a string representation of this customer.
     * 
     * @return The id of the customer.
     */
    public String toString() {
        return Integer.toString(this.id);
    }

    /**
     * Checks if two customers have the same id.
     * 
     * @param obj Another objects to compared against.
     * @return true if obj is a customers with the same id; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Customer) {
            Customer c = (Customer) obj;
            return c.id == this.id;
        }
        return false;
    }

    /**
     * Return the hashcode for this customer.
     * 
     * @return the ID of this customer as its hashcode.
     */
    @Override
    public int hashCode() {
        return id;
    }
}
