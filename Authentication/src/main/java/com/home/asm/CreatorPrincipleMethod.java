package com.home.asm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreatorPrincipleMethod {

    private int access;
    private String name;
    private String description;
    private String modifier;
    private String type;
    private String operation;
    private String owner;
    private List<CreatorPrincipleField> fieldList = new ArrayList<>();
    private List<CreatorPrincipleMethod> methodList = new ArrayList<>();

    public CreatorPrincipleMethod(int access, String name, String description) {
        this.access = access;
        this.name = name;
        this.description = description;
    }

    public CreatorPrincipleMethod(int opcode, String owner, String name, String descriptor) {
        this.access = opcode;
        this.type = owner;
        this.name = name;
        this.description = descriptor;
        this.owner = owner;
    }

    public CreatorPrincipleMethod(String name, String modifier, String type, String operation) {
        this.name = name;
        this.modifier = modifier;
        this.type = type;
        this.operation = operation;
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
        CreatorPrincipleField cpf = new CreatorPrincipleField(opcode, name, description);
        fieldList.add(cpf);
//        if(!fieldList.contains(cpf)) {
//            fieldList.add(cpf);
//        }
    }

    public void addFieldToList(int opcode, String name, String owner, String descriptor, String ownerMethod, String ownerMethodReturnType) {
        CreatorPrincipleField cpf = new CreatorPrincipleField(opcode, name, owner, descriptor, ownerMethod, ownerMethodReturnType);
        fieldList.add(cpf);
//        if(!fieldList.contains(cpf)) {
//            fieldList.add(cpf);
//        }
    }

    public void addMethodToList(int opcode, String owner, String name, String descriptor) {
        CreatorPrincipleMethod cpm = new CreatorPrincipleMethod(opcode, owner, name, descriptor);
        if(!methodList.contains(cpm)) {
            methodList.add(cpm);
        }
    }

    public List<CreatorPrincipleMethod> getSubMethodList() {
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

    public List<CreatorPrincipleField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<CreatorPrincipleField> fieldList) {
        this.fieldList = fieldList;
    }

    public List<CreatorPrincipleMethod> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<CreatorPrincipleMethod> methodList) {
        this.methodList = methodList;
    }

    @Override
    public String toString() {
        return "CreatorPrincipleMethod{" +
                "access=" + access +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", modifier='" + modifier + '\'' +
                ", type='" + type + '\'' +
                ", operation='" + operation + '\'' +
                ", owner='" + owner + '\'' +
                ", fieldList=" + fieldList.stream().map(CreatorPrincipleField::getName).toList() +
                ", methodList=" + methodList.stream().map(CreatorPrincipleMethod::getName).toList() +
                '}';
    }

//    @Override
//    public String toString() {
//        return "CreatorPrincipleMethod{" +
//                "name='" + name + '\'' +
//                ", modifier='" + modifier + '\'' +
//                ", type='" + type + '\'' +
//                ", operation='" + operation + '\'' +
//                '}';
//    }
}
