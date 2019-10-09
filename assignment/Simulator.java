import java.util.PriorityQueue;

/**
 * This is the simulator class that will be runned.
 */
public class Simulator {
    private PriorityQueue<Customer> queue;
    private Server[] servers;

    private double totalWaitingTime;
    private int numberServed;
    private int totalCustomers;
    
    /**
     * Creates a Simulator with a queue of customers and array of servers.
     * @param   queue   queue of customers
     * @param   servers array of servers
     */
    public Simulator(PriorityQueue<Customer> queue, Server[] servers) {
        this.queue = queue;
        this.servers = servers;

        totalWaitingTime = 0.0;
        numberServed = 0;
        totalCustomers = queue.size();
    }
    
    /**
     * This methods runs the simulator and prints out changes in events.
     */
    public void run() {
        int n = getNumberServers();

        while (queue.isEmpty() == false) {
            int k = 0;
            boolean allServing = false;
            boolean allWaiting = false;
            while (k < n) {
                if (queue.isEmpty() == true) {
                    break;
                }

                Server server = servers[k];
                Customer customer = queue.poll();
                if (customer.isServed() || customer.isDone()) {
                    if (customer.isServed() && server.isServing(customer)) {
                        System.out.println(customer);
                        server.makeDone(customer);
                        queue.add(customer);
                        k = 0;
                        continue;
                    } else if (customer.isDone() && server.isServing(customer)) {
                        System.out.println(customer);
                        server.release(customer);
                        k = 0;
                        continue;
                    }
                }

                if ((customer.getServerId() != server.getId()) && 
                    (customer.getServerId() != 0)) {
                    queue.add(customer);
                    k += 1;
                    continue;
                }

                if (customer.isArrive() && (allServing == false)) {
                    if (server.getCustomer() == null) {
                        System.out.println(customer);
                        server.serve(customer);
                        numberServed += 1;
                        queue.add(customer);
                        break;
                    } else {
                        k += 1;
                        if (k == n) {
                            allServing = true;
                            k = 0;
                            queue.add(customer);
                            continue;
                        }
                        queue.add(customer);
                        continue;
                    }
                } else if ((customer.isArrive()) && (allWaiting == false)) {
                    if (server.getWaiting() == null) {
                        System.out.println(customer);
                        server.makeWait(customer);
                        //logs waiting customer
                        System.out.println(customer);
                        double waitingTime = server.adjustWaitingTime(customer);
                        server.serveFuture(customer);
                        numberServed += 1;
                        totalWaitingTime += waitingTime;
                        queue.add(customer);
                        break;
                    } else {
                        k += 1;
                        if (k == n) {
                            allWaiting = true;
                            k = 0;
                            queue.add(customer);
                            continue;
                        }
                        queue.add(customer);
                        continue;
                    }
                } else if (allServing && allWaiting) {
                    System.out.println(customer);
                    server.makeLeave(customer);
                    System.out.println(customer);
                    break;
                }

                continue;
            }
        }
    }
    
    /**
     * Returns the average waiting time of
     * all the customers in the queue.
     * @return  average waiting time
     */
    public double averageWaitingTime() {
        return totalWaitingTime / numberServed;
    }
    
    /**
     * Returns the total number of served
     * customers in the queue.
     * @return  total served customers
     */
    public int getNumberServed() {
        return numberServed;
    }
    
    /**
     * Returns the total number of customers
     * who left the queue.
     * @return  total left customers
     */
    public int getNumberLeft() {
        return totalCustomers - numberServed;
    }
    
    /**
     * Returns the number of servers in the
     * array of servers.
     * @return  number of servers
     */
    public int getNumberServers() {
        return servers.length;
    } 

    /**
     * This method overrides the toString() method of Object and returns a
     * string in the format of {@link Simulator#averageWaitingTime()}
     * {@link Simulator#getNumberServed()} {@link Simulator#getNumberLeft()}.
     * @return  String
     */
    @Override
    public String toString() {
        return String.format("[%.3f %d %d]", averageWaitingTime(), 
                getNumberServed(), getNumberLeft());
    }
}
