package com.home.asm;

import org.objectweb.asm.*;

import java.io.IOException;
import java.util.*;

import static org.objectweb.asm.Opcodes.ASM9;

public class NumberOfMethodsService {

    private boolean isInterface;
    private Set<String> classUsed = new HashSet<>();
    private Set<String> listOfAllMethods = new HashSet<>();
    // private List<MethodModel> listOfAllSubMethods = new ArrayList<>();
    private List<String> listOfAllMethodsList = new ArrayList<>();
    private List<InspectedMethod> listOfAllInspectedMethods = new ArrayList<>();
    private final Map<String, String> fieldAssignments = new HashMap<>();

    public NumberOfMethodsService() {
    }

    public int analyzePackage2(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;

        classReader.accept(new ClassVisitor(ASM9) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

//                System.out.println("visitMethod, name: " + name + ", desc: " + desc + ", signature: " + signature + ", exceptions: " + exceptions + ", access: " + access);
                if(!name.equals("<init>") && !name.equals("<clinit>")) {
                    classUsed.add(name);
                }

//                if(name.equals("<clinit>")) {
//                    System.out.println("CLINIT | name: " + name + ", desc: " + desc + ", signature: " + signature + ", exceptions: " + exceptions);
//                }

                return null;
            }
        }, 0);

//        Iterator<String> iterator = classUsed.iterator();
//
//        while(iterator.hasNext()) {
//            System.out.println("ITERATOR: " + iterator.next());
//        }

        return classUsed.size();
    }

    public Set<String> getAllMethods(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;
        HashSet<String> interfaceList = new HashSet<>();

        classReader.accept(new ClassVisitor(ASM9) {

            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

                if(interfaces != null) {
                    for(int i = 0; i < interfaces.length; i++) {
                        interfaceList.add(interfaces[i]);
                    }
                }
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                if(!name.equals("<init>") && !name.equals("<clinit>")) {
                    listOfAllMethods.add(name);
                } else if(name.equals("<init>")) {
                    return new MethodVisitor(ASM9) {
                        @Override
                        public void visitParameter(String name, int access) {
                            System.out.println("VISITPARAMETER: " + name);
                            super.visitParameter(name, access);
                        }

                        @Override
                        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
//                            System.out.println("VISITFIELDINSN: " + name);

                            super.visitFieldInsn(opcode, owner, name, descriptor);
                        }
                    };
                }
                return null;
            }
        }, 0);
