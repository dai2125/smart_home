package com.home.asm;

public class CreatorPrincipleField {

    private int opcode;
    private int access = -1;
    private String name;
    private String modifier;
    private String type;
    private String operation;
    private String owner;
    private String fieldReturnType;
    private String ownerMethod;
    private String ownerMethodReturnType;
    private String signature;

    public CreatorPrincipleField() {
    }

    public CreatorPrincipleField(int opcode, String name, String type) {
        this.opcode = opcode;
        this.name = name;
        this.type = type;
    }

    public CreatorPrincipleField(int access, String name, String owner, String descriptor, String ownerMethod) {
        this.access = access;
        this.name = name;
        this.owner = owner;
        this.fieldReturnType = descriptor;
        this.ownerMethod = ownerMethod;
        if(ownerMethod.equals("<init>")) {
            this.ownerMethodReturnType = "<init>";
        }
    }

    public CreatorPrincipleField(int access, String name, String owner, String descriptor, String signature, String signature2, String ownerMethod) {
        this.access = access;
        this.name = name;
        this.owner = owner;
        this.fieldReturnType = descriptor;
        this.signature = signature;
        this.ownerMethod = ownerMethod;
        if(ownerMethod.equals("<init>")) {
            this.ownerMethodReturnType = "<init>";
        }
    }

    public CreatorPrincipleField(String name, String modifier, String type, String operation) {
        this.name = name;
        this.modifier = modifier;
        this.type = type;
        this.operation = operation;
    }

    public CreatorPrincipleField(int opcode, String name, String owner, String fieldReturnType, String ownerMethod, String ownerMethodReturnType) {
        this.opcode = opcode;
        this.name = name;
        this.owner = owner;
        this.fieldReturnType = fieldReturnType;
        this.ownerMethod = ownerMethod;
        this.ownerMethodReturnType = ownerMethodReturnType;
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

    public int getOpcode() {
        return opcode;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFieldReturnType() {
        return fieldReturnType;
    }

    public void setFieldReturnType(String fieldReturnType) {
        this.fieldReturnType = fieldReturnType;
    }

    public String getOwnerMethod() {
        return ownerMethod;
    }

    public void setOwnerMethod(String ownerMethod) {
        this.ownerMethod = ownerMethod;
    }

    public String getOwnerMethodReturnType() {
        return ownerMethodReturnType;
    }

    public void setOwnerMethodReturnType(String ownerMethodReturnType) {
        this.ownerMethodReturnType = ownerMethodReturnType;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return "CreatorPrincipleField{" +
                "opcode=" + opcode +
                ", access=" + access +
                ", name='" + name + '\'' +
                ", modifier='" + modifier + '\'' +
                ", type='" + type + '\'' +
                ", operation='" + operation + '\'' +
                ", owner='" + owner + '\'' +
                ", fieldReturnType='" + fieldReturnType + '\'' +
                ", ownerMethod='" + ownerMethod + '\'' +
                ", ownerMethodReturnType='" + ownerMethodReturnType + '\'' +
                ", signature='" + signature + '\'' +

                '}';
    }

//    @Override
//    public String toString() {
//        return "CreatorPrincipleField{" +
//                "opcode=" + opcode +
//                ", access=" + access +
//                ", name='" + name + '\'' +
//                ", modifier='" + modifier + '\'' +
//                ", type='" + type + '\'' +
//                ", operation='" + operation + '\'' +
//                ", owner='" + owner + '\'' +
//                '}';
//    }
}
