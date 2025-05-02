package com.home.asm;

import lcom.sourceModel.*;
import net.bytebuddy.implementation.bytecode.Throw;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class MethodInformation {

    private String name;
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

    private List<IfStatement> ifStatements = new ArrayList<>();
    private List<SwitchStatement> switchStatements = new ArrayList<>();
    private List<SwitchCase> switchCases = new ArrayList<>();
    private List<SwitchCase> switchCasesWitoutDefaults = new ArrayList<>();
    private List<ForStatement> forStatements = new ArrayList<>();
    private List<WhileStatement> whileStatements = new ArrayList<>();
    private List<DoStatement> doStatements = new ArrayList<>();
    private List<EnhancedForStatement> foreachStatements = new ArrayList<>();
    private List<TryStatement> tryStatements = new ArrayList<>();
    private List<ReturnStatement> returnStatements = new ArrayList<>();
    private List<ThrowStatement> throwStatements = new ArrayList<>();
    private List<ReturnStatement> allReturnStatements = new ArrayList<>();
    private List<ThrowStatement> allThrowStatements = new ArrayList<>();

    private boolean abstractMethod;
    private boolean finalMethod;
    private boolean staticMethod;
    private boolean isConstructor;
    private SM_Type parentType;

    private MethodDeclaration methodDeclaration;
    private List<SM_Method> calledMethodsList = new ArrayList<>();
    private List<SM_Parameter> parameterList = new ArrayList<>();
    private List<SM_LocalVar> localVarList = new ArrayList<>();
    private List<MethodInvocation> calledMethods = new ArrayList<>();
    private List<SM_Type> referencedTypeList = new ArrayList<>();
    private List<SimpleName> namesInMethod = new ArrayList<>();
    private List<FieldAccess> thisAccessesInMethod = new ArrayList<>();
    private List<SM_Field> directFieldAccesses = new ArrayList<>();
    private List<SM_Field> superClassFieldAccesses = new ArrayList<>();
    private List<Type> typesInInstanceOf = new ArrayList<>();
    private List<SM_Type> smTypesInInstanceOf = new ArrayList<>();
    private List<SM_Type> smTypesInNewStatements = new ArrayList<>();
    private List<Type> newStatementTypes;
    private boolean isOverridden;



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

    public MethodInformation(String name, int numOfParameters, int cyclomaticComplexity, int numOfLines, int numOfIfStatements, int numOfSwitchCaseStatementsWitoutDefault, int numOfForStatements, int numOfWhileStatements, int numOfDoStatements, int numOfForeachStatements, int numOfReturnStatements, int numOfThrowStatements, boolean isUnsupportedOperationException, boolean returnsNull, List<IfStatement> ifStatements, List<SwitchStatement> switchStatements, List<SwitchCase> switchCases, List<SwitchCase> switchCasesWitoutDefaults, List<ForStatement> forStatements, List<WhileStatement> whileStatements, List<DoStatement> doStatements, List<EnhancedForStatement> foreachStatements, List<TryStatement> tryStatements, List<ReturnStatement> returnStatements, List<ThrowStatement> throwStatements, List<ReturnStatement> allReturnStatements, List<ThrowStatement> allThrowStatements) {
        this.name = name;
        this.numOfParameters = numOfParameters;
        this.cyclomaticComplexity = cyclomaticComplexity;
        this.numOfLines = numOfLines;
        this.numOfIfStatements = numOfIfStatements;
        this.numOfSwitchCaseStatementsWitoutDefault = numOfSwitchCaseStatementsWitoutDefault;
        this.numOfForStatements = numOfForStatements;
        this.numOfWhileStatements = numOfWhileStatements;
        this.numOfDoStatements = numOfDoStatements;
        this.numOfForeachStatements = numOfForeachStatements;
        this.numOfReturnStatements = numOfReturnStatements;
        this.numOfThrowStatements = numOfThrowStatements;
        this.isUnsupportedOperationException = isUnsupportedOperationException;
        this.returnsNull = returnsNull;
        this.ifStatements = ifStatements;
        this.switchStatements = switchStatements;
        this.switchCases = switchCases;
        this.switchCasesWitoutDefaults = switchCasesWitoutDefaults;
        this.forStatements = forStatements;
        this.whileStatements = whileStatements;
        this.doStatements = doStatements;
        this.foreachStatements = foreachStatements;
        this.tryStatements = tryStatements;
        this.returnStatements = returnStatements;
        this.throwStatements = throwStatements;
        this.allReturnStatements = allReturnStatements;
        this.allThrowStatements = allThrowStatements;
    }

    public MethodInformation(String name,
                             boolean isAbstract,
                             boolean isFinal,
                             boolean isStatic,
                             List<SM_Method> calledMethodsList,
                             List<SimpleName> namesInMethod,
                             List<FieldAccess> thisAccessesInMethod,
                             List<SM_Field> superClassFieldAccesses,
                             List<Type> typesInInstanceOf,
                             List<SM_Type> smTypesInInstanceOf,
                             List<SM_Type> smTypesInNewStatements,
                             List<Type> newStatementTypes,
                             List<SM_Field> directFieldAccesses,
                             List<SM_Field> nonStaticFieldAccesses,
                             boolean overridden,
                             List<SM_Field> fieldAccessesFromSuperClass,
                             MethodDeclaration methodDeclaration,
                             List<ReturnStatement> allReturnStatements,
                             List<ThrowStatement> allThrowStatements,
                             List<IfStatement> ifStatements,
                             List<ForStatement> forStatements,
                             List<WhileStatement> whileStatements,
                             List<DoStatement> doStatements,
                             List<TryStatement> tryStatements,
                             List<SwitchStatement> switchStatements,
                             List<ThrowStatement> throwStatements,
                             List<ReturnStatement> returnStatements,
                             List<SwitchCase> switchCases,
                             List<SwitchCase> switchCasesWitoutDefaults,
                             List<EnhancedForStatement> foreachStatements,
                             int cyclomaticComplexity) {
        this.name = name;
        this.abstractMethod = isAbstract;
        this.finalMethod = isFinal;
        this.staticMethod = isStatic;
        this.calledMethodsList = calledMethodsList;
        this.namesInMethod = namesInMethod;
        this.thisAccessesInMethod = thisAccessesInMethod;
        this.superClassFieldAccesses = superClassFieldAccesses;
        this.typesInInstanceOf = typesInInstanceOf;
        this.smTypesInInstanceOf = smTypesInInstanceOf;
        this.smTypesInNewStatements = smTypesInNewStatements;
        this.newStatementTypes = newStatementTypes;
        this.directFieldAccesses = directFieldAccesses;
        //this.nonStaticFieldAccesses = nonStaticFieldAccesses;
        this.isOverridden = overridden;
        //this.fieldAccessFromSuperClass = fieldAccessesFromSuperClass;
        this.allReturnStatements = allReturnStatements;
        this.methodDeclaration = methodDeclaration;
        this.allThrowStatements = allThrowStatements;
        this.ifStatements = ifStatements;
        this.switchStatements = switchStatements;
        this.switchCases = switchCases;
        this.switchCasesWitoutDefaults = switchCasesWitoutDefaults;
        this.forStatements = forStatements;
        this.whileStatements = whileStatements;
        this.doStatements = doStatements;
        this.foreachStatements = foreachStatements;
        this.tryStatements = tryStatements;
        this.returnStatements = returnStatements;
        this.throwStatements = throwStatements;
        this.cyclomaticComplexity = cyclomaticComplexity;
    }

    public String getName() {
        return name;
    }

    public int getNumOfParameters() {
        return parameterList.size();
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
        return ifStatements.size();
    }

    public void setNumOfIfStatements(int numOfIfStatements) {
        this.numOfIfStatements = numOfIfStatements;
    }

    public int getNumOfSwitchCaseStatementsWitoutDefault() {
        return switchCasesWitoutDefaults.size();
    }

    public void setNumOfSwitchCaseStatementsWitoutDefault(int numOfSwitchCaseStatementsWitoutDefault) {
        this.numOfSwitchCaseStatementsWitoutDefault = numOfSwitchCaseStatementsWitoutDefault;
    }

    public int getNumOfForStatements() {
        return forStatements.size();
    }

    public void setNumOfForStatements(int numOfForStatements) {
        this.numOfForStatements = numOfForStatements;
    }

    public int getNumOfWhileStatements() {
        return whileStatements.size();
    }

    public void setNumOfWhileStatements(int numOfWhileStatements) {
        this.numOfWhileStatements = numOfWhileStatements;
    }

    public int getNumOfDoStatements() {
        return doStatements.size();
    }

    public void setNumOfDoStatements(int numOfDoStatements) {
        this.numOfDoStatements = numOfDoStatements;
    }

    public int getNumOfForeachStatements() {
        return foreachStatements.size();
    }

    public void setNumOfForeachStatements(int numOfForeachStatements) {
        this.numOfForeachStatements = numOfForeachStatements;
    }

    public int getNumOfReturnStatements() {
        return returnStatements.size();
    }

    public void setNumOfReturnStatements(int numOfReturnStatements) {
        this.numOfReturnStatements = numOfReturnStatements;
    }

    public List<ReturnStatement> getReturnStatements() {
        return returnStatements;
    }

    public int getNumOfThrowStatements() {
        return throwStatements.size();
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

    public void setName(String name) {
        this.name = name;
    }

    public List<IfStatement> getIfStatements() {
        return ifStatements;
    }

    public void setIfStatements(List<IfStatement> ifStatements) {
        this.ifStatements = ifStatements;
    }

    public List<SwitchStatement> getSwitchStatements() {
        return switchStatements;
    }

    public void setSwitchStatements(List<SwitchStatement> switchStatements) {
        this.switchStatements = switchStatements;
    }

    public List<SwitchCase> getSwitchCases() {
        return switchCases;
    }

    public void setSwitchCases(List<SwitchCase> switchCases) {
        this.switchCases = switchCases;
    }

    public List<SwitchCase> getSwitchCasesWitoutDefaults() {
        return switchCasesWitoutDefaults;
    }

    public void setSwitchCasesWitoutDefaults(List<SwitchCase> switchCasesWitoutDefaults) {
        this.switchCasesWitoutDefaults = switchCasesWitoutDefaults;
    }

    public List<ForStatement> getForStatements() {
        return forStatements;
    }

    public void setForStatements(List<ForStatement> forStatements) {
        this.forStatements = forStatements;
    }

    public List<WhileStatement> getWhileStatements() {
        return whileStatements;
    }

    public void setWhileStatements(List<WhileStatement> whileStatements) {
        this.whileStatements = whileStatements;
    }

    public List<DoStatement> getDoStatements() {
        return doStatements;
    }

    public void setDoStatements(List<DoStatement> doStatements) {
        this.doStatements = doStatements;
    }

    public List<EnhancedForStatement> getForeachStatements() {
        return foreachStatements;
    }

    public void setForeachStatements(List<EnhancedForStatement> foreachStatements) {
        this.foreachStatements = foreachStatements;
    }

    public List<TryStatement> getTryStatements() {
        return tryStatements;
    }

    public void setTryStatements(List<TryStatement> tryStatements) {
        this.tryStatements = tryStatements;
    }

    public void setReturnStatements(List<ReturnStatement> returnStatements) {
        this.returnStatements = returnStatements;
    }

    public void setThrowStatements(List<ThrowStatement> throwStatements) {
        this.throwStatements = throwStatements;
    }

    public List<ReturnStatement> getAllReturnStatements() {
        return allReturnStatements;
    }

    public void setAllReturnStatements(List<ReturnStatement> allReturnStatements) {
        this.allReturnStatements = allReturnStatements;
    }

    public List<ThrowStatement> getAllThrowStatements() {
        return allThrowStatements;
    }

    public void setAllThrowStatements(List<ThrowStatement> allThrowStatements) {
        this.allThrowStatements = allThrowStatements;
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

    public String methodInformation() {
        return "MethodInformation{" +
                "name='" + name + '\'' +
                ", numOfParameters=" + numOfParameters +
                ", cyclomaticComplexity=" + cyclomaticComplexity +
                ", numOfLines=" + numOfLines +
                ", method=" + method +
                ", numOfIfStatements=" + numOfIfStatements +
                ", numOfSwitchCaseStatementsWitoutDefault=" + numOfSwitchCaseStatementsWitoutDefault +
                ", numOfForStatements=" + numOfForStatements +
                ", numOfWhileStatements=" + numOfWhileStatements +
                ", numOfDoStatements=" + numOfDoStatements +
                ", numOfForeachStatements=" + numOfForeachStatements +
                ", numOfReturnStatements=" + numOfReturnStatements +
                ", numOfThrowStatements=" + numOfThrowStatements +
                ", isUnsupportedOperationException=" + isUnsupportedOperationException +
                ", returnsNull=" + returnsNull +
                ", ifStatements=" + ifStatements +
                ", switchStatements=" + switchStatements +
                ", switchCases=" + switchCases +
                ", switchCasesWitoutDefaults=" + switchCasesWitoutDefaults +
                ", forStatements=" + forStatements +
                ", whileStatements=" + whileStatements +
                ", doStatements=" + doStatements +
                ", foreachStatements=" + foreachStatements +
                ", tryStatements=" + tryStatements +
                ", returnStatements=" + returnStatements +
                ", throwStatements=" + throwStatements +
                ", allReturnStatements=" + allReturnStatements +
                ", allThrowStatements=" + allThrowStatements +
                ", abstractMethod=" + abstractMethod +
                ", finalMethod=" + finalMethod +
                ", staticMethod=" + staticMethod +
                ", isConstructor=" + isConstructor +
                ", parentType=" + parentType +
                ", methodDeclaration=" + methodDeclaration +
                ", calledMethodsList=" + calledMethodsList +
                ", parameterList=" + parameterList +
                ", localVarList=" + localVarList +
                ", calledMethods=" + calledMethods +
                ", referencedTypeList=" + referencedTypeList +
                ", namesInMethod=" + namesInMethod +
                ", newStatementTypes=" + newStatementTypes +
                ", isOverridden=" + isOverridden +
                '}';
    }

    public String methodInformation2() {
        return "MethodInformation{" +
                "name='" + name + '\'' +
                ", numOfParameters=" + getNumOfParameters() +
                ", cyclomaticComplexity=" + cyclomaticComplexity +
                ", numOfLines=" + numOfLines +
                ", method=" + method +
                ", numOfIfStatements=" + getNumOfIfStatements() +
                ", numOfSwitchCaseStatementsWitoutDefault=" + getNumOfSwitchCaseStatementsWitoutDefault() +
                ", numOfForStatements=" + getNumOfForStatements() +
                ", numOfWhileStatements=" + getNumOfWhileStatements() +
                ", numOfDoStatements=" + getNumOfDoStatements() +
                ", numOfForeachStatements=" + getNumOfForeachStatements() +
                ", numOfReturnStatements=" + getNumOfReturnStatements() +
                ", numOfThrowStatements=" + getNumOfThrowStatements() +
                ", ifStatements.size()=" + ifStatements.size() +
                ", switchStatements=" + switchStatements.size() +
                ", switchCases=" + switchCases.size() +
                ", switchCasesWitoutDefaults=" + switchCasesWitoutDefaults.size() +
                ", forStatements=" + forStatements.size() +
                ", whileStatements=" + whileStatements.size() +
                ", doStatements=" + doStatements.size() +
                ", foreachStatements=" + foreachStatements.size() +
                ", tryStatements=" + tryStatements.size() +
                ", returnStatements=" + returnStatements.size() +
                ", throwStatements=" + throwStatements.size() +
                ", allReturnStatements=" + allReturnStatements.size() +
                ", allThrowStatements=" + allThrowStatements.size() +
                ", abstractMethod=" + abstractMethod +
                ", finalMethod=" + finalMethod +
                ", staticMethod=" + staticMethod +
                ", isConstructor=" + isConstructor +
                ", parentType=" + parentType +
                ", calledMethodsList=" + calledMethodsList +
                ", parameterList=" + parameterList +
                ", localVarList=" + localVarList +
                ", calledMethods=" + calledMethods.size() +
                ", namesInMethod=" + namesInMethod +
                ", newStatementTypes=" + newStatementTypes.size() +
                ", isOverridden=" + isOverridden +
                '}';
    }
}
