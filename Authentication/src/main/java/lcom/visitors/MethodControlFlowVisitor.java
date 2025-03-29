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
		ifStatements.add(node);
		return true;
	}
	
	public boolean visit(SwitchCase node) {
		switchCases.add(node);
		if (!node.isDefault()) {
			switchCasesWitoutDefaults.add(node);
		}
		return true;
	}
	
	public boolean visit(SwitchStatement node) {
		switchStatements.add(node);
		return true;
	}
	
	public boolean visit(ForStatement node) {
		forStatements.add(node);
		return true;
	}
	
	public boolean visit(WhileStatement node) {
		whileStatements.add(node);
		return true;
	}
	
	public boolean visit(DoStatement node) {
		doStatements.add(node);
		return true;
	}
	
	public boolean visit(EnhancedForStatement node) {
		foreachStatements.add(node);
		return true;
	}
	
	public boolean visit(TryStatement node) {
		tryStatements.add(node);
		return true;
	}

	public boolean visit(ReturnStatement node) {
		returnStatements.add(node);
//		System.out.println("FOUND STATEMENT " + node.getExpression());
		allReturnStatements.add(node);
		return true;
	}

	public boolean visit(ThrowStatement node) {
		throwStatements.add(node);
//		System.out.println("FOUND STATEMENT " + node.getExpression());
		allThrowStatements.add(node);
		return true;
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
}
