package com.home.asm;

import org.objectweb.asm.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.objectweb.asm.Opcodes.ASM9;

public class IndirectionService {

    /* INDIRECTION
    *
    *   Compound darf nicht direkt angesprochen werden, nur über Mediator
    *
    *   start() gibt alle Verwendung der Klasse in anderen Klasse zurück (LocalVariable, TypeInsn, Fields,
    *                                                                       Functions, InvokeVirtual, InvokeSpecial)
    *   setIndirectionBetween() muss gesetzt werden um eine Indirection zu setzten
    *   setIndirectionBetween() zählt immer nur vom Component Richtung Orchestrator, nicht umgehkehrt
    * */

    /* AuWo Service - Indirection
    *
    * Indirection eine Klasse soll über eine Mediator Schnittstelle verwendet werden
    * setDirectionBetween() setzt erlaubte Verbindungen Compound zu Mediator. Standardmäßig ist keine Verbindung gesetzt
    * indirectionPrinciple() überprüft ob die gewählte Klasse in anderen Klassen(FanIn) verwendet wird die nicht als Mediator gesetzt wurden
    * */

    private String packagePath;
    private File directory;
    private boolean isInterface;
    private final static String PRINCIPLE = "INDIRECTION PRINCIPLE ";
    private final static String PRINCIPLEVIOLATION = "\nPrinciple is violated";

    public IndirectionService(String packagePath, File directory) {
        // System.out.println("IndirectionService: " + packagePath);
        // System.out.println("IndirectionService: " + directory);
        this.packagePath = packagePath;
        this.directory = directory;
    }

    public void setIndirectionBetween(InspectedClass inspectedClass, InspectedClass orchestrator) {
        inspectedClass.addOrchestrator(orchestrator);
    }

    public String start(InspectedClass inspectedClass, List<InspectedClass> inspectedClassList) throws IOException {
        StringBuilder sb = new StringBuilder(print.INDIRECTIONRESULT + PRINCIPLE + inspectedClass.getName().replaceFirst(".*/", ""));

        if(inspectedClass.getFanin() == 0) {
            sb.append("\n" + print.INDIRECTIONRESULT + inspectedClass.getName() + " - Error class isnt used in other class in this package");
            return sb.toString();
        }

//        System.out.println("inspectedClass.getFanin(): " + inspectedClass.getFanin());

        // repair LOGIK FEHLER?
        for(int i = 0; i < inspectedClassList.size(); i++) {
            if(!inspectedClassList.get(i).getName().equals(inspectedClass.getName())) {
                indirectionPrinciple(directory, inspectedClassList.get(i), sb, inspectedClass);
            }
        }

        if(sb.toString().contains("\n")) {
            sb.append(print.INDIRECTIONRESULT + PRINCIPLEVIOLATION);
        }

        return sb.toString();
    }

