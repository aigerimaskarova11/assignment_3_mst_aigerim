package runner;
import model.Edge;
import model.Graph;
import alg.KruskalMST;
import alg.PrimMST;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MSTRunner {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: MSTRunner <input.json> <output.json>");
            return;
        }
        String inputPath = args[0];
        String outputPath = args[1];

        String text = new String(Files.readAllBytes(Paths.get(inputPath)), StandardCharsets.UTF_8);
        JSONObject root = new JSONObject(text);
        JSONArray graphs = root.getJSONArray("graphs");

        JSONObject outRoot = new JSONObject();
        JSONArray results = new JSONArray();

        for (int i = 0; i < graphs.length(); i++) {
            JSONObject g = graphs.getJSONObject(i);
            int graphId = g.getInt("id");
            JSONArray nodes = g.getJSONArray("nodes");
            Map<String, Integer> nameToIndex = new HashMap<>();
            List<String> indexToName = new ArrayList<>();
            for (int k = 0; k < nodes.length(); k++) {
                String name = nodes.getString(k);
                nameToIndex.put(name, k);
                indexToName.add(name);
            }
            Graph graph = new Graph(nodes.length());
            JSONArray edges = g.getJSONArray("edges");
            for (int e = 0; e < edges.length(); e++) {
                JSONObject eo = edges.getJSONObject(e);
                String from = eo.getString("from");
                String to = eo.getString("to");
                double weight = eo.getDouble("weight");
                int u = nameToIndex.get(from);
                int vtx = nameToIndex.get(to);
                Edge edge = new Edge(u, vtx, weight, from, to);
                graph.addEdge(edge);
            }

            JSONObject graphResult = new JSONObject();
            graphResult.put("graph_id", graphId);
            JSONObject inputStats = new JSONObject();
            inputStats.put("vertices", graph.V());
            inputStats.put("edges", graph.E());
            graphResult.put("input_stats", inputStats);

            // Prim
            long primStart = System.nanoTime();
            PrimMST prim = new PrimMST(graph);
            long primEnd = System.nanoTime();
            double primMs = (primEnd - primStart) / 1_000_000.0;

            JSONObject primObj = new JSONObject();
            JSONArray primEdges = new JSONArray();
            for (Edge e : prim.edges()) {
                JSONObject eo = new JSONObject();
                eo.put("from", e.fromName());
                eo.put("to", e.toName());
                eo.put("weight", e.weight());
                primEdges.put(eo);
            }
            primObj.put("mst_edges", primEdges);
            primObj.put("total_cost", prim.weight());
            primObj.put("operations_count", prim.operationsCount());
            primObj.put("execution_time_ms", primMs);

            // Kruskal
            long krStart = System.nanoTime();
            KruskalMST kr = new KruskalMST(graph);
            long krEnd = System.nanoTime();
            double krMs = (krEnd - krStart) / 1_000_000.0;

            JSONObject krObj = new JSONObject();
            JSONArray krEdges = new JSONArray();
            for (Edge e : kr.edges()) {
                JSONObject eo = new JSONObject();
                eo.put("from", e.fromName());
                eo.put("to", e.toName());
                eo.put("weight", e.weight());
                krEdges.put(eo);
            }
            krObj.put("mst_edges", krEdges);
            krObj.put("total_cost", kr.weight());
            krObj.put("operations_count", kr.operationsCount());
            krObj.put("execution_time_ms", krMs);

            graphResult.put("prim", primObj);
            graphResult.put("kruskal", krObj);

            double pcost = prim.weight();
            double kcost = kr.weight();
            if (Math.abs(pcost - kcost) > 1e-6) {
                graphResult.put("warning", String.format("Prim and Kruskal produced different costs: prim=%.6f kruskal=%.6f", pcost, kcost));
            }

            results.put(graphResult);
        }

        outRoot.put("results", results);

        try (Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), StandardCharsets.UTF_8))) {
            w.write(outRoot.toString(2));
        }

        System.out.println("Results written to " + outputPath);
    }
}
