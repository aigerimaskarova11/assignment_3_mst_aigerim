package model;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int V;
    private int E;
    private final List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public Graph(int V) {
        this.V = V;
        this.E = 0;
        this.adj = (List<Edge>[]) new List[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
        }
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public Iterable<Edge> edges() {
        List<Edge> list = new ArrayList<>();
        boolean[][] seen = new boolean[V][V];
        for (int v = 0; v < V; v++) {
            for (Edge e : adj[v]) {
                int w = e.other(v);
                if (!seen[v][w]) {
                    list.add(e);
                    seen[v][w] = true;
                    seen[w][v] = true;
                }
            }
        }
        return list;
    }
}
