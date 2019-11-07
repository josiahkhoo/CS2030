package cs2030.simulator;

public class SERVER_BACK extends Event {

    private final Server server;

    SERVER_BACK(double time, Server server) {
        super(time);
        System.out.printf("CREATED: %s BACK AT: %.3f\n", server, time);
        this.server = server;
    }

    public SimState simulate(SimState sim) {
        System.out.printf("SIMULATED: %s BACK AT: %.3f\n", server, time);
        sim.simulateServerBack(time, server);
        return sim;
    }

}