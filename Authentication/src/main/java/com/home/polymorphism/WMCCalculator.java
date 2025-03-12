package com.home.polymorphism;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.io.IOException;
import java.io.InputStream;

public class WMCCalculator {
    private ClassLoader classLoader;
    private int wmc = 0;
    private String className;

    public WMCCalculator() {
        this.classLoader = getClass().getClassLoader();
    }

    public int calculateWMC(String className) throws IOException {
        this.className = className;
        wmc = 0;

        ClassReader reader = getClassReader(className);
        if (reader == null) {
            System.err.println("Klasse nicht gefunden: " + className);
            return 0;
        }

        // Verwenden Sie ClassNode statt ClassVisitor für einfacheren Zugriff auf Methoden
        ClassNode classNode = new ClassNode();
        reader.accept(classNode, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

        // Für jede Methode die zyklomatische Komplexität berechnen
        for (MethodNode method : classNode.methods) {
            int complexity = calculateMethodComplexity(method);
            System.out.println("Methode: " + method.name + method.desc + " - Komplexität: " + complexity);
            wmc += complexity;
        }

        return wmc;
    }

    private int calculateMethodComplexity(MethodNode method) {
        // Die einfachste Berechnung der zyklomatischen Komplexität:
        // 1 + Anzahl der Verzweigungen

        // Wenn keine Anweisungen vorhanden sind (z.B. native oder abstrakte Methoden)
        if (method.instructions == null || method.instructions.size() == 0) {
            return 1;
        }

        System.out.println("method.instructions: " + method.instructions.size());
        for(int i = 0; i < method.instructions.size(); i++) {
            System.out.println("XX: " + method.instructions.get(i).toString());
            System.out.println(method.instructions.get(i).getOpcode());
        }

        int edges = 0;
        int nodes = 0;

        for (AbstractInsnNode insn = method.instructions.getFirst(); insn != null; insn = insn.getNext()) {
            nodes++;

            switch (insn.getOpcode()) {
                case Opcodes.IFEQ:
                case Opcodes.IFNE:
                case Opcodes.IFLT:
                case Opcodes.IFGE:
                case Opcodes.IFGT:
                case Opcodes.IFLE:
                case Opcodes.IF_ICMPEQ:
                case Opcodes.IF_ICMPNE:
                case Opcodes.IF_ICMPLT:
                case Opcodes.IF_ICMPGE:
                case Opcodes.IF_ICMPGT:
                case Opcodes.IF_ICMPLE:
                case Opcodes.IF_ACMPEQ:
                case Opcodes.IF_ACMPNE:
                case Opcodes.IFNULL:
                case Opcodes.IFNONNULL:
                    edges += 2;
                    break;

                case Opcodes.TABLESWITCH:
                    TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) insn;
                    edges += tableSwitchInsnNode.labels.size() + 1; // +1 für den default-Fall
                    break;
                case Opcodes.LOOKUPSWITCH:
                    LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode) insn;
                    edges += lookupSwitchInsnNode.labels.size() + 1; // +1 für den default-Fall
                    break;

                case Opcodes.GOTO:
                case Opcodes.JSR:
                case Opcodes.RET:
                    edges++;
                    break;

                case Opcodes.RETURN:
                case Opcodes.IRETURN:
                case Opcodes.LRETURN:
                case Opcodes.FRETURN:
                case Opcodes.DRETURN:
                case Opcodes.ARETURN:
                    edges++;
                    break;

                default:
                    if (insn.getOpcode() >= 0) {
                        edges++;
                    }
                    break;
            }
        }

        // Zyklomatische Komplexität = E - N + 2, wobei E = Anzahl der Kanten, N = Anzahl der Knoten
        // Alternativ: 1 + Anzahl der Entscheidungspunkte
        return (edges - nodes) + 2;
    }

//    private int calculateMethodComplexity(MethodNode method) {
//        int complexity = 1;
//
//        for(AbstractInsnNode insn = method.instructions.getFirst(); insn != null; insn = insn.getNext()) {
//            int opcode = insn.getOpcode();
//
//            if((opcode >= Opcodes.IFEQ && opcode <= Opcodes.IF_ACMPNE) ||
//                opcode == Opcodes.IFNULL || opcode == Opcodes.IFNONNULL) {
//                complexity++;
//            }
//
//            else if(opcode == Opcodes.LOOKUPSWITCH) {
//                LookupSwitchInsnNode switchInsn = (LookupSwitchInsnNode) insn;
//                complexity += switchInsn.labels.size();
//            }
//
//            else if(opcode == Opcodes.TABLESWITCH) {
//                TableSwitchInsnNode switchInsn = (TableSwitchInsnNode) insn;
//                complexity += switchInsn.labels.size();
//            }
//        }
//
//        if(method.tryCatchBlocks != null) {
//            complexity += method.tryCatchBlocks.size();
//        }
//        return complexity;
//    }

    private ClassReader getClassReader(String className) throws IOException {
        try {
            String resourceName = className + ".class";
            InputStream is = classLoader.getResourceAsStream(resourceName);
            if (is == null) {
                resourceName = className.replace('/', '.') + ".class";
                is = classLoader.getResourceAsStream(resourceName);

                if (is == null) {
                    return null;
                }
            }
            return new ClassReader(is);
        } catch (IOException e) {
            System.err.println("Fehler beim Laden von " + className + ": " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String className = "com/home/polymorphism/fix/EmailSender";

        try {
            WMCCalculator wmcCalculator = new WMCCalculator();
            int wmc = wmcCalculator.calculateWMC(className);

            System.out.println("\nWMC-Wert für " + className + " = " + wmc);
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}