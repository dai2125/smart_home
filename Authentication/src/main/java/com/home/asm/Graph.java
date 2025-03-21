package com.home.asm;

import java.util.ArrayList;
import java.util.Arrays;

public class Graph {

    private ArrayList<Vertex> vertices;
    private boolean isWeighted;
    private boolean isDirected;

    public Graph(boolean inputIsWeighted, boolean inputIsDirected) {
        this.vertices = new ArrayList<Vertex>();
        this.isWeighted = inputIsWeighted;
        this.isDirected = inputIsDirected;
    }

    public Vertex addVertex(String data) {
        Vertex newVertex = new Vertex(data);
        this.vertices.add(newVertex);
        return newVertex;
    }

    public void addEdge(Vertex vertex1, Vertex vertex2, Integer weight) {
        if(!this.isWeighted) {
            weight = null;
        }
        vertex1.addEdge(vertex2, weight);
        if(!this.isDirected) {
            vertex2.addEdge(vertex1, weight);
        }
    }

    public void removeEdge(Vertex vertex1, Vertex vertex2) {
        vertex1.removeEdge(vertex2);

        if(!this.isDirected) {
            vertex2.removeEdge(vertex1);
        }
    }

    public void removeEdge(Vertex vertex) {
        this.vertices.remove(vertex);
    }

    public ArrayList<Vertex> getVertices() {
        return this.vertices;
    }

    public boolean isWeighted() {
        return this.isWeighted;
    }

    public boolean isDirected() {
        return this.isDirected;
    }

    public Vertex getVertexByValues(String value) {
        for(Vertex v : this.vertices) {
            if(v.getData() == value) {
                return v;
            }
        }
        return null;
    }

    public void print() {
        for(Vertex v : this.vertices) {
            v.print(isWeighted);
        }
    }

    public void printGraph() {
        for(Vertex v : this.vertices) {
            System.out.println("Vertex: " + v.getData() + " " );//+ v.getEdges().forEach(System.out::println); // + Arrays.toString(new ArrayList[]{v.getEdges()}));

            if(!v.getEdges().isEmpty()) {
                System.out.println("\tFirst Edge Target: " + v.getEdges().get(0).getEnd().getData());

            }

            System.out.println("\tEdges: ");
            for(Edge e : v.getEdges()) {
                System.out.println("\t-> " + e.getEnd().getData() + " (Weight: " + e.getWeight() + ")");
            }
        }
        System.out.println();
    }
}
