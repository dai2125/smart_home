package com.home.indirection.mistake.example2;

public class Main {

    public static void main(String[] args) {
        var student = new Student("Bill", 2);
        var studentView = new StudentView(student);
        studentView.print();
        studentView.printPerson();

        var person = new Person("Sam");
        var personView = new PersonView(person);
        personView.print();
    }

}