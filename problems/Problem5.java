package problems;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import graphs.*;

/** 5. Write a java program that receives a list of edges of a simple graph
 * and determine whether the graph is bipartite.
 */
public class Problem5 {
    final static String RED = "RED";
    final static String BLUE = "BLUE";
    static String color = RED;
    static Map<Vertex, String> visitedVertexColor = new HashMap<>();

    public static void main(String[] args) {
        Graph[] graphs = makeGraphs();
        for (Graph g : graphs) {
            solve(g);
        }
    }

    private static void solve(Graph graph) {
        System.out.println("Coloring graph: " + graph.toString());
        if (isBipartite(graph)) {
            System.out.println("It's bipartite.");
        } else {
            System.out.println("Not bipartite.");
        }
        System.out.println();
    }

    public static boolean isBipartite(Graph graph) {
        // Colors vertices with blue or red to determine if it's bipartite
        for (Vertex v : graph.getVertexSet()) {
            if (!visited(v)) {
                if (!dfs(v, RED)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean dfs(Vertex v, String color) {
        // Helper for isBipartite
        color(v, color);
        System.out.println("Colored " + v.getLabel() + " with " + color);

        Set<Vertex> neighbors = v.getNeighbors().keySet();
        for (Vertex neighbor : neighbors) {
            if (colorOf(v).equals(colorOf(neighbor))) {
                // System.out.println(v.getLabel() + " is " + color + " while " + neighbor.getLabel() + " is " + colorOf(neighbor));
                return false;
            }
            if (!visited(neighbor)) {
                if (!dfs(neighbor, oppositeColor(color))) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void color(Vertex v, String color) {
        visitedVertexColor.put(v, color);
    }

    public static String colorOf(Vertex v) {
        return visitedVertexColor.get(v);
    }

    public static String oppositeColor(String color) {
        if (color.equals(RED)) {
            return BLUE;
        } else {
            return RED;
        }
    }

    public static boolean visited(Vertex v) {
        return visitedVertexColor.containsKey(v);
    }

    private static Graph[] makeGraphs() {
        Graph g1 = new Graph(false);
        g1.addVertices(Set.of("a", "b", "c", "d", "e", "f"));
        g1.addEdge("a", "b");
        g1.addEdge("b", "c");
        g1.addEdge("c", "d");
        g1.addEdge("d", "e");
        g1.addEdge("e", "f");

        Graph g2 = new Graph(false);
        g2.addVertices(Set.of("a", "b", "c", "d", "e", "f"));
        g2.addEdge("a", "b");
        g2.addEdge("b", "c");
        g2.addEdge("c", "d");
        g2.addEdge("d", "e");
        g2.addEdge("e", "f");
        g2.addEdge("f", "d");

        Graph g3 = new Graph(false);

        Graph g4 = new Graph(false);
        g4.addVertices(Set.of("a"));

        Graph g5 = new Graph(false);
        g5.addVertices(Set.of("a", "b"));

        Graph g6 = new Graph(false);
        g6.addVertices(Set.of("a", "b", "c", "d", "e", "f", "g", "h", "i"));
        g6.addEdge("a", "b");
        g6.addEdge("b", "c");
        g6.addEdge("c", "d");
        g6.addEdge("d", "e");
        g6.addEdge("e", "f");
        g6.addEdge("g", "h");

        return new Graph[]{g1, g2, g3, g4, g5, g6};
    }
}