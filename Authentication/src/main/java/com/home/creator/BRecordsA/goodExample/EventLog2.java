package com.home.creator.BRecordsA.goodExample;

import java.util.*;

public class EventLog2 {

    private List<Event> events;
    private List<Integer> integerList;
    private Map<Event, String> map = new HashMap<>();
    HashSet<Event> set = new HashSet<>();
    Queue<Event> queue = new PriorityQueue<>();
    Stack<Event> stack = new Stack<>();

    public EventLog2() {
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
        queue.add(event);
    }

    public void printLog() {
        for(Event event : events) {
            System.out.println("Logged Event: " + event.getDescription());
        }

        for(Event event : queue) {
            System.out.println("Queue Event: " + queue.element());
        }
    }

}
