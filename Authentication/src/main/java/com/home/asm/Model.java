package com.home.asm;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Model {

    private String _fieldName;
    private String _name;
    private String _type;
    private boolean _usedAsFieldInClass;
    private String _clazzName;
    private String _invokeSpecial;
    private boolean constructorParameter;
    private int classCounter;
    private int amountOfFields;
    private int amountOfMethods;
    private int fieldCounter;
    private int methodCounter;
    private ArrayList<String> fields = new ArrayList();
    private ArrayList<String> methods = new ArrayList();
    private String _owner;
    private Hashtable<String, Boolean> ownerMethods = new Hashtable();
    private int ownerTotalMethods;
    private String constructorParameterName;
    private List<NewStatementModel> newStatementModels = new ArrayList();
    private String createdType;
    private String name;
    private String descriptor;
    private boolean objectCreated;
    private boolean constructorInitialized;
    private String lastNewType;
    private String ownerClass;
    private String ownerMethod;
    private String ownerMethodReturnType;

    public Model() {

    }

    public Model(String name, String descriptor, String lastNewType, String ownerClass, String ownerMethod, String ownerMethodReturnType, boolean objectCreated, boolean constructorInitialized) {
        this.name = name;
        this.descriptor = descriptor;
        this.lastNewType = lastNewType;
        this.ownerClass = ownerClass;
        this.ownerMethod = ownerMethod;
        this.ownerMethodReturnType = ownerMethodReturnType;
        this.objectCreated = objectCreated;
        this.constructorInitialized = constructorInitialized;

    }

    public void setFieldName(String fieldName) {
        _fieldName = fieldName;
    }

    public String getFieldName() {
        return _fieldName;
    }

    public void setName(String name) {
        _name = name;
//        _name = cleanUp(name);
        setClazzName(clazzCleanUp(name));
        setType(name);
//        setConstructorParameterName(name.replaceFirst("L", "").replaceAll("/", ".").replaceAll(";", ""));
    }

    public void setNameExtra(String name) {
        _name = name.replaceFirst("L", "").replaceAll(";", "");
    }

    public String getName() {
        return _name;
    }

    public void setOwnerClass(String className) {
        ownerClass = className;
    }

    public String getOwnerClass() {
        return ownerClass;
    }

    public void setType(String type) {
        _type = type;
//        _type = typeCleanUp(type);
        setClazzName(clazzCleanUp(type));
    }

    public String getType() {
        return _type;
    }

    private void setClazzName(String name) {
        _clazzName = name;
    }

    public String getClazzName() {
        return _clazzName;
    }

    public void setField(boolean field) {
        _usedAsFieldInClass = field;
    }

    public boolean getField() {
        return _usedAsFieldInClass;
    }

    public void setInvokeSpecial(String invokeSpecial) {
        _invokeSpecial = invokeSpecial;
    }

    public String getInvokeSpecial() {
        return _invokeSpecial;
    }

    public void setConstructorParameter(boolean constructorParameter) {
        this.constructorParameter = constructorParameter;
    }

    public boolean getConstructorParameter() {
        return constructorParameter;
    }

    public void setConstructorInitialized(boolean constructorInitialized) {
        this.constructorInitialized = constructorInitialized;
    }

    public boolean getConstructorInitialized() {
        return constructorInitialized;
    }

    public void setAmountOfMethods(int amountOfMethods) {
        this.amountOfMethods = amountOfMethods;
    }

    public void setClassCounter(int classCounter) {
        this.classCounter = classCounter;
    }

    public int getClassCounter() {
        return classCounter;
    }

    public void increaseFieldCounter() {
        fieldCounter++;
    }

    public int getFieldCounter() {
        return fieldCounter;
    }

    public void addField(String field) {
        fields.add(_clazzName + "." + field);
    }

    public String getField(String field) {
        for(int i = 0; i < fields.size(); i++) {
            if(field.equals(fields.get(i))) {
                return fields.get(i);
            }
        }
        return null;
    }

    public void increaseMethodCounter() {
        methodCounter++;
    }

    public void setAmountOfFields(int amountOfFields) {
        this.amountOfFields = amountOfFields;
    }

    public int getMethodCounter() {
        return methodCounter;
    }

    public void addMethod(String method) {
        methods.add(_clazzName + "." + method);
    }

    public String getMethod(String method) {
        for(int i = 0; i < methods.size(); i++) {
            if(method.equals(methods.get(i))) {
                return methods.get(i);
            }
        }
        return null;
    }

    public void setOwner(String owner) {
        _owner = owner;
    }

    public String getOwner() {
        return _owner;
    }

    public boolean doesOwnerMethodContainsThisMethodAlready(String method) {
        if(ownerMethods.containsKey(method)) {
            return true;
        }
        return false;
    }

    public void addToOwnerMethods(String methodName, boolean methodUsesObject) {
        ownerMethods.put(methodName, methodUsesObject);
    }

    public void getAllOwnerMethod() {
        for(Map.Entry<String, Boolean> entry : ownerMethods.entrySet()) {
            String methodName = entry.getKey();
            boolean methodUsesObject = entry.getValue();
            System.out.println("methodName = " + methodName + ", methodUsesObject = " + methodUsesObject);
        }
    }

    public void increaseOwnerTotalMethods() {
        ownerTotalMethods++;
    }

    public int getOwnerTotalMethods() {
        return ownerTotalMethods;
    }

    public int getAllOccurencesInOwnerMethods() {
        int ans = 0;
        for(Map.Entry<String, Boolean> entry : ownerMethods.entrySet()) {
            String methodName = entry.getKey();
            boolean methodUsesObject = entry.getValue();
            if(methodUsesObject) {
                ans++;
            }
        }
        return ans;
    }

    public void setObjectCreated(boolean objectCreated) {
        this.objectCreated = objectCreated;
    }

    public boolean getObjectCreated() {
        return objectCreated;
    }

    public void setConstructorParameterName(String constructorParameterName) {
        this.constructorParameterName = constructorParameterName;
    }

    public String getConstructorParameterName() {
        return constructorParameterName;
    }

    public void addNewStatementModels(NewStatementModel newStatementModel) {
        newStatementModels.add(newStatementModel);
    }

    public List<NewStatementModel> getNewStatementModels() {
        return newStatementModels;
    }

    public void setCreatedType(String createdType) {
        this.createdType = createdType;
    }

    public String getCreatedType() {
        return createdType;
    }

    public String get_fieldName() {
        return _fieldName;
    }

    public void set_fieldName(String _fieldName) {
        this._fieldName = _fieldName;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public boolean is_usedAsFieldInClass() {
        return _usedAsFieldInClass;
    }

    public void set_usedAsFieldInClass(boolean _usedAsFieldInClass) {
        this._usedAsFieldInClass = _usedAsFieldInClass;
    }

    public String get_clazzName() {
        return _clazzName;
    }

    public void set_clazzName(String _clazzName) {
        this._clazzName = _clazzName;
    }

    public String get_invokeSpecial() {
        return _invokeSpecial;
    }

    public void set_invokeSpecial(String _invokeSpecial) {
        this._invokeSpecial = _invokeSpecial;
    }

    public boolean isConstructorParameter() {
        return constructorParameter;
    }

    public boolean isConstructorInitialized() {
        return constructorInitialized;
    }

    public int getAmountOfFields() {
        return amountOfFields;
    }

    public int getAmountOfMethods() {
        return amountOfMethods;
    }

    public void setFieldCounter(int fieldCounter) {
        this.fieldCounter = fieldCounter;
    }

    public void setMethodCounter(int methodCounter) {
        this.methodCounter = methodCounter;
    }

    public ArrayList<String> getFields() {
        return fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }

    public ArrayList<String> getMethods() {
        return methods;
    }

    public void setMethods(ArrayList<String> methods) {
        this.methods = methods;
    }

    public String get_owner() {
        return _owner;
    }

    public void set_owner(String _owner) {
        this._owner = _owner;
    }

    public Hashtable<String, Boolean> getOwnerMethods() {
        return ownerMethods;
    }

    public void setOwnerMethods(Hashtable<String, Boolean> ownerMethods) {
        this.ownerMethods = ownerMethods;
    }

    public void setOwnerTotalMethods(int ownerTotalMethods) {
        this.ownerTotalMethods = ownerTotalMethods;
    }

    public boolean isObjectCreated() {
        return objectCreated;
    }

    public void setNewStatementModels(List<NewStatementModel> newStatementModels) {
        this.newStatementModels = newStatementModels;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getLastNewType() {
        return lastNewType;
    }

    public void setLastNewType(String lastNewType) {
        this.lastNewType = lastNewType;
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

    @Override
    public String toString() {
        return "Model{" +
//                "_fieldName='" + _fieldName + '\'' +
//                ", _name='" + _name + '\'' +
//                ", _type='" + _type + '\'' +
//                ", _usedAsFieldInClass=" + _usedAsFieldInClass +
//                ", _clazzName='" + _clazzName + '\'' +
//                ", _invokeSpecial='" + _invokeSpecial + '\'' +
//                ", constructorParameter=" + constructorParameter +
//                ", classCounter=" + classCounter +
//                ", amountOfFields=" + amountOfFields +
//                ", amountOfMethods=" + amountOfMethods +
//                ", fieldCounter=" + fieldCounter +
//                ", methodCounter=" + methodCounter +
//                ", fields=" + fields +
//                ", methods=" + methods +
//                ", _owner='" + _owner + '\'' +
//                ", ownerMethods=" + ownerMethods +
//                ", ownerTotalMethods=" + ownerTotalMethods +
//                ", constructorParameterName='" + constructorParameterName + '\'' +
//                ", newStatementModels=" + newStatementModels +
//                ", createdType='" + createdType + '\'' +
                ", name='" + name + '\'' +
                ", descriptor='" + descriptor + '\'' +
                ", objectCreated=" + objectCreated +
                ", constructorInitialized=" + constructorInitialized +
                ", lastNewType='" + lastNewType + '\'' +
                ", ownerClass='" + ownerClass + '\'' +
                ", ownerMethod='" + ownerMethod + '\'' +
                ", ownerMethodReturnType='" + ownerMethodReturnType + '\'' +
                '}';
    }

//    @Override
//    public String toString() {
//        return "Model{" +
//                "_fieldName='" + _fieldName + '\'' +
//                ", _name='" + _name + '\'' +
//                ", _type='" + _type + '\'' +
//                ", _usedAsFieldInClass=" + _usedAsFieldInClass +
//                ", _clazzName='" + _clazzName + '\'' +
//                ", _invokeSpecial='" + _invokeSpecial + '\'' +
//                ", constructorParameter=" + constructorParameter +
//                ", constructorInitialized=" + constructorInitialized +
//                ", classCounter=" + classCounter +
//                ", amountOfFields=" + amountOfFields +
//                ", amountOfMethods=" + amountOfMethods +
//                ", fieldCounter=" + fieldCounter +
//                ", methodCounter=" + methodCounter +
//                ", fields=" + fields +
//                ", methods=" + methods +
//                ", _owner='" + _owner + '\'' +
//                ", ownerMethods=" + ownerMethods +
//                ", ownerTotalMethods=" + ownerTotalMethods +
//                ", objectCreated=" + objectCreated +
//                ", constructorParameterName='" + constructorParameterName + '\'' +
//                ", newStatementModels=" + newStatementModels +
//                ", createdType='" + createdType + '\'' +
//                ", ownerClass='" + ownerClass + '\'' +
//                '}';
//    }

//    public String toString() {
//        return "Name: " + _name + "\n"
//                + "Type: " + _type + "\n"
////                + "Field: " + _usedAsFieldInClass + "\n"
////                + "ClazzName: " + _clazzName + "\n"
////                + "InvokeSpecial: " + _invokeSpecial + "\n"
//                + "Constructor Parameter: " + constructorParameter + "\n"
//                + "Constructor Parameter Name: " + constructorParameterName + "\n"
////                + "Constructor Initialized: " + constructorInitialized + "\n"
////                + "Class Counter: " + classCounter + "\n"
////                + "Amount of Fields: " + amountOfFields + "\n"
////                + "Amount of Methods: " + amountOfMethods + "\n"
////                + "Field List: " + fields + "\n"
////                + "Method List: " + methods + "\n"
//                + "Owner: " + _owner + "\n"
//                + "Field Name: " + _fieldName + "\n"
//                + "Owner within <init> Counter: " + fieldCounter + "\n"
//                + "Owner within Method Counter: " + methodCounter + "\n"
//                + "Owner total Methods: " + ownerTotalMethods + "\n"
//                + "Object Created: " + objectCreated + "\n"
//                + "Occurences in owner methods: " + getAllOccurencesInOwnerMethods() + "\n"
//                + "CreatedType: " + createdType + "\n"
//                + "NewStatementModels: " + newStatementModels;
//    }

    private String cleanUp(String var) {
        var = var.replaceAll(";", "");
        var = var.replaceAll(",", "");
        var = var.replaceAll("\n", "");
        var = var.trim();
        return var;
    }

    private String typeCleanUp(String var) {
        //var = var.replaceAll(";", "");
        var = var.replaceAll(",", "");
        var = var.replaceAll("\n", "");
        var = var.trim();
        return var;
    }

    private String clazzCleanUp(String var) {
        var = cleanUp(var);
        var = var.replaceAll("/", ".");
        if(var.startsWith("L")) {
            var = var.replaceFirst("L", "");
        }
        return var;
    }
}
