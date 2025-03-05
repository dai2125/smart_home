package com.home.creator.BCloselyUsesA;

//import com.home.creator.BCloselyUsesA.badExample.ReportGenerator;
import com.home.creator.BCloselyUsesA.goodExample.ReportGenerator;
import com.home.creator.BCloselyUsesA.goodExample.Calculator;
import org.junit.jupiter.api.Test;

import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;
// TODO LCOM Wert mit einbeziehen plus ob das Objekt im Constructor initialisiert wird!
public class TestCreatorPattern  {

    @Test
    public void testCreatorPrincipleGoodExample() throws Exception {
        Class<?> clazz = Class.forName("com.home.creator.BCloselyUsesA.goodExample.ReportGenerator");

        Constructor<?> constructor = clazz.getDeclaredConstructor(Calculator.class);
        Field[] fields = constructor.getDeclaringClass().getDeclaredFields();
        Object instance = constructor.newInstance(new Calculator());

        constructor.setAccessible(true);

        for (Field field : fields) {
            field.setAccessible(true);

            Object fieldValue = field.get(instance);

            System.out.println("Field: " + field.getName()
                    + " | Type: " + field.getType()
                    + " | Value: " + fieldValue);

            if (fieldValue != null && fieldValue instanceof Calculator) {
                System.out.println("Das Feld " + field.getName() + " enthält eine Instanz von Calculator.");
            } else {
                System.out.println("Das Feld " + field.getName() + " wurde nicht korrekt initialisiert.");
            }
        }
    }

    @Test
    public void testCreatorPrincipleBadExample() throws Exception {
        Class<?> clazz = Class.forName("com.home.creator.BCloselyUsesA.badExample.ReportGenerator");
        Constructor<?> constructor = clazz.getDeclaredConstructor();

        Object instance = constructor.newInstance();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            Object fieldValue = field.get(instance);

            if (fieldValue != null && fieldValue instanceof Calculator) {
                System.out.println("Das Feld " + field.getName() + " enthält eine Instanz von Calculator.");
            } else {
                System.out.println("Das Feld " + field.getName() + " wurde nicht korrekt initialisiert.");
            }
        }
//        constructor.setAccessible(true);
    }
}
