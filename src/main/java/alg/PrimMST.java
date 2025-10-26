package alg;
import model.Edge;
import model.Graph;
import java.util.ArrayList;
import java.util.List;

public class PrimMST {
    private final List<Edge> mstEdges = new ArrayList<>();
    private double totalWeight = 0.0;
    private long operationsCount = 0;

    public PrimMST(Graph G) {
        int V = G.V();
        boolean[] inMST = new boolean[V];
        double[] dist = new double[V];
        Edge[] edgeTo = new Edge[V];

        for (int i = 0; i < V; i++) dist[i] = Double.POSITIVE_INFINITY;

        for (int start = 0; start < V; start++) {
            if (inMST[start]) continue;
            dist[start] = 0.0;
            for (int cnt = 0; cnt < V; cnt++) {
                int u = -1;
                double best = Double.POSITIVE_INFINITY;
                for (int v = 0; v < V; v++) {
                    operationsCount++;
                    if (!inMST[v] && dist[v] < best) {
                        best = dist[v];
                        u = v;
                    }
                }
                if (u == -1) break;
                inMST[u] = true;
                if (edgeTo[u] != null) {
                    mstEdges.add(edgeTo[u]);
                    totalWeight += edgeTo[u].weight();
                }
                for (Edge e : G.adj(u)) {
                    int w = e.other(u);
                    operationsCount++;
                    if (!inMST[w] && e.weight() < dist[w]) {
                        dist[w] = e.weight();
                        edgeTo[w] = e;
                        operationsCount++;
                    }
                }
            }
        }
    }

    public List<Edge> edges() {
        return mstEdges;
    }

    public double weight() {
        return totalWeight;
    }

    public long operationsCount() {
        return operationsCount;
    }
}
