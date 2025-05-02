package com.home.asm;

import org.eclipse.jdt.core.dom.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InterfaceSegregationService {

    static List<InspectedClass> inspectedClassList;

    public static String start(InspectedClass inspectedClass, String className, String filePath) {
//        System.out.println(className);
//        System.out.println(filePath);

//        System.out.println(2 + ". " + className);
//        System.out.println(3 + ". " + filePath);

        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
            return analyzeClassFields(inspectedClass, className, sourceCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String analyzeClassFields(InspectedClass inspectedClass, String className, String sourceCode) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(sourceCode.toCharArray());
        parser.setResolveBindings(true);
//        parser.setBindingsRecovery(true);

//        String jdkPath = System.getProperty("java.home");
//        String[] classpath = { jdkPath };
//
//        parser.setEnvironment(classpath, new String[] { "" }, null, true);

        int[] returnsNull = {0};
        int[] unsupportedExceptions = {0};
        int[] bodyLenghtLessThanOneHundred = {0};
        int[] paramCounter = {0};
        int[] objectParamCounter = {0};
        boolean[] inheritate = {false};
        boolean[] fullFilled = {true};
        boolean[] hasObjectsWithParameters = {false};

        CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

        List<String> constructorParameters = new ArrayList<>();
        List<ParameterField> parameterFieldList = new ArrayList<>();
        List<InspectedMethod> methodList = new ArrayList<>();

//        StringBuilder sb = new StringBuilder(className.replaceFirst(".*/", "") + "\n");

        compilationUnit.accept(new ASTVisitor() {

            @Override
            public boolean visit(TypeDeclaration node) {

                inspectedClass.setIsInterface(node.isInterface());

                for (int i = 0; i < node.getMethods().length; i++) {
                    if(node.getMethods()[i].isConstructor()) {
                        inspectedClass.addConstructor(node.getMethods()[i]);
                    } else {
                        inspectedClass.addMethod(node.getMethods()[i]);
                    }
                }

                for(int i = 0; i < node.superInterfaceTypes().size(); i++) {
//                    System.out.println("superInterfaces: " + node.superInterfaceTypes().get(i));
                }
                return super.visit(node);
            }
        });

        StringBuilder sb = new StringBuilder();
        sb.append(print.ISPRESULT + "INTERFACE SEGREGATION PRINCIPLE\n");
        sb.append(print.ISPRESULT + inspectedClass.getName() + " ");

        if(inspectedClass.getIsInterface()) {
            sb.append("is an Interface");
            return sb.toString();
        } else if(inspectedClass.getDit() == 1) {
            sb.append("doesnt inherits from another class");
            return sb.toString();
        } else if(inspectedClass.getDit() == 2 && inspectedClass.getMethodList().size() != inspectedClass.getInterfaceMethodList().size()) {
            sb.append("has ("
                        + inspectedClass.getMethodList().size()
                        + "/"
                        + inspectedClass.getInterfaceMethodList().size()
                        + ") methods not all from Interfaces");
            return sb.toString();
        } else if(inspectedClass.getDit() >= 3) {
            sb.append("\n" + print.ISPRESULT + "has an DIT greater than 2 and the methods from interfaces tracking wont work");
        }

//        sb.append("\n");

        for(int i = 0; i < inspectedClass.getMethodList().size(); i++) {
            List<IExtendedModifier> modifiers = inspectedClass.getMethodList().get(i).modifiers();
            for (IExtendedModifier modifier : modifiers) {
                if (modifier.isAnnotation()) {
                    Annotation annotation = (Annotation) modifier;
                    if (!annotation.getTypeName().getFullyQualifiedName().equals("Override") || annotation.getTypeName().getFullyQualifiedName().equals("@Override")) {
                        sb.append("\n" + print.ISPRESULT + "Function" + inspectedClass.getMethodList().get(i).getName() + " doesnt start with an @Override, Principle violation");
                        fullFilled[0] = false;
                    }
                }
            }

            for (int j = 0; j < inspectedClass.getMethodList().get(i).getBody().statements().size(); j++) {
                if (inspectedClass.getMethodList().get(i).getBody().statements().get(j).toString().contains("UnsupportedOperationException")) {
                    sb.append("\n" + print.ISPRESULT + "Function" + inspectedClass.getMethodList().get(i).getName() + " contains an UnsupportedOperationException, Principle violation");
                    fullFilled[0] = false;
                }

                if (inspectedClass.getMethodList().get(i).getBody().getLength() < 100 && inspectedClass.getMethodList().get(i).getBody().statements().get(j).toString().contains("return null;")) {
                    sb.append("\n" + print.ISPRESULT + "Function" + inspectedClass.getMethodList().get(i).getName() + " has less than 100 characters and returns null, Principle violation");
                    fullFilled[0] = false;

//                    for(MethodDeclaration val : inspectedClass.getMethodList()) {
//
//                        System.out.println(33 + " : " + val.getName().getLength() + " : " + val.getName().getIdentifier().toString() + " : " + val.getBody().getLength() + " : " + val.getBody().statements().get(j).toString());
//                        System.out.println(33 + " : " + inspectedClass.getMethodList().get(i).getBody().statements().get(j).toString());
//                        inspectedClass.getMethodList().get(i).getBody().statements(
//                    }
                }

                if (inspectedClass.getMethodList().get(i).getBody().getLength() < 100) {
                    bodyLenghtLessThanOneHundred[0]++;
                }
                if (inspectedClass.getMethodList().get(i).getBody().statements().get(j).toString().contains("return null;")) {
                    returnsNull[0]++;
                }
                if (inspectedClass.getMethodList().get(i).getBody().statements().get(j).toString().contains("UnsupportedOperationException")) {
                    unsupportedExceptions[0]++;
                }
            }

            sb.append("\n" + print.ISPRESULT + inspectedClass.getName() + (fullFilled[0] ? " full fills the Interface Segregation Principle" : " violates the Interface Segregation Principle"));

            inspectedClass.setBodyLengthLessThanOneHundred(bodyLenghtLessThanOneHundred[0]);
            inspectedClass.setReturnsNull(returnsNull[0]);
            inspectedClass.setUnsupportedExceptions(unsupportedExceptions[0]);

            inspectedClassList.add(inspectedClass);

        }

//        System.out.println("DIT: " + inspectedClass.getDit());


//        for(int i = 0; i < inspectedClass.getMethodList().size(); i++) {
//            System.out.println("method: " + inspectedClass.getMethodList().get(i));
//            for(int j = 0; j < inspectedClass.getMethodList().get(i).thrownExceptionTypes().size(); j++) {
//                System.out.println("exception: " + inspectedClass.getMethodList().get(i).thrownExceptionTypes().get(j));
//            }
//        }
//
//        for(int i = 0; i < inspectedClass.getInterfaceMethodList().size(); i++) {
//            System.out.println("interfaceMethod: " + inspectedClass.getInterfaceMethodList().get(i));
//        }


        // PrincipleChartISP principleChartISP = new PrincipleChartISP(inspectedClassList);
        return sb.toString();
    }
}