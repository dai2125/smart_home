package com.home.asm;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.objectweb.asm.Opcodes.ASM9;

public class FanInService {
    private static final Map<String, Set<String>> classUses = new HashMap<>();      // FAN-OUT: Klassen, die von dieser Klasse verwendet werden
    private static final Map<String, Set<String>> classUsedBy = new HashMap<>();    // FAN-IN: Klassen, die diese Klasse verwenden
    private Map<String, Integer> classCallCount = new HashMap<>();
    private ClassReader classReader;
    private String targetClassName;
    private String packagePath;
    private File directory;
    private boolean isInterface;

    public FanInService(String targetClassName, String packagePath, File directory) {
        this.targetClassName = targetClassName;
        this.packagePath = packagePath;
        this.directory = directory;
    }

    public static boolean doesClassCallAnother(String className, String targetClassName) throws IOException {
        ClassReader classReader = new ClassReader(className);
        FanInCalculator.ClassCallVisitor visitor = new FanInCalculator.ClassCallVisitor(targetClassName);
        classReader.accept(visitor, 0);
        return visitor.isClassCalled();
    }

    class ClassCallVisitor extends ClassVisitor {
        private final String targetClassName;
        private boolean classCalled = false;

        public ClassCallVisitor(String targetClassName) {
            super(ASM9);
            this.targetClassName = targetClassName.replace('/', '.');
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return new FanInService.ClassCallVisitor.MethodCallVisitor(targetClassName);
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

    public Map<String, Integer> analyzePackage(File directory, String targetClass) throws IOException {
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Verzeichnis nicht gefunden: " + directory.getAbsolutePath());
            return classCallCount;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.err.println("Fehler beim Lesen des Verzeichnisses: " + directory.getAbsolutePath());
            return classCallCount;
        }

        for (File file : files) {
            if (file.isDirectory()) {
//                System.out.println("file.isDirectory: " + file.getName());
                analyzePackage(file, targetClass);
            } else if (file.getName().endsWith(".class")) {
//                System.out.println("file.getName().endsWith(.class): " + file.getName());
            } else if (file.getName().endsWith(".java")) {

//                System.out.println("String fileName: " + file.getPath());

                // AuWo Path
                String basePath = System.getProperty("user.dir").replaceAll("\\\\", "/") + "/Authentication/src/main/java/";
                String fileName = file.getPath().replace(".java", "")
                        .replaceAll("\\\\", "/")
                        .replace(basePath, "");
                //.replace("C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/", "");
//                System.out.println("String fileName: "+ fileName);
                ClassReader classReader = new ClassReader(fileName);
                isInterface = false;

                if (!fileName.equals(targetClass) /* && fileName.equals("com/home/indirection/fourthAnalysis/fix/BookRepository") */ ) {
//                    System.out.println("FILENAME: " + fileName);
                    classReader.accept(new ClassVisitor(ASM9) {

                        @Override
                        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
//                            System.out.println("ANALYSIERE INTERFACE: version: " + version + ", access: " + access + ", name: " + name + ", superName: " + superName + ", interfaces.length: " + interfaces.length);
                            isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
//                            System.out.println("IS INTERFACE: " + isInterface);


                            if(interfaces != null) {
                                for(String iface : interfaces) {
//                                    System.out.println("ERWEITERT: " + iface);
                                }
                                for(int i = 0; i < interfaces.length; i++) {
//                                    if(interfaces[i])
                                }
                            }
                        }

                        @Override
                        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//                            System.out.println("visitMethod name: " + name + ", desc: " + desc + ", signature: " + signature + ", access: " + access);

                            if(isInterface && desc.contains(targetClass)) {
//                                System.out.println();
                                Type[] argumentTypes = Type.getArgumentTypes(desc);
                                Type returnType = Type.getReturnType(desc);
//                                System.out.println("RRRR: " + returnType.getInternalName());
                                if(targetClass.equals(returnType.getInternalName())) {
                                    if(classCallCount.containsKey(fileName)) {
                                        classCallCount.put(fileName, classCallCount.get(fileName) + 1);
                                    } else {
                                        classCallCount.put(fileName, 1);
                                    }
                                }

                                for(Type arg : argumentTypes) {
//                                    System.out.println("PARAMETER TYPE: " + arg.getClassName());
//                                    System.out.println("PARAMETER TYPE: " + arg.getClassName().replaceAll("\\.", "/"));
//                                    System.out.println("PARAMETER TYPE: " + arg.getReturnType());
//                                    System.out.println("PARAMETER TYPE: " + arg.getInternalName());

                                    if(targetClass.equals(arg.getInternalName())) {
                                        if(classCallCount.containsKey(fileName)) {
                                            classCallCount.put(fileName, classCallCount.get(fileName) + 1);
                                        } else {
                                            classCallCount.put(fileName, 1);
                                        }
                                    }
                                }
                            }

                            if (desc.contains(targetClass)) {
//                                System.out.println("XXXX " + desc.replaceFirst("\\(\\)L", "").replace(";", ""));

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
//                                    System.out.println("visitMethodInsn owner: " + owner + ", name: " + name + ", desc: " + desc + ", itf: " + itf);

                                    if(Opcodes.NEW == opcode) {
//                                        System.out.println("NEW owner: " + owner + ", name: " + name + ", desc: " +desc);
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
//                                            System.out.println("PARAMETER TYPE: " + arg.getClassName());
//                                            System.out.println("PARAMETER TYPE: " + arg.getClassName().replaceAll("\\.", "/"));
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
//                                    System.out.println("visitFieldInsn, opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", description: " + desc);
                                    if(Opcodes.NEW == opcode) {
//                                        System.out.println("NEW");
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
//                                    System.out.println("visitTypeInsn type: " + type);
                                    if(Opcodes.NEW == opcode && type.equals(targetClass)) {
//                                        System.out.println("NEwww: " + type);
                                        if(classCallCount.containsKey(fileName)) {
                                            classCallCount.put(fileName, classCallCount.get(fileName) + 1);
                                        } else {
                                            classCallCount.put(fileName, 1);
                                        }
                                    }

                                }

                                @Override
                                public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
//                                    System.out.println("visitLocalVariable name: " + name + ", descriptor: " + descriptor + ", signature: " + signature);
                                    if(descriptor.contains(targetClass)) {
//                                        System.out.println("ZZZZZ " + descriptor.replaceFirst("L", "").replace(";", ""));

                                        if(targetClass.equals(descriptor.replaceFirst("L", "").replace(";", ""))) {
                                            if(classCallCount.containsKey(fileName)) {
                                                classCallCount.put(fileName, classCallCount.get(fileName) + 1);
                                            } else {
                                                classCallCount.put(fileName, 1);
                                            }
                                        }
                                    }
                                }

                            };
                        }
                    }, 0);
                }
            }
        }
        return classCallCount;
    }

    public static void analyzeClass(File classFile, String basePath, String packagePrefix) throws IOException {
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