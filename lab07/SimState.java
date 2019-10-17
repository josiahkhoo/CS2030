import java.util.Optional;
import java.util.function.Function;
/**
 * This class encapsulates all the simulation states.  There are four main
 * components: (i) the event queue, (ii) the statistics, (iii) the shop
 * (the servers) and (iv) the event logs.
 *
 * @author atharvjoshi
 * @author weitsang
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
public class SimState {

    public class Event implements Comparable<Event> {

        private final double time;
        private final Function<SimState,SimState> function;
        
        public Event(double time, Function<SimState,SimState> function) {
            this.time = time;
            this.function = function;
        }

        public int compareTo(Event other) {
            return (int)Math.signum(this.time - other.time);
        }

        public SimState simulate(SimState simState) {
            return function.apply(simState);
        }

    }

    /** The priority queue of events. */
    private final PriorityQueue<Event> events;

    /** The statistics maintained. */
    private final Statistics stats;

    /** The shop of servers. */
    private final Shop shop;

    /** The log maintained. */
    private final String log;
    
    /** The counter for customer id. */
    private final int id;

    /**
     * Constructor for creating the simulation state from scratch.
     * @param numOfServers The number of servers.
     */
    public SimState(int numOfServers) {
        this.shop = new Shop(numOfServers);
        this.stats = new Statistics();
        this.events = new PriorityQueue<Event>();
        this.log = new String();
        this.id = 1;
    }

    public SimState(PriorityQueue<Event> events, Statistics stats, Shop shop, String log, int id) {
        this.events = events;
        this.stats = stats;
        this.shop = shop;
        this.log = log;
        this.id = id;
    }

    /**
     * Add an event to the simulation's event queue.
     * @param  e The event to be added to the queue.
     * @return The new simulation state.
     */
    public SimState addEvent(double time, Function<SimState,SimState> function) {
        Event event = new Event(time, function);
        PriorityQueue<Event> events = this.events.add(event);
        Statistics stats = this.stats;
        Shop shop = this.shop;
        String log = this.log;
        int id = this.id;
        return new SimState(events, stats, shop, log, id);
    }
    
    public SimState increaseCustomerCount() {
        PriorityQueue<Event> events = this.events;
        Statistics stats = this.stats;
        Shop shop = this.shop;
        String log = this.log;
        int id = this.id + 1;
        return new SimState(events, stats, shop, log, id);
    }

    /**
     * Retrieve the next event with earliest time stamp from the
     * priority queue, and a new state.  If there is no more event, an
     * Optional.empty will be returned.
     * @return A pair object with an (optional) event and the new simulation
     *     state.
     */
    public Pair<Optional<Event>, SimState> nextEvent() {
        Pair<Optional<Event>, PriorityQueue<Event>> result = this.events.poll();
        Statistics stats = this.stats;
        Shop shop = this.shop;
        String log = this.log; int id = this.id;
        SimState newState = new SimState(result.second, stats, shop, log, id);
        return Pair.of(result.first, newState);
    }

    /**
     * Log a customer's arrival in the simulation.
     * @param time The time the customer arrives.
     * @param c The customer that arrrives.
     * @return A new state of the simulation after the customer arrives.
     */
    public SimState noteArrival(double time, Customer c) {
        PriorityQueue<Event> events = this.events;
        Statistics stats = this.stats;
        Shop shop = this.shop;
        String log = this.log + String.format("%.3f %s arrives\n", time, c);
        int id = this.id;
        return new SimState(events, stats, shop, log, id);
    }

    /**
     * Log when a customer waits in the simulation.  
     * @param time The time the customer starts waiting.
     * @param s The server the customer is waiting for.
     * @param c The customer who waits.
     * @return A new state of the simulation after the customer waits.
     */
    public SimState noteWait(double time, Server s, Customer c) {
        PriorityQueue<Event> events = this.events;
        Statistics stats = this.stats;
        Shop shop = this.shop;
        String log = this.log + String.format("%.3f %s waits to be served by %s\n", time, c, s);
        int id = this.id;
        return new SimState(events, stats, shop, log, id);
    }

    /**
     * Log when a customer is served in the simulation.  
     * @param time The time the customer arrives.
     * @param s The server that serves the customer.
     * @param c The customer that is served.
     * @return A new state of the simulation after the customer is served.
     */
    public SimState noteServed(double time, Server s, Customer c) {
        PriorityQueue<Event> events = this.events;
        Statistics stats = this.stats.serveOneCustomer().recordWaitingTime(c.timeWaited(time));
        Shop shop = this.shop;
        String log = this.log + String.format("%.3f %s served by %s\n", time, c, s);
        int id = this.id;
        return new SimState(events, stats, shop, log, id);
    }

    /**
     * Log when a customer is done being served in the simulation.
     * @param time The time the customer arrives.
     * @param s The server that serves the customer.
     * @param c The customer that is served.
     * @return A new state of the simulation after the customer is done being
     *     served.
     */
    public SimState noteDone(double time, Server s, Customer c) {
        PriorityQueue<Event> events = this.events;
        Statistics stats = this.stats;
        Shop shop = this.shop;
        String log = this.log + String.format("%.3f %s done serving by %s\n", time, c, s);
        int id = this.id;
        return new SimState(events, stats, shop, log, id);
    }

    /**
     * Log when a customer leaves the shops without service.
     * @param  time  The time this customer leaves.
     * @param  customer The customer who leaves.
     * @return A new state of the simulation.
     */
    public SimState noteLeave(double time, Customer customer) {
        PriorityQueue<Event> events = this.events;
        Statistics stats = this.stats.looseOneCustomer();
        Shop shop = this.shop;
        String log = this.log + String.format("%.3f %s leaves\n", time, customer);
        int id = this.id;
        return new SimState(events, stats, shop, log, id);
    }

    private SimState replaceShopServer(Server server) {
        Shop shop = this.shop.replace(server);
        PriorityQueue<Event> events = this.events;
        Statistics stats = this.stats;
        String log = this.log;
        int id = this.id;
        return new SimState(events, stats, shop, log, id);
    }

    /**
     * Simulates the logic of what happened when a customer arrives.
     * The customer is either served, waiting to be served, or leaves.
     * @param time The time the customer arrives.
     * @param id The id of customer who arrives.
     * @return A new state of the simulation.
     */
    public SimState simulateArrival(double time) {
        int id = this.id;
        Customer customer = new Customer(time, id);
        return this.increaseCustomerCount()
            .noteArrival(time, customer).processArrival(time, customer);
    }

    /**
     * Handle the logic of finding idle servers to serve the customer, 
     * or a server that the customer can wait for, or leave.  Called
     * from simulateArrival.
     * @param time The time the customer arrives.
     * @param customer The customer to be served.
     * @return A new state of the simulation.
     */
    public SimState processArrival(double time, Customer customer) {
        Optional<Server> s1 = shop.find(server -> server.isIdle());
        if (s1.isPresent()) {
            Server server = s1.get();
            server = server.serve(customer);
            return this.replaceShopServer(server).serveCustomer(time, server, customer);
        }
        Optional<Server> s2 = shop.find(server -> !(server.hasWaitingCustomer()));
        if (s2.isPresent()) {
            Server server = s2.get();
            server = server.askToWait(customer);
            return this.replaceShopServer(server).noteWait(time, server, customer);
        }
        return this.noteLeave(time, customer);
    }

    /**
     * Simulate the logic of what happened when a customer is done being
     * served.  The server either serve the next customer or becomes idle.
     * @param time The time the service is done.
     * @param server The server serving the customer.
     * @param customer The customer being served.
     * @return A new state of the simulation.
     */
    public SimState simulateDone(double time, Server server, Customer customer) {
        Server prevServer = server;
        Server matchServer = this.shop.find(x -> x.hashCode() == prevServer.hashCode()).get();
        Optional<Customer> c = matchServer.getWaitingCustomer();
        if (c.isPresent()) {
            return this.noteDone(time, matchServer, customer).serveCustomer(time, matchServer, c.get());
        }
        matchServer = matchServer.makeIdle();
        return this.replaceShopServer(matchServer).noteDone(time, matchServer, customer);
    }

    /**
     * Handle the logic of server serving customer.  A new done event
     * is generated and scheduled.
     * @param  time  The time this customer is served.
     * @param  server The server serving this customer.
     * @param  customer The customer being served.
     * @return A new state of the simulation.
     */
    public SimState serveCustomer(double time, Server server, Customer customer) {
        double doneTime = time + Simulation.SERVICE_TIME;
        Server updatedServer = server.serve(customer);
        return this.replaceShopServer(updatedServer)
            .noteServed(time, updatedServer, customer)
            .addEvent(doneTime, state -> state.simulateDone(doneTime, updatedServer, customer));
    }

    /**
     * The main simulation loop.  Repeatedly get events from the event
     * queue, simulate and update the event.  Return the final simulation
     * state.
     * @return The final state of the simulation.
     */
    public SimState run() {
        Pair<Optional<Event>, SimState> p = nextEvent();
        if (p.first.isPresent()) {
            return p.first.get().simulate(p.second).run();
        }
        return p.second;
    }

    /**
     * Return a string representation of the simulation state, which
     * consists of all the logs and the stats.
     * @return A string representation of the simulation.
     */
    public String toString() {
        return this.log + stats.toString();
    }

}
