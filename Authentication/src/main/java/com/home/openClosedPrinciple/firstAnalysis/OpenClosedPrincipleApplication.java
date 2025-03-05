package com.home.openClosedPrinciple.firstAnalysis;

import com.home.openClosedPrinciple.ReadMetrics;

import java.io.FileNotFoundException;

public class OpenClosedPrincipleApplication {

    public static void main(String[] args) throws FileNotFoundException {
        ReadMetrics readMetrics = new ReadMetrics();

        readMetrics.readTypeMetrics();
        readMetrics.readDesignSmells();
    }
}

