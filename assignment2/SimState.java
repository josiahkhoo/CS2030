package cs2030.simulator;

import java.util.function.Function;

/**
 * This class encapsulates all the simulation states. There are four main
 * components: (i) the event queue, (ii) the statistics, (iii) the shop (the
 * servers) and (iv) the event logs.
 */
public class SimState {

    /**
     * The Event class encapsulates information and methods pertaining to a
     * Simulator event. This is an abstract class that should be subclassed into a
     * specific event in the simulator. The {@code simulate} method must be written.
     */
    public class Event implements Comparable<Event> {
        /** The time this event occurs at. */
        private final double time;

        /** The simulation function to apply on the SimState. */
        private final Function<SimState, SimState> function;

        /**
         * Creates an event and initializes it.
         *
         * @param time     The time of occurrence.
         * @param function The function to be applied on the SimState.
         */
        public Event(double time, Function<SimState, SimState> function) {
            this.time = time;
            this.function = function;
        }

        /**
         * Defines natural ordering of events by their time. Events ordered in ascending
         * order of their timestamps.
         *
         * @param other Another event to compare against.
         * @return 0 if two events occur at same time, a positive number if this event
         *         has later than other event, a negative number otherwise.
         */
        public int compareTo(Event other) {
            return (int) Math.signum(this.time - other.time);
        }

        /**
         * The method that simulates this event on the SimState passed in.
         *
         * @param sim The simulator.
         * @return The updated state after simulating this event.
         */
        public SimState simulate(SimState sim) {
            return function.apply(sim);
        }
    }

    /** The priority queue of events. */
    private final PriorityQueue<Event> events;

    /** The statistics maintained. */
    private final Statistics stats;

    /** The shop of servers. */
    private final Shop shop;

    /** The number of customers. */
    private final int numOfCustomers;

    /** The probability of resting. */
    private final double restingProbability;

    /** The probability of a greedy customer. */
    private final double greedyProbability;

    /** The random number generator. */
    private RandomGenerator rng;

    /**
     * Constructor for creating the simulation state from scratch.
     * 
     * @param numOfServers
     * @param maxQueueLength
     * @param numOfSelfCheckout
     * @param numOfCustomers
     * @param restingProbability
     * @param seed
     * @param arrivalRate
     * @param serviceRate
     * @param serviceTime
     * @param greedyProbability
     */
    public SimState(int numOfServers, int maxQueueLength, int numOfSelfCheckout, int numOfCustomers,
            double restingProbability, int seed, double arrivalRate, double serviceRate, double serviceTime,
            double greedyProbability) {
        this.shop = new Shop(numOfServers, numOfSelfCheckout, maxQueueLength);
        this.stats = new Statistics();
        this.events = new PriorityQueue<Event>();
        this.numOfCustomers = numOfCustomers;
        this.restingProbability = restingProbability;
        this.greedyProbability = greedyProbability;
        this.rng = new RandomGenerator(seed, arrivalRate, serviceRate, serviceTime);
    }

    /**
     * Add an event to the simulation's event queue.
     * 
     * @param e The event to be added to the queue.
     * @return The new simulation state.
     */
    public SimState addEvent(Event e) {
        events.add(e);
        return this;
    }

    /**
     * Retrieve the next event with earliest time stamp from the priority queue, and
     * a new state. If there is no more event, an Optional.empty will be returned.
     * 
     * @return A pair object with an (optional) event and the new simulation state.
     */
    private Pair<Event, SimState> nextEvent() {
        Pair<Event, PriorityQueue<Event>> result = this.events.poll();
        return Pair.of(result.first, this);
    }

    /**
     * Log a customer's arrival in the simulation.
     * 
     * @param time The time the customer arrives.
     * @param c    The customer that arrrives.
     * @return A new state of the simulation after the customer arrives.
     */
    private SimState noteArrival(double time, Customer c) {
        System.out.printf("%.3f %s arrives\n", time, c);
        return this;
    }

    /**
     * Log when a customer waits in the simulation.
     * 
     * @param time The time the customer starts waiting.
     * @param s    The server the customer is waiting for.
     * @param c    The customer who waits.
     * @return A new state of the simulation after the customer waits.
     */
    private SimState noteWait(double time, Server s, Customer c) {
        System.out.printf("%.3f %s waits to be served by %s\n", time, c, s);
        return this;
    }

    /**
     * Log when a customer is served in the simulation.
     * 
     * @param time The time the customer arrives.
     * @param s    The server that serves the customer.
     * @param c    The customer that is served.
     * @return A new state of the simulation after the customer is served.
     */
    private SimState noteServed(double time, Server s, Customer c) {
        System.out.printf("%.3f %s served by %s\n", time, c, s);
        stats.serveOneCustomer();
        stats.recordWaitingTime(c.timeWaited(time));
        return this;
    }

    /**
     * Log when a customer is done being served in the simulation.
     * 
     * @param time The time the customer arrives.
     * @param s    The server that serves the customer.
     * @param c    The customer that is served.
     * @return A new state of the simulation after the customer is done being
     *         served.
     */
    private SimState noteDone(double time, Server s, Customer c) {
        System.out.printf("%.3f %s done serving by %s\n", time, c, s);
        return this;
    }

    /**
     * Log when a customer leaves the shops without service.
     * 
     * @param time     The time this customer leaves.
     * @param customer The customer who leaves.
     * @return A new state of the simulation.
     */
    private SimState noteLeave(double time, Customer customer) {
        System.out.printf("%.3f %s leaves\n", time, customer);
        stats.looseOneCustomer();
        return this;
    }

