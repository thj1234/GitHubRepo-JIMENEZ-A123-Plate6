/**
 * This is a modified version of CodingCleverly's implementation.
 * https://www.youtube.com/watch?v=dS44jZyj5gU
 */

package graphs;

public class Edge {
    private final Vertex start, end;

    public Edge(Vertex start, Vertex end) {
        this.start = start;
        this.end = end;
    }

    public Vertex getStart() {
        return this.start;
    }

    public Vertex getEnd() {
        return this.end;
    }

    public boolean equals(Edge e) {
        return (this.start.equals(e.getStart())
             && this.end.equals(e.getEnd()));
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.start == null) ? 0 : this.start.hashCode());
        result = prime * result + ((this.end == null) ? 0 : this.end.hashCode());
        return result;
    }

    public String toString() {
        return "{" + this.start.getLabel() + ", " + this.end.getLabel() + "}";
    }
}
