package com.home.asm;

import org.objectweb.asm.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.objectweb.asm.Opcodes.ASM9;

public class FanOutService {
    private static final Map<String, Set<String>> classUses = new HashMap<>();      // FAN-OUT: Klassen, die von dieser Klasse verwendet werden
    private static final Map<String, Set<String>> classUsedBy = new HashMap<>();    // FAN-IN: Klassen, die diese Klasse verwenden
    private Map<String, Integer> classCallCount = new HashMap<>();
    private ClassReader classReader;
    private String targetClassName;
    private String packagePath;
    private File directory;
    private boolean isInterface;
    private Set<String> classUsed = new HashSet<>();

    public FanOutService(String targetClassName, String packagePath, File directory) {
        this.targetClassName = targetClassName;
        this.packagePath = packagePath;
        this.directory = directory;
    }

    public static boolean doesClassCallAnother(String className, String targetClassName) throws IOException {
        ClassReader classReader = new ClassReader(className);
        FanOutCalculator.ClassCallVisitor visitor = new FanOutCalculator.ClassCallVisitor(targetClassName);
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
            return new FanOutService.ClassCallVisitor.MethodCallVisitor(targetClassName);
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

    public int analyzePackage2(String className, String targetClass) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;

        if (!className.equals(targetClass) /* && fileName.equals("com/home/indirection/fourthAnalysis/fix/BookRepository") */ ) {
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
                            classUsed.add(targetClass);
                            if(classCallCount.containsKey(className)) {
                                classCallCount.put(className, classCallCount.get(className) + 1);
                            } else {
                                classCallCount.put(className, 1);
                            }
                        }

                        for(Type arg : argumentTypes) {
//                                    System.out.println("PARAMETER TYPE: " + arg.getClassName());
//                                    System.out.println("PARAMETER TYPE: " + arg.getClassName().replaceAll("\\.", "/"));
//                                    System.out.println("PARAMETER TYPE: " + arg.getReturnType());
//                                    System.out.println("PARAMETER TYPE: " + arg.getInternalName());

                            if(targetClass.equals(arg.getInternalName())) {
                                classUsed.add(targetClass);

                                if(classCallCount.containsKey(className)) {
                                    classCallCount.put(className, classCallCount.get(className) + 1);
                                } else {
                                    classCallCount.put(className, 1);
                                }
                            }
                        }
                    }

                    if (desc.contains(targetClass)) {
//                                System.out.println("XXXX " + desc.replaceFirst("\\(\\)L", "").replace(";", ""));

                        if(targetClass.equals(desc.replaceFirst("\\(\\)L", "").replace(";", ""))) {
                            classUsed.add(targetClass);

                            if(classCallCount.containsKey(className)) {
                                classCallCount.put(className, classCallCount.get(className) + 1);
                            } else {
                                classCallCount.put(className, 1);
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
                                classUsed.add(targetClass);

                                if(classCallCount.containsKey(className)) {
                                    classCallCount.put(className, classCallCount.get(className) + 1);
                                } else {
                                    classCallCount.put(className, 1);
                                }
                            } else if(Opcodes.INVOKEVIRTUAL == opcode) {
                                Type[] argumentTypes = Type.getArgumentTypes(desc);

                                for(Type arg : argumentTypes) {
//                                            System.out.println("PARAMETER TYPE: " + arg.getClassName());
//                                            System.out.println("PARAMETER TYPE: " + arg.getClassName().replaceAll("\\.", "/"));
                                    if(arg.getClassName().replaceAll("\\.", "/").equals(targetClass)) {
                                        classUsed.add(targetClass);

                                        if(classCallCount.containsKey(className)) {
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
//                                    System.out.println("visitFieldInsn, opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", description: " + desc);
                            if(Opcodes.NEW == opcode) {
//                                        System.out.println("NEW");
                            }

                            if(owner.equals(targetClass)) {
                                classUsed.add(targetClass);

                                if(classCallCount.containsKey(className)) {
                                    classCallCount.put(className, classCallCount.get(className) + 1);
                                } else {
                                    classCallCount.put(className, 1);
                                }
                            }
                        }

                        @Override
                        public void visitTypeInsn(int opcode, String type) {
//                                    System.out.println("visitTypeInsn type: " + type);
                            if(Opcodes.NEW == opcode && type.equals(targetClass)) {
//                                        System.out.println("NEwww: " + type);
                                classUsed.add(targetClass);

                                if(classCallCount.containsKey(className)) {
                                    classCallCount.put(className, classCallCount.get(className) + 1);
                                } else {
                                    classCallCount.put(className, 1);
                                }
                            }
                        }

                        @Override
                        public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
//                                    System.out.println("visitLocalVariable name: " + name + ", descriptor: " + descriptor + ", signature: " + signature);
                            if(descriptor.contains(targetClass)) {
//                                        System.out.println("ZZZZZ " + descriptor.replaceFirst("L", "").replace(";", ""));

                                if(targetClass.equals(descriptor.replaceFirst("L", "").replace(";", ""))) {
                                    classUsed.add(targetClass);

                                    if(classCallCount.containsKey(className)) {
                                        classCallCount.put(className, classCallCount.get(className) + 1);
                                    } else {
                                        classCallCount.put(className, 1);
                                    }
                                }
                            }
                        }
                    };
                }
            }, 0);
        }

//        for(String name : classCallCount.keySet()) {
//            String key = name;
//            int value = classCallCount.get(name);
//            System.out.println("classCallCount: key: " + key + ", value: " + value);
//        }

        Iterator<String> itr = classUsed.iterator();
//        while(itr.hasNext()) {
//            System.out.println("ITERATOR: " + itr.next());
//        }

        return classUsed.size();
//        return classCallCount.size();
    }
}