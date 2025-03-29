package com.home.asm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewMethod {

    private String name;
    private String returnType;
    private int access;
    private List<String> constructorParameters;
    private List<SubField> fields = new ArrayList<>();
    private List<SubMethod> methods = new ArrayList<>();
    private boolean isInterface;
    private int opcode;
    private String descriptor;
    private String signature;
    private String[] exceptions;
    private int counter;

    public NewMethod(String name, String returnType) {
        this.name = name;
        this.returnType = returnType;
        this.counter = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public int getAccess() {
        return access;
    }

    public void setConstructorParameters(List<String> constructorParameters) {
        this.constructorParameters = constructorParameters;
    }

    public List<String> getConstructorParameters() {
        return constructorParameters;
    }

    public void addFields(SubField subField) {
        fields.add(subField);
    }

    public List<SubField> getFields() {
        return fields;
    }

    public void addSubMethods(SubMethod subMethod) {
        methods.add(subMethod);
    }

    public List<SubMethod> getSubMethods() {
        return methods;
    }

    public void setInterface(boolean anInterface) {
        isInterface = anInterface;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    public int getOpcode() {
        return opcode;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    public void setExceptions(String[] exceptions) {
        this.exceptions = exceptions;
    }

    public String[] getExceptions() {
        return exceptions;
    }

    public void increaseCounter() {
        this.counter++;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "MethodModel{" +
                "name='" + name + '\'' +
                ", returnType='" + returnType + '\'' +
                ", access=" + access +
                ", constructorParameters=" + constructorParameters +
                ", fields size=" + fields.size() +
                ", methods size=" + methods.size() +
                ", fields=" + fields.stream().map(SubField::toString).toList() +
                ", methods=" + methods.stream().map(SubMethod::toString).toList() +
                ", isInterface=" + isInterface +
                ", opcode=" + opcode +
                ", descriptor='" + descriptor + '\'' +
                ", signature='" + signature + '\'' +
                ", exceptions=" + Arrays.toString(exceptions) +
                '}';
    }
}
