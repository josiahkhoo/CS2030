package cs2030.simulator;

public class RngGen {

    private RandomGenerator rng;

    public RngGen(int seed, double arrivalRate, double serviceRate, double serviceTime) {
        rng = new RandomGenerator(seed, arrivalRate, serviceRate, serviceTime);
    }

    public double genInterArrivalTime() {
        return rng.genInterArrivalTime();
    }

    public double genServiceTime() {
        return rng.genServiceTime();
    }

    public double genRestPeriod() {
        return rng.genRestPeriod();
    }

    public double genRandomRest() {
        return rng.genRandomRest();
    }

}


   
