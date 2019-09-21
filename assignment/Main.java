import java.util.Scanner;
import java.util.PriorityQueue;

public class Main {

    static int count = 0;

    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in);
        int numberServers = scanner.nextInt();
        Server[] servers = new Server[numberServers];
            for (int i = 1; i <= numberServers; i++) {
                Server server = new Server(i);
                servers[i-1] = server;
            } 
        PriorityQueue<Customer> customerQueue = new PriorityQueue<Customer>(
            new SortByTime());
        while (scanner.hasNext()) {
            //create new customer
            Customer customer = new Customer(++count, scanner.nextDouble());
            //add customer into queue
            customerQueue.add(customer);
        }
        Simulator serverSimulator = new Simulator(customerQueue, servers);
        serverSimulator.run();
        System.out.println(serverSimulator);
        scanner.close();
    }

    public static void printCount() {
        String output = new String();
        output = String.format("Number of customers: %d", count);
        System.out.println(output);
    }
}
