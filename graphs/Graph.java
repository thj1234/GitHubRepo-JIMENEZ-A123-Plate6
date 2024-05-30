/**
 * This is a modified version of CodingCleverly's implementation.
 * https://www.youtube.com/watch?v=dS44jZyj5gU
 */

package graphs;

// import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * This class represents a graph data structure.
 * It has a set of vertices and is either directed or undirected.
 * Each vertex has a label and maintains a map of its connected edges 
 * to their corresponding counts (allowing for duplicate edges).
 */
public class Graph {
    private HashSet<Vertex> vertices;
    private final boolean isDirected;

    public Graph(boolean isDirected) {
        this.vertices = new HashSet<Vertex>();
        this.isDirected = isDirected;
    }

    public Vertex addVertex(String label) {
        Vertex v = new Vertex(label);
        this.vertices.add(v);
        return v;
    }

    public void addVertices(Set<String> labels) {
        for (String label : labels) {
            addVertex(label);
        }
    }

    public void addEdge(Vertex start, Vertex end) throws IllegalArgumentException {
        if (!this.vertices.contains(start) || !this.vertices.contains(end)) {
            throw new IllegalArgumentException(
                "Both vertices must be in the graph before adding an edge."
            );
        }

        start.addEdge(end);
        if (!this.isDirected && !start.equals(end)) {
            end.addEdge(start);
        }
    }

    public void addEdge(String start, String end) throws IllegalArgumentException {
        Vertex startV = this.getVertex(start);
        Vertex endV = this.getVertex(end);
        this.addEdge(startV, endV);
    }

    public void removeEdge(Vertex start, Vertex end) throws NoSuchElementException, IllegalArgumentException {
        if (!vertices.contains(start) || !vertices.contains(end)) {
            throw new IllegalArgumentException(
                "Both vertices must be in the graph before adding an edge."
            );
        }

        start.removeEdge(end);
        if (!isDirected && !start.equals(end)) {
            end.removeEdge(start);
        }
    }

    public void removeEdge(Edge e) throws NoSuchElementException, IllegalArgumentException {
        this.removeEdge(e.getStart(), e.getEnd());
    }

    /** Removes a vertex from the graph's vertex set.
     * Also removes all edges that connect to this vertex.
     */
    public void removeVertex(Vertex v) {
        vertices.remove(v);

        // remove all edges ending at this vertex
        for (Vertex w : vertices) {
            for (Edge e : w.getEdges().keySet()) {
                if (e.getEnd().equals(v)) {
                    w.removeEdge(v);
                }
            }
        }
    }

    public Vertex getVertex(String label) {
        for (Vertex v : vertices) {
            if (label.equals(v.getLabel())) {
                return v;
            }
        }
        return null;
    }

    public HashSet<Vertex> getVertexSet() {
        return new HashSet<>(this.vertices);
    }

    public void setVertexSet(HashSet<Vertex> vertices) {
        this.vertices = vertices;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public Map<Vertex, Map<Vertex, Integer>> toAdjMatrix() {
        // ugly
        // System.out.println("---- Creating adj matrix ----");
        Map<Vertex, Map<Vertex, Integer>> adjMat = new HashMap<>();

        int count = 0;
        for (Vertex v : this.vertices) {
            // System.out.println("Vertex..." + v.toString());
            Map<Vertex, Integer> neighbors = v.getNeighbors();
            for (Vertex neighbor : neighbors.keySet()) {
                // System.out.println("Looking at this neighbor: " + neighbor.toString());
                count = neighbors.get(neighbor);
                if (adjMat.containsKey(v)) {
                    adjMat.get(v).put(neighbor, count);
                } else {
                    Map<Vertex, Integer> cell = new HashMap<>();
                    cell.put(neighbor, count);
                    adjMat.put(v, cell);
                }
                // System.out.println("adjMat changed. " + adjMat.toString());
            }
        }
        // System.out.println("---- Done creating adj matrix ----");
        return adjMat;
    }

    public String toString() {
        String direction;
        if (isDirected) {
            direction = "Directed";
        } else {
            direction = "Undirected";
        }
        return direction + " Graph " + vertices.toString();
    }

    public Graph copyOf() {
        Graph copy = new Graph(this.isDirected);
        copy.setVertexSet(this.getVertexSet());
        return copy;
    }

    public void print() {
        System.out.println(this.toString());
    }

    public void printAdjacencyMatrix(Map<Vertex, Map<Vertex, Integer>> adjMatrix) {
        // Get all vertex labels
        Set<String> vertexLabels = new HashSet<>();
        for (Vertex vertex : adjMatrix.keySet()) {
            vertexLabels.add(vertex.getLabel());
        }
        
        // Print header row with vertex labels
        System.out.print("       ");
        for (String label : vertexLabels) {
            System.out.print(padRight(label, 5) + " ");
        }
        System.out.println();
        
        // Print separator line
        System.out.print("     ");
        for (int i = 0; i < vertexLabels.size(); i++) {
            System.out.print("----");
        }
        System.out.println();
        
        // Print each row
        for (Vertex sourceVertex : adjMatrix.keySet()) {
            String sourceLabel = sourceVertex.getLabel();
            Map<Vertex, Integer> neighborCount = adjMatrix.get(sourceVertex);
            
            System.out.print(padRight(sourceLabel, 5) + ": ");
            
            for (String targetLabel : vertexLabels) {
                int count = neighborCount.getOrDefault(getVertex(targetLabel), 0);
                System.out.print(padRight(String.valueOf(count), 5) + " ");
            }
            System.out.println();
        }
    }
      
    private String padRight(String str, int numChars) {
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < numChars) {
            sb.append(' ');
        }
        return sb.toString();
    }
      
