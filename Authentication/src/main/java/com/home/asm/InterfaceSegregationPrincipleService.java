package com.home.asm;

// TODO DEPREACTED
public class InterfaceSegregationPrincipleService {

    public static String interfaceSegregationPrinciple(InspectedClass inspectedClass) {
        if(inspectedClass.getIsInterface()) {
            return "ERROR " + inspectedClass.getName() + " is an Interface";
        }
        if(inspectedClass.getDit() == 1) {
            return "ERROR " + inspectedClass.getName() + " doesnt inherits from another class";
        }

//        System.out.println("inspectedClass.getMethodListSize(): " +  inspectedClass.getMethodListSize());
        System.out.println("inspectedClass: " + inspectedClass.toString());

        for(int i = 0; i < inspectedClass.getMethodList().size(); i++) {
            for(int j = 0; j < inspectedClass.getMethodList().get(i).thrownExceptionTypes().size(); j++) {
                if(checkUnsupportedOperationException(inspectedClass.getMethodList().get(i).thrownExceptionTypes().get(j).toString())) {
                    return "ERROR " + inspectedClass.getName() + " contains an UnsupportedOperationException. Interface Segregation Principle most likely not correct" ;
                }
            }
        }

//        for(int i = 0; i < inspectedClass.getMethodList().size(); i++) {
//            for(int j = 0; j < inspectedClass.getMethodList().get(i).getReturnStatements().size(); j++) {
//                if(checkReturnNull(inspectedClass.getMethodList().get(i).getReturnStatements().get(j).getExpression().toString())
//                        && checkReturnStatementSize(inspectedClass.getMethodList().get(i).getReturnStatements().size())
//                        && checkLinesOfCode(inspectedClass.getMethodList().get(i).getNumOfLines())) {
//                    return "ERROR " + inspectedClass.getName() + " has only " + inspectedClass.getMethodList().get(i).getReturnStatements().size() + " and the RETURN value is null. Interface Segregation Principle most likely not correct" ;
//                }
//            }
//        }

        return inspectedClass.getName() + " fullfills the Interface Segregation Principle";
    }

    private static boolean checkUnsupportedOperationException(String method) {
        if(method.startsWith("new UnsupportedOperationException")) {
            return true;
        }
        return false;

//        for(int i = 0; i < inspectedClass.getMethodListSize(); i++) {
//            for(int j = 0; j < inspectedClass.getMethodList().get(i).getThrowStatements().size(); j++) {
////                ThrowStatement throwStatement = inspectedClass.getMethodList().get(i).getThrowStatements().get(j);
////                Expression expression = throwStatement.getExpression();
//
//                if(inspectedClass.getMethodList().get(i).getThrowStatements().get(j).getExpression().toString().startsWith("new UnsupportedOperationException")) {
//                    return true;
////                    System.out.println("RR " + inspectedClass.getMethodList().get(i).getThrowStatements().get(j));
//                }
////                if(expression instanceof ClassInstanceCreation) {
////                    ClassInstanceCreation creation = (ClassInstanceCreation) expression;
////                    if(creation.getType().toString().equals("UnsupportedOperationException")) {
////                        System.out.println("GGGGGGGGGGGGGGGGGGG " + inspectedClass.getMethodList().get(i).getThrowStatements().get(j));
////                    }
////                    System.out.println("FOUND : " + inspectedClass.getMethodList().get(i).getReturnStatements().get(j));
////                }
//            }
//        }
//        return false;
    }

    private static boolean checkReturnNull(String method) {
        if(method.equals("null") ) {
            return true;
//                    System.out.println("FOUND : " + inspectedClass.getMethodList().get(i).getReturnStatements().get(j));
        }
        return false;
//        return false;
//
//        for(int i = 0; i < inspectedClass.getMethodListSize(); i++) {
//            for(int j = 0; j < inspectedClass.getMethodList().get(i).getReturnStatements().size(); j++) {
////                System.out.println("EEEE: " + inspectedClass.getMethodList().get(i).getReturnStatements().get(j).getExpression().resolveUnboxing());
//                if(inspectedClass.getMethodList().get(i).getReturnStatements().get(j).getExpression().toString().equals("null") ) {
//                    return true;
////                    System.out.println("FOUND : " + inspectedClass.getMethodList().get(i).getReturnStatements().get(j));
//                }
//            }
//        }
//        return false;
    }

    private static boolean checkLinesOfCode(int numOfLines) {
        if(numOfLines < 5) {
            return true;
        }
        return false;

//        for(int i = 0; i < inspectedClass.getMethodListSize(); i++) {
////                System.out.println("EEEE: " + inspectedClass.getMethodList().get(i).getReturnStatements().get(j).getExpression().resolveUnboxing());
////                if(inspectedClass.getMethodList().get(i).getNumOfLines() < 100 ) {
//                    System.out.println(inspectedClass.getName() + ": " + inspectedClass.getMethodList().get(i).getName() + " " + inspectedClass.getMethodList().get(i).getNumOfLines());
////                    return true;
////                    System.out.println("FOUND : " + inspectedClass.getMethodList().get(i).getReturnStatements().get(j));
////            }
//        }
//        return false;
    }

    private static boolean checkReturnStatementSize(int value) {
        if(value == 1) {
            return true;
        }
        return false;
    }

    public static void deleteMeAfterwards(int value) {
        System.out.println("LiskovSubstitutionPrincipleService.deleteMeAfterwards(): " + value);
    }
}
