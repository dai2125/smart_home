package com.home.asm;

import org.eclipse.jdt.core.dom.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProtectedVariationService {

    /* TODO
        Solution Identify points of predicted variation or instability; assign responsibilities to
                create a stable interface around them.
                Note: The term "interface" is used in the broadest sense of an access view; it
                does not literally only mean something like a Java or COM interface.
        Problem How to design objects, subsystems, and systems so that the variations or instability
                in these elements does not have an undesirable impact on other elements?
        Identify components that are expected to change frequently and
                establish stable interfaces and contracts between components to protect them against changes.
        Ensure that changes to one component do not break other components by thorough testing and validation.
        Avoid tight coupling between components, as it can lead to a ripple effect of changes throughout the system.
        Use Anti-Corruption layers to isolate the system from external changes.

        zum analysieren verwendest du alle Felder, alle privaten Felder, alle Methoden, alle privaten Methoden,
        alle Methoden die von einem Interface stammen, den DIT, den Fanout
        vielleicht kann die Kohäsivität zwischen den Methoden noch überprüft werden
    */

    // TODO funktioniert nur für Interfaces
    // TODO ProtectedVariations - Interfaces sollen konkrekte Implementierungen verstecken, beim Wechsel wird die Implementierung geschützt

    /* AuWo
    *
    * ProtectedVariations - Interfaces sollen konkrekte Implementierungen verstecken, beim Wechsel wird die Implementierung geschützt
    * ProtectedVariations funktioniert aktuell nur für Interfaces nicht für Abstract Classes
    * überprüft DIT, Anzahl der Methoden und die Anzahl der Methoden die von Interfaces stammen.
    *
    * */
    List<MethodDeclaration> listOfAllParentMethods = new ArrayList<>();

    public static String start(InspectedClass inspectedClass, String className, String filePath) {
//        System.out.println(className);
//        System.out.println(filePath);

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

        int[] methodCounter = {0};
        int[] constructorCounter = {0};
        int[] fieldCounter = {0};
        int[] paramCounter = {0};
        int[] objectParamCounter = {0};
        boolean[] inheritate = {false};
        boolean[] fullFilled = {true};
        boolean[] hasObjectsWithParameters = {false};

        CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

        List types = compilationUnit.types();
//        for(int i = 0; i < types.size(); i++) {
//            System.out.println("types: " + types.get(i));
//        }

        List<String> constructorParameters = new ArrayList<>();
        List<ParameterField> parameterFieldList = new ArrayList<>();
        List<InspectedMethod> methodList = new ArrayList<>();

//        StringBuilder sb = new StringBuilder(className.replaceFirst(".*/", "") + "\n");

        compilationUnit.accept(new ASTVisitor() {

            @Override
            public boolean visit(TypeDeclaration node) {

                inspectedClass.setIsInterface(node.isInterface());
//                System.out.println("node.getSuperClassType: "+ node.getSuperclassType());

                if(node.getSuperclassType() != null) {

                }

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

            @Override
            public boolean visit(MethodDeclaration node) {
//                System.out.println("llllllllllllllllllllllllllll");
                IMethodBinding binding = node.resolveBinding();
                if (binding != null) {
                    IMethodBinding overridden = binding.getMethodDeclaration().getMethodDeclaration();
                    ITypeBinding declaringClass = binding.getDeclaringClass();
                    ITypeBinding superclass = declaringClass.getSuperclass();

                    while (superclass != null) {
                        for (IMethodBinding superMethod : superclass.getDeclaredMethods()) {
                            if (binding.overrides(superMethod)) {
                                System.out.println("Methode " + node.getName() + " überschreibt Methode von: " + superclass.getName());
                                break;
                            }
                        }
                        superclass = superclass.getSuperclass();
                    }
                }
                return true;
            }
        });

        StringBuilder sb = new StringBuilder(print.PROTECTEDVARIATIONSRESULT + print.PROTECTEDVARIATIONS);
        //sb.append("PROTECTED VARIATION PRINCIPLE\n");
        sb.append(print.PROTECTEDVARIATIONSRESULT + inspectedClass.getName() + " ");

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
            sb.append("\n" + print.PROTECTEDVARIATIONSRESULT + "has an DIT greater than 2 and the methods from interfaces tracking wont work");
        } else if(inspectedClass.getDit() == 2 && inspectedClass.getMethodList().size() == inspectedClass.getInterfaceMethodList().size()) {
            for(int i = 0; i < inspectedClass.getMethodList().size(); i++) {
                List<IExtendedModifier> modifiers = inspectedClass.getMethodList().get(i).modifiers();
                for (IExtendedModifier modifier : modifiers) {
                    if (modifier.isAnnotation()) {
                        Annotation annotation = (Annotation) modifier;
                        if(!annotation.getTypeName().getFullyQualifiedName().equals("Override") || annotation.getTypeName().getFullyQualifiedName().equals("@Override")) {
                            sb.append("\n" + print.PROTECTEDVARIATIONSRESULT + "Function" + inspectedClass.getMethodList().get(i).getName() + " doesnt start with an @Override, Principle violation");
                            fullFilled[0] = false;
                        }
                    }
                }
            }

            boolean found = false;
            for(int i = 0; i < inspectedClass.getMethodList().size(); i++) {

                for(int j = 0; j < inspectedClass.getInterfaceMethodList().size(); j++) {

                    if(inspectedClass.getMethodList().get(i).getName().toString().equals(inspectedClass.getInterfaceMethodList().get(j))) {
                        found = true;
                        break;
                    }
                    if(!found) {
                        fullFilled[0] = false;
                        sb.append("\n" + print.PROTECTEDVARIATIONSRESULT + "Function " + inspectedClass.getMethodList().get(i) + " not in the interface list");
                    }
                }
            }
        }
//        sb.append("\n");

        sb.append("\n" + print.PROTECTEDVARIATIONSRESULT + inspectedClass.getName() + (fullFilled[0] ? " full fills the Protected Variation Principle" : " violates the Protected Variation Principle"));

        return sb.toString();
    }
}
