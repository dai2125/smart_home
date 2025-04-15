package com.home.creator.principle3.badExample;

public class Main {

    public static void main(String[] args) {
        EventLog log = new EventLog();
        log.addEvent(new Event("System started"));

        log.events.add(new Event("Malicious event"));
//        log.events.clear();

        for (Event event : log.events) {
            System.out.println("Event: " + event.description);
        }
    }
}
