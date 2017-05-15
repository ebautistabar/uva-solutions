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

// 10557 - Single Source Shortest Paths (SSSP), On Graph With Negative Weight Cycle: Bellman Ford's, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1498
// Bellman Ford, maximizing instead of minimizing, conditional relax, notes below
class UVA10557XYZZY {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int rooms = Integer.parseInt(getLine().trim());
        while (rooms != -1) {
            int[] roomEnergy = new int[rooms];
            List<List<Integer>> adjList = new ArrayList<>();
            for (int i = 0; i < rooms; i++) {
                StringTokenizer st = new StringTokenizer(getLine());
                roomEnergy[i] = Integer.parseInt(st.nextToken());
                int doors = Integer.parseInt(st.nextToken());
                List<Integer> adj = new ArrayList<>(doors);
                while (doors > 0) {
                    if (!st.hasMoreTokens()) {
                        st = new StringTokenizer(getLine());
                    }
                    while (st.hasMoreTokens()) {
                        adj.add(Integer.parseInt(st.nextToken()) - 1); // 1-based
                        doors--;
                    }
                }
                adjList.add(adj);
            }

            // Bellman Ford, maximizing instead of minimizing
            int[] power = new int[rooms];
            Arrays.fill(power, (int) -1e9); // smaller INF to prevent overflow
            power[0] = 100; // initial power
            for (int i = 0; i < rooms - 1; i++) { // v - 1 times
                for (int u = 0; u < rooms; u++) {
                    for (int j = 0; j < adjList.get(u).size(); j++) {
                        int next = adjList.get(u).get(j);
                        // Relax only if we don't lose our energy, i.e. we stay alive
                        if (power[u] + roomEnergy[next] > 0 &&
                                power[next] < power[u] + roomEnergy[next]) {
                            power[next] = power[u] + roomEnergy[next];
                        }
                    }
                }
            }

            boolean winnable = true;
            int goal = rooms - 1;
            // We reached the goal alive
            if (power[goal] > 0) {
                winnable = true;
            } else {
                // Are we stuck in a positive power cycle?
                boolean hasCycle = false;
                boolean goalIsReachable = false;
                for (int u = 0; u < rooms; u++) {
                    for (int j = 0; j < adjList.get(u).size(); j++) {
                        int next = adjList.get(u).get(j);
                        if (power[u] + roomEnergy[next] > 0 &&
                                power[next] < power[u] + roomEnergy[next]) {
                            hasCycle = true;
                            // Then we have infinite energy. Try reaching the goal
                            goalIsReachable = isGoalReachable(next, adjList);
                            break;
                        }
                    }
                    if (hasCycle) break;
                }
                winnable = goalIsReachable;
            }

            out.println(winnable ? "winnable" : "hopeless");
            rooms = Integer.parseInt(getLine().trim());
        }
        out.close();
    }

    private static boolean isGoalReachable(int start, List<List<Integer>> adjList) {
        int goal = adjList.size() - 1;
        boolean visited[] = new boolean[adjList.size()];
        visited[start] = true;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        while (!q.isEmpty()) {
            int current = q.remove();
            for (int next : adjList.get(current)) {
                if (!visited[next]) {
                    visited[next] = true;
                    if (next == goal) {
                        return true;
                    }
                    q.add(next);
                }
            }
        }
        return false;
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
We need to go from start to goal, can only go in one direction and the movements have a weight.
We want to reach the goal with weight > 0. So instead of getting the cheapest path we want the
most expensive path. In the same way as it happens when doing BellmanFord for the cheapest path,
we can get stuck in a loop. In the usual minimizing version it was a negative loop. Here it is
a positive loop. It doesn't matter in terms of our weight level. As long as it increases we can
keep playing. We simply need to detect the loop, and from one of the nodes in the loop try to
reach the goal with a regular graph traversal like BFS. Because the loop gives us infinite weight,
there's no danger in losing all the weight on the way to the goal so don't bother checking.
Also, at each step (at every relax operation) we must check that our weight is positive. It's
not enough to reach the goal with weight > 0. The weight must be positive after every movement
through the grid.
*/
