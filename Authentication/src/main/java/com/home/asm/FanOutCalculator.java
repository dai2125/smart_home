package com.home.asm;

import org.objectweb.asm.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.objectweb.asm.Opcodes.ASM9;

public class FanOutCalculator {

    private static final Map<String, Set<String>> classUses = new HashMap<>();      // FAN-OUT: Klassen, die von dieser Klasse verwendet werden
    private static final Map<String, Set<String>> classUsedBy = new HashMap<>();    // FAN-IN: Klassen, die diese Klasse verwenden
    private Map<String, Integer> classCallCount = new HashMap<>();
    private ClassReader classReader;
    private Set<String> classTypes = new HashSet<>();

    public static boolean doesClassCallAnother(String className, String targetClassName) throws IOException {
        ClassReader classReader = new ClassReader(className);
        ClassCallVisitor visitor = new ClassCallVisitor(targetClassName);
        classReader.accept(visitor, 0);
        return visitor.isClassCalled();
    }

    static class ClassCallVisitor extends ClassVisitor {
        private final String targetClassName;
        private boolean classCalled = false;

        public ClassCallVisitor(String targetClassName) {
            super(ASM9);
            this.targetClassName = targetClassName.replace('/', '.');
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return new MethodCallVisitor(targetClassName);
        }

        public boolean isClassCalled() {
            return classCalled;
        }

        class MethodCallVisitor extends MethodVisitor {
            public MethodCallVisitor(String targetClassName) {
                super(ASM9);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                if (owner.equals(targetClassName)) {
                    classCalled = true;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String packagePath = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\pureFabrication\\fifthExample";
        String targetClass = "com/home/pureFabrication/fifthExample/PayStrategy";

        String basePath = System.getProperty("user.dir") + "/target/classes/";
        FanOutCalculator fanOutCalculator = new FanOutCalculator();

        File directory = new File(packagePath);

        fanOutCalculator.analyzePackage(directory, basePath, packagePath, targetClass);

        Iterator<String> itr = fanOutCalculator.classTypes.iterator();
        while(itr.hasNext()) {
//            System.out.println(itr.next());
            fanOutCalculator.targetClassContainsOtherClass(targetClass, itr.next());
        }

        for (String name : fanOutCalculator.classCallCount.keySet()) {
            String key = name;
            String value = fanOutCalculator.classCallCount.get(key).toString();
            System.out.println(key + " " + value);
        }

        System.out.println("FANOUT von " + targetClass + " ist = " + fanOutCalculator.classCallCount.size());
    }

    private void targetClassContainsOtherClass(String targetClass, String className) throws IOException {
        ClassReader classReader = new ClassReader(targetClass);

        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                System.out.println("QQ name: " + name + ", desc: " + desc + ", signature: " + signature);
                if (desc.contains(className)) {
                    System.out.println("XX " + desc.replaceFirst("\\(\\)L", "").replace(";", ""));
                    System.out.println("XX " + desc);
                    System.out.println("XX " + className);

                    if (className.equals(desc.replaceFirst("\\(\\)L", "").replace(";", ""))) {
                        if (classCallCount.containsKey(className)) {
                            classCallCount.put(className, classCallCount.get(className) + 1);
                        } else {
                            classCallCount.put(className, 1);
                        }
                    }
                }
                return new MethodVisitor(ASM9) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                        if (Opcodes.NEW == opcode) {
                            System.out.println("NEW owner: " + owner + ", name: " + name + ", desc: " + desc);
                        } else if (Opcodes.INVOKESPECIAL != opcode && owner.equals(className)) {
                            if (classCallCount.containsKey(className)) {
                                classCallCount.put(className, classCallCount.get(className) + 1);
                            } else {
                                classCallCount.put(className, 1);
                            }
                        } else if (Opcodes.INVOKEVIRTUAL == opcode) {
                            Type[] argumentTypes = Type.getArgumentTypes(desc);

                            for (Type arg : argumentTypes) {
                                System.out.println("PARAMETER TYPE: " + arg.getClassName());
                                System.out.println("PARAMETER TYPE: " + arg.getClassName().replaceAll("\\.", "/"));
                                if (arg.getClassName().replaceAll("\\.", "/").equals(targetClass)) {
                                    if (classCallCount.containsKey(className)) {
                                        classCallCount.put(className, classCallCount.get(className) + 1);
                                    } else {
                                        classCallCount.put(className, 1);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                        System.out.println("owner: " + owner + ", name: " + name + ", description: " + desc);
                        if (Opcodes.NEW == opcode) {
                            System.out.println("NEW");
                        }

                        if (owner.equals(className)) {
                            if (classCallCount.containsKey(className)) {
                                classCallCount.put(className, classCallCount.get(className) + 1);
                            } else {
                                classCallCount.put(className, 1);
                            }
                        }
                    }

                    @Override
                    public void visitTypeInsn(int opcode, String type) {
                        System.out.println("owner: " + type);
                        if (Opcodes.NEW == opcode && type.equals(className)) {
                            System.out.println("NEwww: " + type);
                            if (classCallCount.containsKey(className)) {
                                classCallCount.put(className, classCallCount.get(className) + 1);
                            } else {
                                classCallCount.put(className, 1);
                            }
                        }

                    }
                };
            }
        }, 0);
    }

