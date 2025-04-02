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

    public void addFieldToList(int opcode, String name, String description) {
        CreatorPrincipleField cpf = new CreatorPrincipleField(opcode, name, description);
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

    @Override
    public String toString() {
        return "CreatorPrincipleMethod{" +
                "name='" + name + '\'' +
                ", modifier='" + modifier + '\'' +
                ", type='" + type + '\'' +
                ", operation='" + operation + '\'' +
                '}';
    }
}
