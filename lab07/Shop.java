import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

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
    Shop(int numOfServers) {
        this.servers = new ArrayList<>(numOfServers);
        IntStream.rangeClosed(1, numOfServers).
            forEach(x -> this.servers.add(new Server(x)));
    }

    Shop(List<Server> servers) {
        this.servers = servers;
    }

    public Optional<Server> find(Predicate<? super Server> predicate) {
        return servers.stream().filter(predicate).findFirst();
    }
    
    public Shop replace(Server server) {
        List<Server> copyServers = new ArrayList<>(servers);
        int matchIndex = copyServers.indexOf(server);
        copyServers.set(matchIndex, server);
        return new Shop(copyServers);
    }
        
    /**
     * Return a string representation of this shop.
     * @return A string reprensetation of this shop.
     */
    public String toString() {
        return servers.toString();
    }

}
