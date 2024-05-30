package problems;

import java.util.Set;

import graphs.*;

/** 8. Write a java program that checks whether two graphs are isomorphic or not, given a set of vertices. */
public class Problem8 {

    public static void main(String[] args) {
        Graph[][] graphPairs = makeGraphPairs();
        int count = 1;
        for (Graph[] pair : graphPairs) {
            System.out.println("Comparison " + count);
            solve(pair[0], pair[1]);
            count++;
            System.out.println();
        }
    }

    private static void solve(Graph a, Graph b) {
        if (a.isIsomorphic(b)) {
            System.out.println("Isomorphic");
        } else {
            System.out.println("Not isomorphic");
        }
    }


    private static Graph[][] makeGraphPairs() {
        // Isomorphic
        Graph g1a = new Graph(false);
        g1a.addVertices(Set.of("a", "b", "c"));
        g1a.addEdge("a", "b");
        Graph g1b = g1a.copyOf();

        // Isomorphic
        Graph g2a = new Graph(false);
        g2a.addVertices(Set.of("a", "b", "c"));
        g2a.addEdge("a", "b");
        Graph g2b = new Graph(false);
        g2b.addVertices(Set.of("x", "y", "z"));
        g2b.addEdge("x", "z");

        // Isomorphic
        Graph g3a = makeGraph("bobby", 50, 25, false);
        Graph g3b = makeGraph("bob", 50, 25, false);
        
        // Not isomorphic
        Graph g4a = makeGraph("bobby", 500, 100, false);
        Graph g4b = makeGraph("bob", 499, 100, false);

        // Isomorphic
        Graph g5a = new Graph(false);
        g5a.addVertices(Set.of("a", "b", "c", "d", "e"));
        g5a.addEdge("a", "b");
        g5a.addEdge("b", "c");
        Graph g5b = new Graph(false);
        g5b.addVertices(Set.of("a", "b", "c", "d", "e"));
        g5b.addEdge("a", "b");
        g5b.addEdge("c", "d");

        return new Graph[][]{{g1a, g1b}, {g2a, g2b}, {g3a, g3b}, {g4a, g4b}, {g5a, g5b}};
    }

    private static Graph makeGraph(String label, int numVertices, int numEdges, boolean isDirected) {
        Graph graph = new Graph(isDirected);
        for (int i = 0; i < numVertices; i++) {
            graph.addVertex(label + i);
        }
        // arbitrary edges
        for (int i = 0; i < numEdges; i++) {
            graph.addEdge(label + i, label + ((i + 1) % numVertices/2));
        }
        return graph;
    }
}
  
