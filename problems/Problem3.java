package problems;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import graphs.*;

/** Write a java program that will determine if a graph has a cycle or not. */
public class Problem3 {

    public static void main(String[] args) {
        Graph[] graphs = makeGraphs();
        for (Graph g : graphs) {
            solve(g);
        }
    }

    public static void solve(Graph graph) {
        System.out.println("Checking this graph: " + graph.toString());
        if (hasCycle(graph)) {
            System.out.println("Has a cycle!");
        } else {
            System.out.println("No cycle.");
        }
        System.out.println();
    }

    public static boolean hasCycle(Graph graph) {
        graph = graph.copyOf();  //important
        Set<Vertex> visited = new HashSet<>();
        for (Vertex v : graph.getVertexSet()) {
            if (!visited.contains(v)) {
                if (dfs(v, visited, graph)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean dfs(Vertex v, Set<Vertex> visited, Graph graph) {
        visited = new HashSet<Vertex>(visited);  // only mark when in the trail
        visited.add(v);
        // System.out.println("---------");
        // System.out.println("Visited: " + v.getLabel());
        // System.out.println("Status: " + visited.toString() + " " + graph.toString());
        Map<Edge, Integer> edgeCount = v.getEdges();
        for (Edge e : edgeCount.keySet()) {
            System.out.println("Edge: " + e.toString());
            int count = edgeCount.get(e);
            if (count >= 1) {
                if (visited.contains(e.getEnd())) {
                    // System.out.println("Cycle!!!!!");
                    return true;
                } else {
                    // System.out.println("Snip!");
                    graph.removeEdge(e);
                    return dfs(e.getEnd(), visited, graph);
                }
            }
        }
        return false;
    }

    private static Graph[] makeGraphs() {
        Graph g1 = new Graph(false);
        g1.addVertices(Set.of("a", "b", "c"));
        g1.addEdge("a", "b");
        g1.addEdge("b", "c");
        g1.addEdge("c", "a");

        Graph g2 = new Graph(true);
        g2.addVertices(Set.of("a", "b", "c"));
        g2.addEdge("a", "b");
        g2.addEdge("b", "c");
        g2.addEdge("c", "a");

        Graph g3 = new Graph(false);
        g3.addVertices(Set.of("a", "b"));
        g3.addEdge("a", "b");

        Graph g4 = new Graph(false);
        g4.addVertices(Set.of("a", "b"));
        g4.addEdge("a", "b");
        g4.addEdge("a", "b");

        Graph g5 = new Graph(false);
        g5.addVertices(Set.of("a", "b", "c", "d"));
        g5.addEdge("a", "b");
        g5.addEdge("b", "c");
        g5.addEdge("c", "d");

        Graph g6 = new Graph(false);
        g6.addVertices(Set.of("a", "b", "c", "d", "e"));
        g6.addEdge("a", "b");
        g6.addEdge("b", "c");
        g6.addEdge("c", "a");
        g6.addEdge("d", "e");

        Graph g7 = new Graph(true);
        g7.addVertices(Set.of("a", "b"));
        g7.addEdge("a", "b");
        g7.addEdge("a", "b");

        return new Graph[]{g1, g2, g3, g4, g5, g6, g7};
    }
}