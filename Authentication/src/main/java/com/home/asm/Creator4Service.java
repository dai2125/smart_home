package com.home.asm;

import org.eclipse.jdt.core.dom.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Creator4Service {

    private static final String CREATORPRINCIPLE = "CREATOR PRINCIPLE 4 ";

    // TODO überprüfung ob die Funktion public ist und die Parameter von außen kommen für das Objekt

    /* AuWo Service
     *
     * Creator Principle - Class B has the initializing Data for Class A
     * analyzeClassFields() werden Objekte erstellt? Wenn diese Objekte Parameter haben, dann wird überprüft ob die Creator Klasse alle Informationen hat um das Objekt zu erstellen,
     * hat das Objekt keine Parameter kann keine Überprüfung stattfinden
     * */

    public static String start(String className, String filePath) {
//        System.out.println(className);
//        System.out.println(filePath);

        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
            return analyzeClassFields(className, sourceCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String analyzeClassFields(String className, String sourceCode) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(sourceCode.toCharArray());
        parser.setResolveBindings(false);

        int[] fieldCounter = {0};
        int[] paramCounter = {0};
        int[] objectParamCounter = {0};
        boolean[] fullFilled = {true};
        boolean[] hasObjectsWithParameters = {false};

        CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

        List<String> constructorParameters = new ArrayList<>();
        List<CreatorPrincipleMethod> methodList = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append(print.CREATOR4RESULT + CREATORPRINCIPLE + "\n");
        sb.append(className.replaceFirst(".*/", "") + "\n");

        compilationUnit.accept(new ASTVisitor() {
            @Override
            public boolean visit(MethodDeclaration node) {

                int modifiers = node.getModifiers();
                CreatorPrincipleMethod cpm = new CreatorPrincipleMethod(node.isConstructor(), node.getName().getIdentifier(), modifiers);
//                System.out.println("modifier: " + modifiers + ", node: " + node.getName());
//                System.out.println("visit Method: " + node.isConstructor() + " " + node.getName());
                if(node.isConstructor()) {
//                    System.out.println("constructor found " + node.getName());
//                    System.out.println(node.parameters().size());

                    for(Object param : node.parameters()) {
                        if(param instanceof SingleVariableDeclaration) {
                            SingleVariableDeclaration variable = (SingleVariableDeclaration) param;
                            String paramType = variable.getType().toString();
                            String paramName = variable.getName().getIdentifier();
                            constructorParameters.add(/* paramType + " " + */ paramName);

                            cpm.addToParameterFieldList(paramType, paramName);

//                            System.out.println("paramType: " + paramType + ", paramName: " + paramName);

                        }
                    }
                } else if(!node.isConstructor()) {
                    for(int i = 0; i < node.parameters().size(); i++) {
                        if(node.parameters().get(i) instanceof SingleVariableDeclaration) {
                            SingleVariableDeclaration variable = (SingleVariableDeclaration) node.parameters().get(i);
                            String paramType = variable.getType().toString();
                            String paramName = variable.getName().getIdentifier();

                            cpm.addToParameterFieldList(paramType, paramName);
                        }
                    }
                }
                methodList.add(cpm);
                return super.visit(node);
            }
        });

        List<String> fieldNames = new ArrayList<>();
        compilationUnit.accept(new ASTVisitor() {
            @Override
            public boolean visit(FieldDeclaration node) {
                for(Object object : node.fragments()) {
                    if(object instanceof VariableDeclarationFragment) {
                        String fieldName = ((VariableDeclarationFragment) object).getName().getIdentifier();
                        fieldNames.add(fieldName);
                    }
                }
                return super.visit(node);
            }
        });

        compilationUnit.accept(new ASTVisitor() {

            String methodName = "<init>";
            boolean methodIsPrivate = false;
            boolean methodIsConstructor = false;

            @Override
            public boolean visit(MethodDeclaration node) {
                methodIsConstructor = node.isConstructor();
                methodIsPrivate = false;

                if(!node.isConstructor()) {
                    methodName = node.getName().getIdentifier();
                } else if(node.isConstructor()) {
                    methodName = node.getName().getIdentifier();
                }

                for(Object obj : node.modifiers()) {
                    if(obj instanceof Modifier) {
                        Modifier modifier = (Modifier) obj;
                        if(modifier.getKeyword() == Modifier.ModifierKeyword.PRIVATE_KEYWORD) {
                            methodIsPrivate = true;
//                            System.out.println(node.getName() + " is private: " + methodIsPrivate);
                        }
                    }
                }
                return super.visit(node);
            }

            @Override
            public boolean visit(ClassInstanceCreation node) {
//                System.out.println("visit: " + node.getType().toString());
//                parser.setResolveBindings(true);
                ITypeBinding binding = node.getType().resolveBinding();


                if (binding != null) {
                    String qualifiedName = binding.getQualifiedName();
                    System.out.println("... " + qualifiedName);
                    // Beispiel: StringBuilder, Random
                    if (qualifiedName.equals("java.lang.StringBuilder") ||
                            qualifiedName.equals("java.util.Random")) {
                        System.out.println("Instance of Java standard type: " + qualifiedName);
                    }
                } else {
//                    System.out.println(node.getType());
                }

                fieldCounter[0] = 0;
                paramCounter[0] = 0;
                if(true) {
//                    System.out.println(paramCounter+ " " + fieldCounter + " " + localCounter);
                    List<?> arguments = node.arguments();
                    objectParamCounter[0] = node.arguments().size();
                    if(node.arguments().size() > 0) {
                        hasObjectsWithParameters[0] = true;
                    }

                    if(arguments.isEmpty()) {
//                        sb.append(node.getType().toString() + " has no parameters\n");
                    } else if(methodName.equals("<init>")) {
                        for(int m = 0; m < arguments.size(); m++) {
                            if(arguments.get(m) instanceof SimpleName) {
                                String argumentName = ((SimpleName) arguments.get(m)).getIdentifier();
                                for(int n = 0; n < fieldNames.size(); n++) {
                                    if(fieldNames.get(n).equals(argumentName)) {
                                        fieldCounter[0]++;
                                    }
                                }
                            }
                        }

                        if(fieldCounter[0] == arguments.size()) {
                            sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "") + " - " + node.getType().toString()
                                    + " in the function "
                                    + methodName
                                    + " got ("
                                    + fieldCounter[0]
                                    + "/"
                                    + arguments.size()
                                    + ") of their parameters are from the <init> Fields\n");
                        } else if(fieldCounter[0] == 0) {
                            sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "") + " - " + node.getType().toString()
                                    + " in the function "
                                    + methodName
                                    + " ("
                                    + fieldCounter[0]
                                    + "/"
                                    + arguments.size()
                                    + ") parameters are from the "
                                    + " are from the <init> Fields"
                                    + " so ("
                                    + (arguments.size() - fieldCounter[0])
                                    + "/"
                                    + arguments.size()
                                    + ") of the Parameters must be local\n");
                        } else {
                            sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "") + " - " + node.getType().toString()
                                    + " only ("
                                    + fieldCounter[0]
                                    + "/"
                                    + arguments.size()
                                    + ") are from the fields and (" +
                                    + (arguments.size() - fieldCounter[0])
                                    + "/"
                                    + arguments.size()
                                    + ") are from LOCAL Fields\n");
                        }
                    } else {
                        for(int j = 0; j < methodList.size(); j++) {
                            if(methodName.equals(methodList.get(j).getName()) && methodList.get(j).isConstructor()) {
                            if(methodList.get(j).getParameterList().isEmpty()) {
                                for(int i = 0; i < arguments.size(); i++) {
                                    if(arguments.get(i) instanceof SimpleName) {
                                        String argumentName = ((SimpleName) arguments.get(i)).getIdentifier();

                                        for(int k = 0; k < fieldNames.size(); k++) {
                                            if(fieldNames.get(k).equals(argumentName)) {
//                                                    System.out.println(";;; " + argumentName + ", " + fieldNames.get(k));
                                                fieldCounter[0]++;
                                            }
                                        }
                                    }
                                }
                                sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "") + " - " + node.getType().toString()
                                        + " in the constructor "
                                        + methodName
                                        + " ("
                                        + paramCounter[0]
                                        + "/"
                                        + arguments.size()
                                        + ") parameters are from the constructor parameters " //\n");
                                        + ", and ("
                                        + fieldCounter[0]
                                        + "/"
                                        + arguments.size()
                                        + ") are from the <init> Fields"
                                        + " so ("
                                        + (arguments.size() - fieldCounter[0] - paramCounter[0])
                                        + "/"
                                        + arguments.size()
                                        + ") of the Parameters must be local\n");
                            } else if(!methodList.get(j).getParameterList().isEmpty()) {
//                                            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
//                                            sb.append("you where here\n");
//                                            System.out.println("methodName: "
//                                                                + methodName
//                                                                + "=" + methodList.get(j).getName());

                                    for(int k = 0; k < methodList.get(j).getParameterList().size(); k++) {

                                        for(int m = 0; m < arguments.size(); m++) {
                                            if(arguments.get(m) instanceof SimpleName) {
                                                String argumentName = ((SimpleName) arguments.get(m)).getIdentifier();
                                                //                                                        System.out.println("...." + argumentName);
                                                if(methodList.get(j).getParameterList().get(k).name().equals(argumentName)) {
//                                                    System.out.println("...."
//                                                            + methodList.get(j).getParameterList().get(k).name()
//                                                            + ", " + argumentName
//                                                            + ", " + node.getType()
//                                                            + ", " + node);
                                                    paramCounter[0]++;
                                                }
                                            }
                                        }
                                    }

                                    for(int i = 0; i < arguments.size(); i++) {
                                        if(arguments.get(i) instanceof SimpleName) {
                                            String argumentName = ((SimpleName) arguments.get(i)).getIdentifier();

                                            for(int k = 0; k < fieldNames.size(); k++) {
                                                if(fieldNames.get(k).equals(argumentName)) {
//                                                    System.out.println(";;; " + argumentName + ", " + fieldNames.get(k));
                                                    fieldCounter[0]++;
                                                }
                                            }
                                        }
                                    }

                                    if(paramCounter[0] == arguments.size()) {
//                                                System.out.println("paramCounter == arguments.size() = " + paramCounter + ":" +  arguments.size());
                                        sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "") + " - " + node.getType().toString()
                                                    + " in the constructor "
                                                    + methodName
                                                    + " ("
                                                    + paramCounter[0]
                                                    + "/"
                                                    + arguments.size()
                                                    + ") parameters are from the constructor parameters\n");
                                    } else if(paramCounter[0] == 0) {
                                        sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "") + " - " + node.getType().toString()
                                                    + " in the constructor "
                                                    + methodName
                                                    + " ("
                                                    + paramCounter[0]
                                                    + "/"
                                                    + arguments.size()
                                                    + ") parameters are from the constructor parameters " //\n");
                                                    + ", and ("
                                                    + fieldCounter[0]
                                                    + "/"
                                                    + arguments.size()
                                                    + ") are from the <init> Fields"
                                                    + " so ("
                                                    + (arguments.size() - fieldCounter[0] - paramCounter[0])
                                                    + "/"
                                                    + arguments.size()
                                                    + ") of the Parameters must be local\n");
                                    } else if(paramCounter[0] != 0 && paramCounter[0] != arguments.size()) {
                                        sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "") + " - " + node.getType().toString()
                                                + " in the constructor "
                                                + methodName
                                                + " ("
                                                + paramCounter[0]
                                                + "/"
                                                + arguments.size()
                                                + ") parameters are from the constructor parameters "// \n");

                                                + ", and ("
                                                + fieldCounter[0]
                                                + "/"
                                                + arguments.size()
                                                + ") are from the <init> Fields"
                                                + " so ("
                                                + (arguments.size() - fieldCounter[0] - paramCounter[0])
                                                + "/"
                                                + arguments.size()
                                                + ") of the Parameters must be local\n");
                                    } else {
                                        sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "") + " - " + node.getType().toString()
                                                    + " all of the ("
                                                    + paramCounter[0]
                                                    + "/"
                                                    + arguments.size()
                                                    + ") are from the constructor parameter\n");

