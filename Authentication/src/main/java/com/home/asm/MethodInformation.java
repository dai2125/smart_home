package com.home.asm;

import lcom.sourceModel.SM_Method;
import net.bytebuddy.implementation.bytecode.Throw;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;

import java.util.ArrayList;
import java.util.List;

public class MethodInformation {

    private String name;
    private int numOfParameters;
    private int cyclomaticComplexity;
    private int numOfLines;
    private int numOfIfStatements;
    private int numOfSwitchCaseStatementsWitoutDefault;
    private int numOfForStatements;
    private int numOfWhileStatements;
    private int numOfDoStatements;
    private int numOfForeachStatements;
    private int numOfReturnStatements;
    private int numOfThrowStatements;
    private boolean isUnsupportedOperationException;
    private boolean returnsNull;
    private List<ReturnStatement> returnStatements = new ArrayList<>();
    private List<ThrowStatement> throwStatements = new ArrayList<>();

    public MethodInformation(String name) {
        this.name = name;
    }

    public MethodInformation(String name, int numOfIfStatements, int numOfSwitchCaseStatementsWitoutDefault, int numOfForStatements, int numOfWhileStatements, int numOfDoStatements, int numOfForeachStatements, int numOfReturnStatements, int numOfThrowStatements, List<ReturnStatement> returnStatements, List<ThrowStatement> throwStatements) {
        this.name = name;
//        this.numOfParameters = numOfParameters;
//        this.cyclomaticComplexity = cyclomaticComplexity;
//        this.numOfLines = numOfLines;
        this.numOfIfStatements = numOfIfStatements;
        this.numOfSwitchCaseStatementsWitoutDefault = numOfSwitchCaseStatementsWitoutDefault;
        this.numOfForStatements = numOfForStatements;
        this.numOfWhileStatements = numOfWhileStatements;
        this.numOfDoStatements = numOfDoStatements;
        this.numOfForeachStatements = numOfForeachStatements;
        this.numOfReturnStatements = numOfReturnStatements;
        this.numOfThrowStatements = numOfThrowStatements;
        this.returnStatements = returnStatements;
        this.throwStatements = throwStatements;
    }

    public String getName() {
        return name;
    }

    public int getNumOfParameters() {
        return numOfParameters;
    }

    public void setNumOfParameters(int numOfParameters) {
        this.numOfParameters = numOfParameters;
    }

    public int getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public void setCyclomaticComplexity(int cyclomaticComplexity) {
        this.cyclomaticComplexity = cyclomaticComplexity;
    }

    public int getNumOfLines() {
        return numOfLines;
    }

    public void setNumOfLines(int numOfLines) {
        this.numOfLines = numOfLines;
    }

    public int getNumOfIfStatements() {
        return numOfIfStatements;
    }

    public void setNumOfIfStatements(int numOfIfStatements) {
        this.numOfIfStatements = numOfIfStatements;
    }

    public int getNumOfSwitchCaseStatementsWitoutDefault() {
        return numOfSwitchCaseStatementsWitoutDefault;
    }

    public void setNumOfSwitchCaseStatementsWitoutDefault(int numOfSwitchCaseStatementsWitoutDefault) {
        this.numOfSwitchCaseStatementsWitoutDefault = numOfSwitchCaseStatementsWitoutDefault;
    }

    public int getNumOfForStatements() {
        return numOfForStatements;
    }

    public void setNumOfForStatements(int numOfForStatements) {
        this.numOfForStatements = numOfForStatements;
    }

    public int getNumOfWhileStatements() {
        return numOfWhileStatements;
    }

    public void setNumOfWhileStatements(int numOfWhileStatements) {
        this.numOfWhileStatements = numOfWhileStatements;
    }

    public int getNumOfDoStatements() {
        return numOfDoStatements;
    }

    public void setNumOfDoStatements(int numOfDoStatements) {
        this.numOfDoStatements = numOfDoStatements;
    }

    public int getNumOfForeachStatements() {
        return numOfForeachStatements;
    }

    public void setNumOfForeachStatements(int numOfForeachStatements) {
        this.numOfForeachStatements = numOfForeachStatements;
    }

    public int getNumOfReturnStatements() {
        return numOfReturnStatements;
    }

    public void setNumOfReturnStatements(int numOfReturnStatements) {
        this.numOfReturnStatements = numOfReturnStatements;
    }

    public List<ReturnStatement> getReturnStatements() {
        return returnStatements;
    }

    public int getNumOfThrowStatements() {
        return numOfThrowStatements;
    }

    public List<ThrowStatement> getThrowStatements() {
        return throwStatements;
    }

    public void setNumOfThrowStatements(int numOfThrowStatements) {
        this.numOfThrowStatements = numOfThrowStatements;
    }

    public boolean isUnsupportedOperationException() {
        return isUnsupportedOperationException;
    }

    public void setUnsupportedOperationException(boolean unsupportedOperationException) {
        isUnsupportedOperationException = unsupportedOperationException;
    }

    public boolean isReturnsNull() {
        return returnsNull;
    }

    public void setReturnsNull(boolean returnsNull) {
        this.returnsNull = returnsNull;
    }

    @Override
    public String toString() {
        return "MethodInformation{" + "\n" +
                "\t\tname='" + name + '\'' + "\n" +
                "\t\tnumOfParameters=" + numOfParameters + "\n" +
                "\t\tcyclomaticComplexity=" + cyclomaticComplexity + "\n" +
                "\t\tnumOfLines=" + numOfLines + "\n" +
                "\t\tnumOfIfStatements=" + numOfIfStatements + "\n" +
                "\t\tnumOfSwitchCaseStatementsWitoutDefault=" + numOfSwitchCaseStatementsWitoutDefault + "\n" +
                "\t\tnumOfForStatements=" + numOfForStatements + "\n" +
                "\t\tnumOfWhileStatements=" + numOfWhileStatements + "\n" +
                "\t\tnumOfDoStatements=" + numOfDoStatements + "\n" +
                "\t\tnumOfForeachStatements=" + numOfForeachStatements + "\n" +
                "\t\tnumOfReturnStatements=" + numOfReturnStatements + "\n" +
                "\t\tnumOfThrowStatements=" + numOfThrowStatements + "\n" +
                "\t\tisUnsupportedOperationException=" + isUnsupportedOperationException + "\n" +
                "\t\treturnsNull=" + returnsNull + "\n" +
                "\t\treturnStatements=" + returnStatements.stream().map(ReturnStatement::toString).toList() + "\n" +
                "\t\tthrowStatements=" + throwStatements.stream().map(ThrowStatement::toString).toList() + "\n" +

                '}';
    }
}
