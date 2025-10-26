package model;

public class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;
    private final String fromName;
    private final String toName;

    public Edge(int v, int w, double weight, String fromName, String toName) {
        this.v = v;
        this.w = w;
        this.weight = weight;
        this.fromName = fromName;
        this.toName = toName;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v) return w;
        if (vertex == w) return v;
        throw new IllegalArgumentException("Invalid endpoint");
    }

    public double weight() {
        return weight;
    }

    public String fromName() {
        return fromName;
    }

    public String toName() {
        return toName;
    }

    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    @Override
    public String toString() {
        return String.format("%s-%s %.2f", fromName, toName, weight);
    }
}
