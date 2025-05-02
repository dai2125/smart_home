package com.home.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

import java.io.IOException;
import java.io.InputStream;

public class WMCService {
    // TODO http://www.mccabe.com/pdf/McCabeCodeQualityMetrics-OutsourcedDev.pdf  It is recommended WMC does not exceed the value 14
    // TODO MCCABE.com wmc shouldnt pass the limit of 14
    /*  TODO https://lionel.kr.hs-niederrhein.de/~rethmann/sof06/sof06.pdf
    *    1 - 10 einfaches Programm, geringes Risiko
    *   11 - 20 komplexeres Programm, erträgliches Risiko
    *   21 - 50 komplexes Programm, hohes Risiko
    *      > 50 untestbares Programm, extrem hohes Risiko
    * */
    private ClassLoader classLoader;
    private int wmc = 0;
    private String className;

    public WMCService() {
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
//            System.out.println("Methode: " + method.name + method.desc + " - Komplexität: " + complexity);
            wmc += complexity;
        }
        return wmc;
    }

    public int calculateMethodComplexity(MethodNode method) {
        // Die einfachste Berechnung der zyklomatischen Komplexität:
        // 1 + Anzahl der Verzweigungen

        // Wenn keine Anweisungen vorhanden sind (z.B. native oder abstrakte Methoden)
        if (method.instructions == null || method.instructions.size() == 0) {
            return 1;
        }

        int edges = 0;
        int nodes = 0;

        // Zählen der Verzweigungsanweisungen
        for (AbstractInsnNode insn = method.instructions.getFirst(); insn != null; insn = insn.getNext()) {
            nodes++;

            switch (insn.getOpcode()) {
                // Bedingte Sprunganweisungen
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
                    edges += 2; // Bedingte Sprünge haben zwei Ausgangskanten
                    break;

                // Switch-Anweisungen
                case Opcodes.TABLESWITCH:
                case Opcodes.LOOKUPSWITCH:
                    TableSwitchInsnNode switchInsn = (TableSwitchInsnNode) insn;
                    edges += switchInsn.labels.size() + 1; // +1 für den default-Fall
                    break;

                default:
                    // Normale Anweisungen haben eine Ausgangskante
                    if (insn.getOpcode() >= 0) { // Ignorieren von Labels, Frames, etc.
                        edges++;
                    }
                    break;
            }
        }

        // Zyklomatische Komplexität = E - N + 2, wobei E = Anzahl der Kanten, N = Anzahl der Knoten
        // Alternativ: 1 + Anzahl der Entscheidungspunkte
        return edges - nodes + 2;
    }

    public ClassReader getClassReader(String className) throws IOException {
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
}
