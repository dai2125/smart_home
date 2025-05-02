package lcom;

import lcom.sourceModel.SM_Project;
import lcom.sourceModel.TargetClass;
import lcom.sourceModel.Tc;
import lcom.utils.Logger;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This is the start of the project
 */
public class LCOM {

    private String[] args;

    public String targetClass = "";

    public static void main(String[] args) {
        if(args == null || args.length == 0) {
//            args = new String[]{"C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\asm\\cohesionDesignite",
//                                "C:\\Users\\aigne"};
            args = new String[]{"-i", "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\pureFabrication\\fourthExample",
                                "-o", "PasteCommand"};
        }

        try {
            InputArgs argsObj = parseArguments(args);
            Logger.logFile = getlogFileName(argsObj);
            analyze(argsObj);
        } catch (Exception ex) {
            String message = ex.getMessage() + " Stacktrace: " +
                    getStackTrace(ex).replaceAll("\n", ";");
            Logger.log(message);
        }
    }

    public static void startLcomProcess(String[] args) {
//        String[] args = new String[]{"-i", "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\pureFabrication\\fourthExample",
//                "-o", "PasteCommand"};
        try {
            InputArgs argsObj = parseArguments(args);
            analyze(argsObj);
        } catch (Exception ex) {
            String message = ex.getMessage() + " Stacktrace: " +
                    getStackTrace(ex).replaceAll("\n", ";");
            Logger.log(message);
        }
    }

    private static String getStackTrace(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    private static void analyze(InputArgs argsObj) {

//        Tc tc = new Tc(argsObj.getOutputFolder());
//        String targetClass = tc.
//        TargetClass.initialize(argsObj.getOutputFolder());
//        String targetClass = TargetClass.getInstance().getTargetClass();
//        System.out.println("targetClass0 " + targetClass);
        SM_Project project = new SM_Project(argsObj);
        project.parse();
        project.resolve();
        project.computeMetrics();
        project.exportAnalysisResult();
        project.printSummary();
//        Logger.log("Done.");

    }

    private static InputArgs parseArguments(String[] args) {
        Options argOptions = new Options();

        Option input = new Option("i", "Input", true, "Input file or folder path");
        input.setRequired(false);
        argOptions.addOption(input);

        Option output = new Option("o", "Output", true, "Path to the output folder");
        output.setRequired(false);
        argOptions.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(argOptions, args);
        } catch (ParseException e) {
            Logger.log(e.getMessage());
            formatter.printHelp("lcom", argOptions);
            Logger.log("Quitting..");
            System.exit(1);
        }
        if (cmd == null) {
            Logger.log("Couldn't parse the command line arguments.");
            formatter.printHelp("lcom", argOptions);
            Logger.log("Quitting..");
            System.exit(2);
        }

        InputArgs inputArgs = null;
        try {
            if (cmd.hasOption("Input") && cmd.hasOption("Output")) {
                inputArgs = new InputArgs(cmd.getOptionValue("Input"), cmd.getOptionValue("Output"));
            }
            else if ((cmd.hasOption("Input") && !cmd.hasOption("Output")) ||
                    (!cmd.hasOption("Input") && cmd.hasOption("Output"))) {
                Logger.log("Please provide both input and output folder path arguments.");
                formatter.printHelp("lcom", argOptions);
                Logger.log("Quitting..");
                System.exit(1);
            } else {
                Logger.log("Please provide appropriate arguments.");
                formatter.printHelp("lcom", argOptions);
                Logger.log("Quitting..");
                System.exit(2);
            }
        } catch (IllegalArgumentException ex) {
            Logger.log(ex.getMessage());
            Logger.log("Quitting..");
            System.exit(3);
        }
        return inputArgs;
    }

    private static String getlogFileName(InputArgs argsObj) {
        String file = null;
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(Calendar.getInstance().getTime());
        if (argsObj.getOutputFolder() != null)
            file = argsObj.getOutputFolder() + File.separator + "LcomLog" + timeStamp + ".txt";
        else
            file = System.getProperty("user.dir") + File.separator + "LcomLog" + timeStamp + ".txt";
//        ensureOutputFolderExists(argsObj.getOutputFolder());
        return file;
    }

    private static void ensureOutputFolderExists(String outputFolder) {
        if (outputFolder == null)
            return;
        File folder = new File(outputFolder);

        if (folder.exists() && folder.isDirectory())
            return;

        try {
            boolean isCreated = folder.mkdirs();
            if (!isCreated) {
                System.out.println("Couldn't create output folder.");
            }
        } catch (Exception ex) {
            System.out.println("Couldn't create output folder. " + ex.getMessage());
        }
    }
}
