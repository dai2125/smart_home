package com.home.asm;

import org.objectweb.asm.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.objectweb.asm.Opcodes.ASM9;

public class InformationExpertService {

    private Set<String> fields = new HashSet<>();
    private Set<String> methods = new HashSet<>();
    private int variables = 0;
    private int objects = 0;

    public InformationExpertService() {

    }

    public void analyze(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);

        classReader.accept(new ClassVisitor(ASM9) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                return new MethodVisitor(ASM9) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                        switch (opcode) {
                            case Opcodes.INVOKESPECIAL:
                                System.out.println("INVOKESPECIAL (super constructor/method call) - owner: " + owner + ", method: " + name + ", desc: " + desc);
                                break;
                            case Opcodes.INVOKEVIRTUAL:
                                System.out.println("INVOKEVIRTUAL (instance method call) - owner: " + owner + ", method: " + name + ", desc: " + desc);
                                break;
                            case Opcodes.INVOKESTATIC:
                                System.out.println("INVOKESTATIC (static method call) - owner: " + owner + ", method: " + name + ", desc: " + desc);
                                break;
                            case Opcodes.INVOKEINTERFACE:
                                System.out.println("INVOKEINTERFACE (interface method call) - owner: " + owner + ", method: " + name + ", desc: " + desc);
                                break;
                            default:
                                System.out.println("OTHER METHOD CALL - opcode: " + opcode + ", owner: " + owner + ", method: " + name + ", desc: " + desc);
                        }
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                        switch (opcode) {
                            case Opcodes.GETFIELD:
                                System.out.println("GETFIELD (Instance Field Read) - owner: " + owner + ", field: " + name + ", type: " + desc);
                                break;
                            case Opcodes.PUTFIELD:
                                System.out.println("PUTFIELD (Instance Field Write) - owner: " + owner + ", field: " + name + ", type: " + desc);
                                break;
                            case Opcodes.GETSTATIC:
                                System.out.println("GETSTATIC (Static Field Read) - owner: " + owner + ", field: " + name + ", type: " + desc);
                                break;
                            case Opcodes.PUTSTATIC:
                                System.out.println("PUTSTATIC (Static Field Write) - owner: " + owner + ", field: " + name + ", type: " + desc);
                                break;
                            default:
                                System.out.println("OTHER FIELD INSTRUCTION - opcode: " + opcode + ", owner: " + owner + ", field: " + name + ", desc: " + desc);
                        }
                    }

                    @Override
                    public void visitTypeInsn(int opcode, String type) {
//                        System.out.println("owner: " + type);
//                        if (Opcodes.NEW == opcode && type.equals(className)) {
//                            System.out.println("NEwww: " + type);
//                        }

                    }
                };
            }


            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                System.out.println("CLASSNAME: " + name + ", signature: " + signature + ", superName: " + superName + ", interfaces: " + interfaces.length);
                super.visit(version, access, name, signature, superName, interfaces);
            }

            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                System.out.println("access: " + access + ", name: " + name + ", descriptor: " + descriptor + ", signature: " + signature);
//                System.out.println(access & Opcodes.ACC_PUBLIC);
//                System.out.println(access & Opcodes.ACC_FINAL);
//                System.out.println(access & Opcodes.ACC_PRIVATE);

                return super.visitField(access, name, descriptor, signature, value);
            }

            @Override
            public void visitEnd() {
                super.visitEnd();
            }
        }, 0);
    }















}
