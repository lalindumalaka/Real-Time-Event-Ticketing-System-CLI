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
