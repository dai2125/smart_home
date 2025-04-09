package com.home.asm;

import org.eclipse.jdt.core.dom.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ClassFieldTracker {
    public static void main(String[] args) throws FileNotFoundException {

//        if(args.length != 1) {
//            System.out.println("Verwendung: java ClassFieldTracker <Pfad zur Java-Datein>");
//            return;
//        }

//        String filePath = args[0];
        String filePath = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\" +
                            "com\\home\\creator\\InitializingData\\thirdExample\\ClassB.java";

        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
//            analyzeClassFields(sourceCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start(String className, String filePath) {
//        System.out.println(className);
//        System.out.println(filePath);

        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
            analyzeClassFields(className, sourceCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void analyzeClassFields(String className, String sourceCode) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(sourceCode.toCharArray());
        parser.setResolveBindings(false);

        CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

        List<String> constructorParameters = new ArrayList<>();
        List<ParameterField> parameterFieldList = new ArrayList<>();
        List<CreatorPrincipleMethod> methodList = new ArrayList<>();

        StringBuilder sb = new StringBuilder(className + "\n");

        compilationUnit.accept(new ASTVisitor() {
            @Override
            public boolean visit(MethodDeclaration node) {

//                parameterFieldList.clear();

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

//                            parameterFieldList.add(new ParameterField(paramType, paramName));
//                            System.out.println("..." + parameterFieldList.size());
//                            System.out.println(new ParameterField(paramType, paramName).type());
//                            System.out.println(new ParameterField(paramType, paramName).name());

                        }
                    }

//                    CreatorPrincipleMethod cpm = new CreatorPrincipleMethod(node.isConstructor(), node.getName().getIdentifier(), modifiers, parameterFieldList);
//                    methodList.add(cpm);

                } else if(!node.isConstructor()) {
                    for(int i = 0; i < node.parameters().size(); i++) {
                        if(node.parameters().get(i) instanceof SingleVariableDeclaration) {
                            SingleVariableDeclaration variable = (SingleVariableDeclaration) node.parameters().get(i);
                            String paramType = variable.getType().toString();
                            String paramName = variable.getName().getIdentifier();

                            cpm.addToParameterFieldList(paramType, paramName);

//                            parameterFieldList.add(new ParameterField(paramType, paramName));

                        }
                    }
//                    CreatorPrincipleMethod cpm = new CreatorPrincipleMethod(node.isConstructor(), node.getName().getIdentifier(), modifiers, parameterFieldList);
                }
                methodList.add(cpm);
//                parameterFieldList.clear();

                return super.visit(node);
            }
        });

        for(int i = 0; i < constructorParameters.size(); i++) {
//            System.out.println("constructor parameters: " + constructorParameters.get(i));
        }

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

            @Override
            public boolean visit(MethodDeclaration node) {
                if(!node.isConstructor()) {
                    methodName = node.getName().getIdentifier();
                } else if(node.isConstructor()) {
                    methodName = node.getName().getIdentifier();
                }
                return super.visit(node);
            }

            @Override
            public boolean visit(ClassInstanceCreation node) {
//                System.out.println("visit: " + node.getType().toString());
//                if(node.getType().toString().equals("ClassC")) {
                if(true) {

                    int paramCounter = 0;
                    int fieldCounter = 0;
                    int localCounter = 0;
//                    System.out.println(paramCounter+ " " + fieldCounter + " " + localCounter);
                    List<?> arguments = node.arguments();
//                    if(arguments.isEmpty()) {
//                        sb.append(node.getType().toString() + " has no parameters\n");
//                    }
//                    for(Object argument : arguments) {
////                        System.out.println("arg: "+ argument);
//                        if(argument instanceof SimpleName) {
////                            System.out.println("aarg: "+ argument);
//                            String argumentName = ((SimpleName) argument).getIdentifier();
//
//                            if(fieldNames.contains(argumentName)) {
//                                System.out.println("\tInit Feld '" + argumentName + "' wird fuer die Instanziierung von " + node.getType() + " ClassC verwendet, methodName: " + methodName);
//                                fieldCounter++;
//                            }
//                        } else {
//                            localCounter++;
//                            System.out.println("Lokal Feld '" + argument + "' wird fuer die Instanziierung von " + node.getType() + " ClassC verwendet, methodName: " + methodName);
//                        }
//                    }

                    // constructorParameters.add(paramType + " " + paramName);
//                    sb.append(methodName + " -- " + "\n");

                    if(arguments.isEmpty()) {
                        sb.append(node.getType().toString() + " has no parameters\n");
                    } else if(methodName.equals("<init>")) {
                        for(int m = 0; m < arguments.size(); m++) {
                            if(arguments.get(m) instanceof SimpleName) {
                                String argumentName = ((SimpleName) arguments.get(m)).getIdentifier();

                                for(int n = 0; n < fieldNames.size(); n++) {
                                    if(fieldNames.get(n).equals(argumentName)) {
                                        fieldCounter++;
                                    }
                                }

//                                if(fieldNames.contains(argumentName)) {
//                                    fieldCounter++;
//                                }
                            }
                        }

                        if(fieldCounter == arguments.size()) {
                            sb.append(node.getType().toString()
                                    + " in the function "
                                    + methodName
                                    + " got ("
                                    + fieldCounter
                                    + "/"
                                    + arguments.size()
                                    + ") of their parameters are from the <init> Fields\n");
                        } else if(fieldCounter == 0) {
                            sb.append(node.getType().toString()
                                    + " in the function "
                                    + methodName
                                    + " ("
                                    + fieldCounter
                                    + "/"
                                    + arguments.size()
                                    + ") parameters are from the "
                                    + " are from the <init> Fields"
                                    + " so ("
                                    + (arguments.size() - fieldCounter)
                                    + "/"
                                    + arguments.size()
                                    + ") of the Parameters must be local\n");
                        } else {
                            sb.append(node.getType().toString()
                                    + " only ("
                                    + fieldCounter
                                    + "/"
                                    + arguments.size()
                                    + ") are from the fields and (" +
                                    + (arguments.size() - fieldCounter)
                                    + "/"
                                    + arguments.size()
                                    + ") are from LOCAL Fields\n");
                        }
                    } else {
//                        for(int i = 0; i < arguments.size(); i++) {
//                            if(arguments.get(i) instanceof SimpleName) {
//                                String argumentName = ((SimpleName) arguments.get(i)).getIdentifier();

                                for(int j = 0; j < methodList.size(); j++) {
                                    if(methodName.equals(methodList.get(j).getName()) && methodList.get(j).isConstructor()) {
                                        if(!methodList.get(j).getParameterList().isEmpty()) {
//                                            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
//                                            sb.append("you where here\n");
//                                            System.out.println("methodName: "
//                                                                + methodName
//                                                                + "=" + methodList.get(j).getName());

                                            for(int k = 0; k < methodList.get(j).getParameterList().size(); k++) {
                                                // TODO diese auskommentierte schleife stimmt nicht
//                                                for(int m = 0; m < arguments.size(); m++) {
//                                                    if(methodList.get(j).getParameterList().get(k).equals(arguments.get(m))) {

                                                        paramCounter++;
//                                                    }
//                                                }
                                            }
                                            if(paramCounter == arguments.size()) {
//                                                System.out.println("paramCounter == arguments.size() = " + paramCounter + ":" +  arguments.size());

                                                sb.append(node.getType().toString()
                                                            + " in the constructor "
                                                            + methodName
                                                            + " ("
                                                            + paramCounter
                                                            + "/"
                                                            + arguments.size()
                                                            + ") parameters are from the constructor parameters\n");
                                            } else if(paramCounter == 0) {
                                                sb.append(node.getType().toString()
                                                            + " in the constructor "
                                                            + methodName
                                                            + " ("
                                                            + paramCounter
                                                            + "/"
                                                            + arguments.size()
                                                            + ") parameters are from the constructor parameters\n");
                                            } else {
                                                sb.append(node.getType().toString()
                                                            + " all of the ("
                                                            + paramCounter
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
                                            for(int k = 0; k < methodList.get(j).getParameterList().size(); k++) {
                                                paramCounter++;
                                            }
                                        }
                                        if(!fieldNames.isEmpty()) {
//                                            for(int k = 0; k < fieldNames.size(); k++) {
                                                for(int m = 0; m < arguments.size(); m++) {

                                                        if(arguments.get(m) instanceof SimpleName) {
//                                                            System.out.println("aarg: " + arguments.get(m));
                                                            String argumentName = ((SimpleName) arguments.get(m)).getIdentifier();
                                                            System.out.println("argumentname: " + argumentName);

                                                            // TODO hier könnte auch ein Fehler sein
                                                            for(int n = 0; n < fieldNames.size(); n++) {
                                                                if(fieldNames.get(n).equals(argumentName)) {
                                                                    System.out.println("argName found in fieldName.List: " + argumentName + ", " + fieldNames.get(n));
                                                                    fieldCounter++;
                                                                }
                                                            }

//                                                            if(fieldNames.contains(argumentName)) {
//                                                                fieldCounter++;
//                                                            }
                                                    }
                                                }
//                                            }
                                        }
//                                        System.out.println("paramCounter == arguments.size() = "
//                                                            + paramCounter
//                                                            + ":"
//                                                            + arguments.size()
//                                                            + ":"
//                                                            + fieldCounter);

                                        if(paramCounter == arguments.size()) {
                                            sb.append(node.getType().toString()
                                                        + " in the function "
                                                        + methodName
                                                        + " ("
                                                        + paramCounter
                                                        + "/"
                                                        + arguments.size()
                                                        + ") of the parameters from the function "
                                                        + methodName
                                                        + " parameters \n");
                                        } else if(fieldCounter == arguments.size()) {
                                            sb.append(node.getType().toString()
                                                        + " in the function "
                                                        + methodName
                                                        + " ("
                                                        + fieldCounter
                                                        + "/"
                                                        + arguments.size()
                                                        + ") of their parameters are from the <init> Fields\n");
                                        } else if(paramCounter == 0 && fieldCounter == 0) {
                                            sb.append(node.getType().toString()
                                                        + " in the function "
                                                        + methodName
                                                        + " ("
                                                        + paramCounter
                                                        + "/"
                                                        + arguments.size()
                                                        + ") parameters are from the "
                                                        + methodName
                                                        + " function parameters, and ("
                                                        + fieldCounter
                                                        + "/"
                                                        + arguments.size()
                                                        + ") are from the <init> Fields"
                                                        + " so ("
                                                        + (arguments.size() - fieldCounter - paramCounter)
                                                        + "/"
                                                        + arguments.size()
                                                        + ") of the Parameters must be local\n");
                                        } else {
                                            sb.append(node.getType().toString()
                                                        + " in the function "
                                                        + methodName
                                                        + " ("
                                                        + paramCounter
                                                        + "/"
                                                        + arguments.size()
                                                        + ") are from the function "
                                                        + methodName
                                                        + " parameter and "
                                                        + fieldCounter
                                                        + " are from the fields and " +
                                                        + (arguments.size() - paramCounter - fieldCounter)
                                                        + " are from LOCAL Fields\n");
                                        }
//                                        System.out.println("fieldCounter: " + fieldCounter + ":" + "paramCounter: " + paramCounter);
                                    }
//                                    else {
//                                        localCounter = arguments.size() - paramCounter - fieldCounter;
//                                        sb.append(node.getType().toString() + " only " + localCounter + " of a total of " + arguments.size() + " are from the " + methodName + " parameter\n");
//                                    }
                                }

//                            System.out.println("\targumentName: " + argumentName);
//                            if(constructorParameters.contains(argumentName)) {
//                                System.out.println("\tConstruct param '" + argumentName + "' wird fuer die Instanziierung von ClassC verwendet, methodName: " + methodName);
//                                paramCounter++;
//                            }
                            }
                        }
//                    }
//                }

                return super.visit(node);
            }
        });

//        System.out.println("CLASSFIELDTRACKER");
        System.out.println(sb.toString());

//        System.out.println("methodList");
//        for(int i = 0; i < methodList.size(); i++) {
//            System.out.println(methodList.get(i).initializingDataToString());
//            for(int j = 0; j < methodList.get(i).getParameterList().size(); j++) {
//                System.out.println(methodList.get(i).getParameterList().get(j));
//            }
//        }

//        System.out.println("parameterFieldList " + parameterFieldList.size());
//        for(int i = 0; i < parameterFieldList.size(); i++) {
//            System.out.println(parameterFieldList.get(i));
//        }

    }
}
