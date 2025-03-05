package com.home.indirection.fix;

public class Main {

    public static void main(String[] args) {
        var studentModel = new StudentModel("Bill", 2);
        var personModel = new PersonModel("Sam");
        var studentView = new StudentView();
        var personView = new PersonView();

        new ControllerImpl(studentModel, studentView).render();
        new ControllerImpl(studentModel, personView).render();
        new ControllerImpl(personModel, personView).render();
    }

}