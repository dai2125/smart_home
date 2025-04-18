package com.home.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class NOCCalculator {

    private ClassLoader classLoader;
    private String targetClassName;
    private Set<String> childClasses = new HashSet<String>();

    public NOCCalculator(String targetClassName) {
        this.classLoader = getClass().getClassLoader();
        this.targetClassName = targetClassName;
    }

    public int calculateNOC(String basePackage) {
        try {
            String baseDir = basePackage.replace('.', '/');
            String resourcePath = classLoader.getResource(baseDir).getPath();
            File packageDir = new File(resourcePath);

            scanPackageForChildren(packageDir, basePackage);

            System.out.println("Kindklassen von " + targetClassName + ":");
            for(String child : childClasses) {
                System.out.println(" - " + child);
            }

            return childClasses.size();
        } catch(Exception e) {
            System.err.println("Fehler bei der NOC-Berechnung: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    private void scanPackageForChildren(File packageDir, String packageName) {
        File[] files = packageDir.listFiles();
        if(files == null) {
            return;
        }

        for(File file : files) {
            if(file.isDirectory()) {
                scanPackageForChildren(file, packageName + "." + file.getName());
            } else if(file.getName().endsWith(".class")) {
//                System.out.println("file.getName(): " + file.getName());
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                checkIfChildClass(className);
            }
        }
    }

    private void checkIfChildClass(String className) {
        try {
            String internalClassName = className.replace('.', '/');
            FileInputStream fis = new FileInputStream(classLoader.getResource(internalClassName + ".class").getPath());
            ClassReader reader = new ClassReader(fis);

            SuperClassChecker checker = new SuperClassChecker();
            reader.accept(checker, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

//            System.out.println("reader: " + reader.getClassName());
//            System.out.println("reader: " + reader.getSuperName());
            System.out.println("FOR LOOP");
            for(int i = 0; i < reader.getInterfaces().length; i++) {
//                System.out.println(reader.getInterfaces()[i]);
                if(reader.getInterfaces()[i].equals(targetClassName)) {
                    childClasses.add(className);
                }
            }

//            System.out.println("targetClassName: " + targetClassName);
//            System.out.println("checker.getSuperClassName(): " + checker.getSuperClassName());
//            System.out.println("checker: " + checker.superClassName);
            if(targetClassName.equals(checker.getSuperClassName())) {
                childClasses.add(className);
            }

            fis.close();
        } catch(IOException e) {
            System.err.println("Fehler beim Prüfen von " + className + ": " + e.getMessage());
        }
    }

    private static class SuperClassChecker extends ClassVisitor {
        private String superClassName;

        public SuperClassChecker() {
            super(Opcodes.ASM9);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            System.out.println("name: " + name + ", signature: " + signature + ", superName: " + superName);
            this.superClassName = superName;
        }

        public String getSuperClassName() {
            return superClassName;
        }
    }

    public static void main(String[] args) {
        String targetClass = "com/home/openClosedPrinciple/firstAnalysis/goodExample/Bicycle";
        String basePackage = "com.home.openClosedPrinciple.firstAnalysis.goodExample";

        NOCCalculator nocCalculator = new NOCCalculator(targetClass);
        int noc = nocCalculator.calculateNOC(basePackage);

        System.out.println("NOC-Wert für " + targetClass + " = " + noc);
    }






}
