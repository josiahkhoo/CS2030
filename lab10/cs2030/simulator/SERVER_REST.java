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
        System.out.printf("CREATED: %s REST AT: %.3f\n", server, time);
    }

    public SimState simulate(SimState sim) {
        System.out.printf("SIMULATED: %s REST AT: %.3f\n", server, time);
        sim.simulateServerRest(time, restTime, server, customer);
        return sim;
    }

}