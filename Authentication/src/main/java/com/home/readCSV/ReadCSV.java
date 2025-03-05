package com.home.readCSV;

import com.home.authentication.User;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import static com.home.readCSV.CountLineOfCode.analyzeClass;
import static com.home.readCSV.CountLineOfCode.countMethodLines;

public class ReadCSV {

    public static void singleResponsibilityPrinciple(double lcom, int nom, int loc, int lineNumber, int nof, String className, int linesOfMethod) {
        System.out.println("singleResponsibilityPrinciple Analysis: " + className);

        if(nom < 1) {
            System.out.println("Klasse hat keine Methoden");
            return;
        } else if(nom == 1) {
            System.out.println("Klasse hat nur eine Methode, schwierig für Analyse");
            return;
        } else if(nom > 1) {
            System.out.println("Klasse hat " + nom + " Methoden");

            lineNumber = lineNumber - 3;
            System.out.println("lineNumber: " + lineNumber + " das sind imports und package ohne constructor und Leerzeilen");
            System.out.println("numberOfFields: " + nof);
            loc = loc - nof;
            System.out.println("lines of code: " + loc + " das sind die Zeilen der Methoden");
//            System.out.println("durschnittliche anzahl an zeilen pro methode " + avgLinesPerMethod);
            System.out.println("anzahl der Zeilen aller Methoden " + linesOfMethod);

            if(lcom != 0.0 && lcom != 1.0 && lcom != -1.0) {
                System.out.println("KOHÄSION HAT EINE MERKWÜRDIGEN WERT: " + lcom);
            }

            if (nom > 1 && nof > 0 && lcom == 1.0) {
                System.out.println("Klasse hat viele Abhängigkeiten, überarbeitung der Klasse eventuell erforderlich.");
            } else if (nom > 1 && lcom == 0.0 && (linesOfMethod / nom) <= 20) {
                System.out.println("Klasse erfüllt das Single Responsibility Prinzip sehr wahrscheinlich.");
            } else if (nom > 1 && lcom == 0.0 && (linesOfMethod / nom) > 20) {
                System.out.println("Klasse erfüllt das Single Responsibility Prinzip sehr wahrscheinlich, aber hat sehr viele Zeilen Code pro Methode.");
            } else if(nom > 1 && (linesOfMethod / nom) <= 20 && lcom == -1.0) {
                System.out.println("Klasse kann nicht überprüft werden, vielleicht handelt es sich um ein Interface");
            } else if(lcom == 0.0) {
                System.out.println("Klasse hat gute Kohäsion");
            } else {
                System.out.println("Es müssen weitere Tests gemacht werden für eine analyse.");
            }
        }
    }

    public static void openClosedPrinciple(int dit, int wmc, String className) {
        // höchster WMC Wert 16 AuthenticationApplication
        // niedrigster WMC Wert 1 DatabaseConnection
        // TODO überprüfe den DIT ob Abstract, Interface oder Polymorphism vorliegt
        // TODO WMC auch überprüfen
        if(dit == 1) {
            System.out.println("Klasse hat " + dit + " Interface implementiert");
        } else if(dit > 1) {
            System.out.println("Klasse hat mehrere Interfaces implementiert");
        }
        if(wmc < 10) {
            System.out.println("Klasse hat gut erweiterbare Methoden");
        } else if(wmc > 10) {
            System.out.println("Klasse hat schwer erweiterbare Methoden");
        }
    }

    public static void checkOpenClosedPrinciple(Class<?> clazz) {
        Class<?> superClass = clazz.getSuperclass();
        boolean isOpenForExtension = superClass != null && !superClass.equals(Object.class);

        Class<?>[] interfaces = clazz.getInterfaces();
        boolean implementsInterface = interfaces.length > 0;

        Method[] methods = clazz.getDeclaredMethods();
        boolean hasExtensionMethods = methods.length > 0;

        if (isOpenForExtension || implementsInterface || hasExtensionMethods) {
//            return true;
            System.out.println("Das OpenClosed Prinzip wird sehr wahrscheinlich erfüllt");
        } else {

            System.out.println("Das OpenClosed Prinzip wird sehr wahrscheinlich verletzt");
        }
//        return false;
    }

//    public static void main(String[] args) {
//        try {
//            String filePath = "C:/Users/aigne/IdeaProjects/smart_home/Authentication/src/main/java/com/home/authentication/DatabaseConnection.java"; // Pfad zur Java-Datei
//            Class<?> clazz = com.home.authentication.DatabaseConnection.class;
//
//            analyzeClass(filePath, clazz);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public static void main(String[] args) throws FileNotFoundException {

        ReadCSV reader = new ReadCSV();

