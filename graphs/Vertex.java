/**
 * This is a modified version of CodingCleverly's implementation.
 * https://www.youtube.com/watch?v=dS44jZyj5gU
 */

package graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * This class represents a vertex in a graph data structure.
 * A vertex has a label and maintains a map of its connected edges 
 * to their corresponding counts (allowing for duplicate edges).
 */
public class Vertex {
    private final String label;
    private Map<Edge, Integer> edges;
    
    public Vertex(String label) {
        this.label = label;
        this.edges = new HashMap<>();
    }

    /**
     * Adds a directed edge to another vertex (`end`) from this vertex.
     * If an edge with the same destination already exists, its count is incremented.
     */
    void addEdge(Vertex end) {
        Edge e = new Edge(this, end);
        this.edges.put(e, this.edges.getOrDefault(e, 0) + 1);
    }

    /** Removes a directed edge to another vertex (`end`) from this vertex. */
    void removeEdge(Vertex end) throws NoSuchElementException {
        Edge e = new Edge(this, end);

        if (!this.edges.containsKey(e)) {
            throw new NoSuchElementException(
                this.toString() + " does not have edge to " + end.toString()
            );
        }
        
        Integer count = this.edges.get(e) - 1;
        if (count == 0) {
            this.edges.remove(e);
        } else {
            this.edges.replace(e, count);
        }
    }

    public int getDegree() {
        int count = 0;
        Map<Edge, Integer> edgeCount = this.getEdges();
        for (Edge e : edgeCount.keySet()) {
            Integer n = edgeCount.get(e);
            // if it's a loop
            if (this.equals(e.getEnd())) {
                count += n * 2;
            } else {
                count += n;
            }
        }
        return count;
    }

    public String getLabel() {
        return this.label;
    }

    public Map<Edge, Integer> getEdges() {
        return Map.copyOf(this.edges);
    }

    /** Returns a map of out-neighbors to the number of edges connecting to each one.*/
    public Map<Vertex, Integer> getNeighbors() {
        Map<Vertex, Integer> neighbors = new HashMap<>();
        int count;
        for (Edge e : edges.keySet()) {
            count = this.edges.get(e);
            neighbors.put(e.getEnd(), count);
        }
        return neighbors;
    }

    public String toString() {
        // For each edge, get the label of the end vertex only
        Map<String, Integer> ends = new HashMap<>();
        String label;
        Integer count;
        for (Edge e : edges.keySet()) {
            label = e.getEnd().getLabel();
            count = edges.get(e);
            ends.put(label, count);
        }

        return "V(" + this.label + " -> " + ends.toString() + ")";
    }

    public boolean equals(Vertex v) {
        boolean sameLabel = this.label.equals(v.getLabel());
        boolean sameEdges = this.edges.equals(v.getEdges());
        return sameLabel && sameEdges;
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }

    // @Override
    // public int hashCode() {
    //     final int prime = 31;
    //     int result = 1;
    //     result = prime * result + ((this.label == null) ? 0 : this.label.hashCode());
    //     result = prime * result + ((this.edges == null) ? 0 : this.edges.hashCode());
    //     return result;
    // }
}
