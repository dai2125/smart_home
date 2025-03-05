package com.home.indirection.firstAnalysis.goodExample;

public class DataAccessLayer {

    private Database database;

    public DataAccessLayer(Database database) {
        this.database = database;
    }

    public String getData() {
        return database.fetchData();
    }
}
