package com.home.asm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class InputArgs {
    private String sourceFolder;
    private String outputFolder;

    public InputArgs() {
        // Initialisiert bei Fehler
    }

    public InputArgs(String inputFolderPath, String outputFolderPath) {
        sourceFolder = inputFolderPath;
        outputFolder = outputFolderPath;
        checkEssentialInputs();
    }

    public String getSourceFolder() {
        return sourceFolder;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    private void checkEssentialInputs() {
        // Stelle sicher, dass der Pfad zum Quellordner existiert und ein Verzeichnis ist
        if (sourceFolder == null) {
            throw new IllegalArgumentException("Input source folder is not specified.");
        }

        // Verwende File-Objekt zur Normierung des Pfades
        File folder = new File(sourceFolder).getAbsoluteFile();
        if (!(folder.exists() && folder.isDirectory())) {
            throw new IllegalArgumentException("Input source folder path is not valid.");
        }

        // Überprüfen des Zielordners
        File outFolder = new File(outputFolder).getAbsoluteFile();
        if (outFolder.exists() && outFolder.isFile()) {
            throw new IllegalArgumentException("The specified output folder path is not valid.");
        }
    }

    /***
     * Gibt den Projektnamen basierend auf dem Quellordner zurück
     * @return Projektname
     */
    public String getProjectName() {
        File temp = new File(sourceFolder);
        if (temp.getName().equals("src") || temp.getName().equals("source")) {
            return new File(temp.getParent()).getName();
        } else {
            return new File(sourceFolder).getName();
        }
    }

    // Beispielmain-Methode
    public static void main(String[] args) throws IOException {
        String directory = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\polymorphism\\fix"; // "C:/path/to/your/project/src"; // Beispielpfad
        analyzePackage(directory);
    }

    private static void analyzePackage(String directory) throws IOException {
        // Umwandlung in ein absoluter Pfad (falls erforderlich)
        File dir = new File(directory).getAbsoluteFile();

        // Verzeichnisüberprüfung
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("Invalid directory: " + dir.getAbsolutePath());
            return;
        }

        // Rekursive Durchsuchung von Verzeichnissen
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                analyzePackage(file.getAbsolutePath());  // Rekursiver Aufruf
            } else if (file.getName().endsWith(".class")) {
                // Ausgabe des absoluten Pfades
                System.out.println("Found class: " + file.getAbsolutePath());
            }
        }
    }
}
