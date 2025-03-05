package com.home.readCSV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CountLineOfCode {

    private static int countImports(String filePath) throws IOException {
        int importCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("import ")) {
                    importCount++;
                }
            }
        }
        return importCount;
    }

    public static int countConstructorLines(String filePath, String fileName) throws IOException {
        int lineCount = 0;
        boolean insideConstructor = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if(line.startsWith("public " + fileName) || line.startsWith("private " + fileName) || line.startsWith("protected " + fileName)) {
                    insideConstructor = true;
                }
                if(insideConstructor) {
                    lineCount++;
                    if(line.contains("}")) {
                        insideConstructor = false;
                        break;
                    }
                }
            }
        }
        return lineCount;
    }

    public static int countFieldLines(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        int fieldLines = 0;

        for (Field field : fields) {
            fieldLines++;
        }
//        return fieldLines;
        return fields.length;
    }

//    private static int countConstructorLines(Class<?> clazz, String filePath) throws IOException {
//        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
//        System.out.println(constructors.length + " constructors");
//        int constructorLines = 0;
//
//        for (Constructor<?> constructor : constructors) {
//            String constructorName = constructor.getName();
//            constructorLines += countMethodOrConstructorLines(filePath, constructorName);
//        }
//        return constructorLines;
//    }

//    private static int countMethodLines(Class<?> clazz, String filePath) throws IOException {
//        Method[] methods = clazz.getDeclaredMethods();
//        int methodLines = 0;
//
//        for (Method method : methods) {
//            String methodName = method.getName();
//            methodLines += countMethodOrConstructorLines(filePath, methodName);
//        }
//        return methodLines;
//    }

    public static int countMethodLines(String filePath) throws IOException {
        int methodLines = 0;
        boolean insideMethod = false;
        int currentMethodLines = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int openBraces = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (!insideMethod && line.matches(".*\\)\\s*\\{")) {
                    insideMethod = true;
                    openBraces = 1;
                    currentMethodLines = 1;
                    continue;
                }

                if (insideMethod) {
                    currentMethodLines++;

                    if (line.contains("{")) {
                        openBraces++;
                    }
                    if (line.contains("}")) {
                        openBraces--;
                    }

                    if (openBraces == 0) {
                        methodLines += currentMethodLines;
                        insideMethod = false;
                        currentMethodLines = 0;
                    }
                }
            }
        }
        return methodLines;
    }



    private static int countMethodOrConstructorLines(String filePath, String methodName) throws IOException {
        int lines = 0;
        boolean insideTarget = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.contains(methodName + "(")) {
                    insideTarget = true;
                }

                if (insideTarget) {
                    lines++;
                    if (line.contains("}")) {
                        insideTarget = false;
                        break;
                    }
                }
            }
        }
        return lines;
    }

    public static void analyzeClass(String filePath, Class<?> clazz) throws IOException {
        int importCount = countImports(filePath);
        int fieldLines = countFieldLines(clazz);
//        int constructorLines = countConstructorLines(clazz, filePath);
        String className = clazz.getSimpleName();
        int constructorLines = countConstructorLines(filePath, className);
        // TODO constructorLines + 1 für die Klasse selber
        int methodLines = countMethodLines(filePath);

        System.out.println("Analyzing class: " + clazz.getSimpleName());
        System.out.println("Number of import lines: " + importCount);
        System.out.println("Number of field lines: " + fieldLines);
//        System.out.println("Number of constructor lines: " + constructorLines);
        System.out.println("Number of method lines: " + methodLines);
        System.out.println("Number of constructor lines: " + constructorLines);
    }



}
