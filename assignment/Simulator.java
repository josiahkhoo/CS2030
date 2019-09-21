import java.util.PriorityQueue;

public class Simulator {
    private PriorityQueue<Customer> queue;
    private Server[] servers;

    private Double totalWaitingTime;
    private int numberServed;
    private int totalCustomers;

    public Simulator(PriorityQueue<Customer> queue, Server[] servers) {
        this.queue = queue;
        this.servers = servers;

        totalWaitingTime = 0.0;
        numberServed = 0;
        totalCustomers = queue.size();
    }

    public void run() {
        int n = getNumberServers();

        while (queue.isEmpty() == false) {
            int k = 0;
            Boolean allServing = false;
            Boolean allWaiting = false;
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
                        Double waitingTime = server.adjustWaitingTime(customer);
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

    public Double averageWaitingTime() {
        return totalWaitingTime / numberServed;
    }

    public int getNumberServed() {
        return numberServed;
    }

    public int getNumberLeft() {
        return totalCustomers - numberServed;
    }

    public int getNumberServers() {
        return servers.length;
    } 

    @Override
    public String toString() {
        return String.format("[%.3f %d %d]", averageWaitingTime(), getNumberServed(), getNumberLeft());
    }
}