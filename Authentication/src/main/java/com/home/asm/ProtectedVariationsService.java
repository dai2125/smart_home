package com.home.asm;

import org.objectweb.asm.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.objectweb.asm.Opcodes.ASM9;

public class ProtectedVariationsService {

    /* TODO
        Solution Identify points of predicted variation or instability; assign responsibilities to
                create a stable interface around them.
                Note: The term "interface" is used in the broadest sense of an access view; it
                does not literally only mean something like a Java or COM interface.
        Problem How to design objects, subsystems, and systems so that the variations or instability
                in these elements does not have an undesirable impact on other elements?
        Identify components that are expected to change frequently and
                establish stable interfaces and contracts between components to protect them against changes.
        Ensure that changes to one component do not break other components by thorough testing and validation.
        Avoid tight coupling between components, as it can lead to a ripple effect of changes throughout the system.
        Use Anti-Corruption layers to isolate the system from external changes.

        zum analysieren verwendest du alle Felder, alle privaten Felder, alle Methoden, alle privaten Methoden,
        alle Methoden die von einem Interface stammen, den DIT, den Fanout
        vielleicht kann die Kohäsivität zwischen den Methoden noch überprüft werden
    */

    private int counterInit = 0;
    private int counterMethod = 0;
    private HashSet<String> putFields = new HashSet<String>();
    private HashSet<String> localVariables = new HashSet<String>();
    private Set<String> getAllInitFields = new HashSet<>();
    private Set<String> listOfAllFunctions = new HashSet<>();
    private Set<String> getAllFieldsWithinMethods = new HashSet<>();

    public ProtectedVariationsService() {

    }

    public int displayInterfaces(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
//        System.out.println("CLASSNAME: " + className);
        classReader.accept(new ClassVisitor(ASM9) {

            boolean init = false;

            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

                System.out.println("CLASSNAME: version: " + version + ", access: " + access + ", name: " + name + ", superName: " + superName + "");
                for(int i = 0; i < interfaces.length; i++) {
                    System.out.println("INTERFACE: " + interfaces[i]);
                }
            }



            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

                MethodModel method = new MethodModel(access, name, descriptor, signature, exceptions);
                System.out.println("access: " + access + ", name: " + name + ", descriptor: " + descriptor + ", signature: " + signature);



                return new MethodVisitor(Opcodes.ASM9) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        System.out.println("opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor + ", isInterface: " + isInterface);

                        SubMethod subMethod = new SubMethod(opcode, owner, name, descriptor, isInterface);
                        method.addSubMethods(subMethod);

                        if (name.equals("<init>")) {
                            init = true;
                        }

                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }

                    @Override
                    public void visitParameter(String name, int access) {
                        System.out.println("visitParameter: name: " + name + ", access: " + access);
                        if (this.api < 327680) {
                            throw new UnsupportedOperationException("This feature requires ASM5");
                        } else {
                            if (this.mv != null) {
                                this.mv.visitParameter(name, access);
                            }

                        }
                    }

                    @Override
                    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                        System.out.println("visitAnnotation: descriptor: " + descriptor + ", visible: " + visible);
                        return this.mv != null ? this.mv.visitAnnotation(descriptor, visible) : null;
                    }

                    @Override
                    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
                        System.out.println("visitTypeAnnotation: typeRef: " + typeRef + ", typePath: " + typePath + ", descriptor: " + descriptor + ", visible: " + visible);

                        if (this.api < 327680) {
                            throw new UnsupportedOperationException("This feature requires ASM5");
                        } else {
                            return this.mv != null ? this.mv.visitTypeAnnotation(typeRef, typePath, descriptor, visible) : null;
                        }
                    }

                    @Override
                    public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
                        System.out.println("visitAnnotableParameterCount: parameterCount: " + parameterCount + ", visible: " + visible);

