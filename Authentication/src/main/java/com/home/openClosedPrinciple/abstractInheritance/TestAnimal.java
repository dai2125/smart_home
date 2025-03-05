package com.home.openClosedPrinciple.abstractInheritance;

public class TestAnimal {

    public static void main(String[] args) {
        Dog dog = new Dog("Willy", 12);
        Cat cat = new Cat("Clarence", 5);

        dog.makeSound();
        cat.makeSound();
    }
}
