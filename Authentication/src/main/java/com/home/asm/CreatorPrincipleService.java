package com.home.asm;

import java.util.ArrayList;
import java.util.List;

public class CreatorPrincipleService {

    private static final ArrayList<CreatorPrinciple> creatorPrincipleList = new ArrayList<CreatorPrinciple>();

    private CreatorPrincipleService() {}

    public static void put(CreatorPrinciple creatorPrinciple) {
//        if(!creatorPrincipleList.contains(creatorPrinciple)) {
//            creatorPrincipleList.add(creatorPrinciple);
//        }
        if(!contains(creatorPrinciple.getName())) {
            creatorPrincipleList.add(creatorPrinciple);
        }
    }

    public static CreatorPrinciple get(String className) {
//        System.out.println("1: ");
        for(CreatorPrinciple creatorPrinciple : creatorPrincipleList) {
//            System.out.println("2:" + creatorPrinciple.getName());
//            System.out.println("2:" + className);

            if(creatorPrinciple.getName().equals(className)) {
//                System.out.println("3: ");

                return creatorPrinciple;
            }
        }
        return null;
    }

    public static void remove(String className) {
        for(CreatorPrinciple creatorPrinciple : creatorPrincipleList) {
            if(creatorPrinciple.getName().equals(className)) {
                creatorPrincipleList.remove(creatorPrinciple);
            }
        }
    }

    public static void clear() {
        creatorPrincipleList.clear();
    }

