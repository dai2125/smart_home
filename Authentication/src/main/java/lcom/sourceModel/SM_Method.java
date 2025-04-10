package lcom.sourceModel;

import com.home.asm.*;
import com.home.asm.NewObjectService;
import lcom.utils.models.Vertex;
import lcom.visitors.DirectAceessFieldVisitor;
import lcom.visitors.InstanceOfVisitor;
import org.eclipse.jdt.core.dom.*;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SM_Method extends SM_SourceItem implements Vertex {
    private boolean abstractMethod;
    private boolean finalMethod;
    private boolean staticMethod;
    private boolean isConstructor;
    private SM_Type parentType;

    private MethodDeclaration methodDeclaration;
    private List<SM_Method> calledMethodsList = new ArrayList<>();
    private List<SM_Parameter> parameterList = new ArrayList<>();
    private List<SM_LocalVar> localVarList = new ArrayList<>();
    private List<MethodInvocation> calledMethods = new ArrayList<>();
    private List<SM_Type> referencedTypeList = new ArrayList<>();
    private List<SimpleName> namesInMethod = new ArrayList<>();
    private List<FieldAccess> thisAccessesInMethod = new ArrayList<>();
    private List<SM_Field> directFieldAccesses = new ArrayList<>();
    private List<SM_Field> superClassFieldAccesses = new ArrayList<>();
    private List<Type> typesInInstanceOf = new ArrayList<>();
    private List<SM_Type> smTypesInInstanceOf = new ArrayList<>();
    private List<SM_Type> smTypesInNewStatements = new ArrayList<>();
    private List<Type> newStatementTypes;
    private boolean isOverridden;

    public SM_Method(MethodDeclaration methodDeclaration, SM_Type typeObj) {
        name = methodDeclaration.getName().toString();
        this.parentType = typeObj;
        this.methodDeclaration = methodDeclaration;
        setMethodInfo(methodDeclaration);
        setAccessModifier(methodDeclaration.getModifiers());
//        System.out.println("WWWW: " + methodDeclaration.getName() + ", " + methodDeclaration.parameters());
//        printMethodDetails();

//        String targetClass = TargetClass.getInstance().getTargetClass();
//        System.out.println("targetClass3 " + targetClass);

//        System.out.println("\nSM_METHOD: ParentType.name " + parentType.name);

        if(!CreatorPrincipleService.contains(parentType.name)) {
            CreatorPrinciple creatorPrinciple = new CreatorPrinciple(parentType.name);

            // TODO hier Kommentar rückgängig machen
//            creatorPrinciple.addFunctionToList(methodDeclaration.getName().toString());

            CreatorPrincipleService.put(creatorPrinciple);
        } else {
            CreatorPrinciple creatorPrinciple = CreatorPrincipleService.get(parentType.name);
            // TODO hier Kommentar rückgängig machen
//            creatorPrinciple.addFunctionToList(methodDeclaration.getName().toString());

            CreatorPrincipleService.put(creatorPrinciple);
        }



//        System.out.println("methode name: " + methodDeclaration.getName());
//        System.out.println("parameter: " + methodDeclaration.parameters().stream().toList());
//        System.out.println("return type: " + methodDeclaration.getReturnType2());
//        System.out.println("called methods: " + calledMethods.stream().map(MethodInvocation::toString).toList());

        //        System.out.println(name + " " + parameterList.stream().map(SM_Parameter::getName).toList());
//        System.out.println(name + " " + calledMethodsList.stream().map(SM_Method::getName).toList());
//        System.out.println(name + " " + namesInMethod.stream().map(SimpleName::toString).toList());


    }

    private void setMethodInfo(MethodDeclaration method) {
        int modifiers = method.getModifiers();

//        System.out.println("MethodDeclaration: " + method.getName());
//        System.out.println("method.getModifiers(): " + method.getModifiers());
//        System.out.println("Modifier.isAbstract(modifiers): " + Modifier.isAbstract(modifiers));
//        System.out.println("Modifier.isFinal(modifiers): " + Modifier.isFinal(modifiers));
//        System.out.println("Modifier.isStatic(modifiers): " + Modifier.isStatic(modifiers));
//        System.out.println("method.isConstructor(): "+ method.isConstructor());
//        System.out.println("method.getParent(): " + method.getParent().toString());

        if (Modifier.isAbstract(modifiers))
            abstractMethod = true;
        if (Modifier.isFinal(modifiers))
            finalMethod = true;
        if (Modifier.isStatic(modifiers))
            staticMethod = true;
        if (method.isConstructor())
            isConstructor = true;
        setMethodOverridden();
    }

    public boolean isAbstract() {
        return this.abstractMethod;
    }

    public boolean isStatic() {
        return this.staticMethod;
    }

    public boolean isFinal() {
        return this.finalMethod;
    }

    public boolean isConstructor() {
        return this.isConstructor;
    }

    public SM_Type getParentType() {
        return parentType;
    }

    public List<SM_Parameter> getParameterList() {
        return parameterList;
    }

    public List<SM_LocalVar> getLocalVarList() {
        return localVarList;
    }

    public List<SM_Method> getCalledMethods() {
        return calledMethodsList;
    }

    private void parseParameters() {
        for (SM_Parameter param : parameterList) {
            param.parse();
        }
    }

    private void parseLocalVar() {
        for (SM_LocalVar var : localVarList) {
            var.parse();
        }
    }

    @Override
    public void parse() {
        populateMethodInvocations();
        populateParameters();
        populateLocalVars();
        populateFieldAccess();
        populateInstanceOf();
        populateNewStatement();
    }

    private void populateNewStatement() {

        // TODO CREATOR PRINCIPLE

        /*
        *   in newStatementTypes speicherst du die Objekt Name und die Objekt Typen ab
        *   die Map soll helfen beim Tracken des Objekts
        *   du willst überprüfen ob die Klasse die das Objekt erstellt auch die Funktionen des Objekts verwendet

        *   Welche Objekte erstellt die Klasse?
        *   Werden die Funktionen des Objektes verwendet?
        *   Kohäsivität?
        * */


        NewStatementVisitor newStatementVisitor = new NewStatementVisitor(methodDeclaration);
        methodDeclaration.accept(newStatementVisitor);
        newStatementTypes = newStatementVisitor.getTypeList();

        Map<String, Type> newInstances = newStatementVisitor.getNewInstances();

//        System.out.println(parentType.getName());
//        System.out.println("\t" + methodDeclaration.getName());
//        System.out.println("\t\t" + newStatementVisitor.getTypeList().stream().map(Type::toString).toList());
//        System.out.println("\t\t" + newInstances.stream().map(Type::toString).toList());
//        System.out.println("\t\t" + newInstances.forEach(key, value), ->System.out.println(););
//        newInstances.forEach((key, value) -> System.out.println(methodDeclaration.getName() + " - new Instances " + key + ":" + value));
//        System.out.println(methodDeclaration.getName());


        MethodInvVisitor methodInvVisitor = new MethodInvVisitor(methodDeclaration);
        methodDeclaration.accept(methodInvVisitor);
        List<MethodInvocation> invocations = methodInvVisitor.getCalledMethods();

        for(MethodInvocation invocation : invocations) {
            Expression expression = invocation.getExpression();
            if(expression instanceof SimpleName) {
                String objectName = expression.toString();

                NewObject object = NewObjectService.get(objectName);

                if(object != null) {
                    object.addFunctionToList(invocation.getName().toString());
//                    System.out.println(object);
                }

//                System.out.println("\t\tMethodenaufruf: " + objectName + "." + invocation.getName());
                if(newInstances.containsKey(objectName)) {
//                    System.out.println("\t\tMethodenaufruf auf erstelltem Objekt: " + newInstances.get(objectName) + " " + objectName);
                }
            }
        }
    }

    private void populateInstanceOf() {
        InstanceOfVisitor instanceOfVisitor = new InstanceOfVisitor();
        methodDeclaration.accept(instanceOfVisitor);
        List<Type> instanceOfTypes = instanceOfVisitor.getTypesInInstanceOf();
        if (instanceOfTypes.size() > 0) {
            typesInInstanceOf.addAll(instanceOfTypes);
        }
    }

    private void populateFieldAccess() {
        DirectAceessFieldVisitor directAceessFieldVisitor = new DirectAceessFieldVisitor();
        methodDeclaration.accept(directAceessFieldVisitor);
        List<SimpleName> names = directAceessFieldVisitor.getNames();
        List<FieldAccess> thisAccesses = directAceessFieldVisitor.getThisAccesses();
        if (names.size() > 0) {
            namesInMethod.addAll(names);
        }
        if (thisAccesses.size() > 0) {
            thisAccessesInMethod.addAll(thisAccesses);
        }
    }

    private void populateLocalVars() {
        LocalVarVisitor localVarVisitor = new LocalVarVisitor(this);
        methodDeclaration.accept(localVarVisitor);
        List<SM_LocalVar> lList = localVarVisitor.getLocalVarList();
        if (lList.size() > 0) {
            localVarList.addAll(lList);
        }
        parseLocalVar();
    }

    private void populateParameters() {
        List<SingleVariableDeclaration> variableList = methodDeclaration.parameters();
        for (SingleVariableDeclaration var : variableList) {
            VariableVisitor parameterVisitor = new VariableVisitor(this);
            // methodDeclaration.accept(parameterVisitor);
            var.accept(parameterVisitor);
            List<SM_Parameter> pList = parameterVisitor.getParameterList();
            if (pList.size() > 0) {
                parameterList.addAll(pList);
            }
            parseParameters();
        }
    }

    private void populateMethodInvocations() {
        MethodInvVisitor invVisitor = new MethodInvVisitor(methodDeclaration);
        methodDeclaration.accept(invVisitor);
        List<MethodInvocation> invList = invVisitor.getCalledMethods();
        if (invList.size() > 0) {
            calledMethods.addAll(invList);
        }
    }

    @Override
    public void resolve() {
        for (SM_Parameter param : parameterList) {
            param.resolve();
        }
        for (SM_LocalVar localVar : localVarList) {
            localVar.resolve();
        }
        calledMethodsList = (new Resolver()).inferCalledMethods(calledMethods, parentType);
        resolveNewInstanceTypes();
        setReferencedTypes();
        setDirectFieldAccesses();
        setSMTypesInInstanceOf();
    }

    private void resolveNewInstanceTypes() {
        Resolver resolver = new Resolver();
        for (Type type : newStatementTypes) {
            SM_Type smType = resolver.resolveType(type, parentType.getParentPkg().getParentProject());
            if (smType != null && !smTypesInNewStatements.contains(smType)) {
                smTypesInNewStatements.add(smType);
            }
        }
    }

    private void setReferencedTypes() {
        for (SM_Parameter param : parameterList) {
            if (!param.isPrimitiveType()) {
                addunique(param.getType());
            }
        }
        for (SM_LocalVar localVar : localVarList) {
            if (!localVar.isPrimitiveType()) {
                addunique(localVar.getType());
            }
        }
        for (SM_Method methodCall : calledMethodsList) {
            if (methodCall.isStatic()) {
                addunique(methodCall.getParentType());
            }
        }
        for (SM_Type type : smTypesInNewStatements) {
            addunique(type);
        }
    }

    private void setDirectFieldAccesses() {
        for (FieldAccess thisAccess : thisAccessesInMethod) {
            SM_Field sameField = getFieldWithSameName(thisAccess.getName().toString());
            if (sameField != null) {
                if (!directFieldAccesses.contains(sameField)) {
                    directFieldAccesses.add(sameField);
                }
            }
            SM_Field field = getFieldFromSuperClass(thisAccess.getName().toString());
            if (field != null) {
                if (!superClassFieldAccesses.contains(field)) {
                    superClassFieldAccesses.add(field);
                }
            }
        }
        for (SimpleName name : namesInMethod) {
            if (!existsAsNameInLocalVars(name.toString())) {
                SM_Field sameField = getFieldWithSameName(name.toString());
                if (sameField != null) {
                    if (!directFieldAccesses.contains(sameField)) {
                        directFieldAccesses.add(sameField);
                    }
                }
                SM_Field field = getFieldFromSuperClass(name.toString());
                if (field != null) {
                    if (!superClassFieldAccesses.contains(field)) {
                        superClassFieldAccesses.add(field);
                    }
                }
            }
        }
    }

    private SM_Field getFieldFromSuperClass(String fieldName) {
        if (parentType.getSuperTypes().isEmpty())
            return null;
        for (SM_Type type : parentType.getSuperTypes())
            for (SM_Field field : type.getFieldList()) {
                if (fieldName.equals(field.getName())) {
                    return field;
                }
            }
        return null;
    }

    private boolean existsAsNameInLocalVars(String name) {
        for (SM_LocalVar localVar : localVarList) {
            if (name.equals(localVar.getName())) {
                return true;
            }
        }
        return false;
    }

    private SM_Field getFieldWithSameName(String name) {
        for (SM_Field field : parentType.getFieldList()) {
            if (name.equals(field.getName())) {
                return field;
            }
        }
        return null;
    }

    private void setSMTypesInInstanceOf() {
        Resolver resolver = new Resolver();
        for (Type type : typesInInstanceOf) {
            SM_Type smType = resolver.resolveType(type, parentType.getParentPkg().getParentProject());
            if (smType != null && !smTypesInInstanceOf.contains(smType)) {
                smTypesInInstanceOf.add(smType);
            }
        }
    }

    private void addunique(SM_Type variableType) {
        if (!referencedTypeList.contains(variableType))
            referencedTypeList.add(variableType);
    }

    List<SM_Type> getReferencedTypeList() {
        return referencedTypeList;
    }

    public List<SM_Field> getDirectFieldAccesses() {
        return directFieldAccesses;
    }

    public List<SM_Field> getNonStaticFieldAccesses() {
        List<SM_Field> fields = new ArrayList<>();
        for (SM_Field field : directFieldAccesses)
            if (!field.isStatic())
                fields.add(field);
        return fields;
    }

    private void setMethodOverridden() {
        MarkerAnnotationVisitor markerAnnotationVisitor = new MarkerAnnotationVisitor();
        methodDeclaration.accept(markerAnnotationVisitor);
        isOverridden = markerAnnotationVisitor.isOverridden();
    }

    public boolean isOverridden() {
        return isOverridden;
    }

    public List<SM_Field> getFieldAccessesFromSuperClass() {
        return superClassFieldAccesses;
    }

    public List<SM_Type> getSMTypesInInstanceOf() {
        return smTypesInInstanceOf;
    }

    public MethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }

    public void printMethodDetails() {
        System.out.println("=========================================");
        System.out.println("Methodenname: " + name);
        System.out.println("Ist abstrakt: " + abstractMethod);
        System.out.println("Ist final: " + finalMethod);
        System.out.println("Ist statisch: " + staticMethod);
        System.out.println("Ist Konstruktor: " + isConstructor);
        System.out.println("Ist überschrieben (@Override): " + isOverridden);
        System.out.println("Elternklasse: " + (parentType != null ? parentType.getName() : "Unbekannt"));

        // Parameterliste
        System.out.println("Parameter:");
        for (SM_Parameter param : parameterList) {
            System.out.println("  - " + param.getType().getName() + " " + param.getName());
        }

        // Lokale Variablen
        System.out.println("Lokale Variablen:");
        for (SM_LocalVar localVar : localVarList) {
            System.out.println("  - " + localVar.getType().getName() + " " + localVar.getName());
        }

        // Aufgerufene Methoden
        System.out.println("Aufgerufene Methoden:");
        for (SM_Method method : calledMethodsList) {
            System.out.println("  - " + method.getParentType().getName() + "." + method.getName());
        }

        // Direkte Feldzugriffe
        System.out.println("Direkte Feldzugriffe:");
        for (SM_Field field : directFieldAccesses) {
            System.out.println("  - " + field.getName() + " (Typ: " + field.getType().getName() + ")");
        }

        // Zugriff auf Superclass-Felder
        System.out.println("Feldzugriffe auf Superklasse:");
        for (SM_Field field : superClassFieldAccesses) {
            System.out.println("  - " + field.getName() + " (Typ: " + field.getType().getName() + ")");
        }

        // `instanceof`-Verwendungen
        System.out.println("Verwendete Typen in instanceof:");
        for (SM_Type type : smTypesInInstanceOf) {
            System.out.println("  - " + type.getName());
        }

        // Instanzen von Objekten, die mit `new` erstellt wurden
        System.out.println("Instanziierte Typen mit 'new':");
        for (SM_Type type : smTypesInNewStatements) {
            System.out.println("  - " + type.getName());
        }

        System.out.println("=========================================");
    }

}
