package com.home.asm;

import java.util.List;

public class SubMethod {

    private int opcode;
    private String owner;
    private String name;
    private String descriptor;
    private List<String> constructorParameters;
    private String returnType;
    private boolean isInterface;

    public SubMethod(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        this.opcode = opcode;
        this.owner = owner;
        this.name = name;
        this.descriptor = descriptor;
//        this.constructorParameters = constructorParameters;
//        this.returnType = returnType;
        this.isInterface = isInterface;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public List<String> getConstructorParameters() {
        return constructorParameters;
    }

    public void setConstructorParameters(List<String> constructorParameters) {
        this.constructorParameters = constructorParameters;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean anInterface) {
        isInterface = anInterface;
    }

    @Override
    public String toString() {
        return "SubMethod{" +
                "opcode=" + opcode +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", descriptor='" + descriptor + '\'' +
                ", constructorParameters=" + constructorParameters +
                ", returnType='" + returnType + '\'' +
                ", isInterface=" + isInterface +
                '}';
    }
}
