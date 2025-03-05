package com.home.indirection.fix;

class StudentModel implements Model {
    private final int course;
    private final String name;

    public StudentModel(String name, int course) {
        this.name = name;
        this.course = course;
    }

    public int getCourse() {
        return course;
    }

    public String getName() {
        return name;
    }
}