package lcom.sourceModel;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import java.util.ArrayList;
import java.util.List;

public class LocalVarVisitor extends ASTVisitor {
	List<SM_LocalVar> localVariables = new ArrayList<SM_LocalVar>();
	private SM_Method parentMethod;
	
	LocalVarVisitor(SM_Method methodObj) {
		this.parentMethod = methodObj;
	}

	public boolean visit(VariableDeclarationStatement variable){
		for (Object o : variable.fragments()) {
			VariableDeclarationFragment fragment = (VariableDeclarationFragment) o;

			SM_LocalVar newLocalVar = new SM_LocalVar(variable, fragment, parentMethod);
			localVariables.add(newLocalVar);
		}
		return super.visit(variable);
	}
	
	List<SM_LocalVar> getLocalVarList() {
		return localVariables;
	}
	
}
