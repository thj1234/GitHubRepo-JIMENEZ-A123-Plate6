package problems;

import java.util.Set;

import graphs.*;

/** 6. Write a java program that receives the vertex pairs associated to the edges of a graph,
 * the program should construct an adjacency matrix for the graph
 * (Produce a version that works when loops, multiple edges, or directed edges are present). */
public class Problem6 {
    public static void main(String[] args) {
        Graph[] graphs = makeGraphs();
        for (Graph g : graphs) {
            solve(g);
        }
    }

    public static void solve(Graph graph) {
        System.out.println("Input graph: " + graph.toString());
        System.out.println("Adj matrix: ");
        graph.printAdjacencyMatrix(graph.toAdjMatrix());
        System.out.println();
    }

    public static Graph[] makeGraphs() {
        Graph g1 = new Graph(false);
        g1.addVertices(Set.of("a", "b", "c", "d"));
        g1.addEdge("a", "b");
        g1.addEdge("b", "c");
        g1.addEdge("c", "d");
        g1.addEdge("d", "a");

        Graph g2 = new Graph(false);
        g2.addVertices(Set.of("a", "b", "c", "d"));
        g2.addEdge("a", "b");
        g2.addEdge("b", "a");
        g2.addEdge("b", "c");
        g2.addEdge("c", "d");
        g2.addEdge("d", "a");

        Graph g3 = new Graph(true);
        g3.addVertices(Set.of("a", "b", "c", "d"));
        g3.addEdge("a", "b");
        g3.addEdge("a", "b");
        g3.addEdge("b", "a");
        g3.addEdge("b", "a");
        g3.addEdge("b", "a");
        g3.addEdge("b", "c");
        g3.addEdge("c", "d");
        g3.addEdge("d", "a");

        Graph g4 = new Graph(false);
        g4.addVertices(Set.of("a", "b", "c", "d"));
        g4.addEdge("a", "a");
        g4.addEdge("a", "b");
        g4.addEdge("a", "b");
        g4.addEdge("b", "a");
        g4.addEdge("b", "a");
        g4.addEdge("b", "a");
        g4.addEdge("b", "c");
        g4.addEdge("c", "d");
        g4.addEdge("d", "a");

        Graph g5 = new Graph(true);
        g5.addVertices(Set.of("a", "b", "c", "d"));
        g5.addEdge("a", "a");
        g5.addEdge("a", "b");
        g5.addEdge("a", "b");
        g5.addEdge("b", "a");
        g5.addEdge("b", "a");
        g5.addEdge("b", "a");
        g5.addEdge("b", "c");
        g5.addEdge("c", "d");
        g5.addEdge("d", "a");

        return new Graph[]{g1, g2, g3, g4, g5};
    }
}