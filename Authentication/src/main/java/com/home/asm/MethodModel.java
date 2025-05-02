package com.home.asm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO DEPRECATED wenn diese Klass gelöscht wird auch die SubField.java und SubMethod.java Klasse löschen
public class MethodModel {

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

    public MethodModel(int access, String name, String descriptor, String signature, String[] exceptions) {
        this.access = access;
        this.name = name;
        this.descriptor = descriptor;
        this.signature = signature;
        this.exceptions = exceptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public List<String> getConstructorParameters() {
        return constructorParameters;
    }

    public void setConstructorParameters(List<String> constructorParameters) {
        this.constructorParameters = constructorParameters;
    }

    public List<SubField> getFields() {
        return fields;
    }

    public void addFields(SubField subField) {
        fields.add(subField);
    }

    public List<SubMethod> getSubMethods() {
        return methods;
    }

    public void addSubMethods(SubMethod subMethod) {
        methods.add(subMethod);
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean anInterface) {
        isInterface = anInterface;
    }

    public int getOpcode() {
        return opcode;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String[] getExceptions() {
        return exceptions;
    }

    public void setExceptions(String[] exceptions) {
        this.exceptions = exceptions;
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
