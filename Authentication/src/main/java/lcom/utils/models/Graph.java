package lcom.utils.models;

import java.util.*;

public class Graph {
	
	private List<Vertex> vertices = new ArrayList<>();
	private Map<Vertex, List<Vertex>> adjacencyList = new HashMap<>();
	private Map<Vertex, List<Vertex>> directedAdjacencyList = new HashMap<>();
	private Map<Vertex, List<Vertex>> reversedDirectedAdjacencyList = new HashMap<>();
	private Map<Vertex, Boolean> visitedVertices = new HashMap<>();
	private List<List<Vertex>> connectedComponents = new ArrayList<>();
	private Map<Vertex, List<Vertex>> connectedComponnentsMapping = new HashMap<>();
	private List<List<Vertex>> stronglyConnectedComponents = new ArrayList<>();
	private Map<Vertex, List<Vertex>> stronglyConnectedComponnentsMapping = new HashMap<>();
	private List<Vertex> helperVertexList = new ArrayList<>();
	
	public void computeConnectedComponents() {
		initializeVisitedVerices();
		for (Vertex vertex : vertices) {
			if (!visitedVertices.get(vertex)) {
				List<Vertex> connectedComponent = new ArrayList<>();
				depthFirstSearch(connectedComponent, vertex, GraphAlingment.UNDIRECTED);
				connectedComponents.add(connectedComponent);
				updateMapping(connectedComponnentsMapping, connectedComponent);
			}
		}
	}
	
	public void computeStronglyConnectedComponents() {
		reversePassDFS();
		straightPassDFS();
	}
	
	private void reversePassDFS() {
		initializeVisitedVerices();
		for (int i = vertices.size()-1; i >= 0 ; i--) { 
			if (!visitedVertices.get(vertices.get(i))) {
				depthFirstSearch(new ArrayList<>(), vertices.get(i), GraphAlingment.REVERSE_DIRECTED);
			}
		}
	}
	
	private void straightPassDFS() {
		initializeVisitedVerices();
		for (int i = vertices.size()-1; i >= 0 ; i--) {
			if (!visitedVertices.get(helperVertexList.get(i))) {
				List<Vertex> stronglyConnectedComponent = new ArrayList<>();
				depthFirstSearch(stronglyConnectedComponent, helperVertexList.get(i), GraphAlingment.DIRECTED);
				stronglyConnectedComponents.add(stronglyConnectedComponent);
				updateMapping(stronglyConnectedComponnentsMapping, stronglyConnectedComponent);
			}
		}
	}
	
	private void initializeVisitedVerices() {
		for (Vertex vertex : vertices) {
			visitedVertices.put(vertex, false);
		}
	}
	
	private void updateMapping(Map<Vertex, List<Vertex>> mapping, List<Vertex> component) {
		for (Vertex vertex : component) {
			mapping.put(vertex, component);
		}
	}
	
	private void depthFirstSearch(List<Vertex> connectedComponent, Vertex vertex, GraphAlingment align) {
		visitedVertices.put(vertex, true);
		if (align != GraphAlingment.REVERSE_DIRECTED) {
			connectedComponent.add(vertex);
		}
		for (Vertex adjacentVertex : getAdjacentVertices(vertex, align)) {
			if (!visitedVertices.get(adjacentVertex)) {
				depthFirstSearch(connectedComponent, adjacentVertex, align);
			}
		}
		if (align == GraphAlingment.REVERSE_DIRECTED) {
			helperVertexList.add(vertex);
		}
	}
	
	public void addVertex(Vertex vertex) {
		if (!vertices.contains(vertex)) {
			initializeVertex(vertex);
		}
	}
	
	public void addEdge(Edge edge) {
		initializeEdge(edge);
		addDirectedEdge(edge);
		addUndirectedEdge(edge);
	}
	
	private void initializeEdge(Edge edge) {
		if (!adjacencyList.containsKey(edge.getFirstVertex())) {
			initializeVertex(edge.getFirstVertex());
		}
		if (!adjacencyList.containsKey(edge.getSecondVertex())) {
			initializeVertex(edge.getSecondVertex());
		}
	}
	
	private void initializeVertex(Vertex vertex) {
		adjacencyList.put(vertex, new ArrayList<>());
		directedAdjacencyList.put(vertex, new ArrayList<>());
		reversedDirectedAdjacencyList.put(vertex, new ArrayList<>());
		vertices.add(vertex);
	}
	
