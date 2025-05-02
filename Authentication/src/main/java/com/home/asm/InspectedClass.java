package com.home.asm;

import lcom.sourceModel.SM_Field;
import lcom.sourceModel.SM_Method;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.awt.*;
import java.util.*;
import java.util.List;

public class InspectedClass {

    private String name;
    private String fullName;
    private int dit;
    private int wmc;
    private int fanIn;
    private int fanOut;
    private int numberOfFields;
    private int numberOfMethods;
    private int numberOfConstructors;
    private int numberOfChildren;
    private int numberOfDirectConnections;
    private int numberOfIndirectConnections;
    private int numberOfPossibleConnections;
    private double yalcom;
    private double lcom1;
    private double lcom2;
    private double lcom3;
    private double lcom4;
    private double lcom5;
    private boolean isInterface = false;
    //private List<MethodModel> methods = new ArrayList<>();
    private List<String> interfaceMethodList = new ArrayList<>();
    private List<MethodDeclaration> methodList = new ArrayList<>();
    private List<MethodDeclaration> constructorList = new ArrayList<>();
    private List<MethodInformation> methodInformationList = new ArrayList<>();
    private Set<String> allInitFields = new HashSet<>();
    private Set<String> allFieldsWithinMethods = new HashSet<>();
    private Set<InspectedClass> orchestratorList = new HashSet<>();

    private int counter;
    // private List<Model> modelList = new ArrayList<>();
    private List<String> parameterList = new ArrayList<>();
    private List<SM_Field> fieldListToControl = new ArrayList<>();
    private List<SM_Method> methodListToControl = new ArrayList<>();
    private List<InspectedField> fieldList = new ArrayList<>();
    private List<InspectedField> fieldInsnList = new ArrayList<>();
    private List<InspectedMethod> inspectedMethodList = new ArrayList<>();

    private Color color;
    private int bodyLengthLessThanOneHundred;
    private int unsupportedExceptions;
    private int returnsNull;

    public InspectedClass(String name) {
//        System.out.println("InspectedClass: " + name);
        this.name = name;
    }

