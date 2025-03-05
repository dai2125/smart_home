package com.home.LiskovSubstitutionPrinciple.secondAnalysis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShapeTest {

    @Test
    public void testCircleArea() {
        Shape shape = new Circle(5);
        assertEquals(Math.PI * 5 * 5, shape.calculateArea(), 0.01);
    }

    @Test
    public void testSquareArea() {
        Shape shape = new Square(4);
        assertEquals(16, shape.calculateArea(), 0.01);
    }

    @Test
    public void testPolymorphism() {
        Shape shape = new Circle(5);
        assertTrue(shape instanceof Circle);
        assertEquals(Math.PI * 5 * 5, shape.calculateArea(), 0.01);

        shape = new Square(4);
        assertTrue(shape instanceof Square);
        assertEquals(16, shape.calculateArea(), 0.01);
    }
}
