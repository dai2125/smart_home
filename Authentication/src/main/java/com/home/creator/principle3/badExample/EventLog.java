package com.home.creator.principle3.badExample;

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

    private boolean one() {
        return true;
    }

    private int two() {
        return 2;
    }
}
