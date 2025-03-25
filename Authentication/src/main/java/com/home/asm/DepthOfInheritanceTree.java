package com.home.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.io.InputStream;

public class DepthOfInheritanceTree extends ClassVisitor {

    private String superClassName;
    private String[] interfaces;
    private boolean isInterface;
    private int dit = 1;
    private ClassLoader classLoader;
    private String targetClassName;
    private ClassReader classReader;

    public DepthOfInheritanceTree(String targetClassName) throws IOException {
        super(Opcodes.ASM9);
        this.classReader = new ClassReader(targetClassName);
        classReader.accept(this, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        this.classLoader = getClass().getClassLoader();
        this.targetClassName = targetClassName;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.superClassName = superName;
        this.interfaces = interfaces;
        this.isInterface = (access & Opcodes.ACC_INTERFACE) != 0;

//        System.out.println("\nBesuchte Klasse: " + name);
//        System.out.println("Superklasse: " + superName);
//        System.out.println("Schnittstellen: " + (interfaces == null ? "null" : String.join(",", interfaces)) + "\n");

    }

    public int calculateDIT() throws IOException {
        if (isInterface) {
            return 1 + calculateMaxDITForInterfaces();
        } else {
            int classDIT = calculateDITForClass();
            int interfaceDIT = calculateMaxDITForInterfaces();

            return interfaceDIT > 0 ? Math.max(classDIT, 1 + interfaceDIT) : classDIT;
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

            DepthOfInheritanceTree.SuperClassVisitor superClassVisitor = new DepthOfInheritanceTree.SuperClassVisitor();
            reader.accept(superClassVisitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            currentClass = superClassVisitor.getSuperClassName();
        }
        return depth;
    }

    private int calculateMaxDITForInterfaces() throws IOException {

        if(interfaces == null) {
            return 0;
        }

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

        DepthOfInheritanceTree.InterfaceVisitor interfaceVisitor = new DepthOfInheritanceTree.InterfaceVisitor();
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

    public boolean checkIfClassIsInterface() {
        return isInterface;
    }

}