//                                                sb.append(node.getType().toString() + " only " + paramCounter + " of a total of " + arguments.size() + " are from the constructor parameter\n");
                                    }
                                }
                            } else if(methodName.equals(methodList.get(j).getName()) && !methodList.get(j).isConstructor()) {
//                                        System.out.println("methodName: "
//                                                            + methodName
//                                                            + "="
//                                                            + methodList.get(j).getName());
                                if(!methodList.get(j).getParameterList().isEmpty()) {
                                    // TODO auch hier solltest du genauer überprüfen
                                    for(int a = 0; a < arguments.size(); a++) {
                                        for(int k = 0; k < methodList.get(j).getParameterList().size(); k++) {
                                            if(arguments.get(a) instanceof SimpleName) {
                                                String argumentName = ((SimpleName) arguments.get(a)).getIdentifier();
                                                if(methodList.get(j).getParameterList().get(k).name().equals(argumentName)) {
                                                    paramCounter[0]++;
                                                }
                                            }
                                        }
                                    }
                                }
                                if(paramCounter[0] != arguments.size()) {
                                    for(int a = 0; a < arguments.size(); a++) {
                                        for(int k = 0; k < fieldNames.size(); k++) {
                                            if(arguments.get(a) instanceof SimpleName) {
                                                String argumentName = ((SimpleName) arguments.get(a)).getIdentifier();
                                                if(fieldNames.get(k).equals(argumentName)) {
                                                    fieldCounter[0]++;
                                                }
                                            }
                                        }
                                    }
                                }

                                if(paramCounter[0] == arguments.size()) {
                                    sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "") + " - " + node.getType().toString()
                                                + " in the function "
                                                + methodName
                                                + " ("
                                                + paramCounter[0]
                                                + "/"
                                                + arguments.size()
                                                + ") of the parameters from the function "
                                                + methodName
                                                + " parameters \n");
                                } else if(fieldCounter[0] == arguments.size()) {
                                    sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "") + " - " + node.getType().toString()
                                                + " in the function "
                                                + methodName
                                                + " ("
                                                + fieldCounter[0]
                                                + "/"
                                                + arguments.size()
                                                + ") of their parameters are from the <init> Fields\n");
                                } else if(paramCounter[0] == 0 && fieldCounter[0] == 0) {
                                    sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "") + " - " + node.getType().toString()
                                                + " in the function "
                                                + methodName
                                                + " ("
                                                + paramCounter[0]
                                                + "/"
                                                + arguments.size()
                                                + ") parameters are from the "
                                                + methodName
                                                + " function parameters, and ("
                                                + fieldCounter[0]
                                                + "/"
                                                + arguments.size()
                                                + ") are from the <init> Fields"
                                                + " so ("
                                                + (arguments.size() - fieldCounter[0] - paramCounter[0])
                                                + "/"
                                                + arguments.size()
                                                + ") of the Parameters must be local\n");
                                } else {
                                    sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "") + " - " + node.getType().toString()
                                                + " in the function "
                                                + methodName
                                                + " ("
                                                + paramCounter[0]
                                                + "/"
                                                + arguments.size()
                                                + ") are from the function "
                                                + methodName
                                                + " parameter and ("
                                                + fieldCounter[0]
                                                + "/"
                                                + arguments.size()
                                                + ")"
                                                + " are from the <init> fields and (" +
                                                + (arguments.size() - paramCounter[0] - fieldCounter[0])
                                                + "/"
                                                + arguments.size()
                                                + ")"
                                                + " are from LOCAL Fields\n");
                                }