	private void addDirectedEdge(Edge edge) {
		List<Vertex> adjacentVertices = directedAdjacencyList.get(edge.getFirstVertex());
		if (!adjacentVertices.contains(edge.getSecondVertex())) {
			adjacentVertices.add(edge.getSecondVertex());
			directedAdjacencyList.put(edge.getFirstVertex(), adjacentVertices);
			addReverseDirectedEdge(edge);
		}
	}
	
	private void addReverseDirectedEdge(Edge edge) {
		List<Vertex> adjacentVertices = reversedDirectedAdjacencyList.get(edge.getSecondVertex());
		adjacentVertices.add(edge.getFirstVertex());
		reversedDirectedAdjacencyList.put(edge.getSecondVertex(), adjacentVertices);
	}
	
	private void addUndirectedEdge(Edge edge) {
		for (Vertex vertex : edge.getVertices()) {
			List<Vertex> adjacentVertices = adjacencyList.get(vertex);
			if (!adjacentVertices.contains(edge.getOtherVertex(vertex))) {
				adjacentVertices.add(edge.getOtherVertex(vertex));
				adjacencyList.put(vertex, adjacentVertices);
			}
		}
	}
	
	private List<Vertex> getAdjacentVertices(Vertex vertex, GraphAlingment align) {
		if (align == GraphAlingment.UNDIRECTED) {
			return adjacencyList.get(vertex);
		} else if (align == GraphAlingment.DIRECTED) {
			return directedAdjacencyList.get(vertex);
		} else if (align == GraphAlingment.REVERSE_DIRECTED) {
			return reversedDirectedAdjacencyList.get(vertex);
		}
		return null;
	}
	
	public List<List<Vertex>> getConnectedComponnents() {
		return connectedComponents;
	}
	
	public List<List<Vertex>> getStronglyConnectedComponents() {
		return stronglyConnectedComponents;
	}
	
	private enum GraphAlingment {
		UNDIRECTED, DIRECTED, REVERSE_DIRECTED
	}

	public int getNumberOfDirectConnections() {
		int count = 0;
//		System.out.println("getNumberOfDirectionsConnections() : " + count + " " +adjacencyList.size());

//		for(int i = 0; i < adjacencyList.size(); i++) {
//			count += adjacencyList.get(i).size();
//		}
		for(List<Vertex> edges : adjacencyList.values()) {
			count += edges.size();
		}
//		System.out.println("getNumberOfDirectionsConnections() : " + count + " " +adjacencyList.size());
		return count / 2;
	}

	public int getNumberOfIndirectConnections() {
		int indirectConnections = 0;
//		System.out.println("getNumberOfIndirectConnections() : " + indirectConnections + " " +adjacencyList.size());

		for (Vertex method : vertices) {
			Set<Vertex> directVariables = new HashSet<>(adjacencyList.get(method));
			Set<Vertex> visitedMethods = new HashSet<>();

			for (Vertex variable : directVariables) {
				for (Vertex connectedMethod : adjacencyList.get(variable)) {
					if (!connectedMethod.equals(method) && !directVariables.contains(connectedMethod)) {
						visitedMethods.add(connectedMethod);
					}
//					if(method.)
				}
			}
			indirectConnections += visitedMethods.size();
		}

		return indirectConnections / 2;
	}

	public int getNumberOfPossibleConnections() {
		int n = vertices.size();
//		System.out.println("getNumberOfPossibleConnections(): " + vertices.size());
		return (n * (n - 1)) / 2;
	}

	public void print() {
		System.out.println("Graph (Direkte und Indirkete Verbindungen): ");
		Map<Vertex, Integer> vertexIds = new HashMap<>();
		int id = 1;
		for (Vertex vertex : vertices) {
			vertexIds.put(vertex, id++);
		}

		for (Vertex vertex : vertices) {
			System.out.print("Knoten " + vertexIds.get(vertex) + " → ");

			// Direkte Verbindungen
			List<Vertex> directConnections = adjacencyList.get(vertex);
			System.out.print(" [Direkt: ");
			for (Vertex neighbor : directConnections) {
				System.out.print(vertexIds.get(neighbor) + " ");
			}
			System.out.print("] ");

			// Indirekte Verbindungen (über mindestens einen weiteren Knoten)
			Set<Vertex> indirectConnections = new HashSet<>();
			for (Vertex direct : directConnections) {
				for (Vertex indirect : adjacencyList.get(direct)) {
					if (!directConnections.contains(indirect) && !indirect.equals(vertex)) {
						indirectConnections.add(indirect);
					}
				}
			}

			System.out.print(" [Indirekt: ");
			for (Vertex indirect : indirectConnections) {
				System.out.print(vertexIds.get(indirect) + " ");
			}
			System.out.println("]");
		}
	}
}
