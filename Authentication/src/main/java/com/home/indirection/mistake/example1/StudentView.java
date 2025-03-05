package com.home.indirection.mistake.example1;

class StudentView {

    private final Student student;

    public StudentView(Student student) {
        this.student = student;
    }

    public void print() {
        System.out.println("Student: ");
        System.out.println("Name: " + student.getName());
        System.out.println("Course: " + student.getCourse());
    }
}