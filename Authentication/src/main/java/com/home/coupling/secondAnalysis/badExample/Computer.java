package com.home.coupling.secondAnalysis.badExample;

public class Computer {

    private Printer printer = new Printer();

    public void startPrintJob() {
        printer.print();
    }
}
