package com.home.testing;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Cohesion {

    public static void cohesion(Class<?> clazz) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            Method[] methods = clazz.getDeclaredMethods();

            int methodCount = methods.length;
            int fieldCount = fields.length;

            int totalFieldInteractions = 0;
            int independentMethods = 0;

            for (Method method : methods) {
                int nameContainsField = methodNameContainsField(method, fields);
                int methodUseFieldAsParameter = methodUseFieldAsParameter(method, fields);
                boolean returnTypeMatchesField = methodUsesFieldAsReturnType(method, fields);

                totalFieldInteractions += nameContainsField + methodUseFieldAsParameter + (returnTypeMatchesField ? 1 : 0);

                if (nameContainsField == 0 && methodUseFieldAsParameter == 0 && !returnTypeMatchesField) {
                    independentMethods++;
                }
            }

            double cohesionScore = (double) totalFieldInteractions / (methodCount * fieldCount);
            System.out.println("Kohäsionsscore: " + cohesionScore);

            if (cohesionScore < 0.5) {
                System.out.println("Die Klasse hat eine schlechte Kohäsion und könnte SRP verletzen.");
            } else if (independentMethods > 0) {
                System.out.println("Es gibt " + independentMethods + " Methoden, die keine Feldinteraktionen haben. Mögliche SRP-Verletzung.");
            } else {
                System.out.println("Die Klasse scheint das Single Responsibility Principle einzuhalten.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int methodNameContainsField(Method method, Field[] fields) {
        int counter = 0;

        for(int i = 0; i < fields.length; i++) {
            if(method.getName().equalsIgnoreCase("get" + fields[i].getName())) {
                counter++;
                return counter;
            }
            if(method.getName().equalsIgnoreCase("set" + fields[i].getName())) {
                counter++;
                return counter;
            }
            if(method.getName().toLowerCase().contains("connect".toLowerCase())) {
                counter++;
                return counter;
            }
            if(method.getName().toLowerCase().contains("login".toLowerCase())) {
                counter++;
                return counter;
            }
            if(method.getName().toLowerCase().contains("sign".toLowerCase())) {
                counter++;
                return counter;
            }
            if(method.getName().toLowerCase().contains("register".toLowerCase())) {
                counter++;
                return counter;
            }
            if(method.getName().toLowerCase().contains(fields[i].getName().toLowerCase())) {
                counter++;
                return counter;
            }
        }
        return counter;
    }

    private static int methodUseFieldAsParameter(Method method, Field[] fields) {
        int counter = 0;
        Parameter[] parameters = method.getParameters();

        for (Parameter parameter : parameters) {
            Class<?> parameterType = parameter.getType();
            String parameterName = parameter.getName();

            for (Field field : fields) {
                if (field.getType().equals(parameterType) && field.getName().equalsIgnoreCase(parameterName)) {
                    counter++;
                    break;
                }
            }
        }
        return counter;
    }

    private static boolean methodUsesFieldAsReturnType(Method method, Field[] fields) {
        Class<?> returnType = method.getReturnType();

        for (Field field : fields) {
            if (field.getType().equals(returnType)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        cohesion(com.home.authentication.DatabaseConnection.class);
    }
}