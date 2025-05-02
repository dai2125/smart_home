package com.home.openClosedPrinciple.firstAnalysis;

import com.home.openClosedPrinciple.OpenClosedDataModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;

//import static com.home.readCSV.CountLineOfCode.countMethodLines;

public class ReadMetrics {

    public void readTypeMetrics() {
        String line = "";
        String splitBy = ",";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\openClosedPrinciple\\firstAnalysis\\result\\TypeMetrics.csv"));
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(splitBy);
                String projectName = data[0];
                String packageName = data[1];
                String className = data[2];
                int numberOfFields = Integer.parseInt(data[3]);
                int numberOfPublicFields = Integer.valueOf(data[4]);
                int numberOfMethods = Integer.valueOf(data[5]);
                int numberOfPublicMethods = Integer.valueOf(data[6]);
                int linesOfCode = Integer.valueOf(data[7]);
                int weightedMethodsPerClass = Integer.valueOf(data[8]);
                int numberOfChildren = Integer.valueOf(data[9]);
                int depthOfInheritanceTree = Integer.valueOf(data[10]);
                Double lackOfCohesionInMethods = Double.valueOf(data[11]);
                int fanIn = Integer.valueOf(data[12]);
                int fanOut = Integer.valueOf(data[13]);
                String filePath = data[14];
                int lineNumber = Integer.valueOf(data[15]);

                // double lcom, int nom, int loc, int lineNumber, int nof
//                int linesOfMethod = countMethodLines(filePath);

//                checkOpenClosedPrinciple(com.home.authentication.DatabaseConnection.class);

//                System.out.println("Class Name=" + data[2] + ", NOF=" + data[3] + ", NOPF=" + data[4] + ", NOM=" + data[5] + ", NOPM=" + data[6] + ", LOC=" + data[7] + ", WMC=" + data[8] + ", NC=" + data[9] + ", DIT=" + data[10] + ", LCOM=" + data[11] + ", FANIN=" + data[12] + ", FANOUT=" + data[13] + ", Line no=" + data[15]);
                System.out.println("NOF=" + data[3] + ", NOPF=" + data[4] + ", NOM=" + data[5] + ", NOPM=" + data[6] + ", LOC=" + data[7] + ", WMC=" + data[8] + ", NC=" + data[9] + ", DIT=" + data[10] + ", LCOM=" + data[11] + ", FANIN=" + data[12] + ", FANOUT=" + data[13] + ", Line no=" + data[15] + " Class Name=" + data[2]);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDesignSmells() {

        String line = "";
        String splitBy = ",";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\openClosedPrinciple\\firstAnalysis\\result\\DesignSmells.csv"));
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(splitBy);
                String projectName = data[0];
                String packageName = data[1];
                String className = data[2];
                String designSmell = data[3];
                String causeOfTheSmell = data[4];

                if(designSmell.equals("Broken Hierarchy")) {

                    String test = packageName + "." + causeOfTheSmell.substring(causeOfTheSmell.indexOf(':') + 1, causeOfTheSmell.length()).trim();
                    Class<?> clazz = Class.forName(test);

                    System.out.println(clazz.isInterface());
                    System.out.println(Modifier.isAbstract(clazz.getModifiers()));

                    if(clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                        String something = clazz.getName();
                        something = something.substring(something.lastIndexOf('.') + 1, something.length()).trim();

                        com.home.openClosedPrinciple.OpenClosedDataModel openClosedDataModel = getOpenClosedDataModel(something);

                        System.out.println("The class " + openClosedDataModel.className() + " was detected, it violates the Open Closed Principle. The class has a value of " + openClosedDataModel.depthOfInheritanceTree() + " depth of inheritance tree, which shouldn´t be greater than 0");
                    }

                    if(!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                        Class<?> clazz2 = Class.forName(packageName + "." + className);
                        Class<?> superclass = clazz2.getSuperclass();
                        if(superclass != null) {
                            System.out.println(clazz2.getName() + " erbt von " + superclass.getName());

                            String something = superclass.getName();
                            something = something.substring(something.lastIndexOf('.') + 1, something.length()).trim();
                            System.out.println(something + " something");

                            com.home.openClosedPrinciple.OpenClosedDataModel openClosedDataModel = getOpenClosedDataModel(something);

                            if(openClosedDataModel.depthOfInheritanceTree() > 0) {
                                System.out.println("The class " + openClosedDataModel.className() + " was detected, it violates the Open Closed Principle. The class has a value of " + openClosedDataModel.depthOfInheritanceTree() + " depth of inheritance tree, which shouldn´t be greater than 0");
                            } else {
                                System.out.println("The class " + openClosedDataModel.className() + " was detected, it violates the Open Closed Principle. The class has " + openClosedDataModel.numberOfMethods() + " methods in comparision to a value of " + openClosedDataModel.weightedMethodsPerClass() + " weigthed method per class");
                            }
//                            System.out.println("Class Name=" + openClosedDataModel.className() + " Number of Children=" + openClosedDataModel.numberOfChildren() + " Number of Methods=" + openClosedDataModel.numberOfMethods() + " Depth of Inheritance Tree=" + openClosedDataModel.depthOfInheritanceTree() + " Weight Methods Per Class=" + openClosedDataModel.weightedMethodsPerClass());
                        } else {
                            System.out.println(clazz2.getName() + " hat keine Oberklasse ");
                        }
                    }
                    Class<?> superclass = clazz.getSuperclass();

//                    OpenClosedDataModel openClosedDataModel = getOpenClosedDataModel(className);
//                    System.out.println(openClosedDataModel.className() + " " + openClosedDataModel.numberOfChildren());

//                    System.out.println(test);

                    System.out.println("Class name=" + className + ", Design Smell=" + designSmell + ", Cause of the Smell=" + causeOfTheSmell);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public com.home.openClosedPrinciple.OpenClosedDataModel getOpenClosedDataModel(String name) {
        com.home.openClosedPrinciple.OpenClosedDataModel openClosedDataModel = null;
        String line = "";
        String splitBy = ",";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\openClosedPrinciple\\firstAnalysis\\result\\TypeMetrics.csv"));
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(splitBy);
                String packageName = data[1];
                String className = data[2];
                int numberOfMethods = Integer.valueOf(data[5]);
                int weightedMethodsPerClass = Integer.valueOf(data[8]);
                int numberOfChildren = Integer.valueOf(data[9]);
                int depthOfInheritanceTree = Integer.valueOf(data[10]);

                if(className.equals(name)) {
                    return new OpenClosedDataModel(packageName, className, numberOfMethods, weightedMethodsPerClass, numberOfChildren, depthOfInheritanceTree);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

