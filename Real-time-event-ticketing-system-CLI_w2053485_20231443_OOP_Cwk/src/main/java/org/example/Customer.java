//Real-time-event-ticketing-system-CLI_w2053485_20231443_OOP_Cwk/src/main/java/org/example/Customer.java
package org.example;

public class Customer implements Runnable {

    private final TicketPool ticketPool;
    private final int purchaseInterval;
    private int ticketsPurchased;

    public Customer(TicketPool ticketPool, int purchaseInterval) {
        this.ticketPool = ticketPool;
        this.purchaseInterval = purchaseInterval;
        this.ticketsPurchased = 0;
    }

    @Override
    public void run() {
        System.out.println("Customer started purchasing tickets.");

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(purchaseInterval);

                Ticket ticket = ticketPool.removeTicket();
                if (ticket != null) {
                    ticketsPurchased++;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Customer interrupted.");
                break;
            }
        }

        System.out.println("Customer finished. Total tickets purchased: " + ticketsPurchased);
    }
}
