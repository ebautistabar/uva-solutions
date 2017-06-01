package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

// 11380 - Max Flow Problem (Edmond Karp's), Variants, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2375
// Network flow, notes below
class UVA11380DownWentTheTitanic {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final char WATER = '~';
    private static final char WOOD = '#';
    private static final char ICEBERG = '@';
    private static final char PERSON = '*';
    private static final int INFINITY = (int) 1e9;
    private static final int[] DELTA_R = {-1, 1, 0, 0};
    private static final int[] DELTA_C = {0, 0, -1, 1};

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int rows = parseInt(st.nextToken());
            int cols = parseInt(st.nextToken());
            int people = parseInt(st.nextToken());
            Map<Integer, Integer> cellToId = new HashMap<>();
            char[][] area = new char[rows][cols];
            for (int r = 0; r < rows; r++) {
                line = getLine();
                for (int c = 0; c < cols; c++) {
                    area[r][c] = line.charAt(c);
                    if (area[r][c] != WATER) {
                        cellToId.put(r * cols + c, cellToId.size());
                    }
                }
            }

            // Build graph
            int nodes = 2 + cellToId.size() * 2;
            int source = nodes - 2;
            int sink = nodes - 1;
            int[][] residual = new int[nodes][nodes];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (area[r][c] != WATER) {
                        int id = cellToId.get(r * cols + c); // first half contains ids of original cells
                        int exitId = id + cellToId.size(); // second half contains ids resulting of splitting cells
                        if (area[r][c] == WOOD) {
                            addEdge(residual, id, exitId, INFINITY);
                            addEdge(residual, exitId, sink, people); // wood can save only so many people
                        } else if (area[r][c] == PERSON) {
                            addEdge(residual, source, id, 1); // there's only 1 person in the cell
                            addEdge(residual, id, exitId, 1);
                        } else if (area[r][c] == ICEBERG) {
                            addEdge(residual, id, exitId, INFINITY);
                        } else { // ice
                            addEdge(residual, id, exitId, 1);
                        }
                        // movements in four directions
                        for (int i = 0; i < DELTA_R.length; i++) {
                            int nr = r + DELTA_R[i];
                            int nc = c + DELTA_C[i];
                            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && area[nr][nc] != WATER) {
                                int nextId = cellToId.get(nr * cols + nc);
                                addEdge(residual, exitId, nextId, INFINITY); // allow as many people as possible between spots
                            }
                        }
                    }
                }
            }

            // Maxflow Edmond-Karp
            int maxFlow = 0;
            int[] previous = new int[nodes];
            while (hasAugmentingPath(source, sink, residual, previous)) {
                // get min flow of path
                int flow = INFINITY;
                for (int v = sink; v != source; v = previous[v]) {
                    flow = Math.min(flow, residual[previous[v]][v]);
                }
                // subtract min flow along path
                for (int v = sink; v != source; v = previous[v]) {
                    residual[previous[v]][v] -= flow;
                    residual[v][previous[v]] += flow;
                }
                // increase max flow
                maxFlow += flow;
            }

            out.println(maxFlow);

            line = getLine();
        }
        out.close();
    }

    private static boolean hasAugmentingPath(int source, int goal, int[][] residual, int[] previous) {
        Arrays.fill(previous, -1);
        Queue<Integer> pending = new ArrayDeque<>();
        pending.add(source);
        while (!pending.isEmpty()) {
            int current = pending.remove();
            for (int next = 0; next < residual[current].length; next++) {
                if (residual[current][next] > 0 && previous[next] == -1) {
                    previous[next] = current;
                    if (next == goal) return true;
                    pending.add(next);
                }
            }
        }
        return false;
    }

    private static void addEdge(int[][] residual, int from, int to, int capacity) {
        //out.printf("(%d,%d)->%d%n", from, to, capacity);
        residual[from][to] = capacity;
        residual[to][from] = 0;
    }


    private static String getLine() throws IOException {
        String line = in.readLine();
        while (line != null && "".equals(line.trim())) // dealing with blank lines
            line = in.readLine();
        return line;
    }
    private static int parseInt(String text) {
        return Integer.parseInt(text.trim());
    }
}
/*
Network flow problem with vertex capacities. The flow is people. It enters the graph from the cells marked as people
and it leaves the graph from the cells marked as wood. The flow can move between one cell and all its adjacent
non-water cells without restriction, that is, the edges can be traversed as many times as needed. But the cells
themselves cannot. The statement explains it as the cells "sinking" but we translate it to capacity. The cells that
sink after one person steps on them, have capacity 1. The cells that don't sink have infinite capacity.
The statement also mentions somewhere that only 1 person can go through 1 cell at a time. The maxflow algorithm
deals in terms of the minimum flow in each augmenting path, but we can just assume that this minimum flow is pushed
through the graph 1 people at a time. In summary we can ignore that detail.
In order to work with vertex capacities, we split the vertices in 2 with an edge between them with the original
capacity.
The edges from the sink and the source connect to the original vertices, the original vertices connect to the split
vertices and whenever there's a path between 2 adjacent cells the split vertex of one connects to the original vertex
of the other.
*/
