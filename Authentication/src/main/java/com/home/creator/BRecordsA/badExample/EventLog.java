package com.home.creator.BRecordsA.badExample;

import java.util.ArrayList;
import java.util.List;

public class EventLog {

    public List<Event> events;

    public EventLog() {
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }
}
