package com.home.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.io.InputStream;

public class DITCalculator extends ClassVisitor {
    private String superClassName;
    private String[] interfaces;
    private boolean isInterface;
    private int dit = 1;
    private ClassLoader classLoader;

    public DITCalculator() {
        super(Opcodes.ASM9);
        this.classLoader = getClass().getClassLoader();
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.superClassName = superName;
        this.interfaces = interfaces;
        this.isInterface = (access & Opcodes.ACC_INTERFACE) != 0;

    }

    public int calculateDIT() throws IOException {
        if (isInterface) {
            return 1 + calculateMaxDITForInterfaces();
        } else {
            int classDIT = calculateDITForClass();
            int interfaceDIT = calculateMaxDITForInterfaces();

            return interfaceDIT > 0 ? Math.max(classDIT, 1 + interfaceDIT) : classDIT;
//            return calculateDITForClass();
        }
    }

    public int calculateDITForClass() throws IOException {
        int depth = 1;
        String currentClass = superClassName;

        if(superClassName == null) {
            return depth;
        }

        while(currentClass != null && !currentClass.equals("java/lang/Object")) {
            depth++;
            ClassReader reader = getSuperClassReader(currentClass);
            if(reader == null) {
                break;
            }

            SuperClassVisitor superClassVisitor = new SuperClassVisitor();
            reader.accept(superClassVisitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            currentClass = superClassVisitor.getSuperClassName();
        }
        return depth;
    }

    private int calculateMaxDITForInterfaces() throws IOException {
        int maxDIT = 0;
        for(String iface : interfaces) {
            int ifaceDIT = getInterfaceDIT(iface, new java.util.HashSet<>());
            maxDIT = Math.max(maxDIT, ifaceDIT);
        }
        return maxDIT;
    }

    private int getInterfaceDIT(String interfaceName, java.util.Set<String> visited) throws IOException {
        if (visited.contains(interfaceName)) {
            return 0;
        }
        visited.add(interfaceName);

        ClassReader reader = getSuperClassReader(interfaceName);
        if (reader == null) return 1;

        InterfaceVisitor interfaceVisitor = new InterfaceVisitor();
        reader.accept(interfaceVisitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

        int maxDIT = 0;
        for (String superInterface : interfaceVisitor.getInterfaces()) {
            int superDIT = getInterfaceDIT(superInterface, visited);
            maxDIT = Math.max(maxDIT, superDIT);
        }
        return maxDIT + 1;
    }

    private ClassReader getSuperClassReader(String className) throws IOException {
        try {
            String resourceName = className + ".class";
            InputStream is = classLoader.getResourceAsStream(resourceName);
            if (is == null) {
                resourceName = className.replace('/', '.') + ".class";
                is = classLoader.getResourceAsStream(resourceName);

                if (is == null) {
                    System.err.println("Klasse nicht gefunden: " + className);
                    return null;
                }
            }
            return new ClassReader(is);
        } catch (IOException e) {
            System.err.println("Fehler beim Laden von " + className + ": " + e.getMessage());
            return null;
        }
    }

    private static class SuperClassVisitor extends ClassVisitor {
        private String superClassName;

        public SuperClassVisitor() {
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

    private static class InterfaceVisitor extends ClassVisitor {
        private String[] interfaces;

        public InterfaceVisitor() {
            super(Opcodes.ASM9);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.interfaces = interfaces;
        }

        public String[] getInterfaces() {
            return interfaces;
        }
    }

    public static void main(String[] args) throws Exception {
//        String className = "com/home/polymorphism/fix/Sender";
//        String className = "com/home/InterfaceSegregationPrinciple/secondAnalysis/badExample/Payment";
//        String className = "com/home/openClosedPrinciple/interfaceInheritance/ElectricVehicle";
        String className = "com/home/pureFabrication/fifthExample/PayStrategy";

        try {
            ClassLoader classLoader = DITCalculator.class.getClassLoader();
            InputStream is = classLoader.getResourceAsStream(className.replace('/', '.') + ".class");
            if (is == null) {
                is = classLoader.getResourceAsStream(className + ".class");
            }

            if (is == null) {
                System.err.println("Klasse nicht gefunden: " + className);
                return;
            }

            ClassReader reader = new ClassReader(is);
            DITCalculator ditCalculator = new DITCalculator();
            reader.accept(ditCalculator, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

            int dit = ditCalculator.calculateDIT();
//            int dit = ditCalculator.calculateDIT();
            System.out.println("DIT-Wert für " + className + " = " + dit);
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}