//        System.out.println("||||| " + className + " " + listOfAllMethods.size());
        return listOfAllMethods;
    }

    public List<String> getAllMethodsList(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;

        classReader.accept(new ClassVisitor(ASM9) {

            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                if(!name.equals("<init>") && !name.equals("<clinit>") && !listOfAllMethodsList.contains(name)) {
                    listOfAllMethodsList.add(name);
                } else if(name.equals("<init>")) {
                    return new MethodVisitor(ASM9) {
                    };
                }
                return null;
            }
        }, 0);
        return listOfAllMethodsList;
    }

    public List<String> getAllInterfaceMethods(String className) throws IOException {
        System.out.println("getAllInterfaceMethods(): " + className);
        ClassReader classReader = new ClassReader(className);
        isInterface = false;
        HashSet<String> interfaceList = new HashSet<>();
        List<String> listOfAllMethodsFromInterfaces = new ArrayList<>();

        classReader.accept(new ClassVisitor(ASM9) {

            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                if(interfaces != null) {
                    for(int i = 0; i < interfaces.length; i++) {
                        interfaceList.add(interfaces[i]);
                    }
                }
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

                try {
                    if(methodIsFromInterface(interfaceList, name)) {
                        listOfAllMethodsFromInterfaces.add(name);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        }, 0);

//        System.out.println("START " + className);
//        for(int i = 0; i < listOfAllMethodsFromInterfaces.size(); i++) {
//            System.out.println(listOfAllMethodsFromInterfaces.get(i));
//        }
//        System.out.println("END");
        return listOfAllMethodsFromInterfaces;
    }

    public boolean methodIsFromInterface(Set<String> listOfInterfaces, String methodName) throws IOException {
        Iterator<String> iterator = listOfInterfaces.iterator();
        final boolean[] ans = {false};

        while(iterator.hasNext()) {
            ClassReader classReader = new ClassReader(iterator.next());
            classReader.accept(new ClassVisitor(ASM9) {

                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    if(name.equals(methodName)) {
                        ans[0] = true;
                    }
                    return super.visitMethod(access, name, descriptor, signature, exceptions);
                }
            }, 0);
        }
        return ans[0];
    }

    public int getAllPrivateMethods(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;
        HashSet<String> interfaceList = new HashSet<>();
        List<String> listOfAllMethodsFromInterfaces = new ArrayList<>();
        final int[] ans = {0};

        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                if(access == 2) {
                    ans[0]++;
                }
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        }, 0);
        return ans[0];
    }

    public List<String> checkInstructions(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;
        int[] counter = new int[]{0};

//        System.out.print("CLASSNAME: " + className);
        classReader.accept(new ClassVisitor(ASM9) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//                if(!name.equals("<init>") && !name.equals("<clinit>") && !listOfAllMethodsList.contains(name)) {
//                    listOfAllMethodsList.add(name);
//                } else if(name.equals("<init>")) {
//                    return new MethodVisitor(ASM9) {
//                    };
//                }
                System.out.print("CLASSNAME " + className + " method: " + name + "\n");

                return new MethodVisitor(ASM9) {
                    @Override
                    public void visitParameter(String name, int access) {
                        System.out.println("name: " + name);
                        super.visitParameter(name, access);
                    }

                    @Override
                    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                        System.out.println("descriptor: " + name);

                        return super.visitAnnotation(descriptor, visible);
                    }

                    @Override
                    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
                        System.out.println("descriptor: " + descriptor);

                        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
                    }

                    @Override
                    public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
                        System.out.println("visible: " + visible);
                        super.visitAnnotableParameterCount(parameterCount, visible);
                    }

                    @Override
                    public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
                        System.out.println("descriptor: " + name + "; visible: "+ visible);

                        return super.visitParameterAnnotation(parameter, descriptor, visible);
                    }

                    @Override
                    public void visitAttribute(Attribute attribute) {
                        System.out.println("attribute.type: " + attribute.type);

                        super.visitAttribute(attribute);
                    }

                    @Override
                    public void visitCode() {
                        super.visitCode();
                    }

                    @Override
                    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
                        super.visitFrame(type, numLocal, local, numStack, stack);
                    }

                    @Override
                    public void visitInsn(int opcode) {
                        System.out.println("visitInsn - opcode: " + opcode);
                        super.visitInsn(opcode);
                    }

                    @Override
                    public void visitIntInsn(int opcode, int operand) {
                        System.out.println("visitIntInsn - opcode: " + opcode + ", operand: " + operand);
                        super.visitIntInsn(opcode, operand);
                    }

                    @Override
                    public void visitVarInsn(int opcode, int varIndex) {
                        System.out.println("visitVarInsn - opcode: " + opcode + ", varIndex: " + varIndex);

                        super.visitVarInsn(opcode, varIndex);
                    }

                    @Override
                    public void visitTypeInsn(int opcode, String type) {
                        System.out.println("visitTypeInsn - opcode: " + opcode + ", type: " + type);

                        super.visitTypeInsn(opcode, type);
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
//                        System.out.println("visitFieldInsn - opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);

                        super.visitFieldInsn(opcode, owner, name, descriptor);
                    }

                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor) {
                        System.out.println("visitMethodInsn - opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);

                        super.visitMethodInsn(opcode, owner, name, descriptor);
                    }

                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        System.out.println("visitMethodInsn - opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor + ", isInterface: " + isInterface);

                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }

                    @Override
                    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
                        System.out.println("visitInvokeDynamicInsn - name: " + name + ", descriptor: " + descriptor + bootstrapMethodHandle.getName() + " " + bootstrapMethodHandle.getOwner());

                        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
                    }

                    @Override
                    public void visitJumpInsn(int opcode, Label label) {
                        System.out.println("visitJumpInsn - opcode: " + opcode);
                        super.visitJumpInsn(opcode, label);
                    }

                    @Override
                    public void visitLabel(Label label) {
                        super.visitLabel(label);
                    }

                    @Override
                    public void visitLdcInsn(Object value) {
                        super.visitLdcInsn(value);
                    }

                    @Override
                    public void visitIincInsn(int varIndex, int increment) {
                        System.out.println("visitIincInsn - varIndex: " + varIndex + ", increment: " + increment);
                        super.visitIincInsn(varIndex, increment);
                    }

                    @Override
                    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
                        System.out.println("visitTableSwitchInsn - min: " + min + ", max: " + max + " " + dflt.toString());
                        super.visitTableSwitchInsn(min, max, dflt, labels);
                    }

                    @Override
                    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
                        System.out.println("visitLookupSwitchInsn - dflt: " + dflt.toString());
                        super.visitLookupSwitchInsn(dflt, keys, labels);
                    }

                    @Override
                    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
                        System.out.println("visitMultiANewArrayInsn - descriptor: " + descriptor + ", numDimensions: " + numDimensions);
                        super.visitMultiANewArrayInsn(descriptor, numDimensions);
                    }

                    @Override
                    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
                        System.out.println("visitInsnAnnotation - typeRef: " + typeRef + ", descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitInsnAnnotation(typeRef, typePath, descriptor, visible);
                    }

                    @Override
                    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
                        System.out.println("visitTryCatchBlock - start: " + start.toString() + " " + end.toString() + ", type: " + type);
                        super.visitTryCatchBlock(start, end, handler, type);
                    }

                    @Override
                    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
                        System.out.println("visitTryCatchAnnotation - typeRef: " + typeRef + ", descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible);
                    }

                    @Override
                    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
                        System.out.println("visitLocalVariable - name: " + name + ", descriptor: " + descriptor + ", signature: " + signature + ", start: " + start.toString() + ", end: " + end.toString() + ", index: " + index);
                        super.visitLocalVariable(name, descriptor, signature, start, end, index);
                    }

                    @Override
                    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible) {
                        System.out.println("visitLocalVariableAnnotation - typeRef: " + typeRef + ", start: " + start.toString() + ", end: " + end.toString() + ", index: " + index + ", descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, descriptor, visible);
                    }

                    @Override
                    public void visitLineNumber(int line, Label start) {
                        System.out.println("visitLineNumber - line: " + line + ", start: " + start.toString() + " " + start.info);
                        super.visitLineNumber(line, start);
                    }

                    @Override
                    public void visitMaxs(int maxStack, int maxLocals) {
                        System.out.println("visitMaxs - maxStack: " + maxStack + ", maxLocals: " + maxLocals);
                        super.visitMaxs(maxStack, maxLocals);
                    }

                    @Override
                    public void visitEnd() {
                        super.visitEnd();
                    }
                };
            }
        }, 0);
            return listOfAllMethodsList;

    }

    public List<InspectedMethod> getInformationOfMethodsFrom(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;
        int[] counter = new int[]{0};

//        System.out.print("CLASSNAME: " + className);
        classReader.accept(new ClassVisitor(ASM9) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

                InspectedMethod inspectedMethod = new InspectedMethod(access, name, desc);

//                if(!name.equals("<init>") && !name.equals("<clinit>") && !listOfAllMethodsList.contains(name)) {
//                    listOfAllMethodsList.add(name);
//                } else if(name.equals("<init>")) {
//                    return new MethodVisitor(ASM9) {
//                    };
//                }
//                System.out.print("CLASSNAME " + className + " method: " + name + ", desc: "+ desc + "\n");

                String ownerMethod = name;
                String ownerMethodReturnType = desc;

                return new MethodVisitor(ASM9) {
                    @Override
                    public void visitParameter(String name, int access) {
//                        System.out.println("visitParameter() name: " + name);
                        super.visitParameter(name, access);

                    }

                    @Override
                    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
//                        System.out.println("descriptor: " + name);

                        return super.visitAnnotation(descriptor, visible);
                    }

                    @Override
                    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
//                        System.out.println("descriptor: " + descriptor);

                        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
                    }

                    @Override
                    public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
//                        System.out.println("visible: " + visible);
                        super.visitAnnotableParameterCount(parameterCount, visible);
                    }

                    @Override
                    public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
