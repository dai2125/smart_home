package lcom.sourceModel;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

import java.util.ArrayList;
import java.util.List;

public class MethodInvVisitor extends ASTVisitor {
	List<MethodInvocation> calledMethods = new ArrayList<MethodInvocation>();

	public MethodInvVisitor(MethodDeclaration methodDeclaration) {
	}

	@Override
	public boolean visit(MethodInvocation method) {
		calledMethods.add(method);
		method.resolveMethodBinding();
//		System.out.println("MethodInvVisitor(): " + method.getName() + " " + method.resolveMethodBinding().getName() + " " + method.arguments().toString() + " " + method.getExpression().toString());
		return super.visit(method);
	}
	
	public boolean visit(SuperMethodInvocation method) {
		return super.visit(method);
	}

	public List<MethodInvocation> getCalledMethods() {
		return calledMethods;
	}
}
