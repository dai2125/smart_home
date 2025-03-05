package com.home.indirection.firstAnalysis.badExample;

public class DataPresenter {

    private DataHandler dataHandler;

    public DataPresenter(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public void displayData() {
        System.out.println(dataHandler.processData());
    }
}
