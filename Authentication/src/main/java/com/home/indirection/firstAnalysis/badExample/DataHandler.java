package com.home.indirection.firstAnalysis.badExample;

public class DataHandler {

    private DataManager dataManager;

    public DataHandler(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public String processData() {
        return dataManager.getData();
    }
}
