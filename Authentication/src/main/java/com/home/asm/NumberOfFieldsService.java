package com.home.asm;

import org.objectweb.asm.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import static org.objectweb.asm.Opcodes.ASM9;

public class NumberOfFieldsService {

    private int counterInit = 0;
    private int counterMethod = 0;
    private HashSet<String> putFields = new HashSet<String>();
    private HashSet<String> localVariables = new HashSet<String>();

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
}
