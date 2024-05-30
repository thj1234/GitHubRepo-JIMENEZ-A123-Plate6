package problems;

import java.util.Map;
import java.util.Set;

import graphs.*;

/**4. Write a java program,
 * given the pair of vertex associated to the edges of an undirected graph,
 * it will output the degree of the vertex. */
public class Problem4 {
    public static void main(String[] args) {
        Graph[] graphs = makeGraphs();
        for (Graph g : graphs) {
            solve(g);
        }
    }

    public static void solve(Graph graph) {
        System.out.println("Looking at this graph: " + graph.toString());
        printVertexDegrees(graph);
        System.out.println();
    }

    /** Prints the degree of each vertex */
    public static void printVertexDegrees(Graph graph) {
        for (Vertex v : graph.getVertexSet()) {
            String label = v.getLabel();
            int deg = getVertexDegree(graph, label);
            System.out.println("Deg(" + label + ") = " + deg);
        }
    }

    public static int getVertexDegree(Graph graph, String vertexLabel) {
        Vertex vertex = graph.getVertex(vertexLabel);

        if (vertex == null) {
            return -1;
        }

        int count = 0;
        Map<Edge, Integer> edgeCount = vertex.getEdges();
        for (Edge e : edgeCount.keySet()) {
            Integer n = edgeCount.get(e);
            // if it's a loop
            if (vertex.equals(e.getEnd())) {
                count += n * 2;
            } else {
                count += n;
            }
        }
        return count;
    }

    private static Graph[] makeGraphs() {
        Graph g1 = new Graph(false);
        g1.addVertices(Set.of("a", "b", "c", "d"));

        Graph g2 = new Graph(false);
        g2.addVertices(Set.of("a", "b", "c", "d"));
        g2.addEdge("a", "b");
        g2.addEdge("a", "c");
        g2.addEdge("a", "d");
        g2.addEdge("b", "c");
        g2.addEdge("b", "d");
        g2.addEdge("c", "d");

        Graph g3 = new Graph(false);
        g3.addVertices(Set.of("a", "b", "c", "d"));
        g3.addEdge("a", "b");
        g3.addEdge("a", "c");
        g3.addEdge("a", "d");
        g3.addEdge("b", "c");
        g3.addEdge("b", "d");
        g3.addEdge("c", "d");
        g3.addEdge("d", "d");

        Graph g4 = new Graph(false);
        g4.addVertex("a");
        g4.addEdge("a", "a");
        g4.addEdge("a", "a");
        g4.addEdge("a", "a");
        g4.addEdge("a", "a");

        Graph g5 = new Graph(false);
        g5.addVertices(Set.of("a", "b", "c", "d"));
        g5.addEdge("a", "b");
        g5.addEdge("a", "c");
        g5.addEdge("a", "d");

        return new Graph[]{g1, g2, g3, g4, g5};
    }
}