import java.util.Scanner;

public class Main {

    static int count = 0;

    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in); 
        while (scanner.hasNext()) {
            Customer customer = new Customer(++count, scanner.nextDouble());
            System.out.println(customer);
        }
        printCount();
    }

    public static void printCount() {
        String output = new String();
        output += "Number of customers: ";
        output += count;
        System.out.println(output);
    }
}
