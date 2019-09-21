import java.util.PriorityQueue;

public class Simulator {
    private PriorityQueue<Customer> queue;
    private Server server;

    private Double totalWaitingTime;
    private int numberServed;
    private int totalCustomers;

    public Simulator(PriorityQueue<Customer> queue, Server server) {
        this.queue = queue;
        this.server = server;

        totalWaitingTime = 0.0;
        numberServed = 0;
        totalCustomers = queue.size();
    }

    public void run() {
        while (queue.isEmpty() == false) {
            Customer customer = queue.poll();
            System.out.println(customer);
           if (customer.isServed() || customer.isDone()) {
                if (customer.isServed() && server.isServing(customer)) {
                    server.makeDone(customer);
                    queue.add(customer);
                    continue;
                // } else if (customer.isServed() && server.isWaiting(customer)) {
                //     server.makeDone(customer);
                //     queue.add(customer);
                //     continue;
                } else if (customer.isDone()) {
                    server.release(customer);
                    continue;
                }
                continue;
            }
            // case 1: someone waiting
            if (server.getWaiting() != null) {
                if (server.getWaiting() == customer) {
                    server.serve(customer);
                    queue.add(customer);
                    continue;
                } else {
                    server.makeLeave(customer);
                    System.out.println(customer);
                    continue;
                }
            }
            // case 2: someone being served
            if (server.getCustomer() != null) {
                server.makeWait(customer);
                //logs waiting customer
                System.out.println(customer);
                Double waitingTime = server.adjustWaitingTime(customer);
                server.serveFuture(customer);
                numberServed += 1;
                totalWaitingTime += waitingTime;
                queue.add(customer);
                continue;
            }
            // case 3: no one being served
            server.serve(customer);
            numberServed += 1;
            queue.add(customer);
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

    @Override
    public String toString() {
        return String.format("[%.3f %d %d]", averageWaitingTime(), getNumberServed(), getNumberLeft());
    }
}