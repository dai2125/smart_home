package com.home.asm;

import org.objectweb.asm.*;

import java.io.IOException;
import java.util.*;

import static org.objectweb.asm.Opcodes.ASM9;

public class CohesionService {

    private double cohesion;
    private Map<String, Set<String>> methodToFields = new HashMap<>();
    private String currentMethod;

    public CohesionService() {

    }

    public void getCohesion(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);

        classReader.accept(new ClassVisitor(ASM9) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                currentMethod = name;
                methodToFields.put(currentMethod, new HashSet<>());
//                System.out.println("visitMethod: " + currentMethod);

                return new MethodVisitor(ASM9) {
                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                        methodToFields.get(currentMethod).add(name);
//                        System.out.println("visitFieldInsn: " + currentMethod + " .add " + name);
                    }
                };
            }

            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                super.visit(version, access, name, signature, superName, interfaces);
            }

            @Override
            public void visitAttribute(Attribute attribute) {
                super.visitAttribute(attribute);
            }

            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                return super.visitField(access, name, descriptor, signature, value);
            }

            @Override
            public void visitEnd() {
                super.visitEnd();
            }
        }, 0);
        calculateLCOM4(methodToFields);
    }

    public static double calculateLCOM4(Map<String, Set<String>> methodToFields) {
        Graph graph = new Graph(false, false);
        Map<String, Vertex> nodes = new HashMap<>();

        for(String method : methodToFields.keySet()) {
            nodes.put(method, graph.addVertex(method));
        }

        for(String method1 : methodToFields.keySet()) {
            for(String method2 : methodToFields.keySet()) {
                if(!method1.equals(method2) && methodToFields.get(method2) != null) {

                    boolean shareFields = !Collections.disjoint(methodToFields.get(method1), methodToFields.get(method2));
                    boolean callsMethod = methodToFields.get(method1).contains(method2);

                    if(shareFields || callsMethod) {
                        graph.addEdge(nodes.get(method1), nodes.get(method2), null);
                    }
                }
            }
        }
//        System.out.println("countConnectedComponentes(graph): " + countConnectedComponents(graph));
//        System.out.println("GRAPH");
//        graph.printGraph();
        return countConnectedComponents(graph);
    }

    public static double calculateLCOM4Designite(Map<String, Set<String>> methodToFields) {
        Graph graph = new Graph(false, false);
        Map<String, Vertex> nodes = new HashMap<>();

        for(String method : methodToFields.keySet()) {
            nodes.put(method, graph.addVertex(method));
        }

        for(String method1 : methodToFields.keySet()) {
            for(String method2 : methodToFields.keySet()) {
                if(!method1.equals(method2) && methodToFields.get(method2) != null) {

                    boolean shareFields = !Collections.disjoint(methodToFields.get(method1), methodToFields.get(method2));
                    boolean callsMethod = methodToFields.get(method1).contains(method2);

                    if(shareFields || callsMethod) {
                        graph.addEdge(nodes.get(method1), nodes.get(method2), null);
                    }
                }
            }
        }
//        System.out.println("GRAPH");
//        graph.printGraph();
//        System.out.println(countConnectedComponents(graph) + " / " + methodToFields.size());

        if(countConnectedComponents(graph) > 1) {
            return countConnectedComponents(graph) / methodToFields.size();
        }
        return 0.0;
    }

    private static double countConnectedComponents(Graph graph) {
        Set<Vertex> visited = new HashSet<>();
        int components = 0;

        for(Vertex v : graph.getVertices()) {
            if(!visited.contains(v)) {
                components++;
                dfs(v, visited);
            }
        }
        return components;
    }

    private static void dfs(Vertex v, Set<Vertex> visited) {
        if(visited.contains(v)) {
            return;
        }
        visited.add(v);
        for(Edge e : v.getEdges()) {
            dfs(e.getEnd(), visited);
        }
    }

    public Map<String, Set<String>> getMethodToFields() {
        return methodToFields;
    }

    public static String analyzeCohesionOfClass(String name, int initializedFields, int fieldsUsedWithinMethods, double yalcom, double lcom4) {
        return name + " " + doesThisClassUseAllItsFields(initializedFields, fieldsUsedWithinMethods) + ", " +
                analyzeYalcom(yalcom) + ", " +
                analyzeLcom4(lcom4);
    }

    private static String doesThisClassUseAllItsFields(int initializedFields, int fieldsUsedWithinMethods) {
        if(initializedFields == 0) {
            return "has (" + fieldsUsedWithinMethods + "/" + initializedFields + ") initialized fields";
        } else if(initializedFields > fieldsUsedWithinMethods) {
            return "unused Fields (" + fieldsUsedWithinMethods + "/" + initializedFields + ")";
        } else if(initializedFields == fieldsUsedWithinMethods) {
            return "all Fields are used (" + fieldsUsedWithinMethods + "/" + initializedFields + ")";
        } else {
            return "ERROR initializedFields < fieldsUsedWithinMethods " + initializedFields + " < " + fieldsUsedWithinMethods;
        }
    }

    private static String analyzeYalcom(double yalcom) {
        if(yalcom < 0) {
            return "YALCOM is negative = " + String.format("%.0f", yalcom);
        } else if(yalcom == 0) {
            return "YALCOM is perfect = " + String.format("%.0f", yalcom);
        } else if(yalcom < 0.5) {
            return "YALCOM is good = " + String.format("%.0f", yalcom);
        } else if(yalcom < 0.99) {
            return "YALCOM is very high = " + String.format("%.0f", yalcom);
        } else {
            return "ERROR";
        }
    }

    private static String analyzeLcom4(double lcom4) {
        if(lcom4 < 0) {
            return "LCOM4 ERROR";
        } else if(lcom4 == 1) {
            return "LCOM4 is perfect = " + lcom4;
        } else if(lcom4 == 2 || lcom4 == 3) {
            return "LCOM4 = " + lcom4 + ", " + String.format("%.0f", lcom4) + " you may re-design this class";
        } else {
            return "LCOM4 = " + lcom4 + ", " + String.format("%.0f", lcom4) + " independent components, Cohesion violation";
        }
    }

}
