package com.home.indirection.fourthAnalysis.fix;

public class BookRepositoryImpl implements BookRepository {

    @Override
    public Book findBookById(int id) {
        System.out.println("Fetching book with ID " + id + " from Database");
        return new Book(id, "Sample Book");
    }

    @Override
    public void saveBook(Book book) {
        System.out.println("Saving book: " + book.getTitle() + " to database");
    }
}