    private void analyzePackage(File directory, String basePath, String packagePrefix, String targetClass) throws IOException {
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Verzeichnis nicht gefunden: " + directory.getAbsolutePath());
            return;
        }

        System.out.println("Durchsuche Verzeichnis: " + directory.getAbsolutePath());

        File[] files = directory.listFiles();
        if (files == null) {
            System.err.println("Fehler beim Lesen des Verzeichnisses: " + directory.getAbsolutePath());
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("file.isDirectory: " + file.getName());
                String newPackagePrefix = packagePrefix + file.getName() + "/";
                analyzePackage(file, basePath, newPackagePrefix, targetClass);
            } else if (file.getName().endsWith(".class")) {
                System.out.println("file.getName().endsWith(.class): " + file.getName());
            } else if (file.getName().endsWith(".java")) {

                System.out.println("else: " + file.getName());
                System.out.println("else: " + file.getAbsolutePath());
                String fileName = file.getPath().replace(".java", "")
                        .replaceAll("\\\\", "/")
                        .replace("C:/Users/aigne/IdeaProjects/smart_home/Authentication/src/main/java/", "");

                System.out.println("fileName: " + fileName + " : " + targetClass);
                if (!fileName.equals(targetClass)) {
                    classTypes.add(fileName);
                }
            }
        }
    }

//    private void analyzeClass(File directory, String basePath, String packagePrefix, String targetClass) throws IOException {
//
//        System.out.println("targetClass: " + targetClass);
//
//        ClassReader classReader = new ClassReader(targetClass);
//
//        classReader.accept(new ClassVisitor(ASM9) {
//            @Override
//            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//                System.out.println("QQ name: " + name + ", desc: " + desc + ", signature: " + signature);
//                if (desc.contains(targetClass)) {
//                    System.out.println("XX " + desc.replaceFirst("\\(\\)L", "").replace(";", ""));
//                    System.out.println("XX " + desc);
//                    System.out.println("XX " + targetClass);
//
//                    if (targetClass.equals(desc.replaceFirst("\\(\\)L", "").replace(";", ""))) {
//                        if (classCallCount.containsKey(fileName)) {
//                            classCallCount.put(fileName, classCallCount.get(fileName) + 1);
//                        } else {
//                            classCallCount.put(fileName, 1);
//                        }
//                    }
//                }
//                return new MethodVisitor(ASM9) {
//                    @Override
//                    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
//                        if (Opcodes.NEW == opcode) {
//                            System.out.println("NEW owner: " + owner + ", name: " + name + ", desc: " + desc);
//                        } else if (Opcodes.INVOKESPECIAL != opcode && owner.equals(targetClass)) {
//                            if (classCallCount.containsKey(fileName)) {
//                                classCallCount.put(fileName, classCallCount.get(fileName) + 1);
//                            } else {
//                                classCallCount.put(fileName, 1);
//                            }
//                        } else if (Opcodes.INVOKEVIRTUAL == opcode) {
//                            Type[] argumentTypes = Type.getArgumentTypes(desc);
//
//                            for (Type arg : argumentTypes) {
//                                System.out.println("PARAMETER TYPE: " + arg.getClassName());
//                                System.out.println("PARAMETER TYPE: " + arg.getClassName().replaceAll("\\.", "/"));
//                                if (arg.getClassName().replaceAll("\\.", "/").equals(targetClass)) {
//                                    if (classCallCount.containsKey(fileName)) {
//                                        classCallCount.put(fileName, classCallCount.get(fileName) + 1);
//                                    } else {
//                                        classCallCount.put(fileName, 1);
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
//                        System.out.println("owner: " + owner + ", name: " + name + ", description: " + desc);
//                        if (Opcodes.NEW == opcode) {
//                            System.out.println("NEW");
//                        }
//
//                        if (owner.equals(targetClass)) {
//                            if (classCallCount.containsKey(fileName)) {
//                                classCallCount.put(fileName, classCallCount.get(fileName) + 1);
//                            } else {
//                                classCallCount.put(fileName, 1);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void visitTypeInsn(int opcode, String type) {
//                        System.out.println("owner: " + type);
//                        if (Opcodes.NEW == opcode && type.equals(targetClass)) {
//                            System.out.println("NEwww: " + type);
//                            if (classCallCount.containsKey(fileName)) {
//                                classCallCount.put(fileName, classCallCount.get(fileName) + 1);
//                            } else {
//                                classCallCount.put(fileName, 1);
//                            }
//                        }
//
//                    }
//                };
//            }
//        }, 0);
//    }
}