    public static boolean contains(String name) {
//        return creatorPrincipleList.contains(name);
        for(int i = 0; i < creatorPrincipleList.size(); i++) {
            if(creatorPrincipleList.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static int printAll() {
        System.out.println("PRINT ALL");
        for(int i = 0; i < creatorPrincipleList.size(); i++) {
            System.out.println(//creatorPrincipleList.get(i).getName() + " "
                                creatorPrincipleList.get(i).toString());
        }
        return 0;
    }

    public static void aaa() {
        for(int i = 0; i < creatorPrincipleList.size(); i++) {
            for(int j = 0; j < creatorPrincipleList.get(i).getFieldList().size(); j++) {
                for(int k = 0; k < creatorPrincipleList.get(i).getFunctionList().size(); k++) { // .get(j).getSubMethodList().size(); k++) {
                    if(creatorPrincipleList.get(i).getFieldList().get(j).getType().equals(creatorPrincipleList.get(i).getFunctionList().get(j).getSubMethodList().get(k).getType())) {
                        System.out.println("xxxxxxxxxxxxxxxxxxxxx");
                        System.out.println(creatorPrincipleList.get(i).getFieldList().get(j).getType());
                        System.out.println(creatorPrincipleList.get(i).getFunctionList().get(k).getType());
                    }

//                    System.out.println("bbbbbbbbb");
//                    System.out.println(creatorPrincipleList.get(i).getFunctionList().get(j).getSubMethodList().get(k).getName() + " "
//                                    + creatorPrincipleList.get(i).getFunctionList().get(j).getSubMethodList().get(k).getType() + " "
//                                    + creatorPrincipleList.get(i).getFunctionList().get(j).getSubMethodList().get(k).getOperation() + " ");

//            for(int j = 0; j < creatorPrincipleList.get(i).getFieldList().size(); j++) {
//                for(int k = 0; k < creatorPrincipleList.get(i).getFunctionList().size(); k++) {
//                    System.out.println("aaaaaaaaaaaaa CREATOR PRINCIPLE SERVICE");

//                    System.out.println(creatorPrincipleList.get(i).getFunctionList().get(j).getName());
//                    System.out.println(creatorPrincipleList.get(i).getFieldList().get(j).getName() + " " + creatorPrincipleList.get(i).getFieldList().get(j).getType() + " " + creatorPrincipleList.get(i).getFieldList().get(j).getOwner());
//                    System.out.println(creatorPrincipleList.get(i).getFieldInsnList().get(k).getName() + " " + creatorPrincipleList.get(i).getFieldInsnList().get(k).getOperation() + " " + creatorPrincipleList.get(i).getFieldInsnList().get(k).getType() + " " + creatorPrincipleList.get(i).getFunctionList().get(k).getOperation());

//                    System.out.println(creatorPrincipleList.get(i).getFunctionList().get(k).getName() + " " + creatorPrincipleList.get(i).getFunctionList().get(k).getOperation() + " " + creatorPrincipleList.get(i).getFunctionList().get(k).getType() + " " + creatorPrincipleList.get(i).getFunctionList().get(k).getOperation());
//                    System.out.println(creatorPrincipleList.get(i).getFieldListToControl().get(k).getName() + " " + creatorPrincipleList.get(i).getFieldListToControl().get(k).getParentType() + " " + creatorPrincipleList.get(i).getFieldListToControl().get(k).getType() + " " + creatorPrincipleList.get(i).getFunctionList().get(k).getOperation());

//                    System.out.println(creatorPrincipleList.get(i).getMethodListToControl().get(k).getName() + " " + creatorPrincipleList.get(i).getMethodListToControl().get(k).getParentType() + " " + creatorPrincipleList.get(i).getMethodListToControl().get(k).getCalledMethods() + " " + creatorPrincipleList.get(i).getFunctionList().get(k).getOperation());


//                    if(creatorPrincipleList.get(i).getFieldList().get(j).getType().equals(creatorPrincipleList.get(i).getFunctionList().get(k).getType())) {
//                        System.out.println("CREATOR PRINCIPLE SERVICE");
//                        System.out.println(creatorPrincipleList.get(i).getFieldList().get(j).getType());
//                        System.out.println(creatorPrincipleList.get(i).getFunctionList().get(k).getType());
//                    }

                }
            }
// visitMethodInsn - opcode: 182, owner: com/home/creator/BRecordsA/goodExample/Event, name: getDescription, descriptor: ()Ljava/lang/String;, isInterface: false
        }
    }

    public static void checkFirstPrincipleObjectInitializedWithinConstructor(String className) {
        System.out.println("checkFirstPrincipleObjectInitializedWithinConstructor - className: " + className);
        for (int i = 0; i < creatorPrincipleList.size(); i++) {
            if (creatorPrincipleList.get(i).getName().equals(className)) {


                CreatorPrinciple principle = creatorPrincipleList.get(i);

                List<CreatorPrincipleField> fields = principle.getFieldList();
                List<CreatorPrincipleField> fieldInsn = principle.getFieldInsnList();
                List<Model> models = principle.getModelList();

//                System.out.println();
//                System.out.print("FIELDINSN:\n" );
//                for(int y = 0; y < fieldInsn.size(); y++) {
//                    System.out.println("\tname: " + fieldInsn.get(y).getName() + ", owner: " + fieldInsn.get(y).getOwner() + ", operation: " + fieldInsn.get(y).getOperation());
//                }
//                System.out.println();
//
//                System.out.print("FIELD:\n");
//                for(int y = 0; y < fields.size(); y++) {
//                    System.out.println("\tname: "  + fields.get(y).getName() + ", owner: " + fields.get(y).getOwner() + ", operation: " + fields.get(y).getOperation());
//                }
//                System.out.println();

//                System.out.println();
//                System.out.print("MODEL:\n" );
//                for(int y = 0; y < models.size(); y++) {
//                    System.out.println("\tname: " + models.get(y).getName() + ", owner: " + models.get(y).getOwner() + ", getObjectCreated: " + models.get(y).getObjectCreated() + ", constructorInitialized" + models.get(y).getConstructorInitialized() + ", type: " + models.get(y).getType());
//                }
//                System.out.println();

                List<CreatorPrincipleMethod> functions = principle.getFunctionList();

                for (int j = 0; j < fields.size(); j++) {
//                    System.out.println("----fields: " + fields.get(j));
                    CreatorPrincipleField field = fields.get(j);

                    for (int k = 0; k < functions.size(); k++) {
                        CreatorPrincipleMethod function = functions.get(k);
                        List<CreatorPrincipleMethod> subMethods = function.getSubMethodList();

                        for (int m = 0; m < subMethods.size(); m++) {
                            CreatorPrincipleMethod subMethod = subMethods.get(m);
//                            if (field.getType().replaceFirst("L", "").replace(";", "").equals(subMethod.getType())) {
//                                System.out.println(className.replaceFirst(".*/", "") + " erstellt ein Objekt vom Typ "
//                                        + field.getType().replaceFirst(".*/", "").replace(";", "")
//                                        + " mit dem Namen " + field.getName() + " "
//                                        + " und verwendet dessen Funktion "
//                                        + subMethod.getName() + " in der Methode " //  + subMethod.getType() + " " + subMethod.getOperation() + " "
//                                        + function.getName());
////                            System.out.println(field.getType());
////                            System.out.println(subMethod.getType());
//                            }

                            if (field.getType() != null) {

//                                if (field.getType().replaceFirst("L", "").replace(";", "").equals(subMethod.getType()) &&
//                                        subMethod.getName().equals("<init>") && function.getName().equals("<init>")) {
//                                    System.out.println(className.replaceFirst(".*/", "") + " erstellt ein Objekt vom Typ "
//                                            + field.getType().replaceFirst(".*/", "").replace(";", "")
//                                            + " mit dem Namen " + field.getName() + ""
//                                            + " innerhalb des " + function.getName() + " Constructors");
////                                System.out.println(field.getName() + " " + field.getOwner() + " " + field.getOperation() + " " + field.getType());
////                                System.out.println(subMethod.getName() + " " + subMethod.getOperation() + " " + subMethod.getType());
////                                System.out.println(function.getName() + " " + function.getOperation());
//                                }

                                // TODO hier weiter machen
                                if (field.getType().replaceFirst("L", "").replace(";", "").equals(subMethod.getType())
                                        && !subMethod.getName().equals("<init>") && !function.getName().equals("<init>")) {
                                }
                                System.out.println(className.replaceFirst(".*/", "") + " erstellt ein Objekt vom Typ "
                                        + field.getType().replaceFirst(".*/", "").replace(";", "")
                                        + " mit dem Namen " + field.getName() + ""
                                        + " innerhalb des " + function.getName() + " Constructors");
                                System.out.println(field.getName() + " " + field.getOwner() + " " + field.getOperation() + " " + field.getType());
                                System.out.println(subMethod.getName() + " " + subMethod.getOperation() + " " + subMethod.getType());
                                System.out.println(function.getName() + " " + function.getOperation());

                            }
                        }
                    }
                }
            }
        }
    }

    public static void checkFirstPrinciple(String className) {
        System.out.println("className: " + className);
        for (int i = 0; i < creatorPrincipleList.size(); i++) {
            if(creatorPrincipleList.get(i).getName().equals(className)) {


                CreatorPrinciple principle = creatorPrincipleList.get(i);

                List<CreatorPrincipleField> fields =  principle.getFieldList();
                List<CreatorPrincipleField> fieldInsn =  principle.getFieldInsnList();
                List<Model> models =  principle.getModelList();

//                System.out.println();
//                System.out.print("FIELDINSN:\n" );
//                for(int y = 0; y < fieldInsn.size(); y++) {
//                    System.out.println("\tname: " + fieldInsn.get(y).getName() + ", owner: " + fieldInsn.get(y).getOwner() + ", operation: " + fieldInsn.get(y).getOperation());
//                }
//                System.out.println();
//
//                System.out.print("FIELD:\n");
//                for(int y = 0; y < fields.size(); y++) {
//                    System.out.println("\tname: "  + fields.get(y).getName() + ", owner: " + fields.get(y).getOwner() + ", operation: " + fields.get(y).getOperation());
//                }
//                System.out.println();

                System.out.println();
                System.out.print("MODEL:\n" );
                for(int y = 0; y < models.size(); y++) {
                    System.out.println("\tname: " + models.get(y).getName() + ", owner: " + models.get(y).getOwner() + ", getObjectCreated: " + models.get(y).getObjectCreated() + ", constructorInitialized" + models.get(y).getConstructorInitialized() + ", type: " + models.get(y).getType());
                }
                System.out.println();

                List<CreatorPrincipleMethod> functions = principle.getFunctionList();

                for (int j = 0; j < fields.size(); j++) {
//                    System.out.println("----fields: " + fields.get(j));
                    CreatorPrincipleField field = fields.get(j);

                    for (int k = 0; k < functions.size(); k++) {
                        CreatorPrincipleMethod function = functions.get(k);
                        List<CreatorPrincipleMethod> subMethods = function.getSubMethodList();

                        for (int m = 0; m < subMethods.size(); m++) {
                            CreatorPrincipleMethod subMethod = subMethods.get(m);
//                            if (field.getType().replaceFirst("L", "").replace(";", "").equals(subMethod.getType())) {
//                                System.out.println(className.replaceFirst(".*/", "") + " erstellt ein Objekt vom Typ "
//                                        + field.getType().replaceFirst(".*/", "").replace(";", "")
//                                        + " mit dem Namen " + field.getName() + " "
//                                        + " und verwendet dessen Funktion "
//                                        + subMethod.getName() + " in der Methode " //  + subMethod.getType() + " " + subMethod.getOperation() + " "
//                                        + function.getName());
////                            System.out.println(field.getType());
////                            System.out.println(subMethod.getType());
//                            }

                            if(field.getType() != null ) {
                                if (field.getType().replaceFirst("L", "").replace(";", "").equals(subMethod.getType()) &&
                                        subMethod.getName().equals("<init>") && function.getName().equals("<init>")) {
                                    System.out.println(className.replaceFirst(".*/", "") + " erstellt ein Objekt vom Typ "
                                            + field.getType().replaceFirst(".*/", "").replace(";", "")
                                            + " mit dem Namen " + field.getName() + ""
                                            + " innerhalb des " + function.getName() + " Constructors");
//                                System.out.println(field.getName() + " " + field.getOwner() + " " + field.getOperation() + " " + field.getType());
//                                System.out.println(subMethod.getName() + " " + subMethod.getOperation() + " " + subMethod.getType());
//                                System.out.println(function.getName() + " " + function.getOperation());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void aaa(int a) {
        for (int i = 0; i < creatorPrincipleList.size(); i++) {
            CreatorPrinciple principle = creatorPrincipleList.get(i);

            List<CreatorPrincipleField> fields = principle.getFieldList();
            List<CreatorPrincipleMethod> functions = principle.getFunctionList();

            for (int j = 0; j < fields.size(); j++) {
                CreatorPrincipleField field = fields.get(j);

                for (int k = 0; k < functions.size(); k++) {
                    CreatorPrincipleMethod function = functions.get(k);
                    List<CreatorPrincipleMethod> subMethods = function.getSubMethodList();

                    for (int m = 0; m < subMethods.size(); m++) {
                        CreatorPrincipleMethod subMethod = subMethods.get(m);
//                        System.out.println("aaaa: " + field.getName());
//                        System.out.println("bbbb: " + field.getOwner());
//                        System.out.println("rrrr: " + field.getType());
//
//                        System.out.println("wwww: " + subMethod.getName());
//                        System.out.println("gggg: " + subMethod.getOperation());
//                        System.out.println("kkkk: " + subMethod.getModifier());
//                        System.out.println("qqqq: " + subMethod.getType());

//                        if(field.getType().contains(subMethod.getType())) {
                        if (field.getType().replaceFirst("L", "").replace(";", "").equals(subMethod.getType())) {
                            System.out.println("xxxxxxxxxxxxxxxxxxxxx");
                            System.out.println(field.getType());
                            System.out.println(subMethod.getType());
                        }
                    }
                }
            }
        }
    }

}
