package problems;

import java.util.HashSet;
// import java.util.Map;
import java.util.Set;
import graphs.*;

/**
 * 1. Write a java program that receives a list of edges of a simple graph,
 * the program should determine whether it is connected
 * and find the number of connected components if it is not connected.
 */
public class Problem1 {
    private static Set<Vertex> visited = new HashSet<>();

    public static void main(String[] args) {
        Graph[] graphs = makeGraphs();
        for (Graph g : graphs) {
            solve(g);
        }
    }

    private static void solve(Graph graph) {
        int count = countComponents(graph);
        System.out.println("Traversing this graph: " + graph.toString());
        if (count == 0) {
            System.out.println("0 components.");
        } else if (count == 1) {
            System.out.println("Connected graph (1 component).");
        } else {
            System.out.println("Disconnected graph (" + count + " components).");
        }
        System.out.println();
    }

    /** Returns the number of components using DFS. */
    private static int countComponents(Graph graph) {
        int count = 0;
        for (Vertex v : graph.getVertexSet()) {
            if (!visited.contains(v)) {
                dfs(v);
                count++;
            }
        }
        return count;
    }

    /** Does DFS on this vertex. Adds visited vertices to the visited set. */
    private static void dfs(Vertex v) {
        visited.add(v);
        Set<Vertex> neighbors = v.getNeighbors().keySet();
        for (Vertex neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor);
            }
        }
    }

    private static Graph[] makeGraphs() {
        Graph g1 = new Graph(false);
        g1.addVertices(Set.of("a", "b", "c", "d", "e"));
        g1.addEdge("a", "b");
        g1.addEdge("b", "c");
        g1.addEdge("c", "a");
        g1.addEdge("d", "e");

        Graph g2 = new Graph(false);
        g2.addVertex("a");

        Graph g3 = new Graph(false);
        g3.addVertices(Set.of("a", "b", "c", "d", "e", "f", "g", "h"));
        g3.addEdge("a", "b");
        g3.addEdge("c", "d");
        g3.addEdge("e", "f");
        g3.addEdge("g", "h");

        Graph g4 = new Graph(false);
        g4.addVertices(Set.of("a", "b", "c", "d"));
        g4.addEdge("a", "b");
        g4.addEdge("a", "c");
        g4.addEdge("a", "d");

        Graph g5 = new Graph(false);
        g5.addVertices(Set.of("a", "b", "c", "d"));
        g5.addEdge("a", "b");
        g5.addEdge("b", "c");

        return new Graph[]{g1, g2, g3, g4, g5};
    }
}
  