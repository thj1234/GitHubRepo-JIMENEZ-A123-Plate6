package problems;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import graphs.*;

/**
 * 2. Write a java program that accepts an adjacency matrix of a graph.
 * The program should list the edges of this graph and give the number of times each edge appears.
 */
public class Problem2 {
    public static void main(String[] args) {
        for (Graph g : makeGraphs()) {
            solve(g);
        }
    }

    public static void solve(Graph graph) {
        System.out.println("Solving this graph: " + graph.toString());
        listEdgesWithCounts(graph.toAdjMatrix(), graph.isDirected());
        System.out.println();
    }

    public static void listEdgesWithCounts(Map<Vertex, Map<Vertex, Integer>> adjMat, boolean isDirected) {
        System.out.println("Looking at this matrix.... " + adjMat.toString());
        // so ugly!
        // for preventing listing undirected edges twice
        Set<Set<Vertex>> undirectedEdgesVisited = new HashSet<>();
        Set<Vertex> undirectedEdge = new HashSet<>();

        // traverse the adjacency matrix
        for (Vertex start : adjMat.keySet()) {
            Map<Vertex, Integer> neighbors = adjMat.get(start);
            for (Vertex end : neighbors.keySet()) {
                int count = neighbors.get(end);
                if (isDirected) {
                    // if directed graph, print the edge this way
                    System.out.println("Edge: " + start.getLabel() + " --> " + end.getLabel() + " (Count: " + count + ")");
                } else {
                    // if undirected, print this way. also add to the set
                    undirectedEdge.clear();
                    undirectedEdge.add(start);
                    undirectedEdge.add(end);
                    if (!undirectedEdgesVisited.contains(undirectedEdge)) {
                        undirectedEdgesVisited.add(undirectedEdge);
                        System.out.println("Edge: " + start.getLabel() + " <-> " + end.getLabel() + " (Count: " + count + ")");
                    }
                }
            }
        }
    }

    private static Graph[] makeGraphs() {
        Graph g1 = new Graph(false);
        g1.addVertices(Set.of("a", "b"));
        g1.addEdge("a", "b");
        g1.addEdge("a", "b");
        g1.addEdge("a", "b");
        g1.addEdge("b", "a");
        g1.addEdge("b", "a");

        Graph g2 = new Graph(true);
        g2.addVertices(Set.of("a", "b"));
        g2.addEdge("a", "b");
        g2.addEdge("a", "b");
        g2.addEdge("a", "b");
        g2.addEdge("b", "a");
        g2.addEdge("b", "a");

        Graph g3 = new Graph(false);
        g3.addVertices(Set.of("a", "b", "c", "d"));
        g3.addEdge("a", "a");
        g3.addEdge("a", "b");
        g3.addEdge("a", "c");
        g3.addEdge("a", "d");

        Graph g4 = new Graph(true);
        g4.addVertices(Set.of("a", "b", "c", "d"));
        g4.addEdge("a", "a");
        g4.addEdge("a", "b");
        g4.addEdge("a", "c");
        g4.addEdge("a", "d");

        Graph g5 = new Graph(false);
        g5.addVertex("a");
        g5.addVertex("b");
        g5.addEdge("a", "a");
        g5.addEdge("a", "b");

        Graph g6 = new Graph(true);
        g6.addVertex("a");
        g6.addVertex("b");
        g6.addEdge("a", "a");
        g6.addEdge("a", "b");

        return new Graph[]{g1, g2, g3, g4, g5, g6};
    }
}
