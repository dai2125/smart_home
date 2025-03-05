package com.home.indirection.fourthAnalysis.fix;

public interface BookRepository {

    Book findBookById(int id);
    void saveBook(Book book);
}
