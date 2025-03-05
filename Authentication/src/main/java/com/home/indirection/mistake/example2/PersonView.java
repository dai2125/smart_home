package com.home.indirection.mistake.example2;

class PersonView {

    private final Person person;

    public PersonView(Person person) {
        this.person = person;
    }

    public void print() {
        System.out.println("Person: ");
        System.out.println("Name: " + person.getName());
    }

}