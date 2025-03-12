package com.home.asm;

import org.objectweb.asm.*;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import static org.objectweb.asm.Opcodes.ASM9;

public class PackageService {

    private HashSet<String> classes = new HashSet<>();

    public HashSet<String> allClasses(File directory) throws IOException {
        if (!directory.exists() || !directory.isDirectory()) {
            return null;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return null;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("file.isDirectory: " + file.getName());
                allClasses(file);
            } else if (file.getName().endsWith(".java")) {
                String fileName = file.getPath().replace(".java", "")
                        .replaceAll("\\\\", "/")
                        .replace("C:/Users/aigne/IdeaProjects/smart_home/Authentication/src/main/java/", "");

                classes.add(fileName);
            }
        }
        return classes;
    }
}
