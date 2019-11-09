package cs2030.simulator;

import java.util.Queue;

/**
 * The SelfCheckout class keeps track of who is the customer being served (if
 * any) and who are the customers waiting to be served if the shared
 * self-checkout queue.
 */
class SelfCheckout extends Server {

    /**
     * Creates a self checkout counter and initialises it with a unique id.
     * 
     * @param queueCapacity The queue capacity of the self checkout queue.
     * @param checkoutQueue The shared self checkout queue.
     */
    public SelfCheckout(int queueCapacity, Queue<Customer> checkoutQueue) {
        super(queueCapacity);
        this.waitingCustomerQueue = checkoutQueue;
    }

    /**
     * Return a string representation of this self checkout counter.
     * 
     * @return A string containing the ID of the self checkout counter, with
     *         "self-check" prefixed in the string.
     */
    @Override
    public String toString() {
        return String.format("self-check %d", this.id);
    }

}