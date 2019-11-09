package cs2030.simulator;

/**
 * The GreedyCustomer class encapsulates information and methods pertaining to a
 * GreedyCustomer in a simulation. GreedyCustomer is a child of the Customer
 * class.
 */
class GreedyCustomer extends Customer {

    /**
     * Create and initalize a new greedy customer. The {@code id} of the greedy
     * customer is set.
     *
     * @param timeArrived The time this greedy customer arrived in the simulation.
     */
    public GreedyCustomer(double timeArrived) {
        super(timeArrived);
    }

    /**
     * Return a string representation of this greedy customer.
     * 
     * @return The id of the customer prefixed with (greedy).
     */
    @Override
    public String toString() {
        return String.format("%s(greedy)", super.toString());
    }

}