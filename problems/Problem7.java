package problems;

import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import graphs.*;

/** 7. Write a java program that accepts vertex pairs associated to the edges of an undirected graph
 * and the number of times each edge appears.
 * The program should construct an incidence matrix for the graph. */
public class Problem7 {
    public static void main(String[] args) {
        Graph[] graphs = makeGraphs();
        for (Graph g : graphs) {
            solve(g);
        }
    }

    private static void solve(Graph graph) {
        printIncidenceMatrix(
            makeIncidenceMatrix(graph)
        );
    }

    private static Map<Vertex, Map<Edge, Integer>> makeIncidenceMatrix(Graph graph) {
        Map<Vertex, Map<Edge, Integer>> incMat = new HashMap<>();
        for (Vertex v: graph.getVertexSet()) {
            incMat.put(v, v.getEdges());
        }
        return incMat;
    }

    private static void printIncidenceMatrix(Map<Vertex, Map<Edge, Integer>> incMat) {
        final int PADDING = 7;

        // undirected edges
        ArrayList<Set<Vertex>> edges = new ArrayList<>();
        for (Map<Edge, Integer> edgeCount : incMat.values()) {
            for (Edge e : edgeCount.keySet()) {
                Set<Vertex> edge = new HashSet<>();
                edge.add(e.getStart());
                edge.add(e.getEnd());
                if (!edges.contains(edge)) {
                    edges.add(edge);
                }
            }
        }
        // Print header row with edges
        System.out.print("       ");
        for (Set<Vertex> edge : edges) {
            System.out.print(padRight(pair(pair(edge)), PADDING) + " ");
        }
        System.out.println();
        
        // Print separator line
        System.out.print("     ");
        for (int i = 0; i < edges.size(); i++) {
            System.out.print("----");
        }
        System.out.println();
        
        // Print each row
        for (Vertex vertex : incMat.keySet()) {
            System.out.print(padRight(vertex.getLabel(), PADDING) + ": ");
            for (Set<Vertex> edge : edges) {
                ArrayList<Vertex> pair = pair(edge);
                Edge e;
                if (pair.size() == 2) {
                    e = new Edge(pair.get(0), pair.get(1));
                } else {
                    e = new Edge(pair.get(0), pair.get(0));
                }
                Integer n = vertex.getEdges().get(e);
                if (n == null) {
                    // undirected edge handling
                    n = vertex.getEdges().get(new Edge(e.getEnd(), e.getStart()));
                }
                if (n == null) {
                    n = 0;
                }
                System.out.print(padRight(n+"", PADDING) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static ArrayList<Vertex> pair(Set<Vertex> edge) {
        ArrayList<Vertex> pair = new ArrayList<>();
        for (Vertex v : edge) {
            pair.add(v);
        }
        return pair;
    }

    private static String pair(ArrayList<Vertex> pair) {
        String msg = "";
        if (pair.size() == 2) {
            msg = pair.get(0).getLabel() + "<->" + pair.get(1).getLabel();
        } else {
            msg = pair.get(0).getLabel() + "<->" + pair.get(0).getLabel();
        }
        return msg;
    }

    private static String padRight(String str, int numChars) {
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < numChars) {
            sb.append(' ');
        }
        return sb.toString();
    }

    private static Graph[] makeGraphs() {
        Graph g1 = new Graph(false);
        g1.addVertices(Set.of("a", "b"));
        g1.addEdge("a", "b");

        Graph g2 = new Graph(false);
        g2.addVertices(Set.of("a", "b"));
        g2.addEdge("a", "b");
        g2.addEdge("a", "b");
        g2.addEdge("a", "b");

        Graph g3 = new Graph(false);
        g3.addVertices(Set.of("a", "b", "c", "d"));
        g3.addEdge("a", "a");
        g3.addEdge("a", "b");
        g3.addEdge("a", "c");
        g3.addEdge("a", "d");

        Graph g4 = new Graph(false);
        g4.addVertices(Set.of("a", "b", "c", "d"));
        g4.addEdge("a", "a");
        g4.addEdge("a", "b");
        g4.addEdge("a", "c");
        g4.addEdge("a", "d");
        g4.addEdge("b", "c");
        g4.addEdge("b", "d");
        g4.addEdge("c", "d");

        Graph g5 = new Graph(false);
        g5.addVertex("a");
        g5.addVertex("b");
        g5.addEdge("a", "a");
        g5.addEdge("a", "b");

        return new Graph[]{g1, g2, g3, g4, g5};
    }
}
  
