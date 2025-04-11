package com.home.asm;

import org.objectweb.asm.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.objectweb.asm.Opcodes.ASM9;

public class IndirectionService {

    /* INDIRECTION
    *   start() gibt alle Verwendung der Klasse in anderen Klasse zurück (LocalVariable, TypeInsn, Fields,
    *                                                                       Functions, InvokeVirtual, InvokeSpecial)
    *   setIndirectionBetween() muss gesetzt werden um eine Indirection zu setzten
    *   setIndirectionBetween() zählt immer nur vom Component Richtung Orchestrator, nicht umgehkehrt
    * */

    private String packagePath;
    private File directory;
    private boolean isInterface;
    private final static String PRINCIPLE = "INDIRECTION PRINCIPLE ";
    private final static String PRINCIPLEVIOLATION = "\nPrinciple is violated";

    public IndirectionService(String packagePath, File directory) {
        this.packagePath = packagePath;
        this.directory = directory;
    }

    public void setIndirectionBetween(InspectedClass inspectedClass, InspectedClass orchestrator) {
        inspectedClass.addOrchestrator(orchestrator);
    }

    public String start(InspectedClass inspectedClass, List<InspectedClass> inspectedClassList) throws IOException {
        StringBuilder sb = new StringBuilder(PRINCIPLE + inspectedClass.getName().replaceFirst(".*/", ""));

        for(int i = 0; i < inspectedClassList.size(); i++) {
            if(!inspectedClassList.get(i).getName().equals(inspectedClass.getName())) {
                indirectionPrinciple(directory, inspectedClass, sb);
            }
        }

        if(sb.toString().contains("\n")) {
            sb.append(PRINCIPLEVIOLATION);
        }

        return sb.toString();
    }

    private void indirectionPrinciple(File directory, InspectedClass inspectedClass, StringBuilder sb) throws IOException {
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Couldnt find directory: " + directory.getAbsolutePath());
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.err.println("Error while reading directory: " + directory.getAbsolutePath());
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                indirectionPrinciple(file, inspectedClass, sb);
            } else if (file.getName().endsWith(".java")) {

                String fileName = file.getPath().replace(".java", "")
                        .replaceAll("\\\\", "/")
                        .replace("C:/Users/aigne/IdeaProjects/smart_home/Authentication/src/main/java/", "");

                ClassReader classReader = new ClassReader(fileName);
                isInterface = false;
                if (!fileName.equals(inspectedClass.getFullName()) && !inspectedClass.orchestratorListContainsFullName(fileName.replaceFirst(".*/", ""))) /* && fileName.equals("com/home/indirection/fourthAnalysis/fix/BookRepository") */  {
                    classReader.accept(new ClassVisitor(ASM9) {

                        @Override
                        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                            isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
                        }

                        @Override
                        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                            if(isInterface && desc.contains(inspectedClass.getName())) {
                                Type[] argumentTypes = Type.getArgumentTypes(desc);
                                Type returnType = Type.getReturnType(desc);
                                if(inspectedClass.getFullName().equals(returnType.getInternalName())) {
                                    if(!sb.toString().contains("\n" + inspectedClass.getName().replaceFirst(".*/", "") + "s function " + name + " is used in the class " + fileName.replaceFirst(".*/", ""))) {
                                        sb.append("\n" + inspectedClass.getName().replaceFirst(".*/", "") + "s function " + name + " is used in the class " + fileName.replaceFirst(".*/", ""));
                                    }
                                }

                                for(Type arg : argumentTypes) {
                                    if(inspectedClass.getFullName().equals(arg.getInternalName())) {
                                        if(!sb.toString().contains("\n" + inspectedClass.getName().replaceFirst(".*/", "") + " is used in the function " + name + " in the class " + fileName.replaceFirst(".*/", ""))) {
                                            sb.append("\n" + inspectedClass.getName().replaceFirst(".*/", "") + " is used in the function " + name + " in the class " + fileName.replaceFirst(".*/", ""));
                                        }
                                    }
                                }
                            }

                            if (desc.contains(inspectedClass.getFullName())) {
                                if(!sb.toString().contains("\n" + inspectedClass.getName().replaceFirst(".*/", "") + " is used in the function " + name + " as parameter in the class " + fileName.replaceFirst(".*/", ""))) {
                                    sb.append("\n" + inspectedClass.getName().replaceFirst(".*/", "") + " is used in the function " + name + " as parameter in the class " + fileName.replaceFirst(".*/", ""));
                                }
                            }
                            return new MethodVisitor(ASM9) {
                                @Override
                                public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                                    if(Opcodes.NEW == opcode) {
                                    }
                                    else if (Opcodes.INVOKESPECIAL != opcode && owner.equals(inspectedClass.getFullName())) {
                                        if(!sb.toString().contains("\n" + inspectedClass.getFullName().replaceFirst(".*/", "") + "s function " + name + " is used in the class " + fileName.replaceFirst(".*/", ""))) {
                                            sb.append("\n" + inspectedClass.getFullName().replaceFirst(".*/", "") + "s function " + name + " is used in the class " + fileName.replaceFirst(".*/", ""));
                                        }
                                    } else if(Opcodes.INVOKEVIRTUAL == opcode) {
                                        Type[] argumentTypes = Type.getArgumentTypes(desc);

                                        for(Type arg : argumentTypes) {
                                            if(arg.getClassName().replaceAll("\\.", "/").equals(inspectedClass.getFullName())) {
                                                if(!sb.toString().contains("\n" + inspectedClass.getFullName().replaceFirst(".*/", "") + "s function " + name + " is used in the class " + fileName.replaceFirst(".*/", ""))) {
                                                    sb.append("\n" + inspectedClass.getFullName().replaceFirst(".*/", "") + "s function " + name + " is used in the class " + fileName.replaceFirst(".*/", ""));
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                                    if(owner.equals(inspectedClass.getFullName())) {
                                        if(!sb.toString().contains("\n" + inspectedClass.getFullName().replaceFirst(".*/", "") + " is used in the Field = " + name + " in the class " + fileName.replaceFirst(".*/", ""))) {
                                            sb.append("\n" + inspectedClass.getFullName().replaceFirst(".*/", "") + " is used in the Field = " + name + " in the class " + fileName.replaceFirst(".*/", ""));
                                        }
                                    }
                                }

                                @Override
                                public void visitTypeInsn(int opcode, String type) {
                                    if(Opcodes.NEW == opcode && type.equals(inspectedClass.getFullName())) {
                                        if(!sb.toString().contains("\n" + inspectedClass.getFullName().replaceFirst(".*/", "") + " is used as TypeInsn in the " + name + ", in the class " + fileName.replaceFirst(".*/", ""))) {
                                            sb.append("\n" + inspectedClass.getFullName().replaceFirst(".*/", "") + " is used as TypeInsn in the " + name + ", in the class " + fileName.replaceFirst(".*/", ""));
                                        }
                                    }
                                }

                                @Override
                                public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
                                    if(descriptor.contains(inspectedClass.getFullName())) {
                                        if(!sb.toString().contains("\n" + inspectedClass.getFullName().replaceFirst(".*/", "") + " is used as LocalVariable = " + name + " in the class " + fileName.replaceFirst(".*/", ""))) {
                                            sb.append("\n" + inspectedClass.getFullName().replaceFirst(".*/", "") + " is used as LocalVariable = " + name + " in the class " + fileName.replaceFirst(".*/", ""));
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
