package com.home.creator.firstAnalysis;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadCSV {


    public static void main(String[] args) throws FileNotFoundException {

        com.home.informationExpert.ReadCSV reader = new com.home.informationExpert.ReadCSV();

        String line = "";
        String splitBy = ",";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\creator\\firstAnalysis\\result\\TypeMetrics.csv"));
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


                System.out.println("Class Name=" + data[2] + "\t\t,NOF=" + data[3] + ",NOPF=" + data[4] + ",NOM=" + data[5] + ",NOPM=" + data[6] + ",LOC=" + data[7] + ",WMC=" + data[8] + ",NC=" + data[9] + ",DIT=" + data[10] + ",LCOM=" + data[11] + ",FANIN=" + data[12] + ",FANOUT=" + data[13] + ",Line no=" + data[15]);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFullClassName(String packageName, String className) {
        StringBuilder stringBuilder = new StringBuilder(packageName);
        stringBuilder.append('.');
        stringBuilder.append(className);
        return stringBuilder.toString();
    }

    private Class<?> getClass(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return clazz;
    }
}
