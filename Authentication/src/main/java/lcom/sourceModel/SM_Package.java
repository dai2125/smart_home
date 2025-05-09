package lcom.sourceModel;

import lcom.InputArgs;
import lcom.metrics.TypeMetrics;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SM_Package extends SM_SourceItem {
    private List<CompilationUnit> compilationUnitList;
    private List<SM_Type> typeList = new ArrayList<>();
    private SM_Project parentProject;
    private Map<SM_Type, TypeMetrics> metricsMapping = new HashMap<>();
    private InputArgs inputArgs;
    private String targetClass;

    SM_Package(String packageName, SM_Project parentObj, InputArgs inputArgs, String targetClass) {
        name = packageName;
        compilationUnitList = new ArrayList<>();
        parentProject = parentObj;
        this.inputArgs = inputArgs;
        this.targetClass = targetClass;
    }

    SM_Project getParentProject() {
        return parentProject;
    }

    public List<SM_Type> getTypeList() {
        return typeList;
    }

    void addCompilationUnit(CompilationUnit unit) {
        compilationUnitList.add(unit);
    }

    private void addNestedClass(List<SM_Type> list) {
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                typeList.add(list.get(i));
                list.get(0).addNestedClass(list.get(i));
                list.get(i).setNestedClass(list.get(0).getTypeDeclaration());
            }
        }
    }

    private void parseTypes(SM_Package parentPkg) {
        for (SM_Type type : typeList) {
            type.parse();
        }
    }

    @Override
    public void parse() {
        for (CompilationUnit unit : compilationUnitList) {
            TypeVisitor visitor = new TypeVisitor(unit, this, inputArgs);
            unit.accept(visitor);
            List<SM_Type> list = visitor.getTypeList();
            if (list.size() > 0) {
                if (list.size() == 1) {
                    typeList.addAll(list); // if the compilation unit contains
                } else {
                    typeList.add(list.get(0));
                    addNestedClass(list);
                }
            }
        }
        parseTypes(this);
    }

    @Override
    public void resolve() {
        for (SM_Type type : typeList) {
            type.resolve();
        }
    }

    void extractTypeMetrics() {
        for(int i = 0; i < typeList.size(); i++) {
            //System.out.println(888 + " " + typeList.get(i).name);
            //System.out.println(888 + " " + typeList.get(i).getFieldList().stream().map(SM_Field::getName).toList());
            //System.out.println(888 + " " + typeList.get(i).getMethodList().stream().map(SM_Method::getName).toList());

        }
        for (SM_Type type : typeList) {
            // System.out.println(777 + " " + type.getName() + " type: "+ type.toString() + ", type.size(): "+ typeList.size());
            type.extractMethodMetrics(targetClass);
//            System.out.println("SM_PACKAGE: " + type.toString());
            TypeMetrics metrics = new TypeMetrics(type);
            metrics.extractMetrics(targetClass);
            metricsMapping.put(type, metrics);
        }
    }

    public TypeMetrics getMetricsFromType(SM_Type type) {
        return metricsMapping.get(type);
    }

    public void exportResults() {
        for (SM_Type type : typeList) {
            exportMetricsToCSV(metricsMapping.get(type), type.getName());
        }
    }

    private void exportMetricsToCSV(TypeMetrics typeMetrics, String typeName) {
        String path = inputArgs.getOutputFolder()
                + File.separator + "TypeMetrics.csv";
//        CSVUtils.addToCSVFile(path, getMetricsAsARow(typeMetrics, typeName));
    }
    private String getMetricsAsARow(TypeMetrics metrics, String typeName) {
        return getParentProject().getName()
                + "," + getName()
                + "," + typeName
                + "," + metrics.getLcom1()
                + "," + metrics.getLcom2()
                + "," + metrics.getLcom3()
                + "," + metrics.getLcom4()
                + "," + metrics.getLcom5()
                + "," + metrics.getYalcom()
                + "\n";
    }
}
