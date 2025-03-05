package com.home.pureFabrication.fifthExample;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class Test {

    static class Graph {
        private final Map<String, Set<String>> adjacencyList = new HashMap<>();

        public void addVertex(String vertex) {
            adjacencyList.putIfAbsent(vertex, new HashSet<>());
        }

        public void addEdge(String v1, String v2) {
            adjacencyList.get(v1).add(v2);
            adjacencyList.get(v2).add(v1);
        }

        public List<Set<String>> findConnectedComponents() {
            Set<String> visited = new HashSet<>();
            List<Set<String>> components = new ArrayList<>();

            for (String vertex : adjacencyList.keySet()) {
                if (!visited.contains(vertex)) {
                    Set<String> component = new HashSet<>();
                    dfs(vertex, visited, component);
                    components.add(component);
                }
            }

            return components;
        }

        private void dfs(String vertex, Set<String> visited, Set<String> component) {
            visited.add(vertex);
            component.add(vertex);

            for (String neighbor : adjacencyList.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    dfs(neighbor, visited, component);
                }
            }
        }
    }

    public static double calculateLCOM(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        Field[] fields = clazz.getDeclaredFields();

        Graph graph = new Graph();

        for (Method method : methods) {
            graph.addVertex(method.getName());
        }

        for (Field field : fields) {
            graph.addVertex(field.getName());
        }

        for (Method method : methods) {
            Set<String> usedFields = findUsedFields(method, fields);

            for (String fieldName : usedFields) {
                graph.addEdge(method.getName(), fieldName);
            }

            for (Method otherMethod : methods) {
                if (method != otherMethod) {
                    Set<String> otherUsedFields = findUsedFields(otherMethod, fields);
                    if (!Collections.disjoint(usedFields, otherUsedFields)) {
                        graph.addEdge(method.getName(), otherMethod.getName());
                    }
                }
            }
        }

        List<Set<String>> allComponents = graph.findConnectedComponents();

        List<Set<String>> validComponents = new ArrayList<>();
        for (Set<String> component : allComponents) {
            if (component.size() != 1 || !isField(component.iterator().next(), fields)) {
                validComponents.add(component);
            }
        }

        if (validComponents.size() > 1) {
            return (double) validComponents.size() / methods.length;
        }

        return 0.0;
    }

    private static Set<String> findUsedFields(Method method, Field[] fields) {
        Set<String> usedFields = new HashSet<>();
        String methodBody = method.getName().toLowerCase();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName().toLowerCase();
            if (methodBody.contains(fieldName)) {
                usedFields.add(field.getName());
            }
        }
        return usedFields;
    }

    private static boolean isField(String name, Field[] fields) {
        for (Field field : fields) {
            if (field.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        double lcom = calculateLCOM(PayByPayPal.class);
        System.out.println("LCOM: " + lcom);
    }
}
