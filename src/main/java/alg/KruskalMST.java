package alg;
import model.Edge;
import model.Graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KruskalMST {
    private final List<Edge> mstEdges = new ArrayList<>();
    private double totalWeight = 0.0;
    private long operationsCount = 0;

    private static class CountableEdge implements Comparable<CountableEdge> {
        static long compareCount = 0;
        final Edge e;

        CountableEdge(Edge e) {
            this.e = e;
        }

        @Override
        public int compareTo(CountableEdge o) {
            compareCount++;
            return Double.compare(this.e.weight(), o.e.weight());
        }
    }

    public KruskalMST(Graph G) {
        List<Edge> list = new ArrayList<>();
        for (Edge e : G.edges()) list.add(e);
        CountableEdge.compareCount = 0;
        CountableEdge[] arr = new CountableEdge[list.size()];
        for (int i = 0; i < list.size(); i++) arr[i] = new CountableEdge(list.get(i));
        Arrays.sort(arr);
        operationsCount += CountableEdge.compareCount;

        UF uf = new UF(G.V());
        for (CountableEdge ce : arr) {
            Edge e = ce.e;
            int v = e.either();
            int w = e.other(v);
            operationsCount += 2;
            int rv = uf.find(v);
            int rw = uf.find(w);
            if (rv != rw) {
                uf.union(rv, rw);
                operationsCount++;
                mstEdges.add(e);
                totalWeight += e.weight();
                if (mstEdges.size() == G.V() - 1) break;
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
