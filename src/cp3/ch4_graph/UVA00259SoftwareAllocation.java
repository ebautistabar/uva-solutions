package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

// 00259 - Standard Max Flow Problem (Edmond Karp's), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=195
// Network flow, notes below
class UVA00259SoftwareAllocation {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int APPS = 26;
    private static final int COMPUTERS = 10;
    private static final int NODES = 2 + APPS + COMPUTERS; // source, sink, 10 computers and 26 apps
    private static final int SOURCE = 0;
    private static final int SINK = 1;
    private static final int APP_OFFSET = 2;
    private static final int COMPUTER_OFFSET = APP_OFFSET + APPS;
    private static final int INFINITY = (int) 1e9;

    public static void main(String args[]) throws IOException {
        String line = in.readLine();
        while (line != null) {
            int[][] residualCapacity = new int[NODES][NODES];
            for (int c = COMPUTER_OFFSET; c < NODES; c++) {
                residualCapacity[c][SINK] = 1; // only run 1 app per computer
            }
            int totalInstances = 0;
            while (line != null && !"".equals(line.trim())) {
                StringTokenizer st = new StringTokenizer(line);
                String application = st.nextToken();
                String computers = st.nextToken();
                int appCode = APP_OFFSET + (application.charAt(0) - 'A');
                int instances = application.charAt(1) - '0';
                residualCapacity[SOURCE][appCode] += instances;
                totalInstances += instances;
                for (int i = 0; i < computers.length() - 1; i++) { // - 1 to ignore the ; at the end
                    int computer = COMPUTER_OFFSET + (computers.charAt(i) - '0');
                    residualCapacity[appCode][computer] = INFINITY;
                }
                line = in.readLine();
            }

            int maxFlow = 0;
            int[] allocation = new int[COMPUTERS];
            Arrays.fill(allocation, -1);
            int[] previous = new int[NODES];
            while (hasAugmentingPath(SOURCE, SINK, residualCapacity, previous)) {
                int flow = INFINITY;
                // Find min capacity
                for (int v = SINK; v != SOURCE; v = previous[v]) {
                    flow = Math.min(flow, residualCapacity[previous[v]][v]);
                }
                // Increase flow along the whole path
                for (int v = SINK; v != SOURCE; v = previous[v]) {
                    //out.print(v + " ");
                    if (previous[v] == SOURCE) { // v is an APP
                        int computer = previous[SINK] - COMPUTER_OFFSET; // only can reach SINK from a computer
                        allocation[computer] = v;
                        //out.println(residualCapacity[previous[SINK]][v] + " " + (char)('A'+v-APP_OFFSET));
                    }
                    residualCapacity[previous[v]][v] -= flow;
                    residualCapacity[v][previous[v]] += flow;
                }
                //out.println();
                maxFlow += flow;
            }

            if (maxFlow == totalInstances) {
                for (int c = COMPUTER_OFFSET; c < NODES; c++) {
                    if (residualCapacity[SINK][c] == 0) {
                        // if there is no flow going out of this computer
                        out.print('_');
                    } else {
                        // search flow from any app to this computer
                        for (int a = APP_OFFSET; a < COMPUTER_OFFSET; a++) {
                            if (residualCapacity[c][a] == 1) {
                                char app = (char)('A' + a - APP_OFFSET);
                                out.print(app);
                                break;
                            }
                        }
                    }
                }
                out.println();
            } else {
                out.println("!");
            }
            line = in.readLine();
        }
        out.close();
    }

    private static boolean hasAugmentingPath(int start, int goal, int[][] residualCapacity, int[] previous) {
        Arrays.fill(previous, -1);
        Queue<Integer> pending = new ArrayDeque<>();
        pending.add(start);
        while (!pending.isEmpty()) {
            int current = pending.remove();
            for (int next = 0; next < residualCapacity.length; next++) {
                if (residualCapacity[current][next] > 0 && previous[next] == -1) {
                    previous[next] = current;
                    if (next == goal) return true;
                    pending.add(next);
                }
            }
        }
        return false;
    }
}
/*
Challenge is identifying and building bipartite graph. Two kind of nodes: apps and computers.
The statement gives edges between apps and computers. We add 2 nodes: source and sink. All
computers have edge to the sink, capacity 1 (as each computer can only run 1 app per day).
The source has edges to all apps, with the capacity being the number of apps of a particular
type that we must run today.
Edges between apps and computers have infinite capacity.
We have a successful allocation when all the instances of the apps are running on a computer.
In order to print the matchings, we must wait till the end. If we store the matchings during
maxflow execution, we are likely to end up with provisional matchings. Don't store anything
and wait till the end to print the result, in order to ensure definitive matchings.
*/