        String line = "";
        String splitBy = ",";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\singleResponsibilityPrinciple\\firstAnalysis\\result\\TypeMetrics.csv"));
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
                int linesOfMethod = countMethodLines(filePath);
                singleResponsibilityPrinciple(lackOfCohesionInMethods, numberOfMethods, linesOfCode, lineNumber, numberOfFields, className, linesOfMethod);

//                checkOpenClosedPrinciple(com.home.authentication.DatabaseConnection.class);
                openClosedPrinciple(depthOfInheritanceTree, weightedMethodsPerClass, className);

                System.out.println("Class Name=" + data[2] + ", NOF=" + data[3] + ", NOPF=" + data[4] + ", NOM=" + data[5] + ", NOPM=" + data[6] + ", LOC=" + data[7] + ", WMC=" + data[8] + ", NC=" + data[9] + ", DIT=" + data[10] + ", LCOM=" + data[11] + ", FANIN=" + data[12] + ", FANOUT=" + data[13] + ", Line no=" + data[15]);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void isClassADataModel(String packageName, String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(getFullClassName(packageName, className));
        // TODO hier weiter machen
    }

    public void getReflection(String packageName, String className) {

        try {

            Class<?> clazz = getClass(getFullClassName(packageName, className));
            Method methods[] = clazz.getMethods();
            Method declaredMethods[] = clazz.getDeclaredMethods();
            Class<?> clazzes[] = clazz.getClasses();
            Annotation annotations[] = clazz.getAnnotations();
            Annotation declaredAnnotations[] = clazz.getDeclaredAnnotations();
            Constructor constructors[] = clazz.getConstructors();
            Constructor declaredConstructors[] = clazz.getDeclaredConstructors();
            AnnotatedType annotatedInterfaces[] = clazz.getAnnotatedInterfaces();
            Field decalredFields[] = clazz.getDeclaredFields();
            Class<?> declaringClass = clazz.getDeclaringClass();
            Method enclosingMethod = clazz.getEnclosingMethod();
            Constructor enclosingConstructor = clazz.getEnclosingConstructor();
            Class<?> enclosingClass = clazz.getEnclosingClass();
            int getModifiers = clazz.getModifiers();
            Module module = clazz.getModule();
            Class<?> getInterfaces[] = clazz.getInterfaces();
            Package packege = clazz.getPackage(); clazz.isLocalClass();
            String getPackageName = clazz.getPackageName();
            boolean isInterface = clazz.isInterface();
            clazz.getSigners();

            System.out.print("methods[]: ");
            for(int i = 0; i < methods.length; i++) {
                System.out.print(methods[i].getName() + ", ");
            }

            System.out.print("\ndeclardedMethods[]: ");
            for(int i = 0; i < declaredMethods.length; i++) {
                System.out.print(declaredMethods[i].getName() + ", ");
            }

            System.out.print("\nclazzes[]: ");
            for(int i = 0; i < clazzes.length; i++) {
                System.out.print(clazzes[i].getName() + ", ");
            }

            System.out.print("\nannotations[]: ");
            for(int i = 0; i < annotations.length; i++) {
                System.out.print(annotations[i].toString() + ", ");
            }

            System.out.print("\ndeclaredAnnotations[]: ");
            for(int i = 0; i < declaredAnnotations.length; i++) {
                System.out.print(declaredAnnotations[i].toString() + ", ");
            }

            System.out.print("\nconstructors[]: ");
            for(int i = 0; i < constructors.length; i++) {
                System.out.print(constructors[i].getName() + ", ");
            }

            System.out.print("\ndeclaredConstructors[]: ");
            for(int i = 0; i < declaredConstructors.length; i++) {
                System.out.print(declaredConstructors[i].getName() + ", ");
            }

            System.out.print("\nannotatedInterfaces[]: ");
            for(int i = 0; i < annotatedInterfaces.length; i++) {
                System.out.print(Arrays.toString(annotatedInterfaces[i].getAnnotations()) + " ");
                System.out.print(annotatedInterfaces[i].getDeclaredAnnotations() + ", ");
            }

            System.out.print("\ndeclaredFields[]: ");
            for(int i = 0; i < decalredFields.length; i++) {
                System.out.print(decalredFields[i].getName()+ ", ");
            }

            System.out.print("\ndeclaringClass[]: ");
            if(declaringClass != null) {
                System.out.print(declaringClass.getName());
            }

            System.out.print("\nenclosingMethod: ");
            if(enclosingMethod != null) {
                System.out.print(enclosingMethod.getName());
            }

            System.out.print("\nenclosingConstructor: ");
            if(enclosingConstructor != null) {
                System.out.print(enclosingConstructor.getName());
            }

            System.out.print("\nenclosingClass: ");
            if(enclosingClass != null) {
                System.out.print(enclosingClass.getName());
            }

            System.out.print("\ngetModifiers: ");
            System.out.print(getModifiers);

            System.out.print("\nmodule: ");
            System.out.print(module.getName());

            System.out.print("\ngetInterfaces[]: ");
            for(int i = 0; i < getInterfaces.length; i++) {
                System.out.print(getInterfaces[i]);
            }

            System.out.print("\npackage: ");
            System.out.print(packege.getName());

            System.out.print("\ngetPackageName: ");
            System.out.print(getPackageName);

            System.out.print("\nisInterface: ");
            System.out.print(isInterface);

            System.out.println();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void getReflection(Class<?> clazz) {
        Method methods[] = clazz.getMethods();
        Method declaredMethods[] = clazz.getDeclaredMethods();
        Class<?> clazzes[] = clazz.getClasses();
        Annotation annotations[] = clazz.getAnnotations();
        Annotation declaredAnnotations[] = clazz.getDeclaredAnnotations();
        Constructor constructors[] = clazz.getConstructors();
        Constructor declaredConstructors[] = clazz.getDeclaredConstructors();
        AnnotatedType annotatedInterfaces[] = clazz.getAnnotatedInterfaces();
        Field decalredFields[] = clazz.getDeclaredFields();
        Class<?> declaringClass = clazz.getDeclaringClass();
        Method enclosingMethod = clazz.getEnclosingMethod();
        Constructor enclosingConstructor = clazz.getEnclosingConstructor();
        Class<?> enclosingClass = clazz.getEnclosingClass();
        int getModifiers = clazz.getModifiers();
        Module module = clazz.getModule();
        Class<?> getInterfaces[] = clazz.getInterfaces();
        Package packege = clazz.getPackage();
        clazz.isLocalClass();
        String getPackageName = clazz.getPackageName();
        boolean isInterface = clazz.isInterface();
        clazz.getSigners();

        System.out.print("methods[]: ");
        for(int i = 0; i < methods.length; i++) {
            System.out.print(methods[i].getName() + ", ");
        }

        System.out.print("\ndeclardedMethods[]: ");
        for(int i = 0; i < declaredMethods.length; i++) {
            System.out.print(declaredMethods[i].getName() + ", ");
        }

        System.out.print("\nclazzes[]: ");
        for(int i = 0; i < clazzes.length; i++) {
            System.out.print(clazzes[i].getName() + ", ");
        }

        System.out.print("\nannotations[]: ");
        for(int i = 0; i < annotations.length; i++) {
            System.out.print(annotations[i].toString() + ", ");
        }

        System.out.print("\ndeclaredAnnotations[]: ");
        for(int i = 0; i < declaredAnnotations.length; i++) {
            System.out.print(declaredAnnotations[i].toString() + ", ");
        }

        System.out.print("\nconstructors[]: ");
        for(int i = 0; i < constructors.length; i++) {
            System.out.print(constructors[i].getName() + ", ");
        }

        System.out.print("\ndeclaredConstructors[]: ");
        for(int i = 0; i < declaredConstructors.length; i++) {
            System.out.print(declaredConstructors[i].getName() + ", ");
        }

        System.out.print("\nannotatedInterfaces[]: ");
        for(int i = 0; i < annotatedInterfaces.length; i++) {
            System.out.print(Arrays.toString(annotatedInterfaces[i].getAnnotations()) + " ");
            System.out.print(annotatedInterfaces[i].getDeclaredAnnotations() + ", ");
        }

        System.out.print("\ndeclaredFields[]: ");
        for(int i = 0; i < decalredFields.length; i++) {
            System.out.print(decalredFields[i].getName()+ ", ");
        }

        System.out.print("\ndeclaringClass[]: ");
        if(declaringClass != null) {
            System.out.print(declaringClass.getName());
        }

        System.out.print("\nenclosingMethod: ");
        if(enclosingMethod != null) {
            System.out.print(enclosingMethod.getName());
        }

        System.out.print("\nenclosingConstructor: ");
        if(enclosingConstructor != null) {
            System.out.print(enclosingConstructor.getName());
        }

        System.out.print("\nenclosingClass: ");
        if(enclosingClass != null) {
            System.out.print(enclosingClass.getName());
        }

        System.out.print("\ngetModifiers: ");
        System.out.print(getModifiers);

        System.out.print("\nmodule: ");
        System.out.print(module.getName());

        System.out.print("\ngetInterfaces[]: ");
        for(int i = 0; i < getInterfaces.length; i++) {
            System.out.print(getInterfaces[i]);
        }

        System.out.print("\npackage: ");
        System.out.print(packege.getName());

        System.out.print("\ngetPackageName: ");
        System.out.print(getPackageName);

        System.out.print("\nisInterface: ");
        System.out.print(isInterface);

        System.out.println();

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
