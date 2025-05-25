//Real-time-event-ticketing-system-CLI_w2053485_20231443_OOP_Cwk/src/main/java/org/example/Ticket.java
package org.example;

public class Ticket {

    private static int counter = 1;

    private final int id;

    public Ticket(){

        this.id =counter++;
    }
    public int getId(){

        return id;
    }
    @Override
    public String toString() {

        return "Ticket #" + id;
    }

}
