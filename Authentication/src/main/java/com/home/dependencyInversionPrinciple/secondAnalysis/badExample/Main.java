package com.home.dependencyInversionPrinciple.secondAnalysis.badExample;

public class Main {

    public static void main(String[] args) {
        Book book1 = new Book("Der Fremde", "super Buch!", "Es begann an einem ruhigen morgen...");
        Book book2 = new Book("Glasperlenspiel", "buch war ok", "Am frühen Nachmittag...");
        Book book3 = new Book("Datenbank Systeme", "sehr interessant", "Zu beginn dieses Buches..");

        Dvd dvd1 = new Dvd("Tenet", "außergewöhnlicher Film", "Play trailer");
        Dvd dvd2 = new Dvd("Dschungel Buch", "Meisterwerk der...", "Play trailer");
        Dvd dvd3 = new Dvd("Bruce allmächtig", "Sehr witziger Film", "Play trailer");

        Shelf shelf = new Shelf();

        shelf.addBook(book1);
        shelf.addBook(book2);
        shelf.addBook(book3);
        shelf.addDvd(dvd1);
        shelf.addDvd(dvd2);
        shelf.addDvd(dvd3);

        System.out.println(shelf.getBookReview(book2));
        System.out.println(shelf.getBookSample(book3));
        System.out.println(shelf.getDvdReview(dvd2));
        System.out.println(shelf.getDvdSample(dvd3));
    }
}
