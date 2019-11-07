package cs2030.simulator;

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

  /** The random number generator. */
  private RngGen rng;

  /**
   * Constructor for creating the simulation state from scratch.
   * @param numOfServers The number of servers.
   * @param rng The random number generator.
   */
  public SimState(int numOfServers, int maxQueueLength, int numOfCustomers, double restingProbability, RngGen rng) {
    this.shop = new Shop(numOfServers, maxQueueLength);
    this.stats = new Statistics();
    this.events = new PriorityQueue<Event>();
    this.numOfCustomers = numOfCustomers;
    this.restingProbability = restingProbability;
    this.rng = rng;
  }

  /**
   * Add an event to the simulation's event queue.
   * @param  e The event to be added to the queue.
   * @return The new simulation state.
   */
  public SimState addEvent(Event e) {
    events.add(e);
    return this;
  }

  /**
   * Retrieve the next event with earliest time stamp from the
   * priority queue, and a new state.  If there is no more event, an
   * Optional.empty will be returned.
   * @return A pair object with an (optional) event and the new simulation
   *     state.
   */
  private Pair<Event, SimState> nextEvent() {
    Pair<Event, PriorityQueue<Event>> result = this.events.poll();
    return Pair.of(result.first, this);
  }

  /**
   * Log a customer's arrival in the simulation.
   * @param time The time the customer arrives.
   * @param c The customer that arrrives.
   * @return A new state of the simulation after the customer arrives.
   */
  private SimState noteArrival(double time, Customer c) {
    System.out.printf("%.3f %s arrives\n", time, c);
    return this;
  }

  /**
   * Log when a customer waits in the simulation.  
   * @param time The time the customer starts waiting.
   * @param s The server the customer is waiting for.
   * @param c The customer who waits.
   * @return A new state of the simulation after the customer waits.
   */
  private SimState noteWait(double time, Server s, Customer c) {
    System.out.printf("%.3f %s waits to be served by %s\n", time, c, s);
    return this;
  }

  /**
   * Log when a customer is served in the simulation.  
   * @param time The time the customer arrives.
   * @param s The server that serves the customer.
   * @param c The customer that is served.
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
   * @param time The time the customer arrives.
   * @param s The server that serves the customer.
   * @param c The customer that is served.
   * @return A new state of the simulation after the customer is done being
   *     served.
   */
  private SimState noteDone(double time, Server s, Customer c) {
    System.out.printf("%.3f %s done serving by %s\n", time, c, s);
    return this;
  }

  /**
   * Log when a customer leaves the shops without service.
   * @param  time  The time this customer leaves.
   * @param  customer The customer who leaves.
   * @return A new state of the simulation.
   */
  private SimState noteLeave(double time, Customer customer) {
    System.out.printf("%.3f %s leaves\n", time, customer);
    stats.looseOneCustomer();
    return this;
  }

  /**
   * Simulates the logic of what happened when a customer arrives.
   * The customer is either served, waiting to be served, or leaves.
   * @param time The time the customer arrives.
   * @return A new state of the simulation.
   */
  public SimState simulateArrival(double time) {
    Customer customer = new Customer(time);
    noteArrival(time, customer);
    processArrival(time, customer);
    return this;
  }

  /**
   * Handle the logic of finding idle servers to serve the customer, 
   * or a server that the customer can wait for, or leave.  Called
   * from simulateArrival.
   * @param time The time the customer arrives.
   * @param customer The customer to be served.
   * @return A new state of the simulation.
   */
  private SimState processArrival(double time, Customer customer) {
    Server s = shop.findIdleServer();
    if (s != null) {
      serveCustomer(time, s, customer);
      return this;
    }
    s = shop.findServerWithNoWaitingCustomer();
    if (s != null) {
      noteWait(time, s, customer);
      s.askToWait(customer);
      return this;
    }
    noteLeave(time, customer);
    return this;
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
    noteDone(time, server, customer);
    Customer c = server.getWaitingCustomer();
    if (c != null) {
      serveCustomer(time, server, c);
      return this;
    }
    server.makeIdle();
    return this;
  }

  public SimState simulateServerRest(double time, double restTime, Server server, Customer customer) {
    noteDone(time, server, customer);
    server.makeRest(time + restTime);
    return this;
  }

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
   * Handle the logic of server serving customer.  A new done event
   * is generated and scheduled.
   * @param  time  The time this customer is served.
   * @param  server The server serving this customer.
   * @param  customer The customer being served.
   * @return A new state of the simulation.
   */
  private SimState serveCustomer(double time, Server server, Customer customer) {
    double doneTime = time + rng.genServiceTime();
    server.serve(customer);
    noteServed(time, server, customer);
    if (serverRests()) {
      double restTime = rng.genRestPeriod();
      addEvent(new SERVER_REST(doneTime, server, restTime, customer));
      addEvent(new SERVER_BACK(doneTime + restTime, server));
    } else {
      addEvent(new DoneEvent(doneTime, server, customer));
    }
    return this;
  }

  private boolean serverRests() {
    return (rng.genRandomRest() < restingProbability);
  }

  /**
   * The main simulation loop.  Repeatedly get events from the event
   * queue, simulate and update the event.  Return the final simulation
   * state.
   * @return The final state of the simulation.
   */
  public SimState run() {
    Pair<Event, SimState> p = nextEvent();
    while (p.first != null) {
      p = p.first.simulate(p.second).nextEvent();
    }
    return p.second;
  }

  public SimState populate() {
    boolean firstCustomer = true;
    double arrivalTime = 0; 
    for (int i = 0; i < numOfCustomers; i++) {
      if (firstCustomer) {
          firstCustomer = false;
          this.addEvent(new ArrivalEvent(arrivalTime));
          continue;
      }
      arrivalTime += rng.genInterArrivalTime();
      this.addEvent(new ArrivalEvent(arrivalTime));
    }
    return this;
  }

  /**
   * Return a string representation of the simulation state, which
   * consists of all the logs and the stats.
   * @return A string representation of the simulation.
   */
  public String toString() {
    return stats.toString();
  }
}
