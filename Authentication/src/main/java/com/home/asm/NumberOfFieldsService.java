package com.home.asm;

import org.objectweb.asm.*;

import java.io.IOException;
import java.util.*;

import static org.objectweb.asm.Opcodes.ASM9;

public class NumberOfFieldsService {

    private int counterInit = 0;
    private int counterMethod = 0;
    private HashSet<String> putFields = new HashSet<String>();
    private HashSet<String> localVariables = new HashSet<String>();
    private Set<String> listOfAllFields = new HashSet<>();
    private Set<String> getAllInitFields = new HashSet<>();
    private Set<String> getAllFieldsWithinMethods = new HashSet<>();

    public NumberOfFieldsService() {

    }

    public int countPutFields(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);

        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//                System.out.println("name: " + name + ", descriptor: " + descriptor + ", signature: " + signature + ", exceptions: " + exceptions);

                if(name.equals("<init>")) {
                }

                return new MethodVisitor(ASM9) {
                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
//                        System.out.println("visitFieldInsn: opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);

                        if(opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC) {
                            putFields.add(owner + "." + name);
                        }
                    }

                    @Override
                    public void visitVarInsn(int opcode, int var) {
//                        System.out.println("visitVarInsn: opcode: " + opcode + ", var: " + var);
                    }
                };
//                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }, 0);

//        Iterator<String> iterator = putFields.iterator();
//        while(iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
//
//        System.out.println("putFields.size(): " + putFields.size());

        return putFields.size();
    }

    public Set<String> getAllFields(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);

        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//                System.out.println("name: " + name + ", descriptor: " + descriptor + ", signature: " + signature + ", exceptions: " + exceptions);
                if(name.equals("<init>")) {
                }

                return new MethodVisitor(ASM9) {
                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
//                        System.out.println("visitFieldInsn: opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);
//                        System.out.println("listOfAllFields : " + owner + "." + name);
                        if(opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC) {
                            listOfAllFields.add(name);
                        }
                    }

                    @Override
                    public void visitVarInsn(int opcode, int var) {
//                        System.out.println("visitVarInsn: opcode: " + opcode + ", var: " + var);
                    }
                };
            }
        }, 0);

//        Iterator<String> iterator = listOfAllFields.iterator();
//        while(iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }

//        System.out.println("listOfAllFields.size(): " + listOfAllFields.size());

        return listOfAllFields;
    }

    public int countLocalVariablesMethods(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);

        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//                System.out.println("name: " + name + ", descriptor: " + descriptor + ", signature: " + signature + ", exceptions: " + exceptions);

                if(name.equals("<init>")) {
                }

                return new MethodVisitor(ASM9) {
                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
//                        System.out.println("visitFieldInsn: opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);

//                        if(opcode == Opcodes.L) {
//                            localVariables.add(owner + "." + name);
//                        }
                    }

                    @Override
                    public void visitVarInsn(int opcode, int var) {
//                        System.out.println("visitVarInsn: opcode: " + opcode + ", var: " + var);
                    }

                    @Override
                    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
//                        System.out.println("LocalVariable: name: " + name + ", descriptor: " + descriptor + ", signature: " + signature + ", index: " + index);
                    }
                };
//                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }, 0);

//        Iterator<String> iterator = localVariables.iterator();
//        while(iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }

//        System.out.println("localVariables.size(): " + localVariables.size());

        return counterInit;
    }

    public Set<String> getAllInitFields(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);

        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//                System.out.println("VVV name: " + name + ", descriptor: " + descriptor);
                boolean init = name.equals("<init>");

//                if(name.equals("<init>")) {
//                    System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
//                    System.out.println("VVV name: " + name + ", descriptor: " + descriptor);
//                    getAllInitFields.add(name);
//                }

                return new MethodVisitor(ASM9) {
                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
//                        System.out.println("visitFieldInsn: opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);
//                        System.out.println("listOfAllFields : " + owner + "." + name);
//                        if(init && opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC) {
//                            getAllInitFields.add(name);
//                        }
                        if(init && owner.endsWith(className)) {
                            getAllInitFields.add(name);
                        }
                    }


                };
            }
            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
//                System.out.println("visitFieldInsn: name: " + name + ", descriptor: " + descriptor);

//                if(init && opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC) {
//                getAllInitFields.add(name);
//                }

                return super.visitField(access, name, descriptor, signature, value);
            }
        }, 0);

//        Iterator<String> iterator = getAllInitFields.iterator();
//        while(iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }

//        System.out.println("getAllInitFields.size(): " + getAllInitFields.size());

        return getAllInitFields;
    }

    public Set<String> getAllFieldsWithinMethods(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);

        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                boolean init = name.equals("<init>");

                return new MethodVisitor(ASM9) {
                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
//                        System.out.println("visitFieldInsn(): opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);
                        if(!init && owner.endsWith(className)) {
                            getAllFieldsWithinMethods.add(name);
                        }
                    }
                };
            }
            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                return super.visitField(access, name, descriptor, signature, value);
            }
        }, 0);
        return getAllFieldsWithinMethods;
    }

    public int getAllPrivateFields(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        final int[] ans = {0};

        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                if(access == 2) {
                    ans[0]++;
                }
                return super.visitField(access, name, descriptor, signature, value);
            }
        }, 0);
        return ans[0];
    }
}
