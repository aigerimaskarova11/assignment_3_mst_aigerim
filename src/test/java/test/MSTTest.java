package test;
import model.Edge;
import model.Graph;
import alg.KruskalMST;
import alg.PrimMST;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MSTTest {

    private Graph makeSample() {
        Graph g = new Graph(5); // id 1 graph
        g.addEdge(new Edge(0,1,3,"A","B"));
        g.addEdge(new Edge(0,2,4,"A","C"));
        g.addEdge(new Edge(1,2,2,"B","C"));
        g.addEdge(new Edge(1,3,6,"B","D"));
        g.addEdge(new Edge(2,4,5,"C","E"));
        g.addEdge(new Edge(3,4,7,"D","E"));
        return g;
    }

    @Test
    public void testMstCostsEqual() {
        Graph g = makeSample();
        PrimMST p = new PrimMST(g);
        KruskalMST k = new KruskalMST(g);
        assertEquals(p.weight(), k.weight(), 1e-9);
    }

    @Test
    public void testEdgesCount() {
        Graph g = makeSample();
        PrimMST p = new PrimMST(g);
        KruskalMST k = new KruskalMST(g);
        assertEquals(g.V() - 1, p.edges().size());
        assertEquals(g.V() - 1, k.edges().size());
    }

    @Test
    public void testAcyclic() {
        Graph g = makeSample();
        PrimMST p = new PrimMST(g);
        assertFalse(hasCycle(p.edges(), g.V()));
        KruskalMST k = new KruskalMST(g);
        assertFalse(hasCycle(k.edges(), g.V()));
    }

    @Test
    public void testOperationCountsNonNegative() {
        Graph g = makeSample();
        PrimMST p = new PrimMST(g);
        KruskalMST k = new KruskalMST(g);
        assertTrue(p.operationsCount() >= 0);
        assertTrue(k.operationsCount() >= 0);
    }

    private boolean hasCycle(List<Edge> edges, int V) {
        int n = V;
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
        for (Edge e : edges) {
            int a = find(parent, e.either());
            int b = find(parent, e.other(e.either()));
            if (a == b) return true;
            parent[a] = b;
        }
        return false;
    }

    private int find(int[] parent, int x) {
        if (parent[x] != x) parent[x] = find(parent, parent[x]);
        return parent[x];
    }
}
