package com.home.asm;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InspectedClass {

    private String name;
    private String fullName;
    private int wmc;
    private int fanin;
    private int fanout;
    private int amountOfMethods;
    private int amountOfFields;
    private int amountOfConstructors;
    private int numberOfChildren;
    private int dit;
    private double yalcom;
    private double lcom1;
    private double lcom2;
    private double lcom3;
    private double lcom4;
    private double lcom5;
    private double lcom4v1;
    private int numberOfIndirectConnections;
    private int numberOfDirectConnections;
    private int numberOfPossibleConnections;
    private List<MethodModel> methods = new ArrayList<>();
    private List<SubField> subFields = new ArrayList<>();
    private boolean isInterface = false;
    private List<MethodInformation> methodInformationList = new ArrayList<>();
    private List<MethodDeclaration> constructorList = new ArrayList<>();
    private List<MethodDeclaration> methodList = new ArrayList<>();
    private List<String> interfaceMethodList = new ArrayList<>();
    private Set<String> allInitFields = new HashSet<>();
    private Set<String> allFieldsWithinMethods = new HashSet<>();

    public InspectedClass(String name) {
        this.name = name;
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

    public int getFanin() {
        return fanin;
    }

    public void setFanin(int fanin) {
        this.fanin = fanin;
    }

    public int getFanout() {
        return fanout;
    }

    public void setFanout(int fanout) {
        this.fanout = fanout;
    }

    public int getAmountOfMethods() {
        return amountOfMethods;
    }

    public void setAmountOfMethods(int amountOfMethods) {
        this.amountOfMethods = amountOfMethods;
    }

    public int getAmountOfFields() {
        return amountOfFields;
    }

    public void setAmountOfFields(int amountOfFields) {
        this.amountOfFields = amountOfFields;
    }

    public int getAmountOfConstructors() {
        return amountOfConstructors;
    }

    public void setAmountOfConstructors(int amountOfConstructors) {
        this.amountOfConstructors = amountOfConstructors;
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

    public void setLcom4v1(double lcom4v1) {
        this.lcom4v1 = lcom4v1;
    }

    public double getLcom4v1() {
        return lcom4v1;
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

    public void addSubMethod(MethodModel subMethod) {
        methods.add(subMethod);
    }

    public void setMethods(List<MethodModel> methodList) {
        methods = methodList;
    }

//    public List<SubMethod> getSubMethods() {
//        return subMethods;
//    }

    public void addSubField(SubField subField) {
        subFields.add(subField);
    }

    public List<SubField> getSubFields() {
        return subFields;
    }

    public void setIsInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }

    public boolean getIsInterface() {
        return isInterface;
    }

    public void addMethodInformation(MethodInformation methodInformation) {
        methodInformationList.add(methodInformation);
    }

    public List<MethodInformation> getmethodInformationList() {
        return methodInformationList;
    }

    public int getmethodInformationListSize() {
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

    @Override
    public String toString() {
        return "InspectedClass{" + "\n" +
                "\tname='" + name + '\n' +
                "\tfullName='" + fullName + '\n' +
//                ", wmc=" + wmc +
//                ", fanin=" + fanin +
//                ", fanout=" + fanout +
                "\tamountOfMethods=" + amountOfMethods + "\n" +
                "\tamountOfFields=" + amountOfFields + "\n" +
//                ", amountOfConstructors=" + amountOfConstructors +
//                ", numberOfChildren=" + numberOfChildren +
//                ", dit=" + dit +
//                ", yalcom=" + yalcom +
//                ", lcom1=" + lcom1 +
//                ", lcom2=" + lcom2 +
//                ", lcom3=" + lcom3 +
//                ", lcom4=" + lcom4 +
//                ", lcom5=" + lcom5 +
//                ", lcom4v1=" + lcom4v1 +
//                ", numberOfIndirectConnections=" + numberOfIndirectConnections +
//                ", numberOfDirectConnections=" + numberOfDirectConnections +
//                ", numberOfPossibleConnections=" + numberOfPossibleConnections +
                "\tmethods size=" + methods.size() + "\n" +
                "\tfields size=" + subFields.size() + "\n" +

                "\tmethods=" + methods.stream().map(MethodModel::toString).toList() + "\n" +
                "\tsubFields=" + subFields.stream().map(SubField::toString).toList() + "\n" +
                "\t"/*"methodInformation="*/ + methodInformationList.stream().map(MethodInformation::toString).toList() + "\n" +
                '}';
    }
}
