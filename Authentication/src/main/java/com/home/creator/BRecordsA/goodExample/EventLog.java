package com.home.creator.BRecordsA.goodExample;

import java.util.ArrayList;
import java.util.List;

public class EventLog {

    private List<Event> events;

    public EventLog() {
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void printLog() {
        for (Event event : events) {
            System.out.println("Logged Event: " + event.getDescription());
        }
    }
}
