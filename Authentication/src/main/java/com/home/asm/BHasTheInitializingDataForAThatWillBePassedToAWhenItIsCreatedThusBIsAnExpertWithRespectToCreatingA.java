package com.home.asm;

import org.objectweb.asm.*;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.ASM9;
import org.objectweb.asm.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.objectweb.asm.Opcodes.ASM9;

public class BHasTheInitializingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA extends ClassVisitor {

    public BHasTheInitializingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA() {
        super(ASM9);
    }

    private final String CLASSNAME = "Lcom/home/creator/BHasTheInitializingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA/thirdExample/ClassA;";
    Model model = new Model();
    NewStatementModel newStatementModel = new NewStatementModel();
//    NewStatementModel newStatementModel = new NewStatementModel();

    @Override // TODO unnötig weil es keine Felder für das Objekt gibt, nur in den Methoden
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {

        // TODO wenn die Methoden willkürlich gesetzt sind ignoriert der ClassVisitor die ersten Einträge des Objekts
        // TODO darum setzt du hier den Name() explizit über den CLASSNAME
        // TODO in den anderen ClassVisitor Klassen wirst du das auch machen müssen
        model.setNameExtra(CLASSNAME);

        if(descriptor.equals(CLASSNAME)) {
            System.out.println("Feld verwendet Class: " + name);
            System.out.println("name:" + name + " descriptor: " + descriptor + " signature: " + signature + " value: " + value + "\n");
            model.setName(descriptor);
        }
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

//        private NewStatementModel newStatementModel = new NewStatementModel();

        System.out.println("\nAnalysiere Methode: " + name);
        System.out.println("access:" + access);
        System.out.println("name:" + name);
        System.out.println("descriptor:" + descriptor);
        System.out.println("signature:" + signature);

        if(!name.equals("<init>")) {
            model.increaseOwnerTotalMethods();
        }

        if(!name.equals("<init>") ) {

        }

        if(name.equals("<init>")) {
            System.out.println("Konstruktor gefunden: ");

            Type[] argumentTypes = Type.getArgumentTypes(descriptor);
            System.out.print("Parameter: ");

            for(Type type : argumentTypes) {
                System.out.print(type.getClassName() + " \n");
                if(type.getClassName().equals(model.getConstructorParameterName())) {
                    System.out.println(CLASSNAME + " is a parameter of this class, " + type.getClassName());
                    model.setConstructorParameter(true);
                    if(model.getConstructorParameter()) {
                        model.setConstructorParameterName(model.getName().replaceFirst("L", "").replaceAll("/", ".").replaceAll(";", ""));
                    }
                }
            }
            System.out.println("\n");
        }
//        return super.visitMethod(access, name, descriptor, signature, exceptions);

        return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {

            private String lastNewType = null;
            private List<String> getFields = new ArrayList<>();
            private List<String> putFields = new ArrayList<>();
            private List<String> parameterFields = new ArrayList<>();
            private boolean usesCreatorFields;
            private int constructorArgs = 0;
//            private NewStatementModel newStatementModel;


            @Override
            public void visitTypeInsn(int opcode, String type) {

                if(opcode == Opcodes.NEW && CLASSNAME.contains(type)) {
                    lastNewType = type;
                    newStatementModel.setType(type);
                    System.out.println("NEUES OBJEKT ERSTELLT: " + type);
                }

                if (opcode == Opcodes.NEW && CLASSNAME.contains(type)) {
                    System.out.println("-> Erstellt Objekt vom Typ: " + type);
                    System.out.println("-> type: " + type);
                    System.out.println("-> name: " + name);
                    System.out.println("-> descriptor: " + descriptor);

                    if(!model.getObjectCreated() && name.equals("<init>")) {
                        model.setObjectCreated(true);
                        model.setType(type);
//                        model.setName(type);
                    }

                    if(!model.getObjectCreated() && !name.equals("<init>")) {
                        model.setObjectCreated(true);
                        model.setType(type);
//                        model.setName(type);
                    }
                }
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String fieldName, String fieldDescriptor) {

                if(opcode == Opcodes.GETFIELD) {
                    System.out.println("NEUES OBJEKT holt Feld: " + owner + "." + fieldName + " (" + fieldDescriptor + ")");

                    if(lastNewType != null && CLASSNAME.contains(lastNewType)) {
                        getFields.add(fieldDescriptor);
                        newStatementModel.setGetFields(getFields);
                    }
                } else if(opcode == Opcodes.SIPUSH) {
                    System.out.println("NEUES OBJEKT speichert in Feld: " + owner + "." + fieldName + " (" + fieldDescriptor + ")");

                    if(lastNewType != null && CLASSNAME.contains(lastNewType)) {
                        putFields.add(fieldDescriptor);
                        newStatementModel.setPutFields(putFields);
                    }
                }

                if(model.getOwner() == null) {
                    model.setOwner(owner);
                }
                if (fieldDescriptor.equals(CLASSNAME)) {
                    String operation = switch (opcode) {
                        case Opcodes.GETFIELD -> "liest Instanzfeld";
                        case Opcodes.PUTFIELD -> "schreibt Instanzfeld";
                        case Opcodes.GETSTATIC -> "liest statisches Feld";
                        case Opcodes.PUTSTATIC -> "schreibt statisches Feld";
                        default -> "unbekannter Feldoperation";
                    };
                    System.out.println("-> " + operation + ": " + owner + "." + fieldName + " fieldName: " + fieldName + " "); //+ "\n");
                    System.out.println("-> fieldDescriptor: " + fieldDescriptor + "\n");

                    if (Opcodes.GETFIELD == opcode && !name.equals("<init>")) {
                        model.increaseMethodCounter();
                    }

                    if (Opcodes.GETFIELD == opcode && name.equals("<init>")) {
                        model.increaseFieldCounter();
                    }

                    if(model.getFieldName() == null) {
                        model.setFieldName(owner + "." + fieldName);
                    }
                }
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String methodName, String methodDescriptor, boolean isInterface) {

                if(opcode == Opcodes.INVOKESPECIAL && methodName.equals("<init>") && CLASSNAME.contains(owner)) {
                    System.out.println("NEUES OBJEKT Konstruktoraufruf von: " + owner);
                    System.out.println("NEUES OBJEKT Methoden-Descriptor: "  + methodDescriptor);

                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i < methodDescriptor.length(); i++) {
                        if(methodDescriptor.charAt(i) == ')') {
                            parameterFields.add(sb.toString());
                            newStatementModel.setParameterValues(parameterFields);

                            break;
                        } else if(methodDescriptor.charAt(i) == '(') {

                        } else if(methodDescriptor.charAt(i) == ';') {
                            parameterFields.add(sb.toString());
                            newStatementModel.setParameterValues(parameterFields);

                            sb = new StringBuilder();
                        } else {
                            sb.append(methodDescriptor.charAt(i));
                        }

                    }

                    if(lastNewType != null && lastNewType.equals(owner)) {
                        Type[] argumentTypes = Type.getArgumentTypes(methodDescriptor);
                        constructorArgs = argumentTypes.length;
                        System.out.println("NEUES OBJEKT Konstsruktor-Parameter (" + constructorArgs + ") ");

                        newStatementModel.setTotalAmountConstructorParameters(constructorArgs);


                        for(Type argType : argumentTypes) {
                            System.out.println("NEUES OBJEKT\t" + argType.getClassName());
                        }
                    }
                }

//                String operation = switch (opcode) {
//                    case Opcodes.GETFIELD -> "liest Instanzfeld";
//                    case Opcodes.PUTFIELD -> "schreibt Instanzfeld";
//                    case Opcodes.GETSTATIC -> "liest statisches Feld";
//                    case Opcodes.PUTSTATIC -> "schreibt statisches Feld";
//                    case Opcodes.INVOKEVIRTUAL -> "ruft Methode auf";
//                    default -> "unbekannter Feldoperation";
//                };
//                System.out.println("-> " + operation + ", owner -> " + owner + ", methodName -> " + methodName + ", methodDescriptor -> " + methodDescriptor + " "); //+ "\n");


                if(Opcodes.INVOKEVIRTUAL == 182 && model.getName() != null && methodDescriptor.contains(model.getName())) {
                    if(!model.doesOwnerMethodContainsThisMethodAlready(name)) {
                        model.increaseMethodCounter();
                        model.addToOwnerMethods(name, true);
                    }
                }
                if (owner.equals(model.getName())) {
                    System.out.println("-> Methode aufgerufen: " + owner + "." + methodName + " " + methodDescriptor + "\n");

                    if(model.getFieldName() == null) {
                        model.setFieldName(owner + ".");
                    }

                    if(!model.doesOwnerMethodContainsThisMethodAlready(name)) {
                        model.increaseMethodCounter();
                        model.addToOwnerMethods(name, true);
                    }
                }
            }

            @Override
            public void visitIntInsn(int opcode, int operand) {
                if(opcode == Opcodes.BIPUSH || opcode == Opcodes.SIPUSH) {
                    System.out.println("NEUES OBJEKT Integer Wert auf Stack gelegt: " + operand);
                }
            }

//            System.out.println(newStatementModel.toString());
//            lastNewType = null;
        };

    }

    private void finish() {
        System.out.println("\n" + model.toString());
        model.getAllOwnerMethod();

        System.out.println(newStatementModel.toString());
    }

    private void bHasTheInitializingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA() {
        if(!model.getConstructorInitialized() && !model.getConstructorParameter() && model.getObjectCreated() && model.getAllOccurencesInOwnerMethods() > 0) {
            System.out.println(model.getName() + " is created within a method of " + model.getOwner());
            System.out.println(model.getName() + " is used in " + model.getAllOccurencesInOwnerMethods()
                    + " of a total of " + model.getOwnerTotalMethods() + " methods");
            System.out.println("Creator Principle B contains A is correct");
        } else {
            System.out.println(model.getName() + " is not created in " + model.getOwner());
        }
    }

    public static void main(String[] args) throws IOException {
        String className = "com/home/creator/BHasTheInitializingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA/thirdExample/ClassB";

        ClassReader reader = new ClassReader(className);
        BHasTheInitializingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA methodPrinter = new BHasTheInitializingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA();
        reader.accept(methodPrinter, 0);

        methodPrinter.finish();
        methodPrinter.bHasTheInitializingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA();
    }
}
