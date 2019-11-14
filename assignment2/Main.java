import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import cs2030.simulator.SimState;

/**
 * This is the main class that will contain the main method to be executed at
 * run-time.
 **/
class Main {
    /**
     * The main method that reads data from file and then run a simulation based on
     * the input data.
     *
     * @param args A file containing a sequence of 5 int values and 5 double values
     *             in the following order: seed, number of servers, number of self
     *             checkout counters, maximum queue length, number of customers,
     *             arrival rate, service rate, resting probability, greedy
     *             probability.
     */
    public static void main(String[] args) {
        Scanner scanner = createScanner(args);
        if (scanner == null) {
            return;
        }
        SimState state = initSimState(scanner);
        state.populate().run();
        System.out.println(state);

    }

    /**
     * Read from inputs, populate the simulator with events, and run.
     *
     * @param scanner The scanner to read inputs from.
     */
    public static SimState initSimState(Scanner scanner) {
        // Read the first line of input as number of servers in the shop
        int seed = scanner.nextInt();
        int numOfServers = scanner.nextInt();
        int numOfSelfCheckout = scanner.nextInt();
        int maxQueueLength = scanner.nextInt();
        int numOfCustomers = scanner.nextInt();
        double arrivalRate = scanner.nextDouble();
        double serviceRate = scanner.nextDouble();
        double restingRate = scanner.nextDouble();
        double restingProbability = scanner.nextDouble();
        double greedyProbability = scanner.nextDouble();
        SimState state = new SimState(numOfServers, maxQueueLength, numOfSelfCheckout, 
                numOfCustomers, restingProbability, seed, arrivalRate, 
                serviceRate, restingRate, greedyProbability);
        return state;
    }

    /**
     * Create and return a scanner. If a command line argument is given, treat the
     * argument as a file and open a scanner on the file. Else, create a scanner
     * that reads from standard input.
     *
     * @param args The arguments provided for simulation.
     * @return A scanner or {@code null} if a filename is provided but the file
     *         cannot be open.
     */
    private static Scanner createScanner(String[] args) {
        Scanner scanner = null;

        try {
            // Read from stdin if no filename is given, otherwise read from the
            // given file.
            if (args.length == 0) {
                // If there is no argument, read from standard input.
                scanner = new Scanner(System.in);
            } else {
                // Else read from file
                FileReader fileReader = new FileReader(args[0]);
                scanner = new Scanner(fileReader);
            }
        } catch (FileNotFoundException exception) {
            System.err.println("Unable to open file " + args[0] + " " + exception);
        }
        return scanner;
    }
}
