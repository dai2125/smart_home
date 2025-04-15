package com.home.asm;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SingleResponsibilityPrincipleService {

    // TODO Number of Methods (NoM)
    // TODO Coupling between Objects
    // TODO LCOM
    // TODO Anzahl der direkten Feldzugriffe
    // TODO Methodennamen mit mehreren Verantwortlichkeiten
    // TODO Cyclomatic Complexity (CC)



    public static void start(InspectedClass inspectedClass, String className, String filePath) {
//        System.out.println(className);
//        System.out.println(filePath);

        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
            analyzeClassFields(inspectedClass, className, sourceCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void analyzeClassFields(InspectedClass inspectedClass, String className, String sourceCode) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(sourceCode.toCharArray());
        parser.setResolveBindings(false);
//        parser.setBindingsRecovery(true);

        List<ClassInstanceCreation> objectList = new ArrayList<>();
        int[] objectCounter = {0};
        int[] fieldCounter = {0};
        int[] paramCounter = {0};
        int[] objectParamCounter = {0};
        boolean[] fullFilled = {true};
        boolean[] hasObjectsWithParameters = {false};

        CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

        List<String> constructorParameters = new ArrayList<>();
        List<ParameterField> parameterFieldList = new ArrayList<>();
        List<CreatorPrincipleMethod> methodList = new ArrayList<>();

        StringBuilder sb = new StringBuilder(className.replaceFirst(".*/", "") + "\n");

        compilationUnit.accept(new ASTVisitor() {

            @Override
            public boolean visit(VariableDeclarationFragment node) {

                if (node.getInitializer() instanceof ClassInstanceCreation) {
                    ClassInstanceCreation creation = (ClassInstanceCreation) node.getInitializer();
                    String varName = node.getName().toString();
    //            Type type = ((ClassInstanceCreation) node.getInitializer()).getType();
                    Type type = creation.getType();

                    NewObject object = new NewObject(node.getName().toString(), type);
                    System.out.println("var: " + type);
                    System.out.println("var: " + object.getName());
                    System.out.println("var: " + varName);

    //            System.out.println(object);
//                    newInstances.put(varName, type);
//                    NewObjectService.put(object);

                }
                System.out.println("VariableDeclaration: " + node);
                System.out.println("VariableDeclaration: " + node.getName().toString());
                System.out.println("VariableDeclaration: " );
                int type = node.getNodeType();
                System.out.println("VariableDeclaration: " + type);
//                System.out.println("VariableDeclaration: " + node.getInitializer().resolveTypeBinding());
//                ITypeBinding binding = node.getType
//
//                if(binding != null) {
//                    System.out.println(binding.isClass());
//                    System.out.println(binding.getName());
//                }

                return super.visit(node);
            }

            @Override
            public boolean visit(ClassInstanceCreation node) {
                System.out.println("visit: " + node);
                Type type = node.getType();

                System.out.println("type: "+ type);
                System.out.println("type: " + type.toString());
                ITypeBinding binding = node.resolveTypeBinding();
                if(binding != null) {
                    System.out.println("Instance: " + binding.getQualifiedName());
                }

                binding = node.getType().resolveBinding();
                if(binding != null) {
                    System.out.println("Instance2:  " + binding.getQualifiedName());
                }

                return true;

            }
            @Override
            public boolean visit(MethodDeclaration node) {

//                parameterFieldList.clear();

                int modifiers = node.getModifiers();
                CreatorPrincipleMethod cpm = new CreatorPrincipleMethod(node.isConstructor(), node.getName().getIdentifier(), modifiers);
//                System.out.println("modifier: " + modifiers + ", node: " + node.getName());
//                System.out.println("visit Method: " + node.isConstructor() + " " + node.getName());
                if(node.isConstructor()) {
                    System.out.println("constructor found " + node.getName());
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
            boolean methodIsPrivate = false;
            boolean methodIsConstructor = false;
//            int paramCounter = 0;
//            int fieldCounter = 0;

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
                objectCounter[0]++;
                objectList.add(node);

                return super.visit(node);
            }
        });
        // TODO Number of Methods (NoM)
        // TODO Coupling between Objects
        // TODO LCOM
        // TODO Anzahl der direkten Feldzugriffe
        // TODO Methodennamen mit mehreren Verantwortlichkeiten
        // TODO Cyclomatic Complexity (CC)
        // TODO fanout <= 2 for example
        // TODO LCOM4 == 1
        // TODO how many Objects does this class create?

        for(int i = 0; i < objectList.size(); i++) {
            System.out.println("objectList(): " + objectList.get(i));
        }



        System.out.println("SINGLE RESPONSIBILITY PRINCIPLE " + objectCounter[0]);
        System.out.println(inspectedClass.getName() + ", nom="
                            + inspectedClass.getAmountOfMethods()
                            + ", fanout=" + inspectedClass.getFanout()
//                            + ", fanin=" + inspectedClass.getFanin()
                            + ", wmc=" + inspectedClass.getWmc()
                            + ", yalcom=" + inspectedClass.getYalcom()
                            + ", LCOM4=" + inspectedClass.getLcom4()
        );

//        if(hasObjectsWithParameters[0]) {
//            System.out.println(className.replaceFirst(".*/", "")
//                    + " "
//                    + (fullFilled[0] ? " full fills the Creator 4 principle" : "violates the Creator 4 principle\n" + sb.toString()));
//        } else {
//            System.out.println(className.replaceFirst(".*/", "")
//                    + " has no Objects with parameters");
//        }


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

    public static String checkSingleResponsibilityPrinciple(InspectedClass inspectedClass) {

        if(checkIfClassIsInterface(inspectedClass)) {
            return inspectedClass.getName() + " is an Interface";
        }
        if(!checkYalcom(inspectedClass.getYalcom())) {
            return inspectedClass.getName() + " has an invalid Lcom of " + inspectedClass.getYalcom() + " and doesnt full fill the Single Responsibility Principle";
        }
        if(!checkLcom4(inspectedClass.getLcom4())) {
            return inspectedClass.getName() + " has an invalid Lcom4 of " + inspectedClass.getLcom4() + " and doesnt full fill the Single Responsibility Principle";
        }
        if(!checkFanout(inspectedClass.getFanout())) {
            return inspectedClass.getName() + " has an invalid FANOUT of " + inspectedClass.getFanout() + " and doesnt full fill the Single Responsibility Principle";
        }

        return inspectedClass.getName() + " full fills the Single Responsibility Principle";
    }

    private static boolean checkYalcom(double yalcom) {
        return yalcom <= 0.75;
    }

    private static boolean checkLcom4(double lcom4) {
        return lcom4 <= 2 && lcom4 >= 1;
    }

    private static boolean checkFanout(int fanout) {
        return fanout <= 2;
    }

    private static boolean checkIfClassIsInterface(InspectedClass inspectedClass) {
        return inspectedClass.getIsInterface();
    }
}
