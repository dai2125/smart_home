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

    public void visitField(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);

        final InspectedField[] inspectedField = {new InspectedField()};

        classReader.accept(new ClassVisitor(ASM9) {


            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
//                InspectedField cpf = new InspectedField(name, access, descriptor);
//                cpf[0] = new InspectedField();

//                cpf[0].setAccess(access);
//                cpf[0].setName(name);
//                cpf[0].setType(descriptor);
//                cpf[0].setOwner(className);
//                cpf[0].setOperation("<init>");

//                if(signature != null) {
//                    System.out.println("SIGNATURE: " + signature);
//                }
                inspectedField[0] = new InspectedField(access, name, className, descriptor, signature, signature, "<init>");

//                System.out.println("visitField(): className: " + className + " " + access + ", name: " + name + ", descriptor: " + descriptor);

                return new FieldVisitor(ASM9) {

                    // TODO hier muss eine contains überprüfung implementiert werden
                    @Override
                    public void visitEnd() {
//                        System.out.println("VISIT END(): " + cpf.getName());
                        InspectedClass inspectedClass = CreatorPrinciple1And3Service.get(className.replaceAll(".*/", ""));
//                        System.out.println("cpf: " + cpf[0].getName() + " " + cpf[0].getType());
//                        System.out.println("____cpf: " + cpf[0].getName() + " " + cpf[0].getOwner());
                        inspectedClass.addToFieldList(inspectedField[0]);
//                        System.out.println("creatorPrinciple.addToFieldList(cpf)");
                        CreatorPrinciple1And3Service.put(inspectedClass);
                        super.visitEnd();
                    }
                };

//                return this.cv != null ? this.cv.visitField(access, name, descriptor, signature, value) : null;

            };

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {


//                System.out.println("____visitMethod() name: " + name);
//                System.out.println("access: " + access + ", name: " + name + ", descriptor: " + descriptor + ", signature: " + signature + ", exceptions.length: "); //+ exceptions.length);
                String ownerMethod = name;
                String ownerMethodReturnType = descriptor;
                return new MethodVisitor(ASM9) {
                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                        inspectedField[0] = new InspectedField(opcode, name, owner, descriptor, ownerMethod, ownerMethodReturnType);
//                        cpf[0].setAccess(access);
//                        cpf[0].setName(name);
//                        cpf[0].setType(descriptor);
//                        cpf[0].setOwner(owner);
//                        cpf[0].setOpcode(opcode);
//                        cpf[0].setOperation(ownerMethod);
                        InspectedClass inspectedClass = CreatorPrinciple1And3Service.get(className.replaceAll(".*/", ""));
                        inspectedClass.addToFieldInsnList(inspectedField[0]);

//                        System.out.println("_____visitFieldInsn() methodName: " + methodName + ", methodDescriptor: " + methodDescriptor + ", opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);

                        super.visitFieldInsn(opcode, owner, name, descriptor);
                    }

                    @Override
                    public void visitEnd() {
//                        System.out.println(name + " FFFFENDDDIII");
//                        CreatorPrinciple creatorPrinciple = CreatorPrinciple1And3Service.get(className);
//                        creatorPrinciple.addToFieldList(cpf[0]);
//                        creatorPrinciple.addToFieldInsnList(cpf[0]);

                    }
                };
//                CreatorPrinciple1And3Service.put(cpf);
            }
            @Override
            public void visitEnd() {
//                System.out.println("ENDDD");
//                CreatorPrinciple creatorPrinciple = CreatorPrinciple1And3Service.get(className);

            }
        }, 0);
//        System.out.println("cpf:" + cpf[0]);
//        return cpf;
    }

    // TODO kann wahrscheinlich gelöscht werden
    public void visitMethod(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);

        final InspectedField[] cpf = {new InspectedField()};
        final Model[] model = {new Model()};

        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                String ownerMethod = name;
                String ownerMethodReturnType = descriptor;
//                System.out.println(className + ", name: " + name +  ", descriptor: " + descriptor);
                model[0] = new Model();

                return new MethodVisitor(ASM9) {

                    String lastNewType = null;

                    @Override
                    public void visitTypeInsn(int opcode, String type) {
                        if(opcode == Opcodes.NEW) {
                            lastNewType = type;
//                            model[0].setType(lastNewType);

//                            System.out.println("000: methodName: " + methodName + ", lastNewType: " + lastNewType);
                        }
                    }

                    @Override
                    public void visitVarInsn(int opcode, int varIndex) {
//                        System.out.println("111: methodName: " + methodName + ", methodParamater: " + methodParameter.replaceFirst(".*/", "") + ", varIndex: " + varIndex);
                        super.visitVarInsn(opcode, varIndex);
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
//                        System.out.println("222: methodName: " + methodName  + ", methodParamater: " + methodParameter.replaceFirst(".*/", "") + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);

                        super.visitFieldInsn(opcode, owner, name, descriptor);
                    }

                    @Override
                    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
//                        System.out.println("555: methodName: " + methodName  + ", methodParamater: " + methodParameter.replaceFirst(".*/", "") +  ", name: " + name + ", descriptor: " + descriptor);

                        if(!descriptor.equals("I") && !descriptor.equals("Z") && !descriptor.equals("D")
                                && !descriptor.equals("B") && !descriptor.equals("C") && !descriptor.equals("F")
                                && !descriptor.equals("S") && !descriptor.equals("J")) {
//                                model[0].setName(name);
                        }

                    }

                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
//                        System.out.println("333: methodName: " + methodName  + ", methodParamater: " + methodParameter.replaceFirst(".*/", "") + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor + ", isInterface:" + isInterface);

                        if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
//                            System.out.println("444: Konstruktor aufgerufen für: " + owner.replace("/", ".") + " in Methode '" + methodName + "'");

//                            System.out.println("ownerMethod: " + ownerMethod + ", ownerMethodReturnType: "+  ownerMethodReturnType + ", owner: " + owner /*.replace("/", ".") */ + ", name: " + name + ", descriptor: " + descriptor + ", lastNewType: " + lastNewType);

                            if(!ownerMethod.equals("<init>")) {
                                model[0] = new Model(owner.replaceFirst(".*/", "") + "." + name, descriptor, lastNewType, className, ownerMethod, ownerMethodReturnType, true, false);
                                System.out.println("xxxxxxxxxxxxxxxxxx:" + model[0].toString());
                                InspectedClass inspectedClass = CreatorPrinciple1And3Service.get(className);
                                //inspectedClass.addToModelList(model[0]);
                            }
                        }
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }

                    @Override
                    public void visitEnd() {
                    }
                };
            }
        }, 0);
    }
}