//                        System.out.println("descriptor: " + name + "; visible: "+ visible);

                        return super.visitParameterAnnotation(parameter, descriptor, visible);
                    }

                    @Override
                    public void visitAttribute(Attribute attribute) {
//                        System.out.println("attribute.type: " + attribute.type);

                        super.visitAttribute(attribute);
                    }

                    @Override
                    public void visitCode() {
                        super.visitCode();
                    }

                    @Override
                    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
                        super.visitFrame(type, numLocal, local, numStack, stack);
                    }

                    @Override
                    public void visitInsn(int opcode) {
//                        System.out.println("visitInsn - opcode: " + opcode);
                        super.visitInsn(opcode);
                    }

                    @Override
                    public void visitIntInsn(int opcode, int operand) {
//                        System.out.println("visitIntInsn - opcode: " + opcode + ", operand: " + operand);
                        super.visitIntInsn(opcode, operand);
                    }

                    @Override
                    public void visitVarInsn(int opcode, int varIndex) {
//                        System.out.println("visitVarInsn - opcode: " + opcode + ", varIndex: " + varIndex);

                        super.visitVarInsn(opcode, varIndex);
                    }

                    @Override
                    public void visitTypeInsn(int opcode, String type) {
//                        System.out.println("visitTypeInsn - opcode: " + opcode + ", type: " + type);

                        super.visitTypeInsn(opcode, type);
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
   //                     System.out.println("visitFieldInsn - opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor + ", ownerMethod: " + ownerMethod + ", ownerMethodReturnType: " + ownerMethodReturnType);
//                        cpm.addFieldToList(opcode, name, descriptor);
                        inspectedMethod.addFieldToList(opcode, name, owner, descriptor, ownerMethod, ownerMethodReturnType);
                        super.visitFieldInsn(opcode, owner, name, descriptor);
                    }

                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor) {
//                        System.out.println("visitMethodInsn - opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);
                        super.visitMethodInsn(opcode, owner, name, descriptor);
                    }

                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
 //                       System.out.println("visitMethodInsn - opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor + ", isInterface: " + isInterface);
