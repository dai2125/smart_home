package com.home.indirection.fourthAnalysis.mistake;

public class Main {

    public static void main(String[] args) {
        BookService service = new BookService();
        service.performBusinessOperation();
    }
}
