package com.home.indirection.firstAnalysis.goodExample;

public class Main {

    public static void main(String[] args) {
        Database database = new Database();
        DataAccessLayer dataAccessLayer = new DataAccessLayer(database);
        DataDisplay dataDisplay = new DataDisplay(dataAccessLayer);

        dataDisplay.showData();
    }
}
