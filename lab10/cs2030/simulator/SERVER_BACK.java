package cs2030.simulator;

public class SERVER_BACK extends Event {

    private final Server server;

    SERVER_BACK(double time, Server server) {
        super(time);
        this.server = server;
        System.out.println("BACK");
    }

    public SimState simulate(SimState sim) {
        sim.simulateServerBack(server);
        return sim;
    }

}