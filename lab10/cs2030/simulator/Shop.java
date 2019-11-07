package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;

/**
 * A shop object maintains the list of servers and support queries
 * for server.
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Shop {
  /** List of servers. */
  private final List<Server> servers;

  /**
   * Create a new shop with a given number of servers.
   * @param numOfServers The number of servers.
   */
  Shop(int numOfServers, int maxQueueLength) {
    this.servers = new ArrayList<>(numOfServers);
    for (int i = 0; i < numOfServers; i++) {
      this.servers.add(new Server(maxQueueLength));
    }
  }

  /**
   * Return the first idle server in the list.
   *
   * @return An idle server, or {@code null} if every server is busy.
   */
  public Server findIdleServer() {
    for (Server server: this.servers) {
      if (server.isIdle() && !(server.isResting())) {
        return server;
      }
    }
    return null;
  }

  /**
   * Return the first server with no waiting customer.
   * @return A server with no waiting customer, or {@code null} is every
   *     server already has a waiting customer.
   */
  public Server findServerWithNoWaitingCustomer() {
    for (Server server: this.servers) {
      if (!server.hasWaitingCustomer()) {
        return server;
      }
    }
    return null;
  }

  /**
   * Return a string representation of this shop.
   * @return A string reprensetation of this shop.
   */
  public String toString() {
    return servers.toString();
  }
}
