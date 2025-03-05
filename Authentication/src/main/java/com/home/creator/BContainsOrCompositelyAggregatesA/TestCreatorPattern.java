package com.home.creator.BContainsOrCompositelyAggregatesA;

import com.home.creator.BContainsOrCompositelyAggregatesA.secondAnalysis.aggregation.Car;
import com.home.creator.BContainsOrCompositelyAggregatesA.secondAnalysis.aggregation.Engine;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.io.*;
import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCreatorPattern  {

    @Test
    public void testJavaSourcePath2() throws Exception {
        Class<?> clazz = Car.class;

        String projectRoot = new File(".").getCanonicalPath();
        String sourcePath = "src/main/java";
        String javaFilePath = clazz.getName().replace('.', File.separatorChar) + ".java";
        String fullJavaPath = new File(projectRoot, sourcePath + File.separator + javaFilePath).getAbsolutePath();
        System.out.println("Java Source Path: " + fullJavaPath);

        String outputDir = new File(projectRoot, "out").getAbsolutePath();
        System.out.println("outputDir: " + outputDir);
        new File(outputDir).mkdirs();

        ProcessBuilder processBuilder = new ProcessBuilder(
                "javac", "-d", outputDir, fullJavaPath
        );

        Process process = processBuilder.start();

        int exitCode = process.waitFor();
        System.out.println("Compilation finished with exit code: " + exitCode);

        File compiledClass = new File(outputDir, clazz.getName().replace('.', File.separatorChar) + ".class");
        if (compiledClass.exists()) {
            System.out.println("Compiled class found: " + compiledClass.getAbsolutePath());
        } else {
            System.out.println("Compiled class not found.");
        }

        ProcessBuilder javapProcessBuilder = new ProcessBuilder(
                "javap", "-c", compiledClass.getAbsolutePath()
        );
        javapProcessBuilder.redirectErrorStream(true);

        Process javapProcess = javapProcessBuilder.start();

        StringBuilder bytecodeOutput = new StringBuilder();
        boolean readingConstructor = false;
        String constructorParameter;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(javapProcess.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bytecodeOutput.append(line).append(System.lineSeparator());
                if(line.contains("{")) {
                    readingConstructor = true;
//                    System.out.println("Found an 'a'.");
                }

                if(readingConstructor) {
                    if(line.contains("(") && line.contains(")") && line.indexOf(")") - line.indexOf("(") > 1) {
                        System.out.println("Constructor found: " + line);
                        constructorParameter = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                        constructorParameter = constructorParameter.substring(constructorParameter.lastIndexOf(".") + 1);
                        System.out.println("Constructor parameter: " + constructorParameter);
                        readingConstructor = false;
                    }
                }

                if(line.contains("invoke")) {
//                    System.out.println("Found a invoke.");
                }
            }
        }

        int javapExitCode = javapProcess.waitFor();
        System.out.println("javap finished with exit code: " + javapExitCode);

        String bytecode = bytecodeOutput.toString();
        System.out.println("Bytecode:\n" + bytecode);

        File bytecodeFile = new File(outputDir, clazz.getSimpleName() + "_bytecode.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bytecodeFile))) {
            writer.write(bytecode);
        }

        System.out.println("Bytecode written to file: " + bytecodeFile.getAbsolutePath());
    }


    @Test
    public void testJavaSourcePath() throws Exception {
        Class<?> clazz = Car.class;

        String projectRoot = new File(".").getCanonicalPath();
        String sourcePath = "src/main/java";
        String javaFilePath = clazz.getName().replace('.', File.separatorChar) + ".java";

        String fullJavaPath = new File(projectRoot, sourcePath + File.separator + javaFilePath).getAbsolutePath();
        System.out.println("Java Source Path: " + fullJavaPath);

//        ProcessBuilder processBuilder = new ProcessBuilder("javac -d out src\\main\\java\\com\\home\\creator\\BContainsOrCompositelyAggregatesA\\secondAnalysis\\aggregation\\*.java");
        ProcessBuilder processBuilder = new ProcessBuilder("javac", "-d", "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\creator\\BContainsOrCompositelyAggregatesA\\secondAnalysis\\aggregation\\out", fullJavaPath);
        processBuilder.start();

//        processBuilder.inheritIO();
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        System.out.println("Compilation finished with exit code: " + exitCode);

        processBuilder.command("javap", "-c", fullJavaPath);

//        processBuilder.command()
    }


    @Test
    public void test() throws Exception {

        Class<?> clazz1 = Car.class;

        String classPath = clazz1.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println("Class Path: " + classPath);

        Class<?> clazz = Class.forName(Car.class.getName());
        final File file = new File(clazz.getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println("file Path: " + file.getAbsolutePath());
//        ProcessBuilder processBuilder = new ProcessBuilder()
    }

    @Test
    public void testCreatorPrincipleAggregation() throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);

        Class<?> clazz = Class.forName("com.home.creator.BContainsOrCompositelyAggregatesA.secondAnalysis.aggregation.Car");
//        Object carInstance = unsafe.allocateInstance(clazz);
//
//        Field engineField = clazz.getDeclaredField("engine");
//        engineField.setAccessible(true);
//        Object engineValue = engineField.get(carInstance);

//        System.out.println("engineField before constructor: " + engineField);
//        System.out.println("engineValue before constructor: " + engineValue);

//        if(engineValue instanceof Engine) {
//            System.out.println("engineValue is instanceof Engine");
//        } else {
//            System.out.println("engineValue is NOT instanceof Engine");
//        }

//        assertNull(engineField, "engineField should be null");

//        assertNull(engineValue, "Engine field should be null before constructor");

        Constructor<?> constructor = clazz.getDeclaredConstructor(Engine.class);
        Field[] fields = constructor.getDeclaringClass().getDeclaredFields();
        Field[] fieldsClass = clazz.getDeclaredFields();

        constructor.setAccessible(true);
        Object instance = constructor.newInstance(new Engine(200));

        for (Field field : fieldsClass) {
            System.out.println("Class fields: " + field);
        }

        for (Field field : fields) {
            field.setAccessible(true);
            System.out.println("Constructor fields: " + field);
            Object fieldValue = field.get(instance);

//            System.out.println("engineValue: " + engineValue);
//            System.out.println("fieldValue: " + fieldValue);

//            System.out.println("Field: " + field.getName()
//                    + " | Type: " + field.getType()
//                    + " | Value: " + fieldValue);

//            if (fieldValue != null && fieldValue instanceof Engine) {
//                System.out.println("Im Constructor Das Feld " + field.getName() + " enthält eine Instanz von Engine.");
//            } else {
//                System.out.println("Im Constructor Das Feld " + field.getName() + " wurde nicht korrekt initialisiert.");
//            }
        }
    }

    @Test
    public void testCreatorPrincipleContains() throws Exception {
        Class<?> clazz = Class.forName("com.home.creator.BContainsOrCompositelyAggregatesA.secondAnalysis.contains.Car");
        Constructor<?> constructor = clazz.getDeclaredConstructor(Engine.class);

        Object instance = constructor.newInstance();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            Object fieldValue = field.get(instance);

            if (fieldValue != null && fieldValue instanceof Engine) {
                System.out.println("Das Feld " + field.getName() + " enthält eine Instanz von Calculator.");
            } else {
                System.out.println("Das Feld " + field.getName() + " wurde nicht korrekt initialisiert.");
            }
        }
//        constructor.setAccessible(true);
    }
}

