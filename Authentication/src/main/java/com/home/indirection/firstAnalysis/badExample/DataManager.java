package com.home.indirection.firstAnalysis.badExample;

public class DataManager {

    private Database database;

    public DataManager(Database database) {
        this.database = database;
    }

    public String getData() {
        return database.fetchData();
    }
}