    /**
     * Simulates the logic of what happened when a customer arrives. The customer is
     * either served, waiting to be served, or leaves.
     * 
     * @param time The time the customer arrives.
     * @return A new state of the simulation.
     */
    public SimState simulateArrival(double time) {
        Customer customer = null;
        if (customerIsGreedy()) {
            customer = new GreedyCustomer(time);
        } else {
            customer = new Customer(time);
        }
        noteArrival(time, customer);
        processArrival(time, customer);
        return this;
    }

    /**
     * Handle the logic of finding idle servers to serve the customer, or a server
     * that the customer would wait for depending on its greed, or leave. Called
     * from simulateArrival.
     * 
     * @param time     The time the customer arrives.
     * @param customer The customer to be served.
     * @return A new state of the simulation.
     */
    private SimState processArrival(double time, Customer customer) {
        Server s = shop.findIdleServer();
        if (s != null) {
            serveCustomer(time, s, customer);
            return this;
        }
        if (customer instanceof GreedyCustomer) {
            s = shop.findServerWithShortestQueue();
        } else {
            s = shop.findServerWithNoWaitingCustomer();
        }
        if (s != null) {
            noteWait(time, s, customer);
            s.askToWait(customer);
            return this;
        }
        noteLeave(time, customer);
        return this;
    }

    /**
     * Simulate the logic of what happens when a customer is done being served. The
     * server either serves the next customer or becomes idle, or takes a rest.
     * 
     * @param time     The time the service is done.
     * @param server   The server serving the customer.
     * @param customer The customer being served.
     * @return A new state of the simulation.
     */
    public SimState simulateDone(double time, Server server, Customer customer) {
        noteDone(time, server, customer);
        Customer c = server.getWaitingCustomer();
        if (!(server instanceof SelfCheckout) && serverRests()) {
            double restTime = rng.genRestPeriod();
            addEvent(new Event(time, state -> state.simulateServerRest(time, restTime, server)));
            addEvent(new Event(time + restTime, state -> state.simulateServerBack(time + restTime, server)));
        } else if (c != null) {
            serveCustomer(time, server, c);
            return this;
        }
        server.makeIdle();
        return this;
    }

    /**
     * Simulate the logic of what happened when a server is resting.
     * 
     * @param time     The time the server starts resting.
     * @param restTime The duration of the rest time.
     * @param server   The server taking a break.
     * @return A new state of the simulation.
     */
    public SimState simulateServerRest(double time, double restTime, Server server) {
        server.makeRest(time + restTime);
        return this;
    }

    /**
     * Simulate the logic of what happens when a server is back from its break.
     * 
     * @param time   The time the server is back from its break.
     * @param server The server that was taking a break
     * @return A new state of the simulation.
     */
    public SimState simulateServerBack(double time, Server server) {
        server.makeBack();
        Customer c = server.getWaitingCustomer();
        if (c != null) {
            serveCustomer(time, server, c);
            return this;
        }
        server.makeIdle();
        return this;
    }

    /**
     * Handle the logic of server serving customer. A new done event is generated
     * and scheduled.
     * 
     * @param time     The time this customer is served.
     * @param server   The server serving this customer.
     * @param customer The customer being served.
     * @return A new state of the simulation.
     */
    private SimState serveCustomer(double time, Server server, Customer customer) {
        double doneTime = time + rng.genServiceTime();
        server.serve(customer);
        noteServed(time, server, customer);
        addEvent(new Event(doneTime, state -> state.simulateDone(doneTime, server, customer)));
        return this;
    }

    /**
     * Calculates whether a rest event for a server will occur.
     * 
     * @return true if the server decides to rest, false otherwise.
     */
    private boolean serverRests() {
        return (rng.genRandomRest() < restingProbability);
    }

    /**
     * Calculates whether a greedy customer will be encountered.
     * 
     * @return true if the customer is greedy, false otherwise.
     */
    private boolean customerIsGreedy() {
        return (rng.genCustomerType() < greedyProbability);
    }

    /**
     * The main simulation loop. Repeatedly get events from the event queue,
     * simulate and update the event. Return the final simulation state.
     * 
     * @return The final state of the simulation.
     */
    public SimState run() {
        Pair<Event, SimState> p = nextEvent();
        while (p.first != null) {
            p = p.first.simulate(p.second).nextEvent();
        }
        return p.second;
    }

    /**
     * Populates the simulation with both greedy and normal customers iteratively,
     * in the form of arrival events for each customer.
     * 
     * @return The simulation with every arrival events.
     */
    public SimState populate() {
        boolean firstCustomer = true;
        double arrivalTime = 0;
        for (int i = 0; i < numOfCustomers; i++) {
            if (firstCustomer) {
                firstCustomer = false;
                double time = Double.valueOf(arrivalTime);
                addEvent(new Event(time, state -> state.simulateArrival(time)));
                continue;
            }
            arrivalTime += rng.genInterArrivalTime();
            double time = Double.valueOf(arrivalTime);
            addEvent(new Event(time, state -> state.simulateArrival(time)));
        }
        return this;
    }

    /**
     * Return a string representation of the simulation state, which consists of all
     * the logs and the stats.
     * 
     * @return A string representation of the simulation.
     */
    public String toString() {
        return stats.toString();
    }
}
