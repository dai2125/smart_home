package lcom.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.Type;

import java.util.ArrayList;
import java.util.List;

public class InstanceOfVisitor extends ASTVisitor {
	
	private List<Type> typesInInstanceOf = new ArrayList<>();;
	
	public boolean visit(InstanceofExpression node) {
		typesInInstanceOf.add(node.getRightOperand());
		return true;
	}
	
	public List<Type> getTypesInInstanceOf() {
		return typesInInstanceOf;
	}
}
