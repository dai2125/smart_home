package com.home.coupling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadCSV {

    public static void main(String[] args) {

        String typeMetricsPath = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\coupling\\thirdAnalysis\\result\\TypeMetrics.csv";
        String methodMetricsPath = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\coupling\\thirdAnalysis\\result\\MethodMetrics.csv";

        try {
            System.out.println("Reading Type Metrics:");
            readCSV(typeMetricsPath, true);

            System.out.println("\nReading Method Metrics:");
            readCSV(methodMetricsPath, false);

        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }

    private static void readCSV(String filePath, boolean isTypeMetrics) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;

        // Skip header
        bufferedReader.readLine();

        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(",");

            if (isTypeMetrics) {
                String className = data[2];
                String nof = data[3];
                String nopf = data[4];
                String nom = data[5];
                String nopm = data[6];
                String loc = data[7];
                String wmc = data[8];
                String nc = data[9];
                String dit = data[10];
                String lcom = data[11];
                String fanIn = data[12];
                String fanOut = data[13];
                String lineNumber = data[15];

                System.out.println(className + ", \t\tNOF=" + nof + ", NOPF=" + nopf + ", NOM=" + nom +
                        ", NOPM=" + nopm + ", LOC=" + loc + ", WMC=" + wmc + ", NC=" + nc + ", DIT=" + dit +
                        ", LCOM=" + lcom + ", FANIN=" + fanIn + ", FANOUT=" + fanOut + ", Line no=" + lineNumber);
            } else {
                String className = data[2];
                String methodName = data[3];
                String loc = data[4];
                String cc = data[5];
                String pc = data[6];
                String lineNumber = data[7];

                System.out.println(className + ", \t\tLOC=" + loc + ", CC=" + cc + ", PC=" + pc + ", Line no=" + lineNumber + ", Method Name=" + methodName);
            }
        }

        bufferedReader.close();
    }
}
