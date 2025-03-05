package com.home.indirection.fourthAnalysis.mistake;

public class BookService {

    public void performBusinessOperation() {
        Book book = fetchBookFromDatabase(1);
        System.out.println("Performing operations on book: " + book.getTitle());
        saveBookToDatabase(book);
    }

    private Book fetchBookFromDatabase(int id) {
        System.out.println("Fetching book with ID " + id + " from database");
        return new Book(id, "Sample book");
    }

    private void saveBookToDatabase(Book book) {
        System.out.println("Saving book: " + book.getTitle() + " to database");
    }
}
