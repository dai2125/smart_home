package lcom.visitors;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class MethodControlFlowVisitor extends ASTVisitor {

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

	public boolean visit(IfStatement node) {
		// System.out.println("IfStatement: " + node + " " + edgeCount);
		ifStatements.add(node);
		return true;
	}
	
	public boolean visit(SwitchCase node) {
		// System.out.println("SwitchCase " + node);
		switchCases.add(node);
		if (!node.isDefault()) {
			switchCasesWitoutDefaults.add(node);
		}
		return true;
	}
	
	public boolean visit(SwitchStatement node) {
		// System.out.println("Switch statement " + node.getExpression());
		switchStatements.add(node);
		return true;
	}
	
	public boolean visit(ForStatement node) {
		// System.out.println("For statement: " + node.getExpression());
		forStatements.add(node);
		return true;
	}
	
	public boolean visit(WhileStatement node) {
		// System.out.println("WhileStatement " + node);
		whileStatements.add(node);
		return true;
	}
	
	public boolean visit(DoStatement node) {
		// System.out.println("DoStatement " + node);

		doStatements.add(node);
		return true;
	}
	
	public boolean visit(EnhancedForStatement node) {
		// System.out.println("EnhancedForStatement " + node);

		foreachStatements.add(node);
		return true;
	}
	
	public boolean visit(TryStatement node) {
		// System.out.println("TryStatement" + node);
		tryStatements.add(node);
		return true;
	}

	public boolean visit(ReturnStatement node) {
		// System.out.println("ReturnStatement " + node.getExpression());
		returnStatements.add(node);
		allReturnStatements.add(node);
		return true;
	}

	public boolean visit(ThrowStatement node) {
		// System.out.println("ThrowStatemen " + node.getExpression());
		throwStatements.add(node);
		allThrowStatements.add(node);
		return true;
	}

	private int nodeCount = 0;
	private int edgeCount = 0;

	@Override
	public void preVisit(ASTNode node) {
		// System.out.println("preVisit(): " + node.getParent());
		nodeCount = 0;
		edgeCount = 0;
		nodeCount++;
		for(Object propObj : node.structuralPropertiesForType()) {
			StructuralPropertyDescriptor prop = (StructuralPropertyDescriptor) propObj;
			Object child = node.getStructuralProperty(prop);

			if(child instanceof ASTNode) {
				// System.out.println("preVisit() child: " + child);
				edgeCount++;
			} else if(child instanceof List<?>) {
				for (Object item : (List<?>) child) {
					if(item instanceof ASTNode) {
						// System.out.println("preVisit() item: " + item);

						edgeCount++;
					}
				}
			}
		}
	}

	public List<ReturnStatement> getAllReturnStatements() {
		return allReturnStatements;
	}

	public List<ThrowStatement> getAllThrowStatements() {
		return allThrowStatements;
	}

	public List<IfStatement> getIfStatements() {
		return ifStatements;
	}
	
	public List<ForStatement> getForStatements() {
		return forStatements;
	}
	
	public List<WhileStatement> getWhileStatements() {
		return whileStatements;
	}
	
	public List<DoStatement> getDoStatements() {
		return doStatements;
	}
	
	public List<TryStatement> getTryStatements() {
		return tryStatements;
	}

	public List<SwitchStatement> getSwitchStatements() {
		return switchStatements;
	}

	public List<ThrowStatement> getThrowStatements() {
		return throwStatements;
	}

	public List<ReturnStatement> getReturnStatements() {
		return returnStatements;
	}

	public int getNumOfIfStatements() {
		return ifStatements.size();
	}

	public int getNumOfSwitchCaseStatementsWitoutDefault() {
		return switchCasesWitoutDefaults.size();
	}

	public int getNumOfForStatements() {
		return forStatements.size();
	}

	public int getNumOfWhileStatements() {
		return whileStatements.size();
	}

	public int getNumOfDoStatements() {
		return doStatements.size();
	}

	public int getNumOfForeachStatements() {
		return foreachStatements.size();
	}

	public int getNumOfThrowStatements() {
		return throwStatements.size();
	}

	public int getNumOfReturnStatements() {
		return returnStatements.size();
	}

	public List<SwitchCase> getSwitchCases() {
		return switchCases;
	}

	public List<SwitchCase> getSwitchCasesWitoutDefaults() {
		return switchCasesWitoutDefaults;
	}

	public List<EnhancedForStatement> getForeachStatements() {
		return foreachStatements;
	}

	public int getNodeCount() {
		return nodeCount;
	}

	public int getEdgeCount() {
		return edgeCount;
	}
}
