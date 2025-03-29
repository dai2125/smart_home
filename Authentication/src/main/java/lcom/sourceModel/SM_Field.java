package lcom.sourceModel;

import com.home.asm.CreatorPrinciple;
import com.home.asm.CreatorPrincipleService;
import lcom.utils.models.Vertex;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SM_Field extends SM_EntitiesWithType implements Vertex {
	private FieldDeclaration fieldDeclaration;
	private SM_Type parentType;
	private SM_Type nestedParentType = null;
	private boolean finalField = false;
	private boolean staticField = false;

	SM_Field(FieldDeclaration fieldDeclaration, VariableDeclarationFragment varDecl, SM_Type parentType) {
		this.fieldDeclaration = fieldDeclaration;
		this.parentType = parentType;
		setAccessModifier(fieldDeclaration.getModifiers());
		setFieldInfo(fieldDeclaration);
		name = varDecl.getName().toString();
		assignToNestedTypeIfNecessary();

//		System.out.println("SM_FIELD");
	// TODO hierher zurück  cccccccccc
		System.out.println("xxxxxxxxxx:" + parentType.name + ":xxxxxxxxxx");
		if(!CreatorPrincipleService.contains(parentType.name)) {
			System.out.println("cccccccccc:" + parentType.name + ":cccccccccc");

			CreatorPrinciple creatorPrinciple = new CreatorPrinciple(parentType.name);
			System.out.println("bbbbbbb:" + fieldDeclaration);
			System.out.println("bbbbbbb:" + fieldDeclaration.getType());
			System.out.println("bbbbbbb:" + fieldDeclaration.getType().toString());

			// TODO hier kommenatr rückgängig machen
//			creatorPrinciple.addToFieldList(fieldDeclaration + ", " + fieldDeclaration.getType().toString());

			creatorPrinciple.increaseCount();

			CreatorPrincipleService.put(creatorPrinciple);
//				System.out.println(creatorPrinciple);
		} else {
			CreatorPrinciple creatorPrinciple = CreatorPrincipleService.get(parentType.name);
//			CreatorPrinciple creatorPrinciple = new CreatorPrinciple(parentType.name);

			// TODO hier kommenatr rückgängig machen
//			creatorPrinciple.addToFieldList(fieldDeclaration + ", " + fieldDeclaration.getType().toString());

			creatorPrinciple.increaseCount();

			CreatorPrincipleService.put(creatorPrinciple);
//				System.out.println(creatorPrinciple);

		}

		System.out.println("SM_FIELD ParentType.name " + parentType.name);
		System.out.println("fieldDeclaration: " + fieldDeclaration);
		System.out.println("fieldDeclaration.getType(): " + fieldDeclaration.getType());

		for(Object fragmentObj : fieldDeclaration.fragments()) {
			if(fragmentObj instanceof VariableDeclarationFragment fragment) {
				System.out.println("Variablenname: " + fragment.getName());

				if(fragment.getInitializer() != null) {
					System.out.println("Initialwert: " + fragment.getInitializer());
				}
			}
		}
	}


	private void setFieldInfo(FieldDeclaration field){
		int modifiers = field.getModifiers();
		if (Modifier.isFinal(modifiers)) 
			finalField =  true;
		if (Modifier.isStatic(modifiers)) 
			staticField =  true;
	}
	
	private void assignToNestedTypeIfNecessary() {
		if (parentType.getNestedTypes().size() < 1) {
		} else {
			String typeName = getNestedParentName();
			if(typeName != null) {
				typeName = typeName.trim();
				this.nestedParentType = parentType.getNestedTypeFromName(typeName);
				if(this.nestedParentType != null) {
				}
			}
		}
	}
	
	private String getNestedParentName() {
		final String regex = "public|private[ ]{1,}class[ ]{1,}([^\\{]*)[\\{]{1}";
		final String inputString = this.fieldDeclaration.getParent().toString();
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(inputString);
		
		String typeName = "";
		while (matcher.find()) {
			typeName = matcher.group(1);
			return typeName;
		}
		return "";
	}
	
	public boolean isFinal() {
		return finalField;
	}
	
	public boolean isStatic() {
		return staticField;
	}
	
	@Override
	public SM_Type getParentType() {
		return parentType;
	}

	@Override
	public void resolve() {
		Resolver resolver = new Resolver();
		typeInfo = resolver.resolveVariableType(fieldDeclaration.getType(), getParentType().getParentPkg().getParentProject(), getParentType());
	}
}
