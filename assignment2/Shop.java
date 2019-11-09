package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A shop object maintains the list of servers and support queries for server.
 */
class Shop {
  /** List of servers. */
  private final List<Server> servers;

  /**
   * Create a new shop with a given number of servers.
   * 
   * @param numOfServers The number of servers.
   */
  Shop(int numOfServers, int numOfSelfCheckout, int maxQueueLength) {
    this.servers = new ArrayList<>(numOfServers);
    Queue<Customer> selfCheckoutQueue = new LinkedList<Customer>();
    for (int i = 0; i < numOfServers; i++) {
      this.servers.add(new Server(maxQueueLength));
    }
    for (int i = 0; i < numOfSelfCheckout; i++) {
      this.servers.add(new SelfCheckout(maxQueueLength, selfCheckoutQueue));
    }
  }

  /**
   * Return the first idle server in the list.
   *
   * @return An idle server, or {@code null} if every server is busy.
   */
  public Server findIdleServer() {
    for (Server server : this.servers) {
      if (server.isIdle() && !(server.isResting())) {
        return server;
      }
    }
    return null;
  }

  /**
   * Return the first server with no waiting customer.
   * 
   * @return A server with no waiting customer, or {@code null} if every server
   *         already has a waiting customer.
   */
  public Server findServerWithNoWaitingCustomer() {
    for (Server server : this.servers) {
      if (!server.hasWaitingCustomer()) {
        return server;
      }
    }
    return null;
  }

  /**
   * Return the first server with the shortest queue.
   * 
   * @return A server with the shortest queue, or {@code null} if every server
   *         already has a waiting customer.
   */
  public Server findServerWithShortestQueue() {
    Server shortestQueueServer = null;
    boolean firstServer = true;
    for (Server server : this.servers) {
      if (!server.hasWaitingCustomer()) {
        if (firstServer == true) {
          shortestQueueServer = server;
          firstServer = false;
        }
        if (server.waitingQueueSize() < shortestQueueServer.waitingQueueSize()) {
          shortestQueueServer = server;
        }
      }
    }
    return shortestQueueServer;
  }

  /**
   * Return a string representation of this shop.
   * 
   * @return A string representation of this shop.
   */
  public String toString() {
    return servers.toString();
  }
}
