package lcom.sourceModel;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ImportDeclaration;

import java.util.ArrayList;
import java.util.List;

public class ImportVisitor extends ASTVisitor {
	List<ImportDeclaration> imports = new ArrayList<>();
	
	@Override
	public boolean visit(ImportDeclaration newImport) {
		imports.add(newImport);
		
		return super.visit(newImport);
	}
	
	public List<ImportDeclaration> getImports(){
		return imports;
	}
}
