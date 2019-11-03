package cs2030.simulator;

public class SERVER_REST extends Event {

    private final Server server;

    private final double restTime;

    SERVER_REST(double time, Server server, double restTime) {
        super(time);
        this.server = server;
        this.restTime = restTime;
    }

    public SimState simulate(SimState sim) {
        sim.simulateServerRest(time + restTime, server);
        return sim;
    }

}