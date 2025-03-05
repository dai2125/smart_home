package com.home.LiskovSubstitutionPrinciple.fourthAnalysis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void testSetAgeInvalidAgeForPerson() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Person person = new Person(-1);
        });
        assertEquals("Invalid age", exception.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            Person person2 = new Person(151);
        });

        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, () -> {
           Person person3 = new Person(152);
        });

        IllegalArgumentException exception4 = assertThrows(IllegalArgumentException.class, () -> {
            Person person4 = new Employee(151, "Manager", 2000.0);
        });

        IllegalArgumentException exception5 = assertThrows(IllegalArgumentException.class, () -> {
            Person person5 = new Employee(-15, "Trainee", 1000.0);
        });

        Person person6 = new Employee(20, "Leader", 1200.0);
        Person person7 = new Person(25);
        Employee employee7 = new Employee(35, "Receptionist", 1300.0);

        assertTrue(person6 instanceof Person);
        assertTrue(person7 instanceof Person);
        assertTrue(employee7 instanceof Person);
    }

    @Test
    public void testSetAgeValidAgeForEmployee() {
        Employee employee = new Employee(40, "Accountant", 1350.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employee.setAge(-1);
        });

        assertTrue(exception instanceof IllegalArgumentException);
        assertEquals("Invalid age", exception.getMessage());
    }
}
