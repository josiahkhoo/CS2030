package cs2030.simulator;

public class SERVER_BACK extends Event {

    private final Server server;

    SERVER_BACK(double time, Server server) {
        super(time);
        this.server = server;
    }

    public SimState simulate(SimState sim) {
        sim.simulateServerBack(time, server);
        return sim;
    }

}