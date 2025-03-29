package lcom.sourceModel;

import com.home.asm.NewObject;
import com.home.asm.NewObjectService;
import org.eclipse.jdt.core.dom.*;

import java.util.*;

public class NewStatementVisitor extends ASTVisitor {
    private final MethodDeclaration methodDeclaration;
    private List<Type> typeList = new ArrayList<>();
    private Map<String, Type> newInstances = new HashMap<>();

    NewStatementVisitor(MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
    }

    public boolean visit(ClassInstanceCreation instanceCreation){
//        newInstances.put(instanceCreation.toString(), instanceCreation.getType());
//        System.out.println("instanceCreation: " + instanceCreation.toString() + " " + instanceCreation.getType() + " " + instanceCreation.getExpression() + " " + instanceCreation.arguments().toString());

        if(instanceCreation.getType() != null)
            typeList.add(instanceCreation.getType());
        return super.visit(instanceCreation);
    }

//    public boolean visit(VariableDeclarationFragment node){
//        String varName = node.getName().toString();
//        Type type = ((ClassInstanceCreation) node.getInitializer()).getType();
//
//        System.out.println("Variable: " + varName + " - Type: " + type);
//        return super.visit(node);
//    }

    // hier werden neue Objekte erstellt
    @Override
    public boolean visit(VariableDeclarationFragment node) {
        if (node.getInitializer() instanceof ClassInstanceCreation) {
            ClassInstanceCreation creation = (ClassInstanceCreation) node.getInitializer();
            String varName = node.getName().toString();
//            Type type = ((ClassInstanceCreation) node.getInitializer()).getType();
            Type type = creation.getType();

            NewObject object = new NewObject(node.getName().toString(), type);
//            System.out.println(object);
            newInstances.put(varName, type);
            NewObjectService.put(object);

        }
        return super.visit(node);
    }

    @Override
    public boolean visit(FieldDeclaration node) {
//        System.out.println("ggggggggggggg");
        for (Object fragment : node.fragments()) {
            if (fragment instanceof VariableDeclarationFragment) {
                VariableDeclarationFragment varFragment = (VariableDeclarationFragment) fragment;

                if (varFragment.getInitializer() instanceof ClassInstanceCreation) {
                    ClassInstanceCreation creation = (ClassInstanceCreation) varFragment.getInitializer();
                    String varName = varFragment.getName().toString();
                    Type type = creation.getType();

                    NewObject object = new NewObject(varName, type);
                    newInstances.put(varName, type);
                    NewObjectService.put(object);

//                    System.out.println("Feld-Variable: " + varName + " - Typ: " + type);
//                    System.out.println("instanceCreation (Feld): " + creation);
                }
            }
        }
        return super.visit(node);
    }



    public List<Type> getTypeList(){
        return typeList;
    }

    public Map<String, Type> getNewInstances() {
        return newInstances;
    }
}