//                        System.out.println("zzzzzzzzzzzzzzzzzzzz");
                        inspectedMethod.addMethodToList(opcode, owner, name, descriptor);

                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }

                    @Override
                    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
//                        System.out.println("visitInvokeDynamicInsn - name: " + name + ", descriptor: " + descriptor + bootstrapMethodHandle.getName() + " " + bootstrapMethodHandle.getOwner());

                        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
                    }

                    @Override
                    public void visitJumpInsn(int opcode, Label label) {
//                        System.out.println("visitJumpInsn - opcode: " + opcode);
                        super.visitJumpInsn(opcode, label);
                    }

                    @Override
                    public void visitLabel(Label label) {
                        super.visitLabel(label);
                    }

                    @Override
                    public void visitLdcInsn(Object value) {
                        super.visitLdcInsn(value);
                    }

                    @Override
                    public void visitIincInsn(int varIndex, int increment) {
//                        System.out.println("visitIincInsn - varIndex: " + varIndex + ", increment: " + increment);
                        super.visitIincInsn(varIndex, increment);
                    }

                    @Override
                    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
//                        System.out.println("visitTableSwitchInsn - min: " + min + ", max: " + max + " " + dflt.toString());
                        super.visitTableSwitchInsn(min, max, dflt, labels);
                    }

                    @Override
                    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
//                        System.out.println("visitLookupSwitchInsn - dflt: " + dflt.toString());
                        super.visitLookupSwitchInsn(dflt, keys, labels);
                    }

                    @Override
                    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
//                        System.out.println("visitMultiANewArrayInsn - descriptor: " + descriptor + ", numDimensions: " + numDimensions);
                        super.visitMultiANewArrayInsn(descriptor, numDimensions);
                    }

                    @Override
                    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
//                        System.out.println("visitInsnAnnotation - typeRef: " + typeRef + ", descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitInsnAnnotation(typeRef, typePath, descriptor, visible);
                    }

                    @Override
                    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
//                        System.out.println("visitTryCatchBlock - start: " + start.toString() + " " + end.toString() + ", type: " + type);
                        super.visitTryCatchBlock(start, end, handler, type);
                    }

                    @Override
                    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
//                        System.out.println("visitTryCatchAnnotation - typeRef: " + typeRef + ", descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible);
                    }

                    @Override
                    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
