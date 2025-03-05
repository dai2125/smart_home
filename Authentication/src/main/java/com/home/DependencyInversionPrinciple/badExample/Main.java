package com.home.DependencyInversionPrinciple.badExample;

public class Main {

    public static void main(String[] args) {
        Notification notification = new Notification();
        notification.notifyUser("Hello via Email!");
    }
}
