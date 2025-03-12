package com.home.asm;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.objectweb.asm.Opcodes.ASM9;

public class FanInCalculator {

    private static final Map<String, Set<String>> classUses = new HashMap<>();      // FAN-OUT: Klassen, die von dieser Klasse verwendet werden
    private static final Map<String, Set<String>> classUsedBy = new HashMap<>();    // FAN-IN: Klassen, die diese Klasse verwenden
    private Map<String, Integer> classCallCount = new HashMap<>();
    private ClassReader classReader;

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
                if(owner.equals(targetClassName)) {
                    classCalled = true;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        String packagePath = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\pureFabrication\\fifthExample";
//        String targetClass = "com/home/pureFabrication/fifthExample/PayStrategy";
        String packagePath = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\pureFabrication\\fifthExample";
        String targetClass = "com/home/pureFabrication/fifthExample/PayByCreditCard";

        String basePath = System.getProperty("user.dir") + "/target/classes/";
        FanInCalculator fanInFanOutCalculator = new FanInCalculator();

//        boolean result = doesClassCallAnother(targetClass);

        File directory = new File(packagePath);

        fanInFanOutCalculator.analyzePackage(directory, targetClass);

        // Ergebnisse ausgeben
        System.out.println("=== FAN-IN und FAN-OUT Metriken ===");
        System.out.println("Klasse                 | FAN-IN | FAN-OUT");
        System.out.println("----------------------------------------");

//        for(int i = 0; i < classUsedBy.size(); i++) {
//            System.out.println(classUsedBy.get(i));
//        }
//
//        for(int i = 0; i < classUses.size(); i++) {
//            System.out.println(classUses.get(i));
//        }

        fanInFanOutCalculator.classCallCount.forEach((cls, count) -> System.out.println("|| " + targetClass + " wird in " + cls + " " + count + " mal in anderen Klassen aufgerufen"));


        List<String> sortedClasses = new ArrayList<>(classUses.keySet());
        Collections.sort(sortedClasses);

        for (String clazz : sortedClasses) {
            int fanOut = classUses.get(clazz).size();
            int fanIn = classUsedBy.getOrDefault(clazz, new HashSet<>()).size();
            System.out.printf("%-23s |\t %6d |\t %7d%n", clazz.replace("com/home/pureFabrication/fourthExample/", ""), fanIn, fanOut);
        }

        for(String name : fanInFanOutCalculator.classCallCount.keySet()) {
            String key = name;
            String value = fanInFanOutCalculator.classCallCount.get(key).toString();
            System.out.println(key + " " + value);
        }

        System.out.println("FANIN von " + targetClass + " ist = " + fanInFanOutCalculator.classCallCount.size());
    }

    private void analyzeClass2(String fileName) throws IOException {
        ClassReader classReader = new ClassReader(fileName);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        classUses.putIfAbsent(classNode.name, new HashSet<>());
        classUsedBy.putIfAbsent(classNode.name, new HashSet<>());

        for(MethodNode method : classNode.methods) {
            for(AbstractInsnNode insn = method.instructions.getFirst(); insn != null; insn = insn.getNext()) {
                if(insn instanceof MethodInsnNode) {
                    MethodInsnNode methodInsnNode = (MethodInsnNode) insn;

                    classUses.get(classNode.name).add(methodInsnNode.name);
                    classUsedBy.putIfAbsent(methodInsnNode.owner, new HashSet<>());
                    classUsedBy.get(methodInsnNode.owner).add(classNode.name);

                }
            }
        }
    }

