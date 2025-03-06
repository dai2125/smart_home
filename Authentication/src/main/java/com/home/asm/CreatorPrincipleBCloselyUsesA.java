package com.home.asm;

import org.objectweb.asm.*;
import java.io.IOException;
import static org.objectweb.asm.Opcodes.ASM9;

public class CreatorPrincipleBCloselyUsesA extends ClassVisitor {
    public CreatorPrincipleBCloselyUsesA() {
        super(ASM9);
    }

    private final String CLASSNAME = "Lcom/home/creator/BCloselyUsesA/badExample/Calculator;";
    private final String FIELDNAME = "com/home/creator/BCloselyUsesA/goodExample/ReportGenerator.calculator";
    private final String OWNERCLASSNAME = "com/home/creator/BCloselyUsesA/goodExample/ReportGenerator";
    Model model = new Model();

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        // Jede Instanz des Objekts scheint hier auf
        if(descriptor.equals(CLASSNAME)) {
            System.out.println("Feld verwendet Calculator: " + name);
            System.out.println("name:" + name + " descriptor: " + descriptor + " signature: " + signature + " value: " + value + "\n");
            model.setName(descriptor);
//            model.increaseOwnerTotalMethods();
        }
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("\nAnalysiere Methode: " + name);
        if(!name.equals("<init>")) {
            model.increaseOwnerTotalMethods();
        }

        return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
            @Override
            public void visitTypeInsn(int opcode, String type) {
                if (opcode == Opcodes.NEW && CLASSNAME.contains(type)) {  //type.contains(CLASSNAME)) {
                    System.out.println("-> Erstellt Objekt vom Typ: " + type);

//                    if(!model.getObjectCreated() && name.equals("<init>")) {
                        model.setObjectCreated(true);
//                    }
                }
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String fieldName, String fieldDescriptor) {
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
                        model.addToOwnerMethods(name, true);
                        model.increaseMethodCounter();
                    }

                    if (Opcodes.GETFIELD == opcode && name.equals("<init>")) {
                        model.increaseFieldCounter();
                    }

                    if(model.getFieldName() == null) {
                        model.setFieldName(owner + "." + fieldName);
                    }

                    if(model.getOwner() == null) {
                        model.setOwner(owner);
                    }
                }
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String methodName, String methodDescriptor, boolean isInterface) {
                if (owner.equals(model.getFieldName())) {
                    System.out.println("-> Methode aufgerufen: " + owner + "." + methodName + " " + methodDescriptor + "\n");
                }
            }
        };
    }

    private void finish() {
        System.out.println("\n" + model.toString());
        model.getAllOwnerMethod();
    }

    private void bCloselyUsesA() {
        if(model.getObjectCreated() && model.getAllOccurencesInOwnerMethods() > 0) {
            System.out.println(model.getName() + " is created in " + model.getOwner());
            System.out.println(model.getName() + " is used in " + model.getAllOccurencesInOwnerMethods()
                                + " of a total of " + model.getOwnerTotalMethods() + " methods");
//            System.out.println(model.getOwner() + " has a total of " + model.getOwnerTotalMethods() + " methods");
            System.out.println("Creator Principle B closely uses A is correct");
        } else {
            System.out.println(model.getName() + " is not created in " + model.getOwner());
        }

    }

    public static void main(String[] args) throws IOException {
//        String className = "java.lang.String";
//        String className = "com/home/creator/BCloselyUsesA/badExample/ReportGenerator";
        String className = "com/home/creator/BCloselyUsesA/badExample/ReportGenerator";

        ClassReader reader = new ClassReader(className);
        CreatorPrincipleBCloselyUsesA methodPrinter = new CreatorPrincipleBCloselyUsesA();
        reader.accept(methodPrinter, 0);

        methodPrinter.finish();
        methodPrinter.bCloselyUsesA();
    }
}


//            @Override
//            public void visitMethodInsn(int opcode, String owner, String methodName, String methodDescriptor, boolean isInterface) {
//                String operation = switch (opcode) {
//                    case Opcodes.INVOKESPECIAL -> "ruft Konstruktor auf";
//                    case Opcodes.INVOKEVIRTUAL -> "ruft Instanzmethode auf";
//                    case Opcodes.INVOKESTATIC -> "ruft statische Methode auf";
//                    default -> "unbekannter Methodeaufruf";
//                };
//                System.out.println("-> " + operation + ": " + owner + "." + methodName + "\n");
//            }

//        System.out.println("Methode gefunden: " + name);
//        System.out.println("\tDescriptor: " + descriptor);
//        System.out.println("\tZugriff: " + access);
//        if(exceptions != null && exceptions.length > 0) {
//            System.out.println("\tExceptions: ");
//            for(String ex : exceptions) {
//                System.out.println("\t- " + ex);
//            }
//        }
//        System.out.println();
//        return super.visitMethod(access, name, descriptor, signature, exceptions);