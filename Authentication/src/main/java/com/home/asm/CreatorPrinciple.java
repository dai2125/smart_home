package com.home.asm;

import lcom.sourceModel.SM_Field;
import lcom.sourceModel.SM_Method;

import java.util.ArrayList;
import java.util.List;

// TODO DEPRECATED
public class CreatorPrinciple {

    private String name;
    private int counter;
    private List<Model> modelList = new ArrayList<>();
    private List<String> parameterList = new ArrayList<>();
    private List<SM_Field> fieldListToControl = new ArrayList<>();
    private List<SM_Method> methodListToControl = new ArrayList<>();
    private List<InspectedField> fieldList = new ArrayList<>();
    private List<InspectedField> fieldInsnList = new ArrayList<>();
    private List<InspectedMethod> inspectedMethodList = new ArrayList<>();

    public void addToModelList(Model model) {
        modelList.add(model);
    }

    public List<Model> getModelList() {
        return modelList;
    }

    public void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }

    public CreatorPrinciple(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFieldList(List<String> fieldList) {
//        this.fieldList = fieldList;
    }

    public void addToFieldList(InspectedField inspectedField) {
        fieldList.add(inspectedField);
    }

    public List<InspectedField> getFieldList() {
        return fieldList;
    }

    public void addToFieldInsnList(InspectedField inspectedField) {
        fieldInsnList.add(inspectedField);
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

    public void addFunctionToList(InspectedField inspectedField) {
//        if(!methodListContains(function)) {
//            methodList.add(function);
//        }

//        if(!fieldList.contains(cpf)) {
            fieldList.add(inspectedField);
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

    private boolean methodListContains(String value) {
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

    public void addMethodToList(InspectedMethod inspectedMethod) {
        if(!methodListContains(inspectedMethod.getName())) {
            inspectedMethodList.add(inspectedMethod);
        }
    }

    @Override
    public String toString() {
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
                "\tmodel=" + modelList.stream().map(Model::getName).toList() + "\n" +
                '}';
    }
}
