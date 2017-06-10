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

// 11487 - Special Graph (Directed Acyclic Graph), Converting General Graph to DAG, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2482
// DP, notes below
class UVA11487GatheringFood {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final char OBSTACLE = '#';
    private static final char START_FOOD = 'A';
    private static final char MAX_FOOD = 'Z';
    private static final int[] DELTA_R = {1, -1, 0, 0};
    private static final int[] DELTA_C = {0, 0, 1, -1};

    public static void main(String args[]) throws IOException {
        int test = 1;
        String line = getLine();
        while (line != null) {
            int size = parseInt(line);
            if (size == 0) break;
            int[] startRow = new int[MAX_FOOD - START_FOOD + 1];
            int[] startCol = new int[MAX_FOOD - START_FOOD + 1];
            int lastFood = START_FOOD;
            char[][] grid = new char[size][size];
            for (int i = 0; i < size; i++) {
                line = getLine();
                for (int j = 0; j < size; j++) {
                    grid[i][j] = line.charAt(j);
                    if (Character.isLetter(grid[i][j])) {
                        int id = grid[i][j] - 'A';
                        startRow[id] = i;
                        startCol[id] = j;
                        if (lastFood < grid[i][j]) {
                            lastFood = grid[i][j];
                        }
                    }
                }
            }

            int totalLen = 0;
            int totalWays = 1;
            for (char food = START_FOOD; food < lastFood; food++) {
                char startFood = food;
                char goalFood = (char)(food + 1);
                int len = getShortestPathLength(startFood, goalFood, grid, startRow, startCol);
                if (len == -1) {
                    totalLen = -1;
                    break;
                }
                int[][] memo = new int[size * size][len + 1];
                for (int i = 0; i < memo.length; i++) Arrays.fill(memo[i], -1);
                int ways = getNumberOfWays(startFood, goalFood, len, grid, memo, startRow, startCol);
                totalLen += len;
                totalWays = (totalWays * ways) % 20437;
            }

            if (totalLen == -1) {
                out.printf("Case %d: Impossible%n", test);
            } else {
                out.printf("Case %d: %d %d%n", test, totalLen, totalWays);
            }
            test++;
            line = getLine();
        }
        out.close();
    }

    private static int getNumberOfWays(char startFood, char goalFood, int len, char[][] grid, int[][] memo, int[] startRow, int[] startCol) {
        int startId = startFood - 'A';
        int goalId = startId + 1;
        return getNumberOfWays(startRow[startId], startCol[startId], len, grid, memo, startRow[goalId], startCol[goalId]);
    }

    private static int getNumberOfWays(int r, int c, int len, char[][] grid, int[][] memo, int goalR, int goalC) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid.length) return 0; // out of bounds
        if (grid[r][c] == OBSTACLE) return 0; // can't step through obstacles
        if (Character.isLetter(grid[r][c]) && grid[r][c] > grid[goalR][goalC]) return 0; // can't step over food that must be eaten later
        if (r == goalR && c == goalC) return 1; // valid base case, found the goal
        if (len == 0) return 0; // didn't reach the goal after len steps
        int cell = r * grid.length + c;
        if (memo[cell][len] == -1) {
            memo[cell][len] = 0;
            for (int i = 0; i < DELTA_R.length; i++) {
                int nr = r + DELTA_R[i];
                int nc = c + DELTA_C[i];
                memo[cell][len] += getNumberOfWays(nr, nc, len - 1, grid, memo, goalR, goalC);
            }
        }
        return memo[cell][len];
    }

    private static int getShortestPathLength(char startFood, char goalFood, char[][] grid, int[] startRow, int[] startCol) {
        int size = grid.length;
        int startId = startFood - 'A';
        int startCell = startRow[startId] * size + startCol[startId];
        int[] distance = new int[size * size];
        Arrays.fill(distance, -1);
        distance[startCell] = 0;
        Queue<Integer> pending = new ArrayDeque<>();
        pending.add(startCell);
        while (!pending.isEmpty()) {
            int current = pending.remove();
            int r = current / size;
            int c = current % size;
            for (int i = 0; i < DELTA_R.length; i++) {
                int nr = r + DELTA_R[i];
                int nc = c + DELTA_C[i];
                if (nr < 0 || nr >= size || nc < 0 || nc >= size) continue; // out of bounds
                if (grid[nr][nc] == OBSTACLE) continue; // can't step through obstacles
                if (Character.isLetter(grid[nr][nc]) && grid[nr][nc] > goalFood) continue; // can't step over food that we'll eat later
                int next = nr * size + nc;
                if (distance[next] == -1) {
                    pending.add(next);
                    distance[next] = distance[current] + 1;
                    if (grid[nr][nc] == goalFood) {
                        return distance[next];
                    }
                }
            }
        }
        return -1;
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
We can find the final answer by calculating the answer for every path between 2 consecutive items of food, i.e. path
from A to B, path from B to C, etc. The total length of the path will be the sum of the individual path, and the total
number of ways to get all the food will be the product of each individual number of ways, i.e. ways from A to B multiplied
by ways from B to C, etc.

To get the length of the shortest path between 2 points in an unweighted graph, we can use BFS. And to get the number
of ways, we can use DP. As we can move in any direction, we need more information than the actual cell, the start
and the goal. We need some variable that is decremented/incremented through each transition. That variable is the
length of the path itself. After all, we are interested in the number of ways to traverse the shortest path, not the
number of ways to traverse any path (which would be infinite if there's no other constraint).

There's something crucial that the statement doesn't explain very clearly in my opinion. When it says "Another philosophy
he follows is that if he lands on a particular food he must collect it.", it means that the bear will in fact collect
any food that he touches, so in order to ensure that he eats his food in the proper order we must ensure that he doesn't
step over any food that must be eaten in the future. That is, food for the future is like an obstacle while food already
eaten is considered just an empty cell.
*/
