package com.home.asm;

import java.util.ArrayList;
import java.util.List;

public class InspectedMethod {

    private int access;
    private String name;
    private String description;
    private String modifier;
    private String type;
    private String operation;
    private String owner;
    // public = 1, private = 2, protected = 4
    private int modifierInt;
    private boolean isConstructor;
    private List<InspectedField> fieldList = new ArrayList<>();
    private List<InspectedMethod> methodList = new ArrayList<>();
    private List<ParameterField> parameterFieldList = new ArrayList<>();

    public InspectedMethod(int access, String name, String description) {
        this.access = access;
        this.name = name;
        this.description = description;
    }

    public InspectedMethod(int opcode, String owner, String name, String descriptor) {
        this.access = opcode;
        this.type = owner;
        this.name = name;
        this.description = descriptor;
        this.owner = owner;
    }

    public InspectedMethod(String name, String modifier, String type, String operation) {
        this.name = name;
        this.modifier = modifier;
        this.type = type;
        this.operation = operation;
    }

    public InspectedMethod(boolean isConstructor, String name, int modifierInt) {
        this.isConstructor = isConstructor;
        this.name = name;
        this.modifierInt = modifierInt;
    }

    public InspectedMethod(boolean isConstructor, String name, int modifierInt, List<ParameterField> parameterFieldList) {
        this.isConstructor = isConstructor;
        this.name = name;
        this.modifierInt = modifierInt;
        this.parameterFieldList = parameterFieldList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOwner() {
        return owner;
    }

    public void addFieldToList(int opcode, String name, String description) {
        InspectedField inspectedField = new InspectedField(opcode, name, description);
        fieldList.add(inspectedField);
//        if(!fieldList.contains(cpf)) {
//            fieldList.add(cpf);
//        }
    }

    public void addFieldToList(int opcode, String name, String owner, String descriptor, String ownerMethod, String ownerMethodReturnType) {
        InspectedField inspectedField = new InspectedField(opcode, name, owner, descriptor, ownerMethod, ownerMethodReturnType);
        fieldList.add(inspectedField);
//        if(!fieldList.contains(cpf)) {
//            fieldList.add(cpf);
//        }
    }

    public void addMethodToList(int opcode, String owner, String name, String descriptor) {
        InspectedMethod inspectedMethod = new InspectedMethod(opcode, owner, name, descriptor);
        if(!methodList.contains(inspectedMethod)) {
            methodList.add(inspectedMethod);
        }
    }

    public List<InspectedMethod> getSubMethodList() {
        return methodList;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<InspectedField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<InspectedField> fieldList) {
        this.fieldList = fieldList;
    }

    public List<InspectedMethod> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<InspectedMethod> methodList) {
        this.methodList = methodList;
    }

    public void setParameterFieldList(List<ParameterField> parameterFieldList) {
        this.parameterFieldList = parameterFieldList;
    }

    public List<ParameterField> getParameterList() {
        return parameterFieldList;
    }

    public void addToParameterFieldList(String type, String name) {
        parameterFieldList.add(new ParameterField(type, name));
    }

    public int getParameterListSize() {
        return parameterFieldList.size();
    }

    public boolean isConstructor() {
        return isConstructor;
    }

    @Override
    public String toString() {
        return "InspectedMethod{" +
                "access=" + access +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", modifier='" + modifier + '\'' +
                ", type='" + type + '\'' +
                ", operation='" + operation + '\'' +
                ", owner='" + owner + '\'' +
                ", fieldList=" + fieldList.stream().map(InspectedField::getName).toList() +
                ", methodList=" + methodList.stream().map(com.home.asm.InspectedMethod::getName).toList() +
                '}';
    }

    public String initializingDataToString() {
        return "InspectedMethod{" +
                "name='" + name + '\'' +
                ", modifier='" + modifierInt + '\'' +
                ", isConstructor='" + isConstructor + '\'' +
                ", parameterList='" + parameterFieldList.stream().map(ParameterField::name).toList() + '\'' +
                '}';
    }
}
