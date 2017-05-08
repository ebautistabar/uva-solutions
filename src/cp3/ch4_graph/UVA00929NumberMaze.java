package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 929 - Single-Source Shortest Paths (SSSP) On Weighted Graph: Dijkstra's, Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=870
// Dijkstra in 2D grid, notes below
class UVA00929NumberMaze {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int INFINITY = Integer.MAX_VALUE / 2;
    private static final int[] DELTA_R = {-1, 1, 0, 0};
    private static final int[] DELTA_C = {0, 0, 1, -1};

    // cells are represented by r * cols + c

    public static void main(String args[]) throws IOException {
        int[] distance = new int[999 * 999]; // 999 is the max size of rows and cols
        int[] grid = new int[999 * 999];
        int tests = Integer.parseInt(getLine().trim());
        for (int i = 1; i <= tests; i++) {
            int rows = Integer.parseInt(getLine().trim());
            int cols = Integer.parseInt(getLine().trim());
            for (int r = 0; r < rows; r++) {
                StringTokenizer st = new StringTokenizer(getLine());
                for (int c = 0; c < cols; c++) {
                    grid[r * cols + c] = Integer.parseInt(st.nextToken());
                }
            }

            int start = 0;
            Arrays.fill(distance, 0, rows * cols, INFINITY);
            distance[start] = grid[0];
            PriorityQueue<Tuple> pq = new PriorityQueue<>();
            pq.add(new Tuple(grid[0], start));
            while (!pq.isEmpty()) {
                Tuple current = pq.remove();
                if (distance[current.cell] < current.d); // we know shorter path already
                int r = current.cell / cols;
                int c = current.cell % cols;
                for (int j = 0; j < DELTA_R.length; j++) {
                    int nr = r + DELTA_R[j];
                    int nc = c + DELTA_C[j];
                    if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue; // out of bounds
                    int next = nr * cols + nc;
                    if (distance[next] > distance[current.cell] + grid[next]) {
                        distance[next] = distance[current.cell] + grid[next];
                        pq.add(new Tuple(distance[next], next));
                    }
                }
            }

            int goal = (rows - 1) * cols + (cols - 1);
            out.println(distance[goal]);
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
        int d;
        int cell;
        public Tuple(int d, int cell) {
            this.d = d;
            this.cell = cell;
        }
        // for the priority queue: first by distance, then by cell
        public int compareTo(Tuple o) {
            int dCmp = Integer.compare(d, o.d);
            return dCmp == 0 ? Integer.compare(cell, o.cell) : dCmp;
        }
    }
}
/*
The weights are in the nodes instead of the edges. We can think of the issue
  like this: the weights are in the edges that we traverse when entering a cell.
The only cell we don't enter is the first one, as we're already there. So we
start with weight start.weight, instead of 0 as usual.
There are no edge cases.
For speed:
- use integer like r * cols + c instead of a Cell class
- declare arrays with max size at the top: grid is overwritten naturally for
each test case when reading the input and distance is initialized explicitly
*/
