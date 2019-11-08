package cs2030.simulator;

class GreedyCustomer extends Customer {

    public GreedyCustomer(double timeArrived) {
        super(timeArrived);
    }

    @Override
    public String toString() {
        return String.format("%s(greedy)", super.toString());
    }

}