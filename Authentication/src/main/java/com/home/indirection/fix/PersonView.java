package com.home.indirection.fix;

class PersonView implements View<Model> {

    public void print(Model person) {
        System.out.println("Person: ");
        System.out.println("Name: " + person.getName());
    }

}