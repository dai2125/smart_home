package com.home.asm;

public class CreatorPrincipleField {

    private int opcode;
    private int access = -1;
    private String name;
    private String modifier;
    private String type;
    private String operation;
    private String owner;

    public CreatorPrincipleField() {
    }

    public CreatorPrincipleField(int opcode, String name, String type) {
        this.opcode = opcode;
        this.name = name;
        this.type = type;
    }

    public CreatorPrincipleField(String name, int access, String type) {
        this.name = name;
        this.access = access;
        this.type = type;
    }

    public CreatorPrincipleField(String name, String modifier, String type, String operation) {
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
                '}';
    }
}
