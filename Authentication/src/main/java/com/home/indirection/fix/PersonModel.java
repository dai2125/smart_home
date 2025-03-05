package com.home.indirection.fix;

class PersonModel implements Model {
    private final String name;

    public PersonModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}