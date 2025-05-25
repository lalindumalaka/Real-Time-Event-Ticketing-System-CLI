//Real-time-event-ticketing-system-CLI_w2053485_20231443_OOP_Cwk/src/main/java/org/example/Ticket.java
package org.example;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TicketPool {

    private final ConcurrentLinkedQueue<Ticket> tickets = new
            ConcurrentLinkedQueue<>();
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized boolean addTicket(Ticket ticket) {
        if (tickets.size() < maxCapacity) {
            tickets.add(ticket);
            System.out.println(ticket + " added. Total tickets: " + tickets.size());
            notifyAll();
            return true;
        } else {
            System.out.println("Ticket pool is at full capacity.");
            return false;
        }
    }
    public synchronized Ticket removeTicket() {
        Ticket ticket = tickets.poll();
        if (ticket != null) {
            System.out.println(ticket + " purchased. Remaining tickets: " + tickets.size());
        } else {
            System.out.println("No tickets available for purchase.");
        }
        return ticket;
    }

    public int getAvailableTickets() {

        return tickets.size();
    }



}
