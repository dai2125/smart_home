package lcom.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.QualifiedName;

import java.util.ArrayList;
import java.util.List;

public class StaticFieldAccessVisitor extends ASTVisitor {
	
	private List<Name> staticFieldAccesses = new ArrayList<>();
	
	public boolean visit(QualifiedName node) {
		staticFieldAccesses.add(node.getQualifier());
		return true;
	}
	
	public List<Name> getStaticFieldAccesses() {
		return staticFieldAccesses;
	}

}
