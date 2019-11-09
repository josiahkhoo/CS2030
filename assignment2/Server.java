package cs2030.simulator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Optional;

/**
 * The Server class keeps track of who is the customer being served (if any) and
 * who is the customer waiting to be served (if any).
 */
class Server {
  /** The unique ID of the last created server. */
  private static int lastServerId = 1;

  /** The unique ID of this server. */
  protected final int id;

  /** The queue capacity of this server. */
  private final int queueCapacity;

  /** The customer currently being served, if any. */
  private Customer currentCustomer;

  /** The customer currently waiting, if any. */
  protected Queue<Customer> waitingCustomerQueue;

  /** Whether the server is currently resting. */
  private boolean resting;

  /** The time when the server is back from it's rest, if any. */
  private Optional<Double> doneRestingTime;

  /**
   * Creates a server and initalizes it with a unique id.
   * 
   * @param queueCapacity The queue capacity of this server in the simulation.
   */
  public Server(int queueCapacity) {
    this.currentCustomer = null;
    this.waitingCustomerQueue = new LinkedList<Customer>();
    this.id = Server.lastServerId;
    this.queueCapacity = queueCapacity;
    this.resting = false;
    this.doneRestingTime = Optional.empty();
    Server.lastServerId++;
  }

  /**
   * Change this server's state to idle by removing its current customer.
   * 
   * @return A new server with the current customer removed.
   */
  public Server makeIdle() {
    this.currentCustomer = null;
    return this;
  }

  /**
   * Checks if the current server is idle.
   * 
   * @return true if the server is idle (no current customer); false otherwise.
   */
  public boolean isIdle() {
    return this.currentCustomer == null;
  }

  public int waitingQueueSize() {
    return this.waitingCustomerQueue.size();
  }

  /**
   * Checks if the server is at its queue capacity.
   * 
   * @return true if server is at its queue capacity; false otherwise.
   */
  public boolean hasWaitingCustomer() {
    return (waitingQueueSize() >= queueCapacity);
  }

  /**
   * Returns the first waiting customer in the queue for given server.
   * 
   * @return The first customer waiting in the queue for given server.
   */
  public Customer getWaitingCustomer() {
    return this.waitingCustomerQueue.peek();
  }

  /**
   * Serve a customer.
   * 
   * @param customer The customer to be served.
   * @return The new server serving this customer.
   */
  public Server serve(Customer customer) {
    this.currentCustomer = customer;
    if (customer.equals(this.waitingCustomerQueue.peek())) {
      this.waitingCustomerQueue.poll();
    }
    return this;
  }

  /**
   * Make a customer wait for this server.
   * 
   * @param customer The customer who will wait for this server.
   * @return The new server with a waiting customer.
   */
  public Server askToWait(Customer customer) {
    this.waitingCustomerQueue.add(customer);
    return this;
  }

  /**
   * Checks if the server is resting.
   * 
   * @return true if the customer is resting; false otherwise.
   */
  public boolean isResting() {
    return this.resting;
  }

  /**
   * Makes the server rest up until a certain time.
   * 
   * @param doneRestingTime
   * @return This server that is resting.
   */
  public Server makeRest(double doneRestingTime) {
    this.resting = true;
    this.doneRestingTime = Optional.of(doneRestingTime);
    return this;
  }

  /**
   * Makes the server come back from his/her rest.
   * 
   * @return This server that is no longer resting.
   */
  public Server makeBack() {
    this.resting = false;
    this.doneRestingTime = Optional.empty();
    return this;
  }

  /**
   * Retrieves the time of which the server is done resting.
   * 
   * @return The time of which the server is back from its rest, or {code null} if
   *         the server is not resting
   */
  public double getDoneRestingTime() {
    return this.doneRestingTime.get();
  }

  /**
   * Return a string representation of this server.
   * 
   * @return A string containing the ID of the server, with "server" prefixed in
   *         the string.
   */
  public String toString() {
    return String.format("server %d", this.id);
  }

  /**
   * Checks if two servers have the same id.
   * 
   * @param obj Another objects to compared against.
   * @return true if obj is a server with the same id; false otherwise.
   */
  public boolean equals(Object obj) {
    if (!(obj instanceof Server)) {
      return false;
    }
    return this.id == ((Server) obj).id;
  }

  /**
   * Return the hashcode for this server.
   * 
   * @return the ID of this server as its hashcode.
   */
  public int hashCode() {
    return this.id;
  }
}
