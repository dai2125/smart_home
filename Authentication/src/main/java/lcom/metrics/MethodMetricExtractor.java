package lcom.metrics;

import com.home.asm.ClassService;
import com.home.asm.InspectedClass;
import com.home.asm.MethodInformation;
import lcom.metrics.algorithms.ILCOM;
import lcom.metrics.algorithms.YALCOM;
import lcom.sourceModel.SM_Method;
import lcom.visitors.MethodControlFlowVisitor;

public class MethodMetricExtractor implements MetricExtractor{

    private SM_Method method;
    private MethodMetrics methodMetrics;

    public MethodMetricExtractor(SM_Method method) {
        this.method = method;
    }

    @Override
    public MethodMetrics extractMetrics(InspectedClass inspectedClass) {
        methodMetrics = new MethodMetrics();
        extractNumOfParametersMetrics();
        extractCyclomaticComplexity(inspectedClass);
        extractNumberOfLines();
        methodMetrics.setMethod(method);
        return methodMetrics;
    }


    private void extractNumOfParametersMetrics() {
        methodMetrics.setNumOfParameters(method.getParameterList().size());
    }

    private void extractCyclomaticComplexity(InspectedClass inspectedClass) {
        methodMetrics.setCyclomaticComplexity(calculateCyclomaticComplexity(inspectedClass));
    }

    private int calculateCyclomaticComplexity(InspectedClass inspectedClass) {
        MethodControlFlowVisitor visitor = new MethodControlFlowVisitor();
        method.getMethodDeclaration().accept(visitor);

        methodMetrics.setNumOfIfStatements(visitor.getNumOfIfStatements());
        methodMetrics.setNumOfSwitchCaseStatementsWitoutDefault(visitor.getNumOfSwitchCaseStatementsWitoutDefault());
        methodMetrics.setNumOfForStatements(visitor.getNumOfForStatements());
        methodMetrics.setNumOfWhileStatements(visitor.getNumOfWhileStatements());
        methodMetrics.setNumOfDoStatements(visitor.getNumOfDoStatements());
        methodMetrics.setNumOfForeachStatements(visitor.getNumOfForeachStatements());
        methodMetrics.setNumOfThrowStatements(visitor.getNumOfThrowStatements());
        methodMetrics.setNumOfReturnStatements(visitor.getNumOfReturnStatements());

        MethodInformation methodInformation = new MethodInformation(method.getName(),
                                                                    visitor.getNumOfIfStatements(),
                                                                    visitor.getNumOfSwitchCaseStatementsWitoutDefault(),
                                                                    visitor.getNumOfForStatements(),
                                                                    visitor.getNumOfWhileStatements(),
                                                                    visitor.getNumOfDoStatements(),
                                                                    visitor.getNumOfForeachStatements(),
                                                                    visitor.getNumOfReturnStatements(),
                                                                    visitor.getNumOfThrowStatements(),
                                                                    visitor.getAllReturnStatements(),
                                                                    visitor.getAllThrowStatements());

        if (methodHasBody()) {
            String body = method.getMethodDeclaration().getBody().toString();
            int length = body.length();
//			long newlines = body.lines().count();
            methodInformation.setNumOfLines(length - body.replace("\n", "").length());
        }

        inspectedClass.addMethodInformation(methodInformation);



        return visitor.getNumOfIfStatements()
                + visitor.getNumOfSwitchCaseStatementsWitoutDefault()
                + visitor.getNumOfForStatements()
                + visitor.getNumOfWhileStatements()
                + visitor.getNumOfDoStatements()
                + visitor.getNumOfForeachStatements()
                + 1;
    }

    private void extractNumberOfLines() {
        if (methodHasBody()) {
            String body = method.getMethodDeclaration().getBody().toString();
            int length = body.length();
//			long newlines = body.lines().count();
            methodMetrics.setNumOfLines(length - body.replace("\n", "").length());
        }
    }

    private boolean methodHasBody() {
        return method.getMethodDeclaration().getBody() != null;
    }





}