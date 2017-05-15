package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 11492 - Single-Source Shortest Paths (SSSP) On Weighted Graph: Dijkstra's, Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2487
// Notes at the bottom
class UVA11492Babel {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int ALPHABET_SIZE = 28;

    public static void main(String args[]) throws IOException {

        int words = Integer.parseInt(getLine().trim());
        while (words != 0) {
            Map<String, Integer> langToIdx = new HashMap<>();
            StringTokenizer st = new StringTokenizer(getLine());
            String start = st.nextToken();
            String goal = st.nextToken();
            Map<String, List<Tuple>> adjList = new HashMap<>();
            // Insert the start language here. In case it doesn't have outgoing edges
            // this prevents us from doing special checks for nulls later in Dijkstra
            langToIdx.put(start, adjList.size());
            adjList.put(start, new ArrayList<Tuple>());
            for (int i = 0; i < words; i++) {
                st = new StringTokenizer(getLine());
                String lang1 = st.nextToken();
                String lang2 = st.nextToken();
                String word = st.nextToken();
                List<Tuple> adj = adjList.get(lang1);
                if (adj == null) {
                    langToIdx.put(lang1, adjList.size());
                    adj = new ArrayList<Tuple>();
                    adjList.put(lang1, adj);
                }
                adj.add(new Tuple(lang2, word.charAt(0), word.length()));
                // Do the same for the lang2->lang1
                adj = adjList.get(lang2);
                if (adj == null) {
                    langToIdx.put(lang2, adjList.size());
                    adj = new ArrayList<Tuple>();
                    adjList.put(lang2, adj);
                }
                adj.add(new Tuple(lang1, word.charAt(0), word.length()));
            }

            int totalDistance = -1;
            int languages = adjList.size();
            int[][] distance = new int[languages][ALPHABET_SIZE + 1];
            for (int i = 0; i < languages; i++) {
                Arrays.fill(distance[i], Integer.MAX_VALUE);
            }
            PriorityQueue<Tuple> pq = new PriorityQueue<>();
            // If start and goal are the same, we must consider there's no solution.
            // Don't insert anything in the queue, so Dijkstra doesn't run
            if (!start.equals(goal)) {
                pq.add(new Tuple(start, ALPHABET_SIZE, 0)); // non-valid first char so we can pick all edges
            }
            distance[langToIdx.get(start)][ALPHABET_SIZE] = 0;
            while (!pq.isEmpty()) {
                Tuple current = pq.remove();
                if (goal.equals(current.lang)) {
                    totalDistance = current.distance;
                    break;
                }
                int currentId = langToIdx.get(current.lang);
                if (distance[currentId][current.firstChar] < current.distance) continue;
                for (Tuple next : adjList.get(current.lang)) {
                    int nextId = langToIdx.get(next.lang);
                    if (next.firstChar != current.firstChar &&
                            distance[nextId][next.firstChar] > distance[currentId][current.firstChar] + next.distance) {
                        distance[nextId][next.firstChar] = distance[currentId][current.firstChar] + next.distance;
                        pq.add(new Tuple(next.lang, next.firstChar, distance[nextId][next.firstChar]));
                    }
                }
            }

            out.println(totalDistance == -1 ? "impossivel" : totalDistance);

            words = Integer.parseInt(getLine().trim());
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

    private static class Tuple implements Comparable<Tuple> {
        String lang;
        int firstChar;
        int distance;
        public Tuple(String lang, char firstChar, int weight) {
            this.lang = lang;
            this.firstChar = firstChar - 'a';
            this.distance = weight;
        }public Tuple(String lang, int firstChar, int weight) {
            this.lang = lang;
            this.firstChar = firstChar;
            this.distance = weight;
        }
        public int compareTo(Tuple o) {
            int wCmp = Integer.compare(distance, o.distance);
            return wCmp;
        }
        public String toString() {
            return String.format("[%s,%c,%d]", lang, firstChar + 'a', distance);
        }
    }
}
/*
first approach that comes to mind is:
languages are nodes, edges are shared words and weights are their length. edge can be traversed
if the previous edge started with a different letter

this approach is not valid, because the previous starting letter influences which edges we can
traverse. Just because the first word started with a particular letter we could be discarding
edges that are really cheap, and which we could be picking if we had started the path with a
different more expensive letter

The key is including the previous char in the node. Thus, the nodes/states are tuples of language
and previous char. This lets us differentiate fully between different ways to reach a node.
*/