                        if (this.mv != null) {
                            this.mv.visitAnnotableParameterCount(parameterCount, visible);
                        }

                    }

                    @Override
                    public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
                        System.out.println("visitParameterAnnotation: parameter: " + parameter + ", descriptor: " + descriptor + ", visible: " + visible);
                        return this.mv != null ? this.mv.visitParameterAnnotation(parameter, descriptor, visible) : null;
                    }

                    @Override
                    public void visitIntInsn(int opcode, int operand) {
                        System.out.println("visitIntInsn: opcode: " + opcode + ", opderand: " + operand);
                        if (this.mv != null) {
                            this.mv.visitIntInsn(opcode, operand);
                        }

                    }

                    @Override
                    public void visitVarInsn(int opcode, int varIndex) {
                        System.out.println("visitVarInsn: opcode: " + opcode + ", varIndex: " + varIndex);
                        if (this.mv != null) {
                            this.mv.visitVarInsn(opcode, varIndex);
                        }

                    }

                    @Override
                    public void visitTypeInsn(int opcode, String type) {
                        System.out.println("visitTypeInsn: opcode: " + opcode + ", type:"  + type);
                        if (this.mv != null) {
                            this.mv.visitTypeInsn(opcode, type);
                        }

                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                        System.out.println("visitFieldInsn: owner: " + owner + ", name: " + name + ", description: " + desc);
                        SubField subField = new SubField(opcode, owner, name, desc);
                        method.addFields(subField);
                        some();
                    }

                    public void some(){
                        System.out.println(method.toString());
                    }
                };

            }

            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                System.out.println("visitField: access: " + access + ", name: " + name + ", descriptor: " + descriptor + ", signature: " + signature + ", value: " +value);

                if(init) {
//                    getAllInitFields.add(name, access, descriptor);
                }

                return new FieldVisitor(ASM9) {
                    @Override
                    public FieldVisitor getDelegate() {
                        return super.getDelegate();
                    }

                    @Override
                    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                        System.out.println("visitAnnotation: descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitAnnotation(descriptor, visible);
                    }

                    @Override
                    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
                        System.out.println("visitTypeAnnotation: typeRef: " + typeRef + ", typePath: " + typePath.toString() + ", descriptor: " + descriptor + ", visible: " + visible);
                        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
                    }

                    @Override
                    public void visitAttribute(Attribute attribute) {
                        System.out.println("visitAttribute: attribute: " + attribute);
                        super.visitAttribute(attribute);
                    }
                };

            }

        }, 0);
        return putFields.size();
    }

    public static String analyzeProtectedVariationOfClass(int privateFields, int totalFields, int methods, int privateMethods, int interfaceMethods, int dit, int fanout) {
        if(dit == 1) {
            return "class has no polymorph structure";
        }
        return areAllFieldsPrivate(privateFields, totalFields) + ", "
                + areAllMethodsFromAnInterface(methods, privateMethods, interfaceMethods) + ", "
                + amountOfFanout(fanout);

    }

    private static String areAllFieldsPrivate(int privateFields, int totalFields) {
        if(totalFields == 0) {
            return "this class has no fields";
        } else if(privateFields == totalFields) {
            return "all fields are private";
        } else if(privateFields < totalFields) {
            return "not all initialized fields are private";
        } else {
            return "ERROR privateFields < totalFields " + privateFields + " < " + totalFields;
        }
    }

    private static String areAllMethodsFromAnInterface(int methods, int privateMethods, int interfaceMethods) {
        if(methods == privateMethods) {
            return "ERROR all methods are private";
        } else if(methods == interfaceMethods) {
            return "all methods are from an interface";
        } else {//if(methods != privateMethods && privateMethods != interfaceMethods) {
            return "of " + methods + " methods " + privateMethods + " are private and " + interfaceMethods + " are from an interface";
        }
    }

    private static String amountOfFanout(int fanout) {
        if(fanout == 0) {
            return "class has a fanout of " + fanout;
        } else {
            return "class has a fanout of " + fanout;
        }
    }
}