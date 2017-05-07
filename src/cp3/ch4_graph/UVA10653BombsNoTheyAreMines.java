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

// 10653 - Single-Source Shortest Paths (SSSP) On Unweighted Graph: BFS, Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1594
// Direct application of BFS on grid (implicit graph)
class UVA10653BombsNoTheyAreMines {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int[] DR = {0, 0, 1, -1};
    private static final int[] DC = {1, -1, 0, 0};

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int rows = Integer.parseInt(st.nextToken());
            int cols = Integer.parseInt(st.nextToken());
            if (rows == 0 && cols == 0) break;
            boolean[][] bombs = new boolean[rows][cols];
            int rowsWithBombs = Integer.parseInt(getLine().trim());
            for (int i = 0; i < rowsWithBombs; i++) {
                st = new StringTokenizer(getLine());
                int row = Integer.parseInt(st.nextToken());
                int colsWithBombs = Integer.parseInt(st.nextToken());
                for (int j = 0; j < colsWithBombs; j++) {
                    int col = Integer.parseInt(st.nextToken());
                    bombs[row][col] = true;
                }
            }
            st = new StringTokenizer(getLine());
            int startR = Integer.parseInt(st.nextToken());
            int startC = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(getLine());
            int goalR = Integer.parseInt(st.nextToken());
            int goalC = Integer.parseInt(st.nextToken());

            Cell start = new Cell(startR, startC);
            Cell goal = new Cell(goalR, goalC);
            int[] distances = new int[rows * cols];
            Arrays.fill(distances, Integer.MAX_VALUE);
            distances[start.toIndex(cols)] = 0;
            Queue<Cell> q = new ArrayDeque<>();
            q.add(start);
            while (!q.isEmpty()) {
                Cell current = q.remove();
                for (int i = 0; i < DR.length; i++) {
                    Cell next = new Cell(current.r + DR[i], current.c + DC[i]);
                    // if out of bounds, try next neighbor
                    if (next.r < 0 || next.r >= rows || next.c < 0 || next.c >= cols || bombs[next.r][next.c]) {
                        continue;
                    }
                    // if found the goal, exit
                    if (next.equals(goal)) {
                        distances[next.toIndex(cols)] = distances[current.toIndex(cols)] + 1;
                        break;
                    }
                    // if unvisited, enqueue
                    if (distances[next.toIndex(cols)] == Integer.MAX_VALUE) {
                        distances[next.toIndex(cols)] = distances[current.toIndex(cols)] + 1;
                        q.add(next);
                    }
                }
                // if found the goal, exit
                if (distances[goal.toIndex(cols)] != Integer.MAX_VALUE) {
                    break;
                }
            }

            out.println(distances[goal.toIndex(cols)]);

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

    private static class Cell {
        int r;
        int c;
        public Cell(int r, int c) {
            this.r = r;
            this.c = c;
        }
        public int toIndex(int cols) {
            return r * cols + c;
        }
        @Override
        public boolean equals(Object other) {
            if (other == this) return true;
            if (!(other instanceof Cell)) return false;
            Cell cell = (Cell) other;
            return r == cell.r && c == cell.c;
        }
        @Override
        public int hashCode() {
            int hash = 17;
            hash = 31 * hash + ((Integer) r).hashCode();
            hash = 31 * hash + ((Integer) c).hashCode();
            return hash;
        }
    }
}
