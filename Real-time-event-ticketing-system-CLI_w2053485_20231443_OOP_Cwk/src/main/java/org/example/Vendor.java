//Real-time-event-ticketing-system-CLI_w2053485_20231443_OOP_Cwk/src/main/java/org/example/Vendor.java
package org.example;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int releaseInterval;
    private final int totalTickets;
    private int ticketsAdded;

    public Vendor(TicketPool ticketPool, int releaseInterval, int totalTickets) {
        this.ticketPool = ticketPool;
        this.releaseInterval = releaseInterval;
        this.totalTickets = totalTickets;
    }

    @Override
    public void run() {
        System.out.println("Vendor started. Will add " + totalTickets + " tickets.");

        while (!Thread.currentThread().isInterrupted() && ticketsAdded < totalTickets) {
            try {
                Thread.sleep(releaseInterval);

                Ticket ticket = new Ticket();

                if (ticketsAdded < totalTickets && ticketPool.addTicket(ticket)) {
                    ticketsAdded++;
                } else if (ticketsAdded >= totalTickets) {
                    System.out.println("Vendor: Reached the total ticket limit of " + totalTickets + ". Stopping ticket addition.");
                    break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Vendor interrupted.");
                break;
            }
        }

        System.out.println("Vendor finished. Total tickets added: " + ticketsAdded);
    }

}
