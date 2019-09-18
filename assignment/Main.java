import java.util.Scanner;
import java.util.PriorityQueue;

public class Main {

    static int count = 0;

    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in); 
        Server server = new Server();
        while (scanner.hasNext()) {
            //create new customer
            Customer customer = new Customer(++count, scanner.nextDouble());
            //print customer arrives
            System.out.println(customer);
            server = server.serve(customer);
            //print new status of customer
            System.out.println(customer);
        }
        printCount();
    }

    public static void printCount() {
        String output = new String();
        output = String.format("Number of customers: %d", count);
        System.out.println(output);
    }
}
