package com.home.coupling.secondAnalysis.goodExample;

public class Computer {

    private Printable printer;

    public Computer(Printable printer) {
        this.printer = printer;
    }

    public void startPrintJob() {
        printer.print();
    }
}
