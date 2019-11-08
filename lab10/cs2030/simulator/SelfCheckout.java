package cs2030.simulator;

import java.util.Queue;

class SelfCheckout extends Server {

    public SelfCheckout(int queueCapacity, Queue<Customer> checkoutQueue) {
        super(queueCapacity);
        this.waitingCustomerQueue = checkoutQueue;
    }

    @Override
    public String toString() {
        return String.format("self-check %d", this.id);
    }

}