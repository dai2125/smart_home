package com.home.asm;

import org.objectweb.asm.*;
import java.io.IOException;
import static org.objectweb.asm.Opcodes.ASM9;

public class CreatorPrincipleBContainsOrCompositelyAggregatesAWithinTheMethods extends ClassVisitor {

    public CreatorPrincipleBContainsOrCompositelyAggregatesAWithinTheMethods() {
        super(ASM9);
    }

    private final String CLASSNAME = "Lcom/home/creator/BContainsOrCompositelyAggregatesA/movieExample/Movie;";
    Model model = new Model();

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
            @Override
            public void visitTypeInsn(int opcode, String type) {
                if (opcode == Opcodes.NEW && CLASSNAME.contains(type)) {
                    System.out.println("-> Erstellt Objekt vom Typ: " + type);
                    System.out.println("-> type: " + type);
                    System.out.println("-> name: " + name);
                    System.out.println("-> descriptor: " + descriptor);

                    if(!model.getObjectCreated() && !name.equals("<init>")) {
                        model.setObjectCreated(true);
                        model.setType(type);
                        model.setName(type);
                    }
                }
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String fieldName, String fieldDescriptor) {
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
        };
    }

    private void finish() {
        System.out.println("\n" + model.toString());
        model.getAllOwnerMethod();
    }

    private void bContainsOrCompositelyAggregatesA() {
        if(!model.getConstructorInitialized() && !model.getConstructorParameter() && model.getObjectCreated() && model.getAllOccurencesInOwnerMethods() > 0) {
            System.out.println(model.getName() + " is created within a method of " + model.getOwner());
            System.out.println(model.getName() + " is used in " + model.getAllOccurencesInOwnerMethods()
                    + " of a total of " + model.getOwnerTotalMethods() + " methods");
            System.out.println("Creator Principle B contains A is correct");
        } else {
            System.out.println(model.getName() + " is not created within a method of " + model.getOwner());
        }
    }

    public static void main(String[] args) throws IOException {
        String className = "com/home/creator/BContainsOrCompositelyAggregatesA/movieExample/MovieProduction";

        ClassReader reader = new ClassReader(className);
        CreatorPrincipleBContainsOrCompositelyAggregatesAWithinTheMethods methodPrinter = new CreatorPrincipleBContainsOrCompositelyAggregatesAWithinTheMethods();
        reader.accept(methodPrinter, 0);

        methodPrinter.finish();
        methodPrinter.bContainsOrCompositelyAggregatesA();
    }
}

