package lcom.metrics;

import java.util.List;

import lcom.sourceModel.SM_Field;
import lcom.sourceModel.SM_Method;
import lcom.sourceModel.SM_Type;

public class MethodMetrics extends Metrics {

    private int numOfParameters;
    private int cyclomaticComplexity;
    private int numOfLines;
    private SM_Method method;
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

    public int getNumOfParameters() {
        return numOfParameters;
    }

    public int getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public int getNumOfLines() {
        return numOfLines;
    }

    public void setNumOfParameters(int numOfParameters) {
        this.numOfParameters = numOfParameters;
    }

    public void setCyclomaticComplexity(int cyclomaticComplexity) {
        this.cyclomaticComplexity = cyclomaticComplexity;
    }

    public void setNumOfLines(int numOfLines) {
        this.numOfLines = numOfLines;
    }

    public void setMethod(SM_Method method){
        this.method = method;
    }

    public SM_Method getMethod() {
        return method;
    }

    public List<SM_Field> getDirectFieldAccesses() {
        return method.getDirectFieldAccesses();
    }

    public List<SM_Type> getSMTypesInInstanceOf() {
        return method.getSMTypesInInstanceOf();
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

    public int getNumOfThrowStatements() {
        return numOfThrowStatements;
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
        return "MethodMetrics{" + "\n" +
                "numOfParameters=" + numOfParameters + "\n" +
                ", cyclomaticComplexity=" + cyclomaticComplexity + "\n" +
                ", numOfLines=" + numOfLines + "\n" +
                ", method=" + method.getName() + "\n" +
                ", numOfIfStatements=" + numOfIfStatements + "\n" +
                ", numOfSwitchCaseStatementsWitoutDefault=" + numOfSwitchCaseStatementsWitoutDefault + "\n" +
                ", numOfForStatements=" + numOfForStatements + "\n" +
                ", numOfWhileStatements=" + numOfWhileStatements + "\n" +
                ", numOfDoStatements=" + numOfDoStatements + "\n" +
                ", numOfForeachStatements=" + numOfForeachStatements + "\n" +
                ", numOfReturnStatements=" + numOfReturnStatements + "\n" +
                ", numOfThrowStatements=" + numOfThrowStatements + "\n" +
                ", isUnsupportedOperationException=" + isUnsupportedOperationException + "\n" +
                ", returnsNull=" + returnsNull + "\n" +
                '}';
    }
}