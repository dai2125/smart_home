package com.home.asm;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class PackageService {

    public static HashSet<String> allClasses(File directory) throws IOException {
        HashSet<String> classes = new HashSet<>();

        if (!directory.exists() || !directory.isDirectory()) {
            return null;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return null;
        }

        for (File file : files) {
            if (file.isDirectory()) {
//                System.out.println("file.isDirectory: " + file.getName());
                allClasses(file);
            } else if (file.getName().endsWith(".java")) {
                System.out.println("file.getAbsolutePath(); "+ file.getAbsolutePath());
                String fileName = file.getPath().replace(".java", "")
                        .replaceAll("\\\\", "/")
//                        .replace("C:/Users/aigne/IdeaProjects/smart_home/Authentication/src/main/java/", "");
                        .replace("C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/", "");
//                fileName = fileName.replaceFirst(".*/", "");
                System.out.println("fileName: " + fileName);
                classes.add(fileName);
            }
        }
        return classes;
    }
}
