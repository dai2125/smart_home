package com.home.dependencyInversionPrinciple.secondAnalysis.badExample;

import java.util.ArrayList;

public class Shelf {

    Book book;
    Dvd dvd;

    ArrayList<Book> bookShelf = new ArrayList();
    ArrayList<Dvd> dvdShelf = new ArrayList();

    public void addBook(Book book) {
        bookShelf.add(book);
    }

    public void addDvd(Dvd dvd) {
        dvdShelf.add(dvd);
    }

    public String getBookReview(Book book) {
        for (int i = 0; i < bookShelf.size(); i++) {
            if (book.getTitle().equals(bookShelf.get(i).getTitle())) {
                return book.getReview();
            }
        }
        return null;
    }

    public String getDvdReview(Dvd dvd) {
        for (int i = 0; i < dvdShelf.size(); i++) {
            if (dvd.getTitle().equals(dvdShelf.get(i).getTitle())) {
                return dvd.getReview();
            }
        }
        return null;
    }

    public String getBookSample(Book book) {
        for (int i = 0; i < bookShelf.size(); i++) {
            if (book.getSample().equals(bookShelf.get(i).getSample())) {
                return book.getSample();
            }
        }
        return null;
    }

    public String getDvdSample(Dvd dvd) {
        for (int i = 0; i < dvdShelf.size(); i++) {
            if (dvd.getSample().equals(dvdShelf.get(i).getSample())) {
                return dvd.getSample();
            }
        }
        return null;
    }
}
