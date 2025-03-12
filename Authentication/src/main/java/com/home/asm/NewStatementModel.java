package com.home.asm;

import java.util.List;

public class NewStatementModel {

    private String type;
    private List<String> getFields;
    private List<String> putFields;
    private List<String> parameterTypes;
    private List<String> parameterValues;
    private boolean usesCreatorFields;
    private int totalAmountConstructorParameters;

    public NewStatementModel() {

    }

    public NewStatementModel(String type, List<String> getFields, List<String> putFields, boolean usesCreatorFields, int totalAmountConstructorParameters, List<String> parameterValues) {
        this.type = type;
        this.getFields = getFields;
        this.putFields = putFields;
        this.usesCreatorFields = usesCreatorFields;
        this.totalAmountConstructorParameters = totalAmountConstructorParameters;
        this.parameterValues = parameterValues;

        System.out.println(this);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getGetFields() {
        return getFields;
    }

    public void setGetFields(List<String> getFields) {
        this.getFields = getFields;
    }

    public List<String> getPutFields() {
        return putFields;
    }

    public void setPutFields(List<String> putFields) {
        this.putFields = putFields;
    }

    public List<String> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<String> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public List<String> getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(List<String> parameterValues) {
        this.parameterValues = parameterValues;
    }

    public boolean isUsesCreatorFields() {
        return usesCreatorFields;
    }

    public void setUsesCreatorFields(boolean usesCreatorFields) {
        this.usesCreatorFields = usesCreatorFields;
    }

    public void setTotalAmountConstructorParameters(int totalAmountConstructorParameters) {
        this.totalAmountConstructorParameters = totalAmountConstructorParameters;
    }

    public int getTotalAmountConstructorParameters() {
        return totalAmountConstructorParameters;
    }

    public String toString() {
        return "NewStatementModel " + "\n" +
                "type = " + type + "\n" +
                "parameterTypes = " + parameterTypes + "\n" +
                "parameterValues = " + parameterValues + "\n" +
                "usesCreatorFields = " + usesCreatorFields + "\n" +
                "totalAmountConstructorParameters = " + totalAmountConstructorParameters + "\n" +
                "getFields = " + getFields + "\n" +
                "putFields = " + putFields + "\n";

    }
}
