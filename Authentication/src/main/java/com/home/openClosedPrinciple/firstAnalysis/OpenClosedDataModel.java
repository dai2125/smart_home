package com.home.openClosedPrinciple.firstAnalysis;

public record OpenClosedDataModel(String packageName, String className, int numberOfMethods,
                                  int weightedMethodsPerClass, int numberOfChildren, int depthOfInheritanceTree) {
}
