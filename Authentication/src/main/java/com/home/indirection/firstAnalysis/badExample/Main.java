package com.home.indirection.firstAnalysis.badExample;

public class Main {

    public static void main(String[] args) {
        Database database = new Database();
        DataManager dataManager = new DataManager(database);
        DataHandler dataHandler = new DataHandler(dataManager);
        DataPresenter dataPresenter = new DataPresenter(dataHandler);

        dataPresenter.displayData();
    }
}
