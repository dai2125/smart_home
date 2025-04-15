package com.home.creator.principle3.goodExample;

import java.util.*;

public class EventLog {

    private List<Event> events;
    private List<Integer> integerList;
    private Map<Event, String> map = new HashMap<>();
    HashSet<Event> set = new HashSet<>();
    Queue<Event> queue = new PriorityQueue<>();
    Stack<Event> stack = new Stack<>();

    public EventLog() {
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
        set.add(event);
        queue.add(event);
        stack.add(event);
    }

    public void printLog() {
        for (Event event : events) {
            System.out.println("Logged Event: " + event.getDescription());
        }
    }
}
