package com.home.openClosedPrinciple.abstractInheritance;

public class Cat extends Animal {

    public Cat(String name, int age) {
        super(name, age);
    }

    @Override
    public void makeSound() {
        System.out.println("Meow meow meow meow");
    }
}
