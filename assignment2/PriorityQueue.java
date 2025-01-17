package cs2030.simulator;

/**
 * This class wraps around the class PriorityQueue from Java Collection
 * Framework. It provides an alternative API that could be modified to support
 * an immutable Priority Queue.
 **/
public class PriorityQueue<T> {
    /** The actual priority queue that stores the items. */
    private final java.util.PriorityQueue<T> pq;

    /**
     * Constructor for an empty priority queue.
     **/
    public PriorityQueue() {
        pq = new java.util.PriorityQueue<>();
    }

    /**
     * Add an object into the priority queue following the add() method of the JCF
     * PriorityQueue. Return the priority queue after the object is added.
     * 
     * @param object The item to add.
     **/
    public PriorityQueue<T> add(T object) {
        this.pq.add(object);
        return this;
    }

    /**
     * Return as a pair, (i) the next priortized item according to the natural order
     * of the objects in the priority queue, and (ii) the priority queue after the
     * item is removed.
     * 
     * @return The pair (item, priority queue).
     **/
    public Pair<T, PriorityQueue<T>> poll() {
        T t = this.pq.poll();
        return Pair.of(t, this);
    }
}
