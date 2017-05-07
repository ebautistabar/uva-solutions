package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 11060 - Topological Sort, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2001
// Notes at the bottom
class UVA11060Beverages {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int test = 1;
        String line = getLine();
        while (line != null) {
            int available = Integer.parseInt(line.trim());
            Map<String, List<String>> adjList = new HashMap<>();
            Map<String, Beverage> beverages = new HashMap<>();
            for (int inputOrder = 0; inputOrder < available; inputOrder++) {
                String name = getLine().trim();
                adjList.put(name, new ArrayList<String>());
                beverages.put(name, new Beverage (name, inputOrder));
            }
            int orderings = Integer.parseInt(getLine().trim());
            for (int i = 0; i < orderings; i++) {
                StringTokenizer st = new StringTokenizer(getLine());
                String smaller = st.nextToken();
                String bigger = st.nextToken();
                adjList.get(smaller).add(bigger);
                beverages.get(bigger).indegree++;
            }

            PriorityQueue<Beverage> pq = new PriorityQueue<>();
            // traverse all beverages and add the ones with in-degree 0 to pq
            List<String> toRemove = new ArrayList<>();
            for (String name : beverages.keySet()) {
                if (beverages.get(name).indegree == 0) {
                    // can't remove while iterating keySet, so use list to
                    // keep track of items to remove
                    toRemove.add(name);
                    pq.add(beverages.get(name));
                }
            }
            for (String name : toRemove) { // now remove the items
                beverages.remove(name);
            }
            List<String> topologicalSort = new ArrayList<>();
            while (!pq.isEmpty()) {
                Beverage beverage = pq.remove();
                topologicalSort.add(beverage.name);
                // decrease in-degree or neighbors
                for (int j = 0; j < adjList.get(beverage.name).size(); j++) {
                    String neighbor = adjList.get(beverage.name).get(j);
                    if (beverages.get(neighbor) != null) {
                        beverages.get(neighbor).indegree--;
                        // if new in-degree is 0, add to pq
                        if (beverages.get(neighbor).indegree == 0) {
                            pq.add(beverages.remove(neighbor));
                        }
                    }
                }
            }
            // We can know if it the graph has cycles by checking if there's
            // still nodes with in-degree > 0 in 'beverages'

            StringBuilder order = new StringBuilder();
            order.append(topologicalSort.get(0));
            for (int i = 1; i < topologicalSort.size(); i++) {
                order.append(' ');
                order.append(topologicalSort.get(i));
            }
            out.printf("Case #%d: Dilbert should drink beverages in this order: %s.%n%n", test, order);
            test++;
            line = getLine();
        }
        out.close();
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }

    private static class Beverage implements Comparable<Beverage> {
        String name;
        int indegree = 0;
        int inputOrder;
        public Beverage(String name, int inputOrder) {
            this.name = name;
            this.inputOrder = inputOrder;
        }
        public int compareTo(Beverage arg0) {
            return Integer.compare(inputOrder, arg0.inputOrder);
        }
    }
}
/*
use kahns algoritm
pick nodes with in-degree 0
decrease in-degree of neighbors
repeat
when picking the next node with in-degree 0, we sort them by input order with a priority queue

it's like that because we cannot output any toposort: in case of tie, we must output the nodes in the order they appear in the input. if it weren't for that, we could do the standard dfs algo for toposort

first, get in-degree of every node
map name to in-degree 0
when reading the orderings, increment in-degree of names
add all nodes with in-degree 0 to prioqueue
while prioqueue not empty
 u = dequeue
 decrease in-degree of all nodes neighbors of u
 if any is 0, insert in prioqueue
*/