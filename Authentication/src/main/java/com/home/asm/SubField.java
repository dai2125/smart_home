package com.home.asm;

public class SubField {

    private int opcode;
    private String owner;
    private String name;
    private String description;

    public SubField(int opcode, String owner, String name, String description) {
        this.opcode = opcode;
        this.owner = owner;
        this.name = name;
        this.description = description;
    }

    public int getOpcode() {
        return opcode;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "SubField{" +
                "opcode='" + opcode + '\'' +
                "owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
