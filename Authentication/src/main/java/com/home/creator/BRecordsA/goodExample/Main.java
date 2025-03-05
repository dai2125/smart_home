package com.home.creator.BRecordsA.goodExample;

public class Main {

    public static void main(String[] args) {
        EventLog log = new EventLog();
        log.addEvent(new Event("System started"));
        log.addEvent(new Event("User logged in"));
        log.printLog();
    }
}
