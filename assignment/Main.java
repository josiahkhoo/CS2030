import java.util.Scanner;
import java.util.PriorityQueue;

/**
 * This is the Main class that will contain the main method
 * to be executed at run-time.
 */
public class Main {

    static int count = 0;
    
    /**
     * This method will be run at run-time and will take in
     * user input and the command line level in the following
     * order.
     * <ul>
     *  <li>Number of servers (1)</li>
     *  <li>Customer in the format of time (n)</li>
     * </ul>
     */
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in);
        int numberServers = scanner.nextInt();
        Server[] servers = new Server[numberServers];
        for (int i = 1; i <= numberServers; i++) {
            Server server = new Server(i);
            servers[i - 1] = server;
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

}
