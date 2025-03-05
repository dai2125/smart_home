package com.home.creator.firstAnalysis.badExample;

public class EngineFactory {

    public Engine createEngine(String type) {
        return new Engine(type);
    }
}
