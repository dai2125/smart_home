package com.home.asm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CreatorPrincipleService {

    /*  Creator Principle
     *
     *   Creator 3 - Erstellt Klasse B die Klasse A und verwendet sie auch
     *
     *   Creator 1 - Verwendet Klasse B die Klasse A zu erstellen
     *
     *   SRP - Eine Klasse sollte immer nur eine Aufgabe haben, erstellt sie Objekte, Anzahl an Methoden, Anzahl an Felder
     *
     * */

    /* AuWo Service - B Closeley uses A - B Records A - Single Responsibility Principle
    *
    * Erstellt die ausgewählte Klasse Objekte, wenn ja dann überprüfe ob diese Objekte auch verwendet werden.
    * firstPrinciple() überprüft ob die ausgwählte Klasse ein Objekt innerhalb des Konstruktor oder als Feld erstellt und dieses Objekt in den Methoden nutzt (2 von 4 Methoden) = Prinzip Verletztung
    * thirdPrinciple() überprüft ob eine Liste(Set, Queue, Map) mit Objekten existiert und diese Liste verwendet wird (wieviel Methoden nutzten die Liste, wird die Liste genutzt)
    * singleResponsibilityPrinciple() erstellt die Klasse Objekte, LCOM4, FanOut, Yalcom, Anzahl der Methoden
    * */

    private static final ArrayList<CreatorPrinciple> creatorPrincipleList = new ArrayList<CreatorPrinciple>();
    private static final String INIT = "<init>";
    private static final String ARRAYLIST = "Ljava/util/List<";
    private static final String CREATORPRINCIPLE1 = "CREATOR PRINCIPLE 1 B CLOSELY USES A";
    private static final String CREATORPRINCIPLE3 = "CREATOR PRINCIPLE 3 B RECORDS A";

    private CreatorPrincipleService() {}

    public static void put(CreatorPrinciple creatorPrinciple) {
//        if(!creatorPrincipleList.contains(creatorPrinciple)) {
//            creatorPrincipleList.add(creatorPrinciple);
//        }
        if(!contains(creatorPrinciple.getName())) {
            creatorPrincipleList.add(creatorPrinciple);
        }
    }

    public static ArrayList<CreatorPrinciple> getCreatorPrincipleList() {
        return creatorPrincipleList;
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

    public static void checkAll(String className) {
        System.out.println("CHECK ALL - className: " + className);
        for (int i = 0; i < creatorPrincipleList.size(); i++) {
            if (creatorPrincipleList.get(i).getName().equals(className)) {


                CreatorPrinciple principle = creatorPrincipleList.get(i);

                List<CreatorPrincipleField> fields = principle.getFieldList();
                List<CreatorPrincipleField> fieldInsn = principle.getFieldInsnList();
                List<Model> models = principle.getModelList();

                /* TODO FieldInsn alle initialisierten Felder
                    ownerMethod.equals("<init>")
                        - objekt wird im constructor erstellt
                    ownerMethod.equals("<init>") && !ownerMethod.equals("<init>")
                        - Objekt wird im constructor erstellt und einer Methode verwendet
                */
                System.out.println();
                System.out.print("FIELDINSN:\n" );
                for(int y = 0; y < fieldInsn.size(); y++) {
                    System.out.println("\tname: " + fieldInsn.get(y).toString());

//                    System.out.println("\tname: " + fieldInsn.get(y).getName() + ", owner: " + fieldInsn.get(y).getOwner() + ", operation: " + fieldInsn.get(y).getOperation());
                }
                System.out.println();

                /* TODO FIELD alle Felder innerhalb des <init> die auch noch nicht initialsiert wurden

                */
                System.out.print("FIELD:\n");
                for(int y = 0; y < fields.size(); y++) {
                    System.out.println("\tname: " + fields.get(y).toString());

//                    System.out.println("\tname: "  + fields.get(y).getName() + ", owner: " + fields.get(y).getOwner() + ", operation: " + fields.get(y).getOperation());
                }
                System.out.println();

                /* TODO FIELDS die in METHODEN INITIALISIERT werden
                    Objekte die außerhalb des <init> erstellt werden
                    der Name kann nicht ausgelesen werden, deßhalb setzt sich der Name aus Typ und Methode zusammen Type + "." + Descriptor
                */
                System.out.print("FIELD IN METHOD:\n" );
                for(int y = 0; y < models.size(); y++) {
                    System.out.println("\tname: " + models.get(y).toString());

//                    System.out.println("\tname: " + models.get(y).getName() + ", owner: " + models.get(y).getOwner() + ", getObjectCreated: " + models.get(y).getObjectCreated() + ", constructorInitialized" + models.get(y).getConstructorInitialized() + ", type: " + models.get(y).getType());
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
                                if (field.getType().replaceFirst("L", "").replace(";", "")
                                        .equals(subMethod.getType())
//                                        && field.getName().equals(function.)
                                        && !subMethod.getName().equals("<init>")
                                        && !function.getName().equals("<init>")) {

                                    System.out.println(className.replaceFirst(".*/", "") + " verwendet ein Objekt vom Typ "
                                            + field.getType().replaceFirst(".*/", "").replace(";", "")
//                                            + " mit dem Namen " + field.getName() + ""
                                            + " innerhalb der " + function.getName() + " Funktion");
//                                            + " und verwendet das Objekt in der Funktion " + function.getName());
//                                    System.out.println("FIELD: " + field.getName() + " " + field.getOwner() + " " + field.getOperation() + " " + field.getType() + " " );
//                                    System.out.println("FUNCTION: " + function.getName() + " " + function.getOperation() + " " + function.getSubMethodList().stream().map(CreatorPrincipleMethod::getOwner).toList() + " " /* + function.toString()) */ );
//                                    System.out.println("SUBMETHOD: " + subMethod.getName() + " " + subMethod.getOperation() + " " + subMethod.getType() + " " + subMethod.getSubMethodList().stream().map(CreatorPrincipleMethod::getName).toList());

                                }
//                                System.out.println(className.replaceFirst(".*/", "") + " erstellt ein Objekt vom Typ "
//                                        + field.getType().replaceFirst(".*/", "").replace(";", "")
//                                        + " mit dem Namen " + field.getName() + ""
//                                        + " innerhalb des " + function.getName() + " Constructors");
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

    // TODO zähle nur die Anzahl eines bestimmten Objekts
    public static int case1(String className) {
        int ans = 0;
        HashSet<String> hashSet = new HashSet<>();

        for (int i = 0; i < creatorPrincipleList.size(); i++) {
            if (creatorPrincipleList.get(i).getName().equals(className)) {
                CreatorPrinciple principle = creatorPrincipleList.get(i);
                List<CreatorPrincipleField> fieldInsn = principle.getFieldInsnList();

                for (int j = 0; j < fieldInsn.size(); j++) {
                    for (int k = 0; k < fieldInsn.size(); k++) {
                        if(fieldInsn.get(j).getName().equals(fieldInsn.get(k).getName())
                                && fieldInsn.get(j).getOwnerMethod().equals(INIT)
                                && !fieldInsn.get(k).getOwnerMethod().equals(INIT)
                                && !fieldInsn.get(j).getFieldReturnType().equals("I")
                                && !fieldInsn.get(j).getFieldReturnType().equals("D")
                                && !fieldInsn.get(j).getFieldReturnType().equals("L")
                                && !fieldInsn.get(j).getFieldReturnType().equals("S")
                                && !fieldInsn.get(j).getFieldReturnType().equals("Z")
                        ) {

                            String fieldCombination = fieldInsn.get(j).getName() + " " + fieldInsn.get(k).getName() + " " + fieldInsn.get(j).getOwnerMethod() + " " + fieldInsn.get(k).getOwnerMethod();
                            if(!hashSet.contains(fieldCombination)) {
                                hashSet.add(fieldCombination);
                                ans++;
//                                System.out.println("--- " + fieldInsn.get(j).getName() + " " + fieldInsn.get(j).getType() + " " + fieldInsn.get(j).getFieldReturnType() + " " + fieldInsn.get(j).getOwnerMethod());
//                                System.out.println("<<< " + fieldInsn.get(k).getName() + " " + " " + fieldInsn.get(k).getType() + " " + fieldInsn.get(k).getFieldReturnType() + " " + fieldInsn.get(k).getOwnerMethod());
//                                System.out.println("ans++: " + ans);
                            }
                        }
                    }
                }
            }
        }
        return ans;
    }

    public static int case3(String fieldName, List<CreatorPrincipleField> fieldInsn) {
        int ans = 0;
        HashSet<String> hashSet = new HashSet<>();

                for (int j = 0; j < fieldInsn.size(); j++) {
                    for (int k = 0; k < fieldInsn.size(); k++) {
                        if(fieldName.equals(fieldInsn.get(k).getName())
                                && fieldInsn.get(j).getOwnerMethod().equals(INIT)
                                && !fieldInsn.get(k).getOwnerMethod().equals(INIT)
                        ) {
                            String fieldCombination = fieldName + " " + fieldInsn.get(k).getName() + " " + " " + fieldInsn.get(k).getOwnerMethod();
                            if(!hashSet.contains(fieldCombination)) {
                                hashSet.add(fieldCombination);
                                ans++;
                            }
                        }
                    }
                }
        return ans;
    }

    public static int countArrayList(String fieldName, List<CreatorPrincipleField> field, List<CreatorPrincipleField> fieldInsn) {
        int ans = 0;
        HashSet<String> hashSet = new HashSet<>();
        for (int j = 0; j < fieldInsn.size(); j++) {

            if(fieldName.equals(fieldInsn.get(j).getName())
                && !fieldInsn.get(j).getOwnerMethod().equals(INIT)) {

                String fieldCombination = fieldName + " " + fieldInsn.get(j).getOwnerMethod();
                    if(!hashSet.contains(fieldCombination)) {
                        hashSet.add(fieldCombination);
                        ans++;
                    }
            }
        }
        return ans;
    }

    public static String firstPrinciple(InspectedClass inspectedClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(print.CREATOR1RESULT + CREATORPRINCIPLE1 + "\n");

        HashSet<String> hashSet = new HashSet<>();
//        System.out.println("creatorPrincipleList: " + creatorPrincipleList.size()  + ", search class: " + className + ", " + numberOfMethods);

        for (int i = 0; i < creatorPrincipleList.size(); i++) {
//            System.out.println("creatorPrincipleList: " + creatorPrincipleList.get(i).getName() + ", inspectedClass.getName(): " + inspectedClass.getName());
            if (creatorPrincipleList.get(i).getName().equals(inspectedClass.getFullName())) {
            //System.out.println("creatorPrincipleList: " + creatorPrincipleList.get(i).getName() + ", inspectedClass.getName(): " + inspectedClass.getFullName());

//            if (creatorPrincipleList.get(i).getName().replaceFirst(".*/", "").equals(inspectedClass.getName())) {
//                System.out.println("FOUND class");
                CreatorPrinciple principle = creatorPrincipleList.get(i);

                List<CreatorPrincipleField> fieldInsn = principle.getFieldInsnList();
                for(int y = 0; y < fieldInsn.size(); y++) {
//                    System.out.println("--- " + fieldInsn.get(y).getName() + " " + fieldInsn.get(y).getOwnerMethod() + " " + fieldInsn.get(y).getFieldReturnType());
                    if(fieldInsn.get(y).getOwnerMethod().equals("<init>")
                            && !fieldInsn.get(y).getFieldReturnType().equals("I")
                            && !fieldInsn.get(y).getFieldReturnType().equals("D")
                            && !fieldInsn.get(y).getFieldReturnType().equals("L")
                            && !fieldInsn.get(y).getFieldReturnType().equals("S")
                            && !fieldInsn.get(y).getFieldReturnType().equals("Z")
                            && !fieldInsn.get(y).getFieldReturnType().equals("Ljava/lang/String;")
                            && !fieldInsn.get(y).getFieldReturnType().equals("Ljava/io/PrintStream;")
                            && !fieldInsn.get(y).getFieldReturnType().equals("Ljava/util/Random;")
                            && !fieldInsn.get(y).getFieldReturnType().equals("Ljava/util/List;")
                            && !fieldInsn.get(y).getFieldReturnType().equals("[Ljava/lang/String;")
                            && !fieldInsn.get(y).getFieldReturnType().equals("[D")
                            && !fieldInsn.get(y).getFieldReturnType().equals("[I")


                    ) {
                        if(!hashSet.contains(fieldInsn.get(y).getName() + " " + fieldInsn.get(y).getOwnerMethod())) {
                            if(inspectedClass.getAmountOfMethods() == 0) {
                                sb.append(print.CREATOR1RESULT + inspectedClass.getName() + " has no methods\n");
                            } else if(inspectedClass.getAmountOfMethods() == case3(fieldInsn.get(y).getName(), fieldInsn)) {
                                sb.append(print.CREATOR1RESULT + inspectedClass.getName() + " creates the object "
                                        + fieldInsn.get(y).getName()
                                        + " in the constructor and uses it in ("
                                        + case3(fieldInsn.get(y).getName(), fieldInsn)
                                        + "/" + inspectedClass.getAmountOfMethods() + ") methods, Principle full filled\n");
                            } else {
                                sb.append(print.CREATOR1RESULT + inspectedClass.getName() + " creates the object "
                                        + fieldInsn.get(y).getName()
                                        + " in the constructor and uses it in ("
                                        + case3(fieldInsn.get(y).getName(), fieldInsn)
                                        + "/" + inspectedClass.getAmountOfMethods() + ") methods, Principle violated\n");
                            }
                        }
                        hashSet.add(fieldInsn.get(y).getName() + " " + fieldInsn.get(y).getOwnerMethod());
                    }
                }
            }
        }
        // repair ausbessern
        if(sb.length() == 0) {
            sb.append(print.CREATOR1RESULT + inspectedClass.getName() + " doesnt creates any object\n");
        }
        return sb.toString();
    }

    public static String firstPrinciple(String className, int numberOfMethods) {
        StringBuilder sb = new StringBuilder();
        sb.append(print.CREATOR1RESULT + CREATORPRINCIPLE1 + "\n");

        HashSet<String> hashSet = new HashSet<>();
//        System.out.println("creatorPrincipleList: " + creatorPrincipleList.size()  + ", search class: " + className + ", " + numberOfMethods);

        for (int i = 0; i < creatorPrincipleList.size(); i++) {
//            System.out.println("creatorPrincipleList: " + creatorPrincipleList.get(i) + ", search class: " + className);
            if (creatorPrincipleList.get(i).getName().replaceFirst(".*/", "").equals(className)) {
//                System.out.println("FOUND class");
                CreatorPrinciple principle = creatorPrincipleList.get(i);

                List<CreatorPrincipleField> fieldInsn = principle.getFieldInsnList();
                for(int y = 0; y < fieldInsn.size(); y++) {
//                    System.out.println("--- " + fieldInsn.get(y).getName() + " " + fieldInsn.get(y).getOwnerMethod() + " " + fieldInsn.get(y).getFieldReturnType());
                    if(fieldInsn.get(y).getOwnerMethod().equals("<init>")
                        && !fieldInsn.get(y).getFieldReturnType().equals("I")
                        && !fieldInsn.get(y).getFieldReturnType().equals("D")
                        && !fieldInsn.get(y).getFieldReturnType().equals("L")
                        && !fieldInsn.get(y).getFieldReturnType().equals("S")
                        && !fieldInsn.get(y).getFieldReturnType().equals("Z")
                        && !fieldInsn.get(y).getFieldReturnType().equals("Ljava/lang/String;")
                        && !fieldInsn.get(y).getFieldReturnType().equals("Ljava/io/PrintStream;")
                        && !fieldInsn.get(y).getFieldReturnType().equals("Ljava/util/Random;")
                        && !fieldInsn.get(y).getFieldReturnType().equals("Ljava/util/List;")
                            && !fieldInsn.get(y).getFieldReturnType().equals("[Ljava/lang/String;")
                            && !fieldInsn.get(y).getFieldReturnType().equals("[D")
                            && !fieldInsn.get(y).getFieldReturnType().equals("[I")


                    ) {
                        if(!hashSet.contains(fieldInsn.get(y).getName() + " " + fieldInsn.get(y).getOwnerMethod())) {
                            if(numberOfMethods == 0) {
                                sb.append(print.CREATOR1RESULT + className.replaceFirst(".*/", "") + " hat keine Methoden\n");
                            } else if(numberOfMethods == case3(fieldInsn.get(y).getName(), fieldInsn)) {
                                sb.append(print.CREATOR1RESULT + className.replaceFirst(".*/", "") + " erstellt das Objekt "
                                        + fieldInsn.get(y).getName()
                                        + " im Konstruktor und verwendet es in ("
                                        + case3(fieldInsn.get(y).getName(), fieldInsn)
                                        + "/" + numberOfMethods + ") Methoden, Prinzip wird erfüllt\n");
                            } else {
                                sb.append(print.CREATOR1RESULT + className.replaceFirst(".*/", "") + " erstellt das Objekt "
                                        + fieldInsn.get(y).getName()
                                        + " im Konstruktor und verwendet es in ("
                                        + case3(fieldInsn.get(y).getName(), fieldInsn)
                                        + "/" + numberOfMethods + ") Methoden, Prinzip wird verletzt\n");
                            }
                        }
                        hashSet.add(fieldInsn.get(y).getName() + " " + fieldInsn.get(y).getOwnerMethod());
                    }
                }
            }
        }
        if(sb.length() == 0) {
            sb.append(print.CREATOR1RESULT + className.replaceFirst(".*/", "") + " erstellt keine Objekte im Konstruktor");
        }
        return sb.toString();
    }

    public static String thirdPrinciple(InspectedClass inspectedClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(print.CREATOR3RESULT + CREATORPRINCIPLE3 + "\n");

        if(inspectedClass.getAmountOfMethods() == 0) {
            return print.CREATOR3RESULT + inspectedClass.getName() + " doesnt own any methods\n";
//            return className.replaceFirst(".*/", "") + " doesnt owns any methods\n";
        }

        for (int i = 0; i < creatorPrincipleList.size(); i++) {
            if (creatorPrincipleList.get(i).getName().equals(inspectedClass.getFullName())) {

                CreatorPrinciple principle = creatorPrincipleList.get(i);

                List<CreatorPrincipleField> fields = principle.getFieldList();
                List<CreatorPrincipleField> fieldsInsn = principle.getFieldInsnList();

                boolean found = false;

                if(fields.size() == 0) {
                    sb.append(print.CREATOR3RESULT + inspectedClass.getName() + " - no fields\n");
                }

                for (int j = 0; j < fields.size(); j++) {
                    CreatorPrincipleField field = fields.get(j);

                    // TODO, die Signature ist null
                    /*
                        FIELDINSN:
	                    name: CreatorPrincipleField{opcode=181, access=-1, name='events', modifier='null', type='null', operation='null', owner='com/home/creator/BRecordsA/goodExample/EventLog', fieldReturnType='Ljava/util/List;', ownerMethod='<init>', ownerMethodReturnType='()V', signature='null'}
	                    name: CreatorPrincipleField{opcode=180, access=-1, name='events', modifier='null', type='null', operation='null', owner='com/home/creator/BRecordsA/goodExample/EventLog', fieldReturnType='Ljava/util/List;', ownerMethod='addEvent', ownerMethodReturnType='(Lcom/home/creator/BRecordsA/goodExample/Event;)V', signature='null'}
	                    name: CreatorPrincipleField{opcode=180, access=-1, name='events', modifier='null', type='null', operation='null', owner='com/home/creator/BRecordsA/goodExample/EventLog', fieldReturnType='Ljava/util/List;', ownerMethod='printLog', ownerMethodReturnType='()V', signature='null'}
	                    name: CreatorPrincipleField{opcode=178, access=-1, name='out', modifier='null', type='null', operation='null', owner='java/lang/System', fieldReturnType='Ljava/io/PrintStream;', ownerMethod='printLog', ownerMethodReturnType='()V', signature='null'}

                    * */

//                    System.out.println("777: " + fields.get(j).toString());
                    if (fields.get(j).getSignature() != null) {
                        String signature = fields.get(j).getSignature().replace(ARRAYLIST, "").replace(";>;", "");
//                        System.out.println("555: " + field.toString());
                        if (!fields.get(j).getSignature().equals("I")
                                && !signature.equals("D")
                                && !signature.equals("L")
                                && !signature.equals("S")
                                && !signature.equals("Z")
                                && !signature.equals("Ljava/lang/String;")
                                && !signature.equals("Ljava/io/PrintStream;")
                                && !signature.equals("Ljava/util/Random;")
                                && !signature.equals("Ljava/util/List;")
                                && !signature.equals("[Ljava/lang/String;")
                                && !signature.equals("Ljava/lang/Integer")
                                && !signature.equals("[D")
                                && !signature.equals("[I")
//                                && !fields.get(j).getSignature().equals("D")
//                                && !fields.get(j).getSignature().equals("L")
//                                && !fields.get(j).getSignature().equals("S")
//                                && !fields.get(j).getSignature().equals("Z")
//                                && !fields.get(j).getSignature().equals("Ljava/lang/String;")
//                                && !fields.get(j).getSignature().equals("Ljava/io/PrintStream;")
//                                && !fields.get(j).getSignature().equals("Ljava/util/Random;")
//                                && !fields.get(j).getSignature().equals("Ljava/util/List;")
//                                && !fields.get(j).getSignature().equals("[Ljava/lang/String;")
//                                && !fields.get(j).getSignature().equals("[D")
//                                && !fields.get(j).getSignature().equals("[I")
                        ) {
//                            System.out.println(signature);
//                            System.out.println(fields.get(j).getSignature());
//                            System.out.println("999");
                            if(countArrayList(fields.get(j).getName(), fields, fieldsInsn) == 0) {
                                sb.append(print.CREATOR3RESULT + inspectedClass.getName()
                                        + " - List " + fields.get(j).getName() + " is unused\n");
                                found = true;
                            } else if(countArrayList(fields.get(j).getName(), fields, fieldsInsn) == inspectedClass.getAmountOfMethods()) {
                                sb.append(print.CREATOR3RESULT + inspectedClass.getName()
                                        + " - List "
                                        + fields.get(j).getName()
                                        + " of the Type "
                                        + fields.get(j).getSignature().replace(ARRAYLIST, "").replace(";>;", "")
                                        + " initialalized in the Constructor and used in ("
                                        + countArrayList(fields.get(j).getName(), fields, fieldsInsn)
                                        + "/" + inspectedClass.getAmountOfMethods() + ") methods, Principle full filled\n");
                                found = true;
                            } else {
                                sb.append(print.CREATOR3RESULT + inspectedClass.getName()
                                        + " - List "
                                        + fields.get(j).getName()
                                        + " of the Type "
                                        + fields.get(j).getSignature().replace(ARRAYLIST, "").replace(";>;", "")
                                        + " initialazied in the Constructor and used in ("
                                        + countArrayList(fields.get(j).getName(), fields, fieldsInsn)
                                        + "/" + inspectedClass.getAmountOfMethods() + ") methods, Principle violated\n"); // TODO full filled?
                                found = true;
                            }
                        } else {
                            sb.append(print.CREATOR3RESULT + inspectedClass.getName()
                                    + " - List " + fields.get(j).getName() + " doesnt contain any Objects \n");
                            found = true;
                        }
                    }

                    if(!found) {
                        sb.append(print.CREATOR3RESULT + inspectedClass.getName() + " - no Lists\n");
                    }

                }
            }
        }
        return sb.toString();
    }

    public static String thirdPrinciple(String className, int numberOfMethods) {
        StringBuilder sb = new StringBuilder();

        if(numberOfMethods == 0) {
            return className.replaceFirst(".*/", "") + " doesnt owns any methods\n";
        }

        for (int i = 0; i < creatorPrincipleList.size(); i++) {
            if (creatorPrincipleList.get(i).getName().equals(className)) {

                CreatorPrinciple principle = creatorPrincipleList.get(i);

                List<CreatorPrincipleField> fields = principle.getFieldList();
                List<CreatorPrincipleField> fieldsInsn = principle.getFieldInsnList();



                for (int j = 0; j < fields.size(); j++) {
                    CreatorPrincipleField field = fields.get(j);

                    // TODO, die Signature ist null
                    /*
                        FIELDINSN:
	                    name: CreatorPrincipleField{opcode=181, access=-1, name='events', modifier='null', type='null', operation='null', owner='com/home/creator/BRecordsA/goodExample/EventLog', fieldReturnType='Ljava/util/List;', ownerMethod='<init>', ownerMethodReturnType='()V', signature='null'}
	                    name: CreatorPrincipleField{opcode=180, access=-1, name='events', modifier='null', type='null', operation='null', owner='com/home/creator/BRecordsA/goodExample/EventLog', fieldReturnType='Ljava/util/List;', ownerMethod='addEvent', ownerMethodReturnType='(Lcom/home/creator/BRecordsA/goodExample/Event;)V', signature='null'}
	                    name: CreatorPrincipleField{opcode=180, access=-1, name='events', modifier='null', type='null', operation='null', owner='com/home/creator/BRecordsA/goodExample/EventLog', fieldReturnType='Ljava/util/List;', ownerMethod='printLog', ownerMethodReturnType='()V', signature='null'}
	                    name: CreatorPrincipleField{opcode=178, access=-1, name='out', modifier='null', type='null', operation='null', owner='java/lang/System', fieldReturnType='Ljava/io/PrintStream;', ownerMethod='printLog', ownerMethodReturnType='()V', signature='null'}

                    * */

//                    System.out.println("777: " + fields.get(j).toString());
                    if (fields.get(j).getSignature() != null) {
                        String signature = fields.get(j).getSignature().replace(ARRAYLIST, "").replace(";>;", "");
//                        System.out.println("555: " + field.toString());
                        if (!fields.get(j).getSignature().equals("I")
                                && !signature.equals("D")
                                && !signature.equals("L")
                                && !signature.equals("S")
                                && !signature.equals("Z")
                                && !signature.equals("Ljava/lang/String;")
                                && !signature.equals("Ljava/io/PrintStream;")
                                && !signature.equals("Ljava/util/Random;")
                                && !signature.equals("Ljava/util/List;")
                                && !signature.equals("[Ljava/lang/String;")
                                && !signature.equals("Ljava/lang/Integer")
                                && !signature.equals("[D")
                                && !signature.equals("[I")
//                                && !fields.get(j).getSignature().equals("D")
//                                && !fields.get(j).getSignature().equals("L")
//                                && !fields.get(j).getSignature().equals("S")
//                                && !fields.get(j).getSignature().equals("Z")
//                                && !fields.get(j).getSignature().equals("Ljava/lang/String;")
//                                && !fields.get(j).getSignature().equals("Ljava/io/PrintStream;")
//                                && !fields.get(j).getSignature().equals("Ljava/util/Random;")
//                                && !fields.get(j).getSignature().equals("Ljava/util/List;")
//                                && !fields.get(j).getSignature().equals("[Ljava/lang/String;")
//                                && !fields.get(j).getSignature().equals("[D")
//                                && !fields.get(j).getSignature().equals("[I")
                        ) {
//                            System.out.println(signature);
//                            System.out.println(fields.get(j).getSignature());
//                            System.out.println("999");
                            if(countArrayList(fields.get(j).getName(), fields, fieldsInsn) == 0) {
                                sb.append(className.replaceFirst(".*/", "")
                                        + " doesnt use the List " + fields.get(j).getName() + "\n");
                            } /* else if(numberOfMethods == 0) {
                                sb.append(className.replaceFirst(".*./", "")
                                        + " has no Functions\n");
                            } */ else if(countArrayList(fields.get(j).getName(), fields, fieldsInsn) == numberOfMethods) {
                                sb.append(className.replaceFirst(".*/", "")
                                        + " hat eine Liste "
                                        + fields.get(j).getName()
                                        + " vom Typ "
                                        + fields.get(j).getSignature().replace(ARRAYLIST, "").replace(";>;", "")
                                        + " im Konstruktor und verwendet die Liste in ("
                                        + countArrayList(fields.get(j).getName(), fields, fieldsInsn)
                                        + "/" + numberOfMethods + ") Methoden, Prinzip wird hier erfüllt\n");
                            } else {
                                sb.append(className.replaceFirst(".*/", "")
                                        + " hat eine Liste "
                                        + fields.get(j).getName()
                                        + " vom Typ "
                                        + fields.get(j).getSignature().replace(ARRAYLIST, "").replace(";>;", "")
                                        + " im Konstruktor und verwendet die Liste in ("
                                        + countArrayList(fields.get(j).getName(), fields, fieldsInsn)
                                        + "/" + numberOfMethods + ") Methoden\n");
                            }
                        } else {
                            sb.append(className.replaceFirst(".*/", "")
                                    + " Lists " + fields.get(j).getName() + " doesnt contain any Objects \n");
                        }
                    }

//                    if (field.getSignature() != null) {
//                        String signature = field.getSignature().replace(ARRAYLIST, "").replace(";>;", "");
////                        System.out.println("555: " + field.toString());
//                        if (!signature.equals("I")
//                                && !signature.equals("D")
//                                && !signature.equals("L")
//                                && !signature.equals("S")
//                                && !signature.equals("Z")
//                                && !signature.equals("Ljava/lang/String;")
//                                && !signature.equals("Ljava/io/PrintStream;")
//                                && !signature.equals("Ljava/util/Random;")
//                                && !signature.equals("Ljava/util/List;")
//                                && !signature.equals("[Ljava/lang/String;")
//                                && !signature.equals("[D")
//                                && !signature.equals("[I")
//                        ) {
//                            System.out.println("444: " );
//                            sb.append(className.replaceFirst(".*/", "")
//                                    + " hat eine Liste "
//                                    + field.getName()
//                                    + " vom Typ "
//                                    + field.getFieldReturnType()
//                                    + " im Konstruktor und verwendet die Liste in "
//                                    + countArrayList(field.getName(), fields)
//                                    + " von " + numberOfMethods + " Methoden\n");
////                            System.out.println("---: " + field.toString());
////                            System.out.println("SIGNATURE: " + field.getSignature());
////                            System.out.println("SIGNATURE: " + field.getSignature().replace(ARRAYLIST, "").replace(";>;", ""));
//
//                        }
//                    }
                }
            }
        }
        return sb.toString();
    }

    public static boolean constructorInitialized(String className) {
        System.out.println("CONSTRUCTORINITIALIZED - className: " + className);
        for (int i = 0; i < creatorPrincipleList.size(); i++) {
            if (creatorPrincipleList.get(i).getName().equals(className)) {
                CreatorPrinciple principle = creatorPrincipleList.get(i);

                List<CreatorPrincipleField> fields = principle.getFieldList();
                List<CreatorPrincipleField> fieldInsn = principle.getFieldInsnList();
                List<Model> models = principle.getModelList();

                List<CreatorPrincipleMethod> functions = principle.getFunctionList();

                for (int j = 0; j < fields.size(); j++) {
                    CreatorPrincipleField field = fields.get(j);

                    for (int k = 0; k < functions.size(); k++) {
                        CreatorPrincipleMethod function = functions.get(k);
                        List<CreatorPrincipleMethod> subMethods = function.getSubMethodList();

                        for (int m = 0; m < subMethods.size(); m++) {
                            CreatorPrincipleMethod subMethod = subMethods.get(m);

                            if (field.getType() != null) {

                                if (field.getType().replaceFirst("L", "")
                                        .replace(";", "").equals(subMethod.getType())
                                        && subMethod.getName().equals("<init>")
                                        && function.getName().equals("<init>")) {
                                    System.out.println(true + " " + className.replaceFirst(".*/", "") + " erstellt ein Objekt vom Typ "
                                            + field.getType().replaceFirst(".*/", "").replace(";", "")
                                            + " mit dem Namen " + field.getName() + ""
                                            + " innerhalb des " + function.getName() + " Constructors");
//                                    return true;
                                    System.out.println("FIELD: " + field.toString());
                                    System.out.println("SUBMETHOD: " + subMethod.toString());
                                    System.out.println("FUNCTION: " + function.toString());
//                                System.out.println("FIELD: " + field.getName() + " " + field.getOwner() + " " + field.getOperation() + " " + field.getType() + " " );
//                                System.out.println("SUBMETHOD: " + subMethod.getName() + " " + subMethod.getOperation() + " " + subMethod.getType() + " " );
//                                System.out.println("FUNCTION: " + function.getName() + " " + function.getOperation() + " " );
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void checkFirstPrinciple(String className) {
        System.out.println("className: " + className);
        for (int i = 0; i < creatorPrincipleList.size(); i++) {
            if(creatorPrincipleList.get(i).getName().equals(className)) {


                CreatorPrinciple principle = creatorPrincipleList.get(i);

                List<CreatorPrincipleField> fields =  principle.getFieldList();
                List<CreatorPrincipleField> fieldInsn =  principle.getFieldInsnList();
                List<Model> models =  principle.getModelList();

                System.out.println();
                System.out.print("FIELDINSN:\n" );
                for(int y = 0; y < fieldInsn.size(); y++) {
                    System.out.println("\tname: " + fieldInsn.get(y).getName() + ", owner: " + fieldInsn.get(y).getOwner() + ", operation: " + fieldInsn.get(y).getOperation());
                }
                System.out.println();

                System.out.print("FIELD:\n");
                for(int y = 0; y < fields.size(); y++) {
                    System.out.println("\tname: "  + fields.get(y).getName() + ", owner: " + fields.get(y).getOwner() + ", operation: " + fields.get(y).getOperation());
                }
                System.out.println();

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

    public static String singleResponsibilityPrinciple(InspectedClass inspectedClass) {
        StringBuilder sb = new StringBuilder();
        HashSet<String> hashSet = new HashSet<>();
        boolean createsObject = false;

        sb.append("Srp Result: SINGLE RESPONSIBILITY PRINCIPLE\n");
        sb.append("Srp Result:> " + inspectedClass.getName() + " ");
        sb.append("has " + inspectedClass.getAmountOfMethods() + " methods");
        sb.append(", fanout = " + inspectedClass.getFanout());
        sb.append(", wmc = " + inspectedClass.getWmc());
        sb.append(", yalcom = " + String.format("%,.2f", inspectedClass.getYalcom()));
        sb.append(", LCOM4 = " + inspectedClass.getLcom4());
        sb.append("\n");

        for (int i = 0; i < creatorPrincipleList.size(); i++) {
            if (creatorPrincipleList.get(i).getName().replaceFirst(".*/", "").equals(inspectedClass.getName())) {
                CreatorPrinciple principle = creatorPrincipleList.get(i);

                List<CreatorPrincipleField> fieldInsn = principle.getFieldInsnList();
                for(int y = 0; y < fieldInsn.size(); y++) {
                    if(fieldInsn.get(y).getOwnerMethod().equals("<init>")
                            && !fieldInsn.get(y).getFieldReturnType().equals("I")
                            && !fieldInsn.get(y).getFieldReturnType().equals("D")
                            && !fieldInsn.get(y).getFieldReturnType().equals("L")
                            && !fieldInsn.get(y).getFieldReturnType().equals("S")
                            && !fieldInsn.get(y).getFieldReturnType().equals("Z")
//                            && !fieldInsn.get(y).getFieldReturnType().equals("Ljava/lang/String;")
//                            && !fieldInsn.get(y).getFieldReturnType().equals("Ljava/io/PrintStream;")
//                            && !fieldInsn.get(y).getFieldReturnType().equals("Ljava/util/Random;")
//                            && !fieldInsn.get(y).getFieldReturnType().equals("Ljava/util/List;")
//                            && !fieldInsn.get(y).getFieldReturnType().equals("[Ljava/lang/String;")
                            && !fieldInsn.get(y).getFieldReturnType().equals("[D")
                            && !fieldInsn.get(y).getFieldReturnType().equals("[I")
                    ) {
                        if(!hashSet.contains(fieldInsn.get(y).getName() + " " + fieldInsn.get(y).getOwnerMethod())) {
                            if(inspectedClass.getAmountOfMethods() == 0) {
                                sb.append("Srp Result:> " + inspectedClass.getName() + " has no methods\n");
                            } else if(inspectedClass.getAmountOfMethods() == case3(fieldInsn.get(y).getName(), fieldInsn)) {
                                sb.append("Srp Result:> " + inspectedClass.getName() + " creates the object "
                                        + fieldInsn.get(y).getName()
                                        + " in the constructor and uses " + fieldInsn.get(y).getName() + " in ("
                                        + case3(fieldInsn.get(y).getName(), fieldInsn)
                                        + "/" + inspectedClass.getAmountOfMethods() + ") methods, Principle full filled\n");
                                createsObject = true;
                            } else {
                                sb.append("Srp Result:> " + inspectedClass.getName() + " creates the object "
                                        + fieldInsn.get(y).getName()
                                        + " in the constructor and uses " + fieldInsn.get(y).getName() + " in ("
                                        + case3(fieldInsn.get(y).getName(), fieldInsn)
                                        + "/" + inspectedClass.getAmountOfMethods() + ") methods, Principle violated\n");
                                createsObject = true;
                            }
                        }
                        hashSet.add(fieldInsn.get(y).getName() + " " + fieldInsn.get(y).getOwnerMethod());
                    }
                }
            }
        }

        if(!createsObject) {
            sb.append("Srp Result:> " + inspectedClass.getName() + " doesnt creates any objects in the constructor\n");
            return sb.toString();
        } else {
//            sb.append(inspectedClass.getName() + " ");
//            sb.append("has " + inspectedClass.getAmountOfMethods() + " methods");
//            sb.append(", fanout = " + inspectedClass.getFanout());
//            sb.append(", wmc = " + inspectedClass.getWmc());
//            sb.append(", yalcom = " + String.format("%,.2f", inspectedClass.getYalcom()));
//            sb.append(", LCOM4 = " + inspectedClass.getLcom4());
//            sb.append("\n");
        }

        if(inspectedClass.getAmountOfMethods() > 5 && inspectedClass.getLcom4() > 2) {
            sb.append("Srp Result:> " + inspectedClass.getName() + " - " + inspectedClass.getAmountOfMethods() + " methods and they arent cohesive\n");
        }
        if(inspectedClass.getFanout() > 3) {
            sb.append("Srp Result:> " + inspectedClass.getName() + " - Fanout is greater than 3, should be lower\n");
        }
        if(inspectedClass.getWmc() > (inspectedClass.getAmountOfConstructors() + inspectedClass.getAmountOfMethods()) * 2) {
            sb.append("Srp Result:> " + inspectedClass.getName() + " - Wmc is very high, functions should be less complex\n");
        }
        if(inspectedClass.getLcom4() > 2) {
            sb.append("Srp Result:> " + inspectedClass.getName() + " - LCOM4 is very high, refactor the cohesion\n");
        }
        if(inspectedClass.getYalcom() > 0.5) {
            sb.append("Srp Result:> " + inspectedClass.getName() + " - Yalcom is very high, refactor the cohesion\n");
        }
        if(sb.length() == 0) {
            sb.append("Srp Result:> " + inspectedClass.getName() + " doesnt creates any objects in the constructor\n");
        }
        return sb.toString();
    }

}
