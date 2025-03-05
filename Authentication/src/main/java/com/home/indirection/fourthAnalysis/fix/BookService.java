package com.home.indirection.fourthAnalysis.fix;

public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public void performBusinessOperation() {
        Book book = repository.findBookById(1);
        System.out.println("book: " + book.getTitle());
        System.out.println("Performing operations on book: " + book.getTitle());
        repository.saveBook(book);
    }
}
