package com.home.indirection.firstAnalysis.goodExample;

public class DataDisplay {

    private DataAccessLayer dataAccessLayer;

    public DataDisplay(DataAccessLayer dataAccessLayer) {
        this.dataAccessLayer = dataAccessLayer;
    }

    public void showData() {
        System.out.println(dataAccessLayer.getData());
    }
}
