package com.home.openClosedPrinciple;

public record OpenClosedDataModel(String packageName, String className, int numberOfMethods,
                                  int weightedMethodsPerClass, int numberOfChildren, int depthOfInheritanceTree) {
}