    public void analyzePackage(File directory, String targetClass) throws IOException {
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
                analyzePackage(file, targetClass);
            } else if (file.getName().endsWith(".class")) {
                System.out.println("file.getName().endsWith(.class): " + file.getName());
            } else if (file.getName().endsWith(".java")) {

                System.out.println("else: " + file.getName());
                System.out.println("else: " + file.getPath());
                System.out.println("else: " + file.getAbsolutePath());
                String fileName = file.getPath().replace(".java", "")
                        .replaceAll("\\\\", "/")
                        .replace("C:/Users/aigne/IdeaProjects/smart_home/Authentication/src/main/java/", "");

                System.out.println("fileName: " + fileName + " : " + targetClass);

                ClassReader classReader = new ClassReader(fileName);

                if (!fileName.equals(targetClass)) {
                    classReader.accept(new ClassVisitor(ASM9) {
                        @Override
                        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                            System.out.println("QQ name: " + name + ", desc: " + desc + ", signature: " + signature);
                            if (desc.contains(targetClass)) {
                                System.out.println("XX " + desc.replaceFirst("\\(\\)L", "").replace(";", ""));
                                System.out.println("XX " + desc);
                                System.out.println("XX " + targetClass);

                                if(targetClass.equals(desc.replaceFirst("\\(\\)L", "").replace(";", ""))) {
                                    if(classCallCount.containsKey(fileName)) {
                                        classCallCount.put(fileName, classCallCount.get(fileName) + 1);
                                    } else {
                                        classCallCount.put(fileName, 1);
                                    }
                                }
                            }
                            return new MethodVisitor(ASM9) {
                                @Override
                                public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                                    if(Opcodes.NEW == opcode) {
                                        System.out.println("NEW owner: " + owner + ", name: " + name + ", desc: " +desc);
                                    }
                                    else if (Opcodes.INVOKESPECIAL != opcode && owner.equals(targetClass)) {
                                        if(classCallCount.containsKey(fileName)) {
                                            classCallCount.put(fileName, classCallCount.get(fileName) + 1);
                                        } else {
                                            classCallCount.put(fileName, 1);
                                        }
                                    } else if(Opcodes.INVOKEVIRTUAL == opcode) {
                                        Type[] argumentTypes = Type.getArgumentTypes(desc);

                                        for(Type arg : argumentTypes) {
                                            System.out.println("PARAMETER TYPE: " + arg.getClassName());
                                            System.out.println("PARAMETER TYPE: " + arg.getClassName().replaceAll("\\.", "/"));
                                            if(arg.getClassName().replaceAll("\\.", "/").equals(targetClass)) {
                                                if(classCallCount.containsKey(fileName)) {
                                                    classCallCount.put(fileName, classCallCount.get(fileName) + 1);
                                                } else {
                                                    classCallCount.put(fileName, 1);
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                                    System.out.println("owner: " + owner + ", name: " + name + ", description: " + desc);
                                    if(Opcodes.NEW == opcode) {
                                        System.out.println("NEW");
                                    }

                                    if(owner.equals(targetClass)) {
                                        if(classCallCount.containsKey(fileName)) {
                                            classCallCount.put(fileName, classCallCount.get(fileName) + 1);
                                        } else {
                                            classCallCount.put(fileName, 1);
                                        }
                                    }
                                }

                                @Override
                                public void visitTypeInsn(int opcode, String type) {
                                    System.out.println("owner: " + type);
                                    if(Opcodes.NEW == opcode && type.equals(targetClass)) {
                                        System.out.println("NEwww: " + type);
                                        if(classCallCount.containsKey(fileName)) {
                                            classCallCount.put(fileName, classCallCount.get(fileName) + 1);
                                        } else {
                                            classCallCount.put(fileName, 1);
                                        }
                                    }

                                }
                            };
                        }
                    }, 0);
                }
            }
        }
    }
    private static void analyzeClass(File classFile, String basePath, String packagePrefix) throws IOException {
        // Relativen Klassenpfad ermitteln
        String classPath = packagePrefix + classFile.getName();
        String className = classPath.substring(0, classPath.length() - 6); // ".class" entfernen

        // Konvertieren zu Java-internem Pfadformat
        String internalClassName = className.replace('/', '.');

        try (FileInputStream fis = new FileInputStream(classFile)) {
            ClassReader reader = new ClassReader(fis);
            ClassNode classNode = new ClassNode();
            reader.accept(classNode, 0);

            // Korrekter interner Klassenname
            String currentClass = classNode.name.replace('/', '.');

            // Initialisiere Sets für die aktuelle Klasse
            classUses.putIfAbsent(currentClass, new HashSet<>());
            classUsedBy.putIfAbsent(currentClass, new HashSet<>());

            // Analysiere alle Methoden
            for (MethodNode method : classNode.methods) {
                if (method.instructions == null) continue;

                for (AbstractInsnNode insn = method.instructions.getFirst(); insn != null; insn = insn.getNext()) {
                    if (insn instanceof MethodInsnNode) {
                        // Methodenaufruf gefunden
                        MethodInsnNode methodInsn = (MethodInsnNode) insn;
                        String usedClass = methodInsn.owner.replace('/', '.');

                        // Aktuelle Klasse benutzt die aufgerufene Klasse (FAN-OUT)
                        classUses.get(currentClass).add(usedClass);

                        // Die aufgerufene Klasse wird von der aktuellen Klasse benutzt (FAN-IN)
                        classUsedBy.putIfAbsent(usedClass, new HashSet<>());
                        classUsedBy.get(usedClass).add(currentClass);

                    } else if (insn instanceof FieldInsnNode) {
                        // Feldzugriff gefunden
                        FieldInsnNode fieldInsn = (FieldInsnNode) insn;
                        String usedClass = fieldInsn.owner.replace('/', '.');

                        // Aktuelle Klasse benutzt die Klasse des Feldes (FAN-OUT)
                        classUses.get(currentClass).add(usedClass);

                        // Die Klasse des Feldes wird von der aktuellen Klasse benutzt (FAN-IN)
                        classUsedBy.putIfAbsent(usedClass, new HashSet<>());
                        classUsedBy.get(usedClass).add(currentClass);

                    } else if (insn instanceof TypeInsnNode) {
                        // Typoperationen (new, instanceof, etc.)
                        TypeInsnNode typeInsn = (TypeInsnNode) insn;
                        if (typeInsn.desc.startsWith("L") && typeInsn.desc.endsWith(";")) {
                            // Bei Objekttypen im Format Ljava/lang/String;
                            String usedClass = typeInsn.desc.substring(1, typeInsn.desc.length() - 1).replace('/', '.');
                            classUses.get(currentClass).add(usedClass);
                            classUsedBy.putIfAbsent(usedClass, new HashSet<>());
                            classUsedBy.get(usedClass).add(currentClass);
                        } else {
                            // Bei einfachen Typnamen
                            String usedClass = typeInsn.desc.replace('/', '.');
                            classUses.get(currentClass).add(usedClass);
                            classUsedBy.putIfAbsent(usedClass, new HashSet<>());
                            classUsedBy.get(usedClass).add(currentClass);
                        }
                    }
                }
            }

            // Selbstreferenzen entfernen
            classUses.get(currentClass).remove(currentClass);
            if (classUsedBy.containsKey(currentClass)) {
                classUsedBy.get(currentClass).remove(currentClass);
            }

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Klassendatei: " + classFile.getAbsolutePath());
            e.printStackTrace();
        }
    }
}