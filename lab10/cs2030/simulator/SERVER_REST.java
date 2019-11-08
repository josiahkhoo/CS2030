package cs2030.simulator;

public class SERVER_REST extends Event {

    private final Server server;

    private final double restTime;

    private final Customer customer;

    SERVER_REST(double time, Server server, double restTime, Customer customer) {
        super(time);
        this.server = server;
        this.restTime = restTime;
        this.customer = customer;
    }

    public SimState simulate(SimState sim) {
        sim.simulateServerRest(time, restTime, server, customer);
        return sim;
    }

}