    private void indirectionPrinciple(File directory, InspectedClass inspectedClass, StringBuilder sb, InspectedClass targetClass) throws IOException {
 //       System.out.println("indirectionPrinciple(): " + directory.getAbsolutePath() + ", inspectedClass: " + inspectedClass.getName());
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Couldnt find directory1: " + directory.getAbsolutePath());
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.err.println("Error while reading directory: " + directory.getAbsolutePath());
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                indirectionPrinciple(file, inspectedClass, sb, targetClass);
            } else if (file.getName().endsWith(".java")) {
//                System.out.println("IndirectionService: " + file.getName());

//                Path rootPath = Paths.get("src/main/java").toAbsolutePath();
//                Path fullPath = file.toPath().toAbsolutePath();
//                String fileName;
//                try {
//                    Path relativePath = rootPath.relativize(fullPath);
//                    fileName = relativePath.toString()
//                            .replace(".java", "")
//                            .replaceAll("\\\\", "/");
//                } catch (IllegalArgumentException e) {
//                    System.err.println("Fehler beim Ermitteln des relativen Pfads: " + fullPath);
//                    return;
//                }

                // AuWo Path
                String basePath = System.getProperty("user.dir").replaceAll("\\\\", "/") + "/Authentication/src/main/java/";
                //System.out.println("111: " + basePath);

                String fileName = file.getPath().replace(".java", "")
                        .replaceAll("\\\\", "/")
                        .replace(basePath, "");
//                        .replace("C:/Users/aigne/IdeaProjects/smart_home/Authentication/src/main/java/", "");
                        //.replace("C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/", "")
                        ;

                //fileName = fileName.replace(basePath, "");
                System.out.println("222: " + fileName);


//                fileName = fileName.replaceAll(".*java/", "");

//                System.out.println("IndirectionService: " + fileName);
                ClassReader classReader = new ClassReader(fileName);
                isInterface = false;
                //System.out.println("indirectionPrinciple(): fileName" + fileName + ", inspectedClass.getFullName(): " + inspectedClass.getFullName() + ", inspectedClass.orchestratorListContainsFullName(): " +  targetClass.orchestratorListContainsFullName(fileName.replaceFirst(".*/", "")));
                //System.out.println("indirectionPrinciple(): fileName" + fileName.replaceFirst(".*/", ""));
                //System.out.println("indirectionPrinciple() targetClass.getFullName(): " + targetClass.getFullName());
                //System.out.println("indirectionPrinciple() inspectedClass.getFullName(): " + inspectedClass.getFullName());
                if(targetClass.orchestratorListContains(inspectedClass)) {
                    //System.out.println("found: " + targetClass.getFullName());
                    //System.out.println("found: " + inspectedClass.getFullName());
                }
                if(!fileName.equals(targetClass.getFullName()) && !targetClass.orchestratorListContainsFullName(fileName.replaceFirst(".*/", "")) && !targetClass.orchestratorListContainsFullName(fileName)) {
                    //System.out.println("inside: "+ inspectedClass.getFullName());
                    //System.out.println("inside: " + targetClass.getFullName());
                //if (!fileName.equals(inspectedClass.getFullName()) && !inspectedClass.orchestratorListContainsFullName(fileName.replaceFirst(".*/", ""))) /* && fileName.equals("com/home/indirection/fourthAnalysis/fix/BookRepository") */  {
                    classReader.accept(new ClassVisitor(ASM9) {

                        @Override
                        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                            isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
                        }

                        @Override
                        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                            if(isInterface && desc.contains(targetClass.getName())) {
                                Type[] argumentTypes = Type.getArgumentTypes(desc);
                                Type returnType = Type.getReturnType(desc);
                                if(targetClass.getFullName().equals(returnType.getInternalName())) {
                                    if(!sb.toString().contains("\n" + print.INDIRECTIONRESULT + targetClass.getName().replaceFirst(".*/", "") + "s function " + name + " is used in the class " + fileName.replaceFirst(".*/", ""))) {
                                        sb.append("\n" + print.INDIRECTIONRESULT + targetClass.getName().replaceFirst(".*/", "") + "s function " + name + " is used in the class " + fileName.replaceFirst(".*/", ""));
                                    }
                                }

                                for(Type arg : argumentTypes) {
                                    if(inspectedClass.getFullName().equals(arg.getInternalName())) {
                                        if(!sb.toString().contains("\n" + print.INDIRECTIONRESULT + targetClass.getName().replaceFirst(".*/", "") + " is used in the function " + name + " in the class " + fileName.replaceFirst(".*/", ""))) {
                                            sb.append("\n" + print.INDIRECTIONRESULT + targetClass.getName().replaceFirst(".*/", "") + " is used in the function " + name + " in the class " + fileName.replaceFirst(".*/", ""));
                                        }
                                    }
                                }
                            }

                            if (desc.contains(inspectedClass.getFullName())) {
                                if(!sb.toString().contains("\n" + print.INDIRECTIONRESULT + targetClass.getName().replaceFirst(".*/", "") + " is used in the function " + name + " as parameter in the class " + fileName.replaceFirst(".*/", ""))) {
                                    sb.append("\n" + print.INDIRECTIONRESULT + targetClass.getName().replaceFirst(".*/", "") + " is used in the function " + name + " as parameter in the class " + fileName.replaceFirst(".*/", ""));
                                }
                            }
                            return new MethodVisitor(ASM9) {
                                @Override
                                public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                                    if(Opcodes.NEW == opcode) {
                                    }
                                    else if (Opcodes.INVOKESPECIAL != opcode && owner.equals(targetClass.getFullName())) {
                                        if(!sb.toString().contains("\n" + print.INDIRECTIONRESULT + targetClass.getFullName().replaceFirst(".*/", "") + "s function " + name + " is used in the class " + fileName.replaceFirst(".*/", ""))) {
                                            sb.append("\n" + print.INDIRECTIONRESULT + targetClass.getFullName().replaceFirst(".*/", "") + "s function " + name + " is used in the class " + fileName.replaceFirst(".*/", ""));
                                        }
                                    } else if(Opcodes.INVOKEVIRTUAL == opcode) {
                                        Type[] argumentTypes = Type.getArgumentTypes(desc);

                                        for(Type arg : argumentTypes) {
                                            if(arg.getClassName().replaceAll("\\.", "/").equals(targetClass.getFullName())) {
                                                if(!sb.toString().contains("\n" + print.INDIRECTIONRESULT + targetClass.getFullName().replaceFirst(".*/", "") + "s function " + name + " is used in the class " + fileName.replaceFirst(".*/", ""))) {
                                                    sb.append("\n" + print.INDIRECTIONRESULT + targetClass.getFullName().replaceFirst(".*/", "") + "s function " + name + " is used in the class " + fileName.replaceFirst(".*/", ""));
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                                    if(owner.equals(targetClass.getFullName())) {
                                        if(!sb.toString().contains("\n" + print.INDIRECTIONRESULT + targetClass.getFullName().replaceFirst(".*/", "") + " is used in the Field = " + name + " in the class " + fileName.replaceFirst(".*/", ""))) {
                                            sb.append("\n" + print.INDIRECTIONRESULT + targetClass.getFullName().replaceFirst(".*/", "") + " is used in the Field = " + name + " in the class " + fileName.replaceFirst(".*/", ""));
                                        }
                                    }
                                }

                                @Override
                                public void visitTypeInsn(int opcode, String type) {
                                    if(Opcodes.NEW == opcode && type.equals(targetClass.getFullName())) {
                                        if(!sb.toString().contains("\n" + print.INDIRECTIONRESULT + targetClass.getFullName().replaceFirst(".*/", "") + " is used as TypeInsn in the " + name + ", in the class " + fileName.replaceFirst(".*/", ""))) {
                                            sb.append("\n" + print.INDIRECTIONRESULT + targetClass.getFullName().replaceFirst(".*/", "") + " is used as TypeInsn in the " + name + ", in the class " + fileName.replaceFirst(".*/", ""));
                                        }
                                    }
                                }

                                @Override
                                public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
                                    if(descriptor.contains(targetClass.getFullName())) {
                                        if(!sb.toString().contains("\n" + print.INDIRECTIONRESULT + targetClass.getFullName().replaceFirst(".*/", "") + " is used as LocalVariable = " + name + " in the class " + fileName.replaceFirst(".*/", ""))) {
                                            sb.append("\n" + print.INDIRECTIONRESULT + targetClass.getFullName().replaceFirst(".*/", "") + " is used as LocalVariable = " + name + " in the class " + fileName.replaceFirst(".*/", ""));
                                        }
                                    }
                                }
                            };
                        }
                    }, 0);
                }
            }
        }
    }
}