    public boolean equals(Graph g) {
        boolean sameVertices = this.vertices.equals(g.getVertexSet());
        boolean sameDirection = this.isDirected == g.isDirected();
        return sameVertices && sameDirection;
    }

    public int numVertices() {
        return this.vertices.size();
    }

    public int numEdges() {
        int totalDeg = 0;
        for (Vertex v : this.vertices) {
            totalDeg += getVertexDegree(v);
        }
        return totalDeg / 2;
    }

    public int getVertexDegree(Vertex v) {
        int count = 0;
        Map<Edge, Integer> edgeCount = v.getEdges();
        for (Edge e : edgeCount.keySet()) {
            Integer n = edgeCount.get(e);
            // if it's a loop
            if (v.equals(e.getEnd())) {
                count += n * 2;
            } else {
                count += n;
            }
        }
        return count;
    }

    /** Returns a sorted list containing all degrees of all vertices in the graph. */
    public int[] getDegreeSequence() {
        int[] sequence = new int[this.numVertices()];
        int i = 0;
        for (Vertex v : this.vertices) {
            sequence[i] = getVertexDegree(v);
            i++;
        }
        Arrays.sort(sequence);  // I could sort it while populating sequence to be more efficient
        return sequence;
    }

    /** Returns a sorted list containing all degrees of the neighbors of this vertex. */
    public int[] getDegreeSequence(Vertex v) {
        int[] sequence = new int[this.numVertices()];
        int i = 0;
        for (Vertex n : v.getNeighbors().keySet()) {
            sequence[i] = getVertexDegree(n);
            i++;
        }
        Arrays.sort(sequence);  // I could sort it while populating sequence to be more efficient
        return sequence;
    }

    /** Returns the number of components using DFS. */
    public int getNumComponents() {
        Set<Vertex> visited = new HashSet<>();

        int count = 0;
        for (Vertex v : this.getVertexSet()) {
            if (!visited.contains(v)) {
                getNumComponentsDFS(v, visited);
                count++;
            }
        }
        return count;
    }

    /** Does DFS on this vertex. Adds visited vertices to the visited set. */
    private void getNumComponentsDFS(Vertex v, Set<Vertex> visited) {
        visited.add(v);
        Set<Vertex> neighbors = v.getNeighbors().keySet();
        for (Vertex neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                getNumComponentsDFS(neighbor, visited);
            }
        }
    }

    public boolean isIsomorphic(Graph g) {
        // basic properties
        if (this.isDirected != g.isDirected() || this.numVertices() != g.numVertices() || this.numEdges() != g.numEdges()) {
            System.out.println("Basic property fail");
            return false;
        }

        // degree sequence
        int[] a = this.getDegreeSequence();
        int[] b = g.getDegreeSequence();
        if (!Arrays.equals(a, b)) {
            System.out.println("Degree sequence fail");
            System.out.println("a: " + Arrays.toString(a));
            System.out.println("b: " + Arrays.toString(b));
            return false;
        }

        // num components
        if (this.getNumComponents() != g.getNumComponents()) {
            System.out.println("Num components fails");
            return false;
        }

        // backtracking
        Set<Vertex> visited = new HashSet<>();
        return isIsomorphicHelper(this, g, visited);
    }

    private boolean isIsomorphicHelper(Graph g1, Graph g2, Set<Vertex> visited) {
        Set<Vertex> vertices1 = g1.getVertexSet();
        Set<Vertex> vertices2 = g2.getVertexSet();
        if (vertices1.isEmpty() && vertices2.isEmpty()) {
            return true;
        }

        // Iterate through vertices in the first graph
        for (Vertex v1 : vertices1) {
            if (!visited.contains(v1)) {
                visited.add(v1);

                // Find a matching vertex in the second graph with same degree
                for (Vertex v2 : vertices2) {
                    if (this.getVertexDegree(v1) == this.getVertexDegree(v2)) {
                        // Check if neighbor degrees match
                        if (areNeighborDegreesMatching(v1, v2, g1, g2)) {
                            // Recursive call with remaining unmapped vertices
                            Graph gx = g1.copyOf();
                            gx.removeVertex(v1);
                            Graph gy = g2.copyOf();
                            gy.removeVertex(v2);
                            if (isIsomorphicHelper(gx, gy, visited)) {
                                return true; // Isomorphism found
                            }
                        }
                    }
                }

                // Backtrack: remove v1 from visited if no match found
                visited.remove(v1);
            }
        }

        // No isomorphism found after exploring all possibilities
        return false;
    }
      
    private boolean areNeighborDegreesMatching(Vertex v1, Vertex v2, Graph g1, Graph g2) {
        int[] a = g1.getDegreeSequence(v1);
        int[] b = g2.getDegreeSequence(v2);
        return Arrays.equals(a, b);
    }
}
