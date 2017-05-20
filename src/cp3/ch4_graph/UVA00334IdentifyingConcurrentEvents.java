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
import java.util.StringTokenizer;

// 00334 - All Pairs Shortest Paths, Variants, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=270
// Floyd Warshall, notes below
class UVA00334IdentifyingConcurrentEvents {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int test = 1;
        String line = getLine();
        while (line != null) {
            int computations = Integer.parseInt(line.trim());
            if (computations == 0) break;
            Map<String, Integer> nameToIdx = new HashMap<>();
            List<String> names = new ArrayList<>();
            List<List<Integer>> eventChains = new ArrayList<>();
            // Read computation chains and build mappings between event names and ids
            for (int i = 0; i < computations; i++) {
                StringTokenizer st = new StringTokenizer(getLine());
                int eventsInComputation = Integer.parseInt(st.nextToken());
                if (eventsInComputation == 0) continue;
                List<Integer> chain = new ArrayList<Integer>(eventsInComputation);
                for (int e = 0; e < eventsInComputation; e++) {
                    String event = st.nextToken();
                    if (!nameToIdx.containsKey(event)) {
                        nameToIdx.put(event, names.size());
                        names.add(event);
                    }
                    chain.add(nameToIdx.get(event));
                }
                eventChains.add(chain);
            }
            // Build adjacency matrix (we needed the number of events first)
            int events = names.size();
            boolean[][] connected = new boolean[events][events];
            // Insert computation chains in the adj. matrix
            for (int i = 0; i < eventChains.size(); i++) {
                List<Integer> computation = eventChains.get(i);
                int previous = computation.get(0);
                for (int j = 1; j < computation.size(); j++) {
                    int current = computation.get(j);
                    connected[previous][current] = true;
                    previous = current;
                }
            }
            // Insert messages in the adj. matrix
            int messages = Integer.parseInt(getLine().trim());
            for (int m = 0; m < messages; m++) {
                StringTokenizer st = new StringTokenizer(getLine());
                int from = nameToIdx.get(st.nextToken());
                int to = nameToIdx.get(st.nextToken());
                connected[from][to] = true;
            }

            // Connectivity with Warshall (transitive closure)
            for (int k = 0; k < events; k++) {
                for (int i = 0; i < events; i++) {
                    for (int j = 0; j < events; j++) {
                        connected[i][j] = connected[i][j] || (connected[i][k] && connected[k][j]);
                    }
                }
            }

            // If two events are not connected in either direction, they are concurrent
            List<Integer> concurrentEvents = new ArrayList<>();
            int concurrent = 0;
            for (int i = 0; i < events; i++) {
                for (int j = i + 1; j < events; j++) {
                    if (!connected[i][j] && ! connected[j][i]) {
                        concurrent++;
                        if (concurrent <= 2) { // Only store 2 pairs
                            concurrentEvents.add(i);
                            concurrentEvents.add(j);
                        }
                    }
                }
            }
            if (concurrent == 0) {
                out.printf("Case %d, no concurrent events.%n", test);
            } else {
                out.printf("Case %d, %d concurrent events:%n", test, concurrent);
                out.printf("(%s,%s) ", names.get(concurrentEvents.get(0)), names.get(concurrentEvents.get(1)));
                if (concurrent > 1) {
                    out.printf("(%s,%s) ", names.get(concurrentEvents.get(2)), names.get(concurrentEvents.get(3)));
                }
                out.println();
            }

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
}
/*
The chain of events of a computation is connected like a linked list. Between each computation,
there are links when events send messages to each other. If we draw the examples, we see that
2 events a, b are considered concurrent if there's no path between a and b in either direction.
It can be solved as a transitive closure problem with Warshall.
*/