//                        System.out.println("visitLocalVariable - name: " + name + ", descriptor: " + descriptor + ", signature: " + signature + ", start: " + start.toString() + ", end: " + end.toString() + ", index: " + index);
                        super.visitLocalVariable(name, descriptor, signature, start, end, index);
                    }

                    @Override
                    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible) {
//                        System.out.println("visitLocalVariableAnnotation - typeRef: " + typeRef + ", start: " + start.toString() + ", end: " + end.toString() + ", index: " + index + ", descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, descriptor, visible);
                    }

                    @Override
                    public void visitLineNumber(int line, Label start) {
//                        System.out.println("visitLineNumber - line: " + line + ", start: " + start.toString() + " " + start.info);
                        super.visitLineNumber(line, start);
                    }

                    @Override
                    public void visitEnd() {
                        if(!CreatorPrinciple1And3Service.contains(className.replaceAll(".*/", ""))) {
                            InspectedClass inspectedClass = new InspectedClass(className.replaceAll(".*/", ""));
                            inspectedClass.addInspectedMethodToList(inspectedMethod);

//                            System.out.println("cpm--: " + cpm.toString());
                            CreatorPrinciple1And3Service.put(inspectedClass);
                        } else {
                            InspectedClass inspectedClass = CreatorPrinciple1And3Service.get(className.replaceAll(".*/", ""));
                            inspectedClass.addInspectedMethodToList(inspectedMethod);

//                            System.out.println("cpm++: " + cpm.toString());
                            CreatorPrinciple1And3Service.put(inspectedClass);
                        }

                        super.visitEnd();
                    }
                };
            }
        }, 0);
        return listOfAllInspectedMethods;
    }

    public void initializingData(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;
        final String[] methodName = new String[1];

        System.out.println("CLASSNAME " + className);
        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                System.out.print("CLASSNAME " + className + " METHOD: " + name + "\n");
                methodName[0] = name;

                if(exceptions == null) {
//                    System.out.println("visitMethod access: " + access + ", name: " + name + ", desc: " +  desc + ", signature: " + signature);
                } else {
//                    System.out.println("visitMethod access: " + access + ", name: " + name + ", desc: " +  desc + ", signature: " + signature + ", exceptions: " + exceptions.length);
                }

                return new MethodVisitor(ASM9) {
                    private boolean insideConstructor = "<init>".equals(name);
                    @Override
                    public void visitParameter(String name, int access) {
//                        System.out.println("visitParameter name: " + name + ", access: " + access);
                        super.visitParameter(name, access);
                    }

                    @Override
                    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
//                        System.out.println("visitAnnotation descriptor: " + descriptor + ", visible: " + visible);

                        return super.visitAnnotation(descriptor, visible);
                    }

                    @Override
                    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
//                        System.out.println("visitTypeAnnotation typeRef: " + typeRef + ", typePath: " + typePath + ", descriptor: " + descriptor + ", visible: " + visible);

                        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
                    }

                    @Override
                    public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
//                        System.out.println("visitAnnotable ParameterCount parameterCount: " + parameterCount + ", visible: " + visible);
                        super.visitAnnotableParameterCount(parameterCount, visible);
                    }

                    @Override
                    public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
//                        System.out.println("visitParameterAnnotation parameter: " + parameter + ", descsriptor: " +  descriptor +  ", name: " + name + ", visible: " + visible);

                        return super.visitParameterAnnotation(parameter, descriptor, visible);
                    }

                    @Override
                    public void visitAttribute(Attribute attribute) {
//                        System.out.println("attribute.type: " + attribute.type);

                        super.visitAttribute(attribute);
                    }

                    @Override
                    public void visitCode() {
                        super.visitCode();
                    }

                    @Override
                    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
//                        System.out.println("visitFrame type: " + type + ", numLocal: " + numLocal + ", local: " + local.length +  ", numStack: " + numStack + ", stack: " + stack.length);
                        super.visitFrame(type, numLocal, local, numStack, stack);
                    }

                    @Override
                    public void visitInsn(int opcode) {
//                        System.out.println("visitInsn opcode: " + opcode);
                        super.visitInsn(opcode);
                    }

                    @Override
                    public void visitIntInsn(int opcode, int operand) {
//                        System.out.println("visitIntInsn opcode: " + opcode + ", operand: " + operand);
                        super.visitIntInsn(opcode, operand);
                    }

                    @Override
                    public void visitVarInsn(int opcode, int varIndex) {
//                        System.out.println("visitVarInsn opcode: " + opcode + ", varIndex: " + varIndex);

                        super.visitVarInsn(opcode, varIndex);
                    }

                    @Override
                    public void visitTypeInsn(int opcode, String type) {
//                        System.out.println("visitTypeInsn opcode: " + opcode + ", type: " + type);

                        super.visitTypeInsn(opcode, type);
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
//                        System.out.println("visitFieldInsn opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);
//                        if(opcode == Opcodes.PUTFIELD && owner.equals(className)) {
//                            fieldAssignments.put(name, "Von extern oder Literal");
//                        } else if(opcode == Opcodes.GETFIELD && owner.equals(className)) {
//                            System.out.println("--- Feld " + name + " wird für " + methodName[0] + " genutzt.");
//                            System.out.println("visitFieldInsn owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);
//                        }
                        super.visitFieldInsn(opcode, owner, name, descriptor);
                    }

                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor) {
//                        System.out.println("visitMethodInsn - opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);

                        super.visitMethodInsn(opcode, owner, name, descriptor);
                    }

                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
