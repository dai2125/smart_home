package com.home.DependencyInversionPrinciple.secondAnalysis.goodExample;

public class Main {

    public static void main(String[] args) {
        Book book1 = new Book("Der Fremde", "super Buch!", "Es begann an einem ruhigen morgen...");
        Book book2 = new Book("Glasperlenspiel", "buch war ok", "Am frühen Nachmittag...");
        Book book3 = new Book("Datenbank Systeme", "sehr interessant", "Zu beginn dieses Buches..");

        Dvd dvd1 = new Dvd("Tenet", "außergewöhnlicher Film", "Play trailer");
        Dvd dvd2 = new Dvd("Dschungel Buch", "Meisterwerk der...", "Play trailer");
        Dvd dvd3 = new Dvd("Bruce allmächtig", "Sehr witziger Film", "Play trailer");

        Shelf shelf = new Shelf();

        shelf.addProduct(book1);
        shelf.addProduct(book2);
        shelf.addProduct(book3);
        shelf.addProduct(dvd1);
        shelf.addProduct(dvd2);
        shelf.addProduct(dvd3);

        System.out.println(shelf.getProductReview(book2));
        System.out.println(shelf.getProductSample(book3));
        System.out.println(shelf.getProductReview(dvd2));
        System.out.println(shelf.getProductSample(dvd3));
    }
}
