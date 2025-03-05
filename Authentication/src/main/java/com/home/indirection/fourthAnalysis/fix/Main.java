package com.home.indirection.fourthAnalysis.fix;

public class Main {

    public static void main(String[] args) {
        BookRepository repository = new BookRepositoryImpl();
        BookService service = new BookService(repository);
        service.performBusinessOperation();
    }
}