//                        System.out.println("visitMethodInsn - opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor + ", isInterface: " + isInterface);
//                        if(opcode == Opcodes.INVOKESPECIAL && name.equals("<inti>") && !owner.equals(className)) {
//                            System.out.println("--- Class wird in " + className + " erstellt mit Parameter aus:");
//                            fieldAssignments.forEach((field, source) -> {
//                                System.out.println("- " + field + " : " + source);
//                            });
//                        }
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }

                    @Override
                    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
//                        System.out.println("visitInvokeDynamicInsn - name: " + name + ", descriptor: " + descriptor + bootstrapMethodHandle.getName() + " " + bootstrapMethodHandle.getOwner());

                        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
                    }

                    @Override
                    public void visitJumpInsn(int opcode, Label label) {
//                        System.out.println("visitJumpInsn - opcode: " + opcode);
                        super.visitJumpInsn(opcode, label);
                    }

                    @Override
                    public void visitLabel(Label label) {
                        if(label != null) {
//                            System.out.println("visitLabel - label.info: " + label.info + ", label.toString: "  + label.toString());
                        }
                        super.visitLabel(label);
                    }

                    @Override
                    public void visitLdcInsn(Object value) {
                        if(value != null) {
//                            System.out.println("visitLdcInsn value: " + value.toString());
                        }
                        super.visitLdcInsn(value);
                    }

                    @Override
                    public void visitIincInsn(int varIndex, int increment) {
//                        System.out.println("visitIincInsn varIndex: " + varIndex + ", increment: " + increment);
                        super.visitIincInsn(varIndex, increment);
                    }

                    @Override
                    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
//                        System.out.println("visitTableSwitchInsn min: " + min + ", max: " + max + " " + dflt.toString());
                        super.visitTableSwitchInsn(min, max, dflt, labels);
                    }

                    @Override
                    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
//                        System.out.println("visitLookupSwitchInsn dflt: " + dflt.toString());
                        super.visitLookupSwitchInsn(dflt, keys, labels);
                    }

                    @Override
                    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
//                        System.out.println("visitMultiANewArrayInsn descriptor: " + descriptor + ", numDimensions: " + numDimensions);
                        super.visitMultiANewArrayInsn(descriptor, numDimensions);
                    }

                    @Override
                    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
//                        System.out.println("visitInsnAnnotation typeRef: " + typeRef + ", descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitInsnAnnotation(typeRef, typePath, descriptor, visible);
                    }

                    @Override
                    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
//                        System.out.println("visitTryCatchBlock start: " + start.toString() + " " + end.toString() + ", type: " + type);
                        super.visitTryCatchBlock(start, end, handler, type);
                    }

                    @Override
                    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
//                        System.out.println("visitTryCatchAnnotation typeRef: " + typeRef + ", descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible);
                    }

                    @Override
                    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
