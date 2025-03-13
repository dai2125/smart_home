package com.home.asm;

import java.util.ArrayList;

public class Vertex {

    private String data;
    private ArrayList<Edge> edges;

    public Vertex(String inputData) {
        this.data = inputData;
        this.edges = new ArrayList<Edge>();
    }

    public void addEdge(Vertex endVertex, Integer weight) {
        this.edges.add(new Edge(this, endVertex, weight));
    }

    public void removeEdge(Vertex endVertex) {
        this.edges.removeIf(edge -> edge.getEnd().equals(endVertex));
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public void print(boolean a) {
        System.out.println(a);
    }

    public String getData() {
        return this.data;
    }
}
