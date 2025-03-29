package lcom.sourceModel;

import com.home.asm.CreatorPrinciple;
import com.home.asm.CreatorPrincipleService;
import com.home.asm.NewObject;
import com.home.asm.NewObjectService;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import java.util.ArrayList;
import java.util.List;

public class FieldVisitor extends ASTVisitor {
	List<SM_Field> fields = new ArrayList<SM_Field>();
	private SM_Type parentType;

	public FieldVisitor(SM_Type parentType) {
		super();
		this.parentType = parentType;
	}

	// TODO ALLES innerhalb der <init> Funktion!
	@Override
	public boolean visit(FieldDeclaration fieldDeclaration) {
//		System.out.println("gggggggggggggggggggggg");
		// Variable
//		System.out.println("VISIT: " + fieldDeclaration.getParent().toString());
//		System.out.println("xxxx: " + fieldDeclaration.getParent().toString());
//		System.out.println("ParentType: " + parentType);
//		System.out.println("fieldDeclaration.fragments(): " + fieldDeclaration.fragments().stream().toList());
//		System.out.println("fieldDeclaration.getType(): " + fieldDeclaration.getType());
		List<VariableDeclarationFragment> fieldList = fieldDeclaration.fragments();
		for (VariableDeclarationFragment field : fieldList) {
			SM_Field newField = new SM_Field(fieldDeclaration, field, parentType);
			fields.add(newField);
//			System.out.println("newField.name: "+ newField.name);

			// Parent
//			System.out.println("newField.getParentType(): " +newField.getParentType().toString());

			if(!CreatorPrincipleService.contains(newField.getParentType().toString().replace("Type=", ""))) {
				CreatorPrinciple creatorPrinciple = new CreatorPrinciple(newField.getParentType().toString().replace("Type=", ""));

				// TODO hier Kommentar rückgängig machen
//				creatorPrinciple.addToFieldList(newField.name + ", " + fieldDeclaration.getType().toString());
				CreatorPrincipleService.put(creatorPrinciple);
//				System.out.println(creatorPrinciple);
			} else {
				CreatorPrinciple creatorPrinciple = new CreatorPrinciple(newField.getParentType().toString().replace("Type=", ""));

				// TODO hier Kommentar rückgängig machen
//				creatorPrinciple.addToFieldList(newField.name + ", " + fieldDeclaration.getType().toString());
				CreatorPrincipleService.put(creatorPrinciple);
//				System.out.println(creatorPrinciple);

			}
		}

		return super.visit(fieldDeclaration);
	}

	public List<SM_Field> getFields() {
		return fields;
	}

	/*
	 * public int countFields() { return fields.size(); }
	 */
}
