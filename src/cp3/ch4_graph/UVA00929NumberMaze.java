package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
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
            CustomPriorityQueue pq = new CustomPriorityQueue();
            pq.add(new Tuple(grid[0], start), grid[0]);
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
                        pq.add(new Tuple(distance[next], next), grid[next]);
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

    private static class CustomPriorityQueue {
        private static final int MAX_VALUE = 10;
        private List<Queue<Tuple>> queues;
        private int size;
        private int currentQueue;
        public CustomPriorityQueue() {
            queues = new ArrayList<Queue<Tuple>>(MAX_VALUE);
            for (int i = 0; i < MAX_VALUE; i++) {
                queues.add(new ArrayDeque<Tuple>());
            }
            size = 0;
            currentQueue = 0;
        }
        public void add(Tuple tuple, int weight) {
            int queue = (currentQueue + weight) % MAX_VALUE;
            queues.get(queue).add(tuple);
            size++;
        }
        public Tuple remove() {
            if (size == 0) return null;
            while (queues.get(currentQueue).isEmpty()) {
                currentQueue = (currentQueue + 1) % MAX_VALUE;
            }
            size--;
            return queues.get(currentQueue).remove();
        }
        public boolean isEmpty() {
            return size == 0;
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
- use custom queue, idea from uva forums
Custom queue: in this problem, the weights are at most 10. It's a small bounded
number. The custom queue uses 10 queues inside. One of is the "working queue",
from which it extracts all items when remove() is called. Whenever the working
queue is empty, we go to the next non-empty queue. The add() operation sets
the new item w queues ahead of the current working queue, with w being the
weight of the edge traversed to arrive at the item. It may take a while to
convince yourself, but this guarantees that we can remove and add with the same
semantics than a priority queue but in constant time.
*/
