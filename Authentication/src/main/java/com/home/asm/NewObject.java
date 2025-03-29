package com.home.asm;

import org.eclipse.jdt.core.dom.Type;

import java.util.ArrayList;
import java.util.List;

public class NewObject {

    private String name;
    private Type type;
    private List<String> functionList = new ArrayList<>();
    private List<String> parameterList = new ArrayList<>();
    private int counter;

    public NewObject() {
    }

    public NewObject(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String getTypeName() {
        return type.toString();
    }

    public void setFunctionList(List<String> functionList) {
        this.functionList = functionList;
    }

    public List<String> getFunctionList() {
        return functionList;
    }

    public int getFunctionListSize() {
        return functionList.size();
    }

    public void addFunctionToList(String function) {
        if(!functionList.contains(function)) {
            this.functionList.add(function);
        }
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
        if(!functionList.contains(parameter)) {
            this.functionList.add(parameter);
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

    @Override
    public String toString() {
        return "NewObject{" + "\n" +
                "\tname=" + name +  "\n" +
                "\ttype=" + type +  "\n" +
                "\tfunctionList=" + functionList.stream().map(String::toString).toList() +  "\n" +
                "\tparameterList=" + parameterList.stream().map(String::toString).toList() +  "\n" +
                "\tcounter=" + counter + "\n" +
                '}';
    }
}
