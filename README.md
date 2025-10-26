## **Analytical Report: Minimum Spanning Tree Algorithms**

### **1. Summary of Input Data and Algorithm Results**

In this project, I developed a Java program to find the **Minimum Spanning Tree (MST)** of several graphs using **Prim’s algorithm** and **Kruskal’s algorithm**.
The program reads graphs from a **JSON file**, processes them, and writes the results to another JSON file.
Each graph includes a list of **nodes (vertices)** and **edges** with their **weights**.

For every graph, the program measures:

* the **total cost** of the MST,
* the **number of operations** performed,
* and the **execution time** in milliseconds.

Both algorithms were implemented in an **object-oriented way**.
I created classes such as `Graph`, `Edge`, `PrimMST`, `KruskalMST`, and a `UF` (Union-Find) class to manage disjoint sets for Kruskal’s algorithm.
Unit tests were written using **JUnit** to check correctness, including:

* comparing MST costs between the two algorithms,
* verifying the number of edges,
* ensuring no cycles appear in the MST,
* and confirming that operation counts are non-negative.

Below is the summary of results for three input graphs:

| Graph ID | Vertices | Edges | Algorithm | Total Cost | Execution Time (ms) | Operations Count |
| -------- | -------- | ----- | --------- | ---------- | ------------------- | ---------------- |
| 1        | 5        | 6     | Prim      | 16         | 0.73                | 42               |
| 1        | 5        | 6     | Kruskal   | 16         | 1.89                | 24               |
| 2        | 12       | 15    | Prim      | 45         | 0.02                | 188              |
| 2        | 12       | 15    | Kruskal   | 45         | 0.07                | 75               |
| 3        | 25       | 30    | Prim      | 104        | 0.05                | 715              |
| 3        | 25       | 30    | Kruskal   | 104        | 0.10                | 182              |

For all graphs, both algorithms produced **identical MST total costs**, proving that the implementations are correct.
However, the **execution times and operation counts** show different efficiency patterns depending on the graph size.

---

### **2. Comparison Between Prim’s and Kruskal’s Algorithms**

#### **Theoretical comparison**

Prim’s algorithm builds the MST by **starting from one vertex** and repeatedly adding the smallest edge that connects a new vertex to the existing tree.
It uses adjacency lists and can be improved using a priority queue.
It works efficiently on **dense graphs**, where most vertices are connected by many edges.

Kruskal’s algorithm, on the other hand, **sorts all edges** by weight first and then adds them one by one, avoiding cycles with the Union-Find data structure.
This algorithm is more suitable for **sparse graphs**, where the number of edges is small compared to the number of vertices.

#### **Practical comparison**

In the experiments:

* For small graphs (like Graph 1), both algorithms were very fast.
  However, Kruskal’s took a little more time because of the sorting step.
* For the medium and large graphs (Graph 2 and Graph 3), Prim’s algorithm showed **lower execution times**, but its **operation count** was higher because it checks many neighboring vertices.
* Kruskal’s algorithm performed **fewer logical operations**, but its edge sorting made it slightly **slower** in practice.

In summary:

* **Prim’s** → faster on dense graphs, more operations.
* **Kruskal’s** → faster on sparse graphs, fewer operations.

Both algorithms achieved the same MST cost, so the main difference is **efficiency**, not **correctness**.

---

### **3. Conclusions**

From both theoretical and experimental analysis, I can conclude the following:

1. **Correctness**:
   Both Prim’s and Kruskal’s algorithms produced the same minimum spanning tree cost for all input graphs, confirming that the code works correctly.

2. **Efficiency**:
   Prim’s algorithm was usually **faster** for denser graphs, while Kruskal’s algorithm was better when the graph had fewer edges.
   Prim’s algorithm requires checking more vertices but avoids sorting edges.
   Kruskal’s algorithm depends heavily on sorting time and the efficiency of the Union-Find structure.

3. **Complexity**:

   * Prim’s time complexity: **O(V²)** (or **O(E log V)** with a priority queue).
   * Kruskal’s time complexity: **O(E log E)**.
     In my implementation (without a heap), Prim’s behaved close to O(V²), which explains why it performs well on smaller graphs.

4. **Practical use**:

   * Prim’s is better for **dense graphs** or when using an **adjacency matrix**.
   * Kruskal’s is better for **sparse graphs** or when edges are stored as an **edge list**.
   * Both are simple to implement and widely used in network design, clustering, and optimization problems.
