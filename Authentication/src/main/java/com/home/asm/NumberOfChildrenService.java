package com.home.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class NumberOfChildrenService {

    private ClassLoader classLoader;
    private String targetClassName;
    private Set<String> childClasses = new HashSet<String>();

    public NumberOfChildrenService(String targetClassName) {

        //        this.classLoader = getClass().getClassLoader();
        this.classLoader = getClass().getClassLoader();
        this.targetClassName = targetClassName;
    }

    public int calculateNOC(String basePackage) {
        try {
//            System.out.println("NoC: " + basePackage);
//            System.out.println("NoC: " + basePackage.replace('.', '/'));
//            String baseDir = basePackage.replace('.', '/');
            String baseDir = basePackage.replaceAll("\\\\", "/");

//            baseDir = baseDir.replaceAll("\\\\", "/");
            baseDir = basePackage.replaceAll(".*/", "");
//            baseDir = basePackage.replaceAll("C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/", "");
//            System.out.println("Noc: " + baseDir);
//            String resourcePath = classLoader.getResource(baseDir).getPath();
            File packageDir = new File(basePackage);

            scanPackageForChildren(packageDir, basePackage);

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
            System.out.println("NoC2: " + internalClassName);
            FileInputStream fis = new FileInputStream(classLoader.getResource(internalClassName + ".class").getPath());
            ClassReader reader = new ClassReader(fis);

            NumberOfChildrenService.SuperClassChecker checker = new NumberOfChildrenService.SuperClassChecker();
            reader.accept(checker, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

//            System.out.println("reader: " + reader.getClassName());
//            System.out.println("reader: " + reader.getSuperName());
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
            this.superClassName = superName;
        }

        public String getSuperClassName() {
            return superClassName;
        }
    }
}