//                                        System.out.println("fieldCounter: " + fieldCounter + ":" + "paramCounter: " + paramCounter);
                            }
                        }
//                            System.out.println("\targumentName: " + argumentName);
                    }
                }

                if(objectParamCounter[0] == 0) {
//                    System.out.println(node + " Object has no parameter");
                } else if(!methodIsConstructor && methodIsPrivate) {
                    if(objectParamCounter[0] != paramCounter[0] + fieldCounter[0]) {
//                        System.out.println(methodName.toUpperCase() + " uses local fields as parameters to create " + node);
                        fullFilled[0] = false;
                    } else {
//                        System.out.println(methodName.toUpperCase() + " full fills the creator 4 principle for " + node);
                    }
                } else if(!methodIsConstructor && !methodIsPrivate) {
                    if(paramCounter[0] != 0) {
//                        System.out.println(methodName.toUpperCase() + " is public and receives parameters from outside to create " + node);
                        fullFilled[0] = false;
                    } else if(objectParamCounter[0] != paramCounter[0] + fieldCounter[0]) {
//                        System.out.println(methodName.toUpperCase() + " uses local fields as parameters for " + node);
                        fullFilled[0] = false;
                    } else {
//                        System.out.println(methodName.toUpperCase() + " full fills the creator 4 principle for " + node);
                    }
                } else if(methodIsConstructor && !methodIsPrivate) {
                    if(paramCounter[0] != 0) {
//                        System.out.println(methodName.toUpperCase() + " receives parameters from outside to create " + node);
                        fullFilled[0] = false;
                    } else if(objectParamCounter[0] != paramCounter[0] + fieldCounter[0]) {
//                        System.out.println(methodName.toUpperCase() + " uses local fields as 33333parameters for " + node);
//                        System.out.println(objectParamCounter[0] + " " + paramCounter[0] + " " + fieldCounter[0]);
                        fullFilled[0] = false;
                    } else {
//                        System.out.println(methodName.toUpperCase() + " full fills the creator 4 principle for " + node);
                    }
                }
                return super.visit(node);
            }
        });

        if(hasObjectsWithParameters[0]) { // className.replaceFirst(".*/", "") + " - " +
                sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "")
                            + " "
                            + (fullFilled[0]
                            ? " full fills the Creator 4 principle"
                            : "violates the Creator 4 principle\n"));
                            // + sb.toString()));

//                System.out.println(className.replaceFirst(".*/", "")
//                    + " "
//                    + (fullFilled[0] ? " full fills the Creator 4 principle" : "violates the Creator 4 principle\n" + sb.toString()));
        } else {
            sb.append(print.CREATOR4RESULT + className.replaceFirst(".*/", "")
                    + " has no Objects with parameters");

//            System.out.println(className.replaceFirst(".*/", "")
//                    + " has no Objects with parameters");
        }
        return sb.toString();
//        System.out.println(sb.toString());
    }
}