    public InspectedClass(String name, String fullName, int numberOfChildren,
                          int dit, boolean isInterface, int fanIn, int fanOut, int wmc,
                          int numberOfMethods, int numberOfConstructors, int numberOfFields,
                          Set<String> allInitFields, Set<String> allFieldsWithinMethods) {
        this.name = name;
        this.fullName = fullName;
        this.numberOfChildren = numberOfChildren;
        this.dit = dit;
        this.isInterface = isInterface;
        this.fanIn = fanIn;
        this.fanOut = fanOut;
        this.wmc = wmc;
        this.numberOfMethods = numberOfMethods;
        this.numberOfConstructors = numberOfConstructors;
        this.numberOfFields = numberOfFields;
        this.allInitFields = allInitFields;
        this.allFieldsWithinMethods = allFieldsWithinMethods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getWmc() {
        return wmc;
    }

    public void setWmc(int wmc) {
        this.wmc = wmc;
    }

    public int getFanIn() {
        return fanIn;
    }

    public void setFanIn(int fanIn) {
        this.fanIn = fanIn;
    }

    public int getFanOut() {
        return fanOut;
    }

    public void setFanOut(int fanOut) {
        this.fanOut = fanOut;
    }

    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    public void setNumberOfMethods(int numberOfMethods) {
        this.numberOfMethods = numberOfMethods;
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

    public void setNumberOfFields(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    public int getNumberOfConstructors() {
        return numberOfConstructors;
    }

    public void setNumberOfConstructors(int numberOfConstructors) {
        this.numberOfConstructors = numberOfConstructors;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public int getDit() {
        return dit;
    }

    public void setDit(int dit) {
        this.dit = dit;
    }

    public void setYalcom(double yalcom) {
        this.yalcom = yalcom;
    }

    public double getYalcom() {
        return yalcom;
    }

    public void setLcom1(double lcom1) {
        this.lcom1 = lcom1;
    }

    public double getLcom1() {
        return lcom1;
    }

    public void setLcom2(double lcom2) {
        this.lcom2 = lcom2;
    }

    public double getLcom2() {
        return lcom2;
    }

    public void setLcom3(double lcom3) {
        this.lcom3 = lcom3;
    }

    public double getLcom3() {
        return lcom3;
    }

    public void setLcom4(double lcom4) {
        this.lcom4 = lcom4;
    }

    public double getLcom4() {
        return lcom4;
    }

    public void setLcom5(double lcom5) {
        this.lcom5 = lcom5;
    }

    public double getLcom5() {
        return lcom5;
    }

    public void setNumberOfPossibleConnections(int value) {
        this.numberOfPossibleConnections = value;
    }

    public int getNumberOfPossibleConnections() {
        return numberOfPossibleConnections;
    }

    public void setNumberOfDirectConnections(int value) {
        this.numberOfDirectConnections = value;
    }

    public int getNumberOfDirectConnections() {
        return numberOfDirectConnections;
    }

    public void setNumberOfIndirectConnections(int value) {
        this.numberOfIndirectConnections = value;
    }

    public int getNumberOfIndirectConnections() {
        return numberOfIndirectConnections;
    }

    //public void setMethods(List<MethodModel> methodList) {
    //    methods = methodList;
    //}

    public void setIsInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }

    public boolean getIsInterface() {
        return isInterface;
    }

    public void addMethodInformation(MethodInformation methodInformation) {
        methodInformationList.add(methodInformation);
    }

    public List<MethodInformation> getMethodInformationList() {
        return methodInformationList;
    }

    public int getMethodInformationListSize() {
        return methodInformationList.size();
    }

    public void addConstructor(MethodDeclaration method) {
        constructorList.add(method);
    }

    public List<MethodDeclaration> getConstructorList() {
        return constructorList;
    }

    public void addMethod(MethodDeclaration method) {
        methodList.add(method);
    }

    public List<MethodDeclaration> getMethodList() {
        return methodList;
    }

    public void setInterfaceMethodList(List<String> interfaceMethodList) {
        this.interfaceMethodList = interfaceMethodList;
    }

    public void addInterfaceMethodList(String interfaceMethod) {
        interfaceMethodList.add(interfaceMethod);
    }

    public List<String> getInterfaceMethodList() {
        return interfaceMethodList;
    }

    public void setAllInitFields(Set<String> allInitFields) {
        this.allInitFields = allInitFields;
    }

    public Set<String> getAllInitFields() {
        return allInitFields;
    }

    public void setAllFieldsWithinMethods(Set<String> allFieldsWithinMethods) {
        this.allFieldsWithinMethods = allFieldsWithinMethods;
    }

    public Set<String> getAllFieldsWithinMethods() {
        return allFieldsWithinMethods;
    }

    public void addOrchestrator(InspectedClass inspectedClass) {
        orchestratorList.add(inspectedClass);
    }

    public boolean orchestratorListContains(InspectedClass inspectedClass) {
        if(orchestratorList.contains(inspectedClass)) {
            return true;
        }
        return false;
    }

    public boolean orchestratorListContainsFullName(String className) {
//        System.out.println("InspectedClass1: "+ className);
        Iterator<InspectedClass> itr = orchestratorList.iterator();
        while(itr.hasNext()) {
            if(itr.next().getName().equalsIgnoreCase(className)) {
//                System.out.println("InspectedClass2: "+ className);

                return true;
            }
        }
        return false;
    }

    /*
    public void addToModelList(Model model) {
        modelList.add(model);
    }

    public List<Model> getModelList() {
        return modelList;
    }

    public void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }

    */

    public void setFieldList(List<String> fieldList) {
//        this.fieldList = fieldList;
    }

    public void addToFieldList(InspectedField cpf) {
        fieldList.add(cpf);
    }

    public List<InspectedField> getFieldList() {
        return fieldList;
    }

    public void addToFieldInsnList(InspectedField cpf) {
        fieldInsnList.add(cpf);
    }

    public List<InspectedField> getFieldInsnList() {
        return fieldInsnList;
    }

//    public void addToFieldList(InspectedField field) {
//        if(!fieldListContains(field)) {
//            fieldList.add(field);
//        }

//        fieldList.add(field);
//    }

    public void setFunctionList(List<InspectedMethod> functionList) {
        inspectedMethodList = functionList;
    }

    public List<InspectedMethod> getFunctionList() {
        return inspectedMethodList;
    }

    public int getFunctionListSize() {
        return inspectedMethodList.size();
    }

    public void addFunctionToList(InspectedField cpf) {
//        if(!methodListContains(function)) {
//            methodList.add(function);
//        }

//        if(!fieldList.contains(cpf)) {
        fieldList.add(cpf);
//        }
    }

    public void setParameterList(List<String> parameterList) {
        this.parameterList = parameterList;
    }

    public List<String> getParameterList() {
        return parameterList;
    }

    public int getParameterListSize() {
        return parameterList.size();
    }

    public void addParameterToList(String parameter) {
        if(!parameterList.contains(parameter)) {
            this.parameterList.add(parameter);
        }
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public void increaseCount() {
        this.counter++;
    }

    public void setFieldListToControl(List<SM_Field> getFieldList) {
        if(fieldListToControl != getFieldList) {
            fieldListToControl = getFieldList;
        }
    }

    public List<SM_Field> getFieldListToControl() {
        return fieldListToControl;
    }

    public void setMethodListToControl(List<SM_Method> getMethodList) {
        if(methodListToControl != getMethodList) {
            methodListToControl = getMethodList;
        }
    }

    public List<SM_Method> getMethodListToControl() {
        return methodListToControl;
    }

    private boolean inspectedMethodListContains(String value) {
        for(int i = 0; i < inspectedMethodList.size(); i++) {
            if(inspectedMethodList.get(i).getName().equals(value)) {
                return true;
            }
        }
        return false;
    }

    private boolean fieldListContains(String value) {
        for(int i = 0; i < fieldList.size(); i++) {
            if(fieldList.get(i).equals(value)) {
                return true;
            }
        }
        return false;
    }

    public void addInspectedMethodToList(InspectedMethod inspectedMethod) {
        if(!inspectedMethodListContains(inspectedMethod.getName())) {
            inspectedMethodList.add(inspectedMethod);
        }
    }

    public String getMethodInformation(String methodName) {
        for(int i = 0; i < methodInformationList.size(); i++) {
            if(methodInformationList.get(i).getName().equals(methodName)) {
                return methodInformationList.get(i).getName();
                //System.out.println("getMethodInformation(): " + methodInformationList.get(i).getName());
            }
        }
        return null;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setBodyLengthLessThanOneHundred(int counter) {
        this.bodyLengthLessThanOneHundred = counter;
    }

    public int getBodyLengthLessThanOneHundred() {
        return bodyLengthLessThanOneHundred;
    }

    public void setReturnsNull(int counter) {
        this.returnsNull = counter;
    }

    public int getReturnsNull() {
        return returnsNull;
    }

    public void setUnsupportedExceptions(int counter) {
        this.unsupportedExceptions = counter;
    }

    public int getUnsupportedExceptions() {
        return unsupportedExceptions;
    }

    @Override
    public String toString() {
        return "InspectedClass{" +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dit=" + dit +
                ", wmc=" + wmc +
                ", fanIn=" + fanIn +
                ", fanOut=" + fanOut +
                ", numberOfFields=" + numberOfFields +
                ", numberOfMethods=" + numberOfMethods +
                ", numberOfConstructors=" + numberOfConstructors +
                ", numberOfChildren=" + numberOfChildren +
                ", numberOfDirectConnections=" + numberOfDirectConnections +
                ", numberOfIndirectConnections=" + numberOfIndirectConnections +
                ", numberOfPossibleConnections=" + numberOfPossibleConnections +
                ", yalcom=" + yalcom +
                ", lcom1=" + lcom1 +
                ", lcom2=" + lcom2 +
                ", lcom3=" + lcom3 +
                ", lcom4=" + lcom4 +
                ", lcom5=" + lcom5 +
                ", isInterface=" + isInterface +
                ", interfaceMethodList=" + interfaceMethodList +
                ", methodList=" + methodList +
                ", constructorList=" + constructorList +
                ", methodInformationList=" + methodInformationList +
                ", allInitFields=" + allInitFields +
                ", allFieldsWithinMethods=" + allFieldsWithinMethods +
                ", orchestratorList=" + orchestratorList +
                ", counter=" + counter +
                ", parameterList=" + parameterList +
                ", fieldListToControl=" + fieldListToControl +
                ", methodListToControl=" + methodListToControl +
                ", fieldList=" + fieldList +
                ", fieldInsnList=" + fieldInsnList +
                ", inspectedMethodList=" + inspectedMethodList +
                '}';
    }

    public String metricToString() {
        return "Metrics of: " + name + "\n" +
                "\twmc=\t\t\t\t\t" + wmc + '\n' +
                "\tfanIn=\t\t\t\t\t" + fanIn + '\n' +
                "\tfanOut=\t\t\t\t\t" + fanOut + '\n' +
                "\tnumberOfMethods=\t\t" + numberOfMethods + "\n" +
                "\tnumberOfFields=\t\t\t" + numberOfFields + "\n" +
                "\tnumberOfConstructors=\t" + numberOfConstructors + '\n' +
                "\tnumberOfChildren=\t\t" + numberOfChildren + '\n' +
                "\tdit=\t\t\t\t\t" + dit + '\n' +
                "\tLCOM4=\t\t\t\t\t" + lcom4 + '\n' +
                "\tYALCOM=\t\t\t\t\t" + yalcom + '\n'
                ;
    }

    public String creatorPrincpleToString() {
        return "CreatorPrinciple{" + "\n" +
                "\tname=" + name +  "\n" +
                "\tfieldList=" + fieldList.stream().map(InspectedField::getName).toList() +  "\n" +
                "\tfieldList.size=" + fieldList.size() +  "\n" +
                "\tmethodList=" + inspectedMethodList.stream().map(com.home.asm.InspectedMethod::getName).toList() +  "\n" +
//                "\tmethodList=" + methodList.stream().map(String::toString).toList() +  "\n" +
                "\tmethodList.size=" + inspectedMethodList.size() +  "\n" +
                "\tfieldListToControl=" + fieldListToControl.stream().map(SM_Field::getName).toList() +  "\n" +
                "\tfieldListToControl.size=" + fieldListToControl.size() +  "\n" +
                "\tmethodListToControl=" + methodListToControl.stream().map(SM_Method::getName).toList() +  "\n" +
                "\tmethodListToControl.size=" + methodListToControl.size() +  "\n" +
//                "\tfunctionList=" + functionList.stream().map(String::toString).toList() +  "\n" +
//                "\tfunctionList.size=" + functionList.size() + " \n" +
                "\tparameterList=" + parameterList.stream().map(String::toString).toList() +  "\n" +
                "\tcounter=" + counter + "\n" +
                //"\tmodel=" + modelList.stream().map(Model::getName).toList() + "\n" +
                '}';
    }

    public String fieldInformation() {
        return "fieldInformation: " +
                "name='" + name + "\n" +
                "\t\t\t\t\tnumberOfFields=" + numberOfFields + "\n" +
                "\t\t\t\t\tallInitFields=" + allInitFields.stream().map(String::toString).toList() + "\n" +
                "\t\t\t\t\tallFieldsWithinMethods=" + allFieldsWithinMethods.stream().map(String::toString).toList() + "\n" +
                "\t\t\t\t\tfieldListToControl=" + fieldListToControl.stream().map(SM_Field::toString).toList() + "\n" +
                "\t\t\t\t\tfieldList=" + fieldList.stream().map(InspectedField::toString).toList() + "\n" +
                "\t\t\t\t\tfieldInsnList=" + fieldInsnList.stream().map(InspectedField::toString).toList() + "\n"                ;
    }

    public String methodInformation() {
        return "methodInformation: " +
                "name='" + name + "\n" +
                "\t\t\t\t\tnumberOfMethods=" + numberOfMethods + "\n" +
                "\t\t\t\t\tnumberOfConstructors=" + numberOfConstructors + "\n" +
                "\t\t\t\t\tinterfaceMethodList=" + interfaceMethodList.stream().map(String::toString).toList() + "\n" +
                "\t\t\t\t\tmethodList=" + methodList.stream().map(MethodDeclaration::toString).toList() + "\n" +
                "\t\t\t\t\tconstructorList=" + constructorList.stream().map(MethodDeclaration::toString).toList() + "\n" +
                "\t\t\t\t\tmethodInformationList=" + methodInformationList.stream().map(MethodInformation::toString).toList() + "\n" +
                "\t\t\t\t\tmethodListToControl=" + methodListToControl.stream().map(SM_Method::toString).toList() + "\n" +
                "\t\t\t\t\tinspectedMethodList=" + inspectedMethodList.stream().map(InspectedMethod::toString).toList() + "\n"
                ;
    }
}
