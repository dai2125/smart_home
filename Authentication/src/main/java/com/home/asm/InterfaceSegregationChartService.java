package com.home.asm;

import org.eclipse.jdt.core.dom.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InterfaceSegregationChartService {

    public static void start(List<InspectedClass> inspectedClassList) {
        for(int i = 0; i < inspectedClassList.size(); i++) {

        }

    }

    public static InspectedClass start(InspectedClass inspectedClass, String className, String filePath) {

        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
            return analyzeClassFields(inspectedClass, className, sourceCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InspectedClass analyzeClassFields(InspectedClass inspectedClass, String className, String sourceCode) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(sourceCode.toCharArray());
        parser.setResolveBindings(true);

        int[] returnsNull = {0};
        int[] unsupportedExceptions = {0};
        int[] bodyLenghtLessThanOneHundred = {0};
        int[] override = {0};
        boolean[] fullFilled = {true};

        CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

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


        for(int i = 0; i < inspectedClass.getMethodList().size(); i++) {
            List<IExtendedModifier> modifiers = inspectedClass.getMethodList().get(i).modifiers();
            for (IExtendedModifier modifier : modifiers) {
                if (modifier.isAnnotation()) {
                    Annotation annotation = (Annotation) modifier;
                    if (!annotation.getTypeName().getFullyQualifiedName().equals("Override") || annotation.getTypeName().getFullyQualifiedName().equals("@Override")) {
                        override[0]++;
                        fullFilled[0] = false;
                    }
                }
            }

            if(!inspectedClass.getIsInterface()) {

                for (int j = 0; j < inspectedClass.getMethodList().get(i).getBody().statements().size(); j++) {
                    if (inspectedClass.getMethodList().get(i).getBody().statements().get(j).toString().contains("UnsupportedOperationException")) {
                        fullFilled[0] = false;
                    }

                    if (inspectedClass.getMethodList().get(i).getBody().getLength() < 100 && inspectedClass.getMethodList().get(i).getBody().statements().get(j).toString().contains("return null;")) {
                        fullFilled[0] = false;
                    }

                    if (inspectedClass.getMethodList().get(i).getBody().getLength() > 100) {
//                        System.out.println("BodyLength: " + inspectedClass.getMethodList().get(i).getBody().getLength());
//                        System.out.println(inspectedClass.getMethodList().get(i).getBody());
//                        System.out.println("BodyLength: " + inspectedClass.getMethodList().get(i).getBody().getLength());

                        bodyLenghtLessThanOneHundred[0]++;
                    }
                    if (inspectedClass.getMethodList().get(i).getBody().statements().get(j).toString().contains("return null;")) {
                        returnsNull[0]++;
                    }
                    if (inspectedClass.getMethodList().get(i).getBody().statements().get(j).toString().contains("UnsupportedOperationException")) {
                        unsupportedExceptions[0]++;
                    }
                }
            }

            inspectedClass.setBodyLengthLessThanOneHundred(bodyLenghtLessThanOneHundred[0]);
            inspectedClass.setReturnsNull(returnsNull[0]);
            inspectedClass.setUnsupportedExceptions(unsupportedExceptions[0]);
        }

        return inspectedClass;
    }
}