//                        System.out.println("visitLocalVariable name: " + name + ", descriptor: " + descriptor + ", signature: " + signature + ", start: " + start.toString() + ", end: " + end.toString() + ", index: " + index);
                        super.visitLocalVariable(name, descriptor, signature, start, end, index);
                    }

                    @Override
                    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible) {
//                        System.out.println("visitLocalVariableAnnotation typeRef: " + typeRef + ", start: " + start.toString() + ", end: " + end.toString() + ", index: " + index + ", descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, descriptor, visible);
                    }

                    @Override
                    public void visitLineNumber(int line, Label start) {
//                        System.out.println("visitLineNumber line: " + line + ", start: " + start.toString() + " " + start.info);
                        super.visitLineNumber(line, start);
                    }

                    @Override
                    public void visitMaxs(int maxStack, int maxLocals) {
//                        System.out.println("visitMaxs maxStack: " + maxStack + ", maxLocals: " + maxLocals);
                        super.visitMaxs(maxStack, maxLocals);
                    }

                    @Override
                    public void visitEnd() {
                        super.visitEnd();
                    }
                };
            }
        }, 0);
    }

    public void initializingData2(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;
        final String[] methodName = new String[1];

        System.out.println("CLASSNAME " + className);
        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                System.out.print("CLASSNAME " + className + " METHOD: " + name + "\n");
                methodName[0] = name;

                if(name.equals("<init>")) {
                    return new ConstructorVisitor(api, super.visitMethod(access, name, desc, signature, exceptions));
                }
                return super.visitMethod(access, name, desc, signature, exceptions);

            }
        }, 0);
    }

    static class ConstructorVisitor extends MethodVisitor {
        public ConstructorVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        @Override
        public void visitVarInsn(int opcode, int varIndex) {
            if(opcode == Opcodes.ALOAD || opcode == Opcodes.ILOAD) {
                System.out.println("Parameter wird geladen an Index: " + varIndex);
            }
            super.visitVarInsn(opcode, varIndex);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if(owner.equals("com/home/creator/InitializingData/thirdExample/ClassA") && name.equals("<init>")) {
                System.out.println("Class wird instanziiert mit Descriptor: " + descriptor);
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }

    public void checkAll(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;
        int[] counter = new int[]{0};

//        System.out.print("CLASSNAME: " + className);
        System.out.println("CLASSNAME " + className);
        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//                if(!name.equals("<init>") && !name.equals("<clinit>") && !listOfAllMethodsList.contains(name)) {
//                    listOfAllMethodsList.add(name);
//                } else if(name.equals("<init>")) {
//                    return new MethodVisitor(ASM9) {
//                    };
//                }
                System.out.print("CLASSNAME " + className + " METHOD: " + name + "\n");
                if(exceptions == null) {
                    System.out.println("visitMethod access: " + access + ", name: " + name + ", desc: " +  desc + ", signature: " + signature);
                } else {
                    System.out.println("visitMethod access: " + access + ", name: " + name + ", desc: " +  desc + ", signature: " + signature + ", exceptions: " + exceptions.length);
                }

                return new MethodVisitor(ASM9) {
                    @Override
                    public void visitParameter(String name, int access) {
                        System.out.println("visitParameter name: " + name + ", access: " + access);
                        super.visitParameter(name, access);
                    }

                    @Override
                    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                        System.out.println("visitAnnotation descriptor: " + descriptor + ", visible: " + visible);

                        return super.visitAnnotation(descriptor, visible);
                    }

                    @Override
                    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
                        System.out.println("visitTypeAnnotation typeRef: " + typeRef + ", typePath: " + typePath + ", descriptor: " + descriptor + ", visible: " + visible);

                        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
                    }

                    @Override
                    public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
                        System.out.println("visitAnnotable ParameterCount parameterCount: " + parameterCount + ", visible: " + visible);
                        super.visitAnnotableParameterCount(parameterCount, visible);
                    }

                    @Override
                    public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
                        System.out.println("visitParameterAnnotation parameter: " + parameter + ", descsriptor: " +  descriptor +  ", name: " + name + ", visible: " + visible);

                        return super.visitParameterAnnotation(parameter, descriptor, visible);
                    }

                    @Override
                    public void visitAttribute(Attribute attribute) {
                        System.out.println("attribute.type: " + attribute.type);

                        super.visitAttribute(attribute);
                    }

                    @Override
                    public void visitCode() {
                        super.visitCode();
                    }

                    @Override
                    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
                        System.out.println("visitFrame type: " + type + ", numLocal: " + numLocal + ", local: " + local.length +  ", numStack: " + numStack + ", stack: " + stack.length);
                        super.visitFrame(type, numLocal, local, numStack, stack);
                    }

                    @Override
                    public void visitInsn(int opcode) {
                        System.out.println("visitInsn - opcode: " + opcode);
                        super.visitInsn(opcode);
                    }

                    @Override
                    public void visitIntInsn(int opcode, int operand) {
                        System.out.println("visitIntInsn - opcode: " + opcode + ", operand: " + operand);
                        super.visitIntInsn(opcode, operand);
                    }

                    @Override
                    public void visitVarInsn(int opcode, int varIndex) {
                        System.out.println("visitVarInsn - opcode: " + opcode + ", varIndex: " + varIndex);

                        super.visitVarInsn(opcode, varIndex);
                    }

                    @Override
                    public void visitTypeInsn(int opcode, String type) {
                        System.out.println("visitTypeInsn - opcode: " + opcode + ", type: " + type);

                        super.visitTypeInsn(opcode, type);
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                        System.out.println("visitFieldInsn - opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);

                        super.visitFieldInsn(opcode, owner, name, descriptor);
                    }

                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor) {
                        System.out.println("visitMethodInsn - opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);

                        super.visitMethodInsn(opcode, owner, name, descriptor);
                    }

                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        System.out.println("visitMethodInsn - opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor + ", isInterface: " + isInterface);

                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }

                    @Override
                    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
                        System.out.println("visitInvokeDynamicInsn - name: " + name + ", descriptor: " + descriptor + bootstrapMethodHandle.getName() + " " + bootstrapMethodHandle.getOwner());

                        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
                    }

                    @Override
                    public void visitJumpInsn(int opcode, Label label) {
                        System.out.println("visitJumpInsn - opcode: " + opcode);
                        super.visitJumpInsn(opcode, label);
                    }

                    @Override
                    public void visitLabel(Label label) {
                        if(label != null) {
                            System.out.println("visitLabel - label.info: " + label.info + ", label.toString: "  + label.toString());
                        }
                        super.visitLabel(label);
                    }

                    @Override
                    public void visitLdcInsn(Object value) {
                        if(value != null) {
                            System.out.println("visitLdcInsn value: " + value.toString());
                        }
                        super.visitLdcInsn(value);
                    }

                    @Override
                    public void visitIincInsn(int varIndex, int increment) {
                        System.out.println("visitIincInsn - varIndex: " + varIndex + ", increment: " + increment);
                        super.visitIincInsn(varIndex, increment);
                    }

                    @Override
                    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
                        System.out.println("visitTableSwitchInsn - min: " + min + ", max: " + max + " " + dflt.toString());
                        super.visitTableSwitchInsn(min, max, dflt, labels);
                    }

                    @Override
                    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
                        System.out.println("visitLookupSwitchInsn - dflt: " + dflt.toString());
                        super.visitLookupSwitchInsn(dflt, keys, labels);
                    }

                    @Override
                    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
                        System.out.println("visitMultiANewArrayInsn - descriptor: " + descriptor + ", numDimensions: " + numDimensions);
                        super.visitMultiANewArrayInsn(descriptor, numDimensions);
                    }

                    @Override
                    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
                        System.out.println("visitInsnAnnotation - typeRef: " + typeRef + ", descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitInsnAnnotation(typeRef, typePath, descriptor, visible);
                    }

                    @Override
                    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
                        System.out.println("visitTryCatchBlock - start: " + start.toString() + " " + end.toString() + ", type: " + type);
                        super.visitTryCatchBlock(start, end, handler, type);
                    }

                    @Override
                    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
                        System.out.println("visitTryCatchAnnotation - typeRef: " + typeRef + ", descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible);
                    }

                    @Override
                    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
                        System.out.println("visitLocalVariable - name: " + name + ", descriptor: " + descriptor + ", signature: " + signature + ", start: " + start.toString() + ", end: " + end.toString() + ", index: " + index);
                        super.visitLocalVariable(name, descriptor, signature, start, end, index);
                    }

                    @Override
                    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible) {
                        System.out.println("visitLocalVariableAnnotation - typeRef: " + typeRef + ", start: " + start.toString() + ", end: " + end.toString() + ", index: " + index + ", descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, descriptor, visible);
                    }

                    @Override
                    public void visitLineNumber(int line, Label start) {
                        System.out.println("visitLineNumber - line: " + line + ", start: " + start.toString() + " " + start.info);
                        super.visitLineNumber(line, start);
                    }

                    @Override
                    public void visitMaxs(int maxStack, int maxLocals) {
                        System.out.println("visitMaxs - maxStack: " + maxStack + ", maxLocals: " + maxLocals);
                        super.visitMaxs(maxStack, maxLocals);
                    }

                    @Override
                    public void visitEnd() {
                        super.visitEnd();
                    }
                };
            }
        }, 0);
    }